/********************************************************************
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Data In Motion Consulting - initial implementation
 ********************************************************************/
package org.eclipse.fennec.stream.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.mapping.ConverterRegistry;
import org.eclipse.fennec.mapping.FunctionRegistry;
import org.eclipse.fennec.mapping.IdentityResolver;
import org.eclipse.fennec.mapping.InMemoryMappingRegistry;
import org.eclipse.fennec.mapping.Mapper;
import org.eclipse.fennec.mapping.ValueExtractor;
import org.eclipse.fennec.model.mapping.IdentityResolution;
import org.eclipse.fennec.model.mapping.MappingFactory;
import org.eclipse.fennec.model.mapping.ValueSource;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.stream.EventCore;
import org.eclipse.fennec.stream.InMemoryStreamStore;
import org.eclipse.fennec.stream.ObjectProjection;
import org.eclipse.fennec.stream.ReplicaTargets;
import org.eclipse.fennec.stream.StreamReceiver;
import org.gecko.messaging.api.Message;
import org.gecko.messaging.api.MessageContext;
import org.gecko.messaging.api.MessageContextBuilder;
import org.gecko.messaging.api.MessagePublisher;
import org.gecko.messaging.api.MessageSubscription;
import org.gecko.messaging.api.MessagingConstants;
import org.gecko.messaging.api.cloudevents.CloudEvent;
import org.gecko.messaging.api.cloudevents.CloudEventMessageContext;
import org.gecko.messaging.api.runtime.ConnectionState;
import org.gecko.messaging.core.MessagingConfiguration;
import org.gecko.messaging.core.memory.InMemoryBroker;
import org.gecko.messaging.core.memory.InMemoryInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.osgi.util.promise.Promise;
import org.osgi.util.pushstream.PushStream;

/**
 * The stream over Gecko Messaging's in-memory broker: origin core → publisher → broker → subscriber →
 * receiver → replica (requirements TRN-01..06, UC-12 first contact, UC-29).
 */
class StreamMessagingTest {

	private final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
	private final EClass pool = eClass("Pool");
	private final EAttribute poolId = id(pool);
	private final EAttribute currentTemperature = attribute(pool, "currentTemperature", EcorePackage.Literals.EDOUBLE);
	private final EClass sensor = eClass("TempSensor");
	private final EAttribute sensorId = id(sensor);
	private final EAttribute sensorPoolId = attribute(sensor, "poolId", EcorePackage.Literals.ESTRING);
	private final EAttribute sensorValue = attribute(sensor, "value", EcorePackage.Literals.EDOUBLE);

	private final List<InMemoryInstance> instances = new ArrayList<>();
	private final List<AutoCloseable> closeables = new ArrayList<>();
	private final XmiChangeSetCodec codec = new XmiChangeSetCodec();
	private final StreamChannels channels = new StreamChannels();
	private EPackage.Registry models;
	private String broker;

	@BeforeEach
	void setUp() {
		ePackage.setName("pool");
		ePackage.setNsPrefix("pool");
		ePackage.setNsURI("https://example.org/pool");
		models = new EPackageRegistryImpl();
		models.put(ePackage.getNsURI(), ePackage);
		broker = "stream-" + UUID.randomUUID();
	}

	@AfterEach
	void tearDown() throws Exception {
		for (AutoCloseable closeable : closeables) {
			closeable.close();
		}
		instances.forEach(InMemoryInstance::stop);
		InMemoryBroker.remove(broker);
	}

	@ParameterizedTest
	@CsvSource({ "binary", "structured" })
	void aReplicaFollowsItsOriginOverTheBroker(String cloudEvents) throws Exception {
		InMemoryInstance originSide = start(cloudEvents);
		InMemoryInstance replicaSide = start(cloudEvents);

		EventCore replica = core(new HashMap<>());
		ReplicaTargets targets = ReplicaTargets.inMemory();
		StreamReceiver receiver = StreamReceiver.builder().core(replica).targets(targets)
				.modelVersions(fp -> ePackage.getNsURI().equals(fp) ? models : null).build();
		StreamSubscriber subscriber = new StreamSubscriber(subscription(replicaSide), codec, receiver);
		closeables.add(subscriber.subscribe(channels.packageChannel(ePackage) + "/#"));
		await(() -> replicaSide.getRuntimeDTO().subscriptions.length == 1);

		Map<String, EObject> pools = new HashMap<>();
		EObject original = EcoreUtil.create(pool);
		original.eSet(poolId, "pool/1");
		pools.put("pool/1", original);
		EventCore origin = core(pools);
		closeables.add(new StreamPublisher(publisher(originSide), codec, channels, models).attach(origin));

		origin.offer(sensor("t1", "pool/1", 24.5), 1_000, null);
		origin.offer(sensor("t1", "pool/1", 25.5), 2_000, null);

		await(() -> targets.find(pool, "pool/1") != null
				&& Double.valueOf(25.5).equals(targets.find(pool, "pool/1").eGet(currentTemperature)));
		EObject copy = targets.find(pool, "pool/1");
		assertThat(copy.eGet(poolId)).isEqualTo("pool/1");
		assertThat(receiver.state(EventCore.streamId(pool, "pool/1"))).isEqualTo(StreamReceiver.State.COMPLETE);
		assertThat(subscriber.dropped()).isZero();
	}

	@Test
	void aClientWithoutFennecSeesTheCloudEventAndTheChannel() throws Exception {
		InMemoryInstance originSide = start("binary");
		InMemoryInstance foreign = start("binary");
		List<Message> received = new CopyOnWriteArrayList<>();
		PushStream<Message> raw = foreign.subscribe("stream/pool/Pool/+");
		raw.forEach(received::add);
		closeables.add(raw::close);
		await(() -> foreign.getRuntimeDTO().subscriptions.length == 1);

		EventCore origin = core(new HashMap<>());
		closeables.add(new StreamPublisher(publisher(originSide), codec, channels, models).attach(origin));
		origin.offer(sensor("t1", "pool-1", 24.5), 1_000, null);

		await(() -> !received.isEmpty());
		Message message = received.get(0);
		assertThat(message.getContext().getChannel()).isEqualTo("stream/pool/Pool/pool-1");
		assertThat(message.getContext().getContentType()).isEqualTo(XmiChangeSetCodec.CONTENT_TYPE);
		assertThat(message.getContext()).isInstanceOf(CloudEventMessageContext.class);
		CloudEvent event = ((CloudEventMessageContext) message.getContext()).getCloudEvent();
		assertThat(event.getType()).isEqualTo(StreamPublisher.CLOUD_EVENT_TYPE);
		assertThat(event.getSource()).isEqualTo("https://example.org/pool#//Pool/pool-1");
		assertThat(event.getSubject()).isEqualTo("pool-1");
		assertThat(event.getDataSchema()).isEqualTo("https://example.org/pool");
		assertThat(String.valueOf(event.getExtensions().get(StreamPublisher.SEQUENCE_EXTENSION))).isEqualTo("1");
	}

	@Test
	void untrustedPayloadsAreDroppedNotApplied() throws Exception {
		InMemoryInstance side = start("binary");
		EventCore replica = core(new HashMap<>());
		StreamReceiver receiver = StreamReceiver.builder().core(replica)
				.modelVersions(fp -> ePackage.getNsURI().equals(fp) ? models : null).build();
		StreamSubscriber subscriber = new StreamSubscriber(subscription(side), codec, receiver, 64);
		closeables.add(subscriber.subscribe("stream/#"));
		await(() -> side.getRuntimeDTO().subscriptions.length == 1);

		side.publish(side.newBuilder().channel("stream/pool/Pool/x").content(bytes("not xmi")).buildMessage(), null).getValue();
		side.publish(side.newBuilder().channel("stream/pool/Pool/x").content(bytes("x".repeat(100))).buildMessage(), null)
				.getValue();

		await(() -> subscriber.dropped() == 2);
	}

	@Test
	void theXmiCodecRoundTripsAChangeSet() throws Exception {
		EventCore origin = core(new HashMap<>());
		List<ChangeSet> written = new CopyOnWriteArrayList<>();
		PushStream<ChangeSet> changes = origin.changes();
		changes.forEach(written::add);
		closeables.add(changes::close);
		origin.offer(sensor("t1", "pool-1", 24.5), 1_000, "device:t1");
		await(() -> written.size() == 2);

		ChangeSet original = written.get(0);
		ChangeSet copy = codec.decode(codec.encode(original));

		assertThat(EcoreUtil.equals(original, copy)).isTrue();
		assertThat(original.eResource()).isNull();
	}

	@Test
	void objectIdsStayOneChannelSegment() {
		assertThat(channels.channel(pool, "a/b+c#d")).isEqualTo("stream/pool/Pool/a%2Fb%2Bc%23d");
		assertThat(StreamChannels.objectIdOf("stream/pool/Pool/a%2Fb%2Bc%23d")).isEqualTo("a/b+c#d");
		assertThat(new StreamChannels("tenant-a").classChannel(pool)).isEqualTo("tenant-a/pool/Pool");
	}

	private EventCore core(Map<String, EObject> pools) {
		ValueExtractor extractor = new ValueExtractor(new FunctionRegistry(), new ConverterRegistry(), null);
		InMemoryMappingRegistry registry = new InMemoryMappingRegistry();
		registry.register(mapping());
		EventCore core = EventCore.builder().mappings(registry).mapper(new Mapper(extractor, new IdentityResolver(extractor)))
				.store(new InMemoryStreamStore()).projection(new ObjectProjection((c, id) -> pools.get(id)))
				.group(new EventCore.ExecutionGroup("default", 2, 16)).build();
		closeables.add(core);
		return core;
	}

	private StreamMapping mapping() {
		StreamMapping mapping = StreamMappingFactory.eINSTANCE.createStreamMapping();
		mapping.setId("pool-temperature");
		mapping.setTarget(pool);
		IdentityResolution identity = MappingFactory.eINSTANCE.createIdentityResolution();
		identity.setSourceClass(sensor);
		identity.setResolver(path(sensorPoolId));
		mapping.getIdentities().add(identity);
		StreamSlot slot = StreamMappingFactory.eINSTANCE.createStreamSlot();
		slot.setTargetFeature(currentTemperature);
		slot.setSourceClass(sensor);
		slot.setValue(path(sensorValue));
		mapping.getSlots().add(slot);
		return mapping;
	}

	private EObject sensor(String id, String poolKey, double value) {
		EObject object = EcoreUtil.create(sensor);
		object.eSet(sensorId, id);
		object.eSet(sensorPoolId, poolKey);
		object.eSet(sensorValue, value);
		return object;
	}

	private InMemoryInstance start(String cloudEvents) throws Exception {
		Map<String, Object> props = new HashMap<>();
		props.put(MessagingConstants.MESSAGING_NAME_PROPERTY, "i" + instances.size());
		props.put("uri", "memory://" + broker);
		props.put("cloudEvents", cloudEvents);
		InMemoryInstance instance = new InMemoryInstance(new MessagingConfiguration(props));
		instances.add(instance);
		instance.startStandalone();
		await(() -> instance.getConnectionState() == ConnectionState.CONNECTED);
		return instance;
	}

	/** The instance as the API service it registers in OSGi. */
	private static MessagePublisher publisher(InMemoryInstance instance) {
		return new MessagePublisher() {
			@Override
			public Promise<Void> publish(Message message) {
				return instance.publish(message, null);
			}

			@Override
			public Promise<Void> publish(Message message, String channel) {
				return instance.publish(message, instance.newBuilder().channel(channel).buildContext());
			}

			@Override
			public Promise<Void> publish(Message message, MessageContext context) {
				return instance.publish(message, context);
			}

			@Override
			public Promise<Void> publish(Function<MessageContextBuilder, Message> builderFunction) {
				return instance.publish(builderFunction.apply(instance.newBuilder()), null);
			}

			@Override
			public Promise<Long> publish(PushStream<? extends Message> messages) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Promise<Long> publish(PushStream<? extends Message> messages, MessageContext context) {
				throw new UnsupportedOperationException();
			}
		};
	}

	private static MessageSubscription subscription(InMemoryInstance instance) {
		return new MessageSubscription() {
			@Override
			public PushStream<Message> subscribe(String channel) {
				return instance.subscribe(channel);
			}

			@Override
			public PushStream<Message> subscribe(MessageContext context) {
				return instance.subscribe(context);
			}
		};
	}

	private static ValueSource path(EAttribute feature) {
		ValueSource source = MappingFactory.eINSTANCE.createValueSource();
		source.getFeaturePath().add(feature);
		return source;
	}

	private static ByteBuffer bytes(String text) {
		return ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8));
	}

	private static void await(BooleanSupplier condition) throws InterruptedException {
		long end = System.currentTimeMillis() + 10_000;
		while (!condition.getAsBoolean()) {
			if (System.currentTimeMillis() > end) {
				throw new AssertionError("Condition not met in time");
			}
			Thread.sleep(5);
		}
	}

	private EClass eClass(String name) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		ePackage.getEClassifiers().add(eClass);
		return eClass;
	}

	private static EAttribute id(EClass owner) {
		EAttribute id = attribute(owner, "id", EcorePackage.Literals.ESTRING);
		id.setID(true);
		return id;
	}

	private static EAttribute attribute(EClass owner, String name, EClassifier type) {
		EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(name);
		attribute.setEType(type);
		owner.getEStructuralFeatures().add(attribute);
		return attribute;
	}
}
