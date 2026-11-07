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
package org.eclipse.fennec.stream.mqtt.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.emf.osgi.ResourceSetFactory;
import org.eclipse.fennec.emf.osgi.metadata.MetadataService;
import org.eclipse.fennec.emf.osgi.model.metadata.PackageMetadata;
import org.eclipse.fennec.model.mapping.IdentityResolution;
import org.eclipse.fennec.model.mapping.MappingFactory;
import org.eclipse.fennec.model.mapping.ValueSource;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.stream.EventCore;
import org.eclipse.fennec.stream.ReplicaTargets;
import org.eclipse.fennec.stream.StreamCore;
import org.eclipse.fennec.stream.messaging.ChangeSetCodec;
import org.gecko.messaging.api.Message;
import org.gecko.messaging.api.MessagePublisher;
import org.gecko.messaging.api.MessageSubscription;
import org.gecko.messaging.api.cloudevents.CloudEventMessageContext;
import org.gecko.messaging.api.runtime.ConnectionState;
import org.gecko.messaging.api.runtime.MessageServiceRuntime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentConfigurationDTO;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.util.pushstream.PushStream;

/**
 * The stream over MQTT 5 with a real Mosquitto 2 (start it with {@code tools/brokers.sh up}). Two
 * MQTT instances share the broker: {@code origin} publishes, {@code replica} subscribes. Every test
 * uses its own channel root, so retained messages of earlier runs do not interfere. Without a broker
 * the tests skip themselves.
 */
class StreamMqttTest {

	private static final String URI_PROPERTY = "fennec.stream.mqtt.uri";
	private static final String DEFAULT_URI = "tcp://localhost:11893";

	@InjectService(timeout = 5000)
	ConfigurationAdmin configAdmin;

	@InjectService(timeout = 5000)
	ResourceSetFactory resourceSets;

	@InjectService(cardinality = 0)
	ServiceAware<MessageServiceRuntime> runtimes;

	@InjectService(timeout = 5000)
	ServiceComponentRuntime scr;

	@TempDir
	Path dir;

	private final List<Configuration> configurations = new ArrayList<>();
	private final String root = "stream-" + UUID.randomUUID();

	@BeforeEach
	void setUp() throws Exception {
		String uri = System.getProperty(URI_PROPERTY, DEFAULT_URI);
		assumeTrue(reachable(uri), "no MQTT broker at " + uri + " - start one with tools/brokers.sh up");

		Path file = dir.resolve("names.xmi");
		Resource resource = resourceSets.createResourceSet().createResource(URI.createFileURI(file.toString()));
		resource.getContents().add(mapping());
		resource.save(Map.of());
		factory("FileEObjectProvider", "emf.eobject.provider.name", "mapping-files", "locations",
				new String[] { file.toString() }, "key.feature", "id");
		factory("EObjectRegistry", "name", "fennec-mappings", "initialProvider.target",
				"(emf.eobject.provider.name=mapping-files)");
		for (String name : new String[] { "origin", "replica" }) {
			// one factory configuration per instance: the alias tells them apart
			// publishes wait for the (re)connect instead of failing (gecko#20)
			factoryAs("org.gecko.messaging.mqtt", name, "osgi.messaging.name", name, "uri", uri, "cloudEvents", "binary",
					"connectTimeout", 3000L, "publishAwaitConnection", 10_000L);
		}
		// only the subscribing side has to be on the broker before a test publishes
		awaitConnected("replica");
	}

	/** Waits until a configuration of the named component is active - its work starts only then. */
	private void awaitActive(String component) throws InterruptedException {
		await(() -> scr.getComponentDescriptionDTOs().stream().filter(d -> component.equals(d.name))
				.flatMap(d -> scr.getComponentConfigurationDTOs(d).stream())
				.anyMatch(c -> c.state == ComponentConfigurationDTO.ACTIVE));
	}

	/** Waits until the named instance reports CONNECTED, so its subscriptions are on the broker at once. */
	private void awaitConnected(String name) throws InterruptedException {
		await(() -> runtimes.getServices().stream()
				.anyMatch(r -> name.equals(r.getRuntimeDTO().name) && r.getConnectionState() == ConnectionState.CONNECTED));
	}

	@AfterEach
	void tearDown() throws Exception {
		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	@Test
	void changeSetsTravelAsCloudEventsOnTheObjectsChannel(@InjectService(cardinality = 0) ServiceAware<StreamCore> cores,
			@InjectService(cardinality = 0, filter = "(osgi.messaging.name=replica)") ServiceAware<MessageSubscription> replicas)
			throws Exception {
		factory("FennecStreamPublisher", "publisher.target", "(osgi.messaging.name=origin)", "root", root);
		awaitActive("FennecStreamPublisher");
		List<Message> received = collect(replicas.waitForService(10_000), root + "/ecore/EClass/+");
		StreamCore core = cores.waitForService(10_000);

		core.offer(eClass("Lake"), 1_000, "test");

		await(() -> !received.isEmpty());
		Message message = received.get(0);
		assertThat(message.getContext().getChannel()).isEqualTo(root + "/ecore/EClass/Lake");
		assertThat(message.getContext()).isInstanceOf(CloudEventMessageContext.class);
		assertThat(((CloudEventMessageContext) message.getContext()).getCloudEvent().getType())
				.isEqualTo("org.eclipse.fennec.stream.changeset");
	}

	@Test
	void aLateSubscriberGetsTheRetainedKeyframe(@InjectService(cardinality = 0) ServiceAware<StreamCore> cores,
			@InjectService(cardinality = 0, filter = "(osgi.messaging.name=replica)") ServiceAware<MessageSubscription> replicas,
			@InjectService(timeout = 5000, filter = "(fennec.stream.codec.contentType=application/json)") ChangeSetCodec codec)
			throws Exception {
		factory("FennecStreamPublisher", "publisher.target", "(osgi.messaging.name=origin)", "root", root,
				"retainKeyframes", Boolean.TRUE);
		awaitActive("FennecStreamPublisher");
		StreamCore core = cores.waitForService(10_000);
		core.offer(eClass("River"), 1_000, "test"); // a value, then the stream's first keyframe
		String stream = EventCore.streamId(EcorePackage.Literals.ECLASS, "River");
		await(() -> core.store().lastSequence(stream) >= 2);
		Thread.sleep(500); // published before anyone listens

		List<Message> received = collect(replicas.waitForService(10_000), root + "/ecore/EClass/#");

		await(() -> !received.isEmpty());
		ChangeSet retained = codec.decode(received.get(0).payload());
		assertThat(retained.getEntries()).extracting(ChangeEntry::getKind).contains(DeltaKind.KEYFRAME);
	}

	@Test
	void theSubscriberAppliesWhatArrivesOverMqtt(@InjectService(cardinality = 0) ServiceAware<ReplicaTargets> targetsAware,
			@InjectService(cardinality = 0, filter = "(osgi.messaging.name=origin)") ServiceAware<MessagePublisher> origins,
			@InjectService(timeout = 5000, filter = "(fennec.stream.codec.contentType=application/json)") ChangeSetCodec codec,
			@InjectService(timeout = 5000) MetadataService metadata) throws Exception {
		factory("FennecStreamSubscriber", "subscription.target", "(osgi.messaging.name=replica)", "channel",
				root + "/ecore/#");
		ReplicaTargets targets = targetsAware.waitForService(10_000);
		MessagePublisher origin = origins.waitForService(10_000);
		Thread.sleep(1_000); // the subscriber component subscribes once it is active

		String fingerprint = metadata.getPackageMetadata(EcorePackage.eINSTANCE).map(PackageMetadata::getModelFingerprint)
				.orElseThrow();
		ByteBuffer payload = codec.encode(keyframe("Sea", fingerprint));
		origin.publish(b -> b.channel(root + "/ecore/EClass/Sea").contentType(codec.contentType()).content(payload)
				.buildMessage()).getValue();

		await(() -> targets.find(EcorePackage.Literals.ECLASS, "Sea") instanceof EClass sea && "Sea".equals(sea.getName()));
	}

	private static List<Message> collect(MessageSubscription subscription, String channel) throws InterruptedException {
		List<Message> received = new CopyOnWriteArrayList<>();
		PushStream<Message> messages = subscription.subscribe(channel);
		messages.forEach(received::add);
		Thread.sleep(500); // the broker has the subscription before anything is published
		return received;
	}

	private static EClass eClass(String name) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		return eClass;
	}

	private static ChangeSet keyframe(String name, String fingerprint) {
		ChangeSet set = StreamFactory.eINSTANCE.createChangeSet();
		set.setId("cs-" + name);
		set.setStreamId(EventCore.streamId(EcorePackage.Literals.ECLASS, name));
		set.setSequence(1);
		set.setTimestamp(1_000);
		set.setContextFingerprint(fingerprint);
		set.setAuthor("origin");
		ChangeEntry entry = StreamFactory.eINSTANCE.createChangeEntry();
		entry.setObjectId(name);
		entry.setKind(DeltaKind.KEYFRAME);
		entry.setClassUri("http://www.eclipse.org/emf/2002/Ecore#//EClass");
		SlotValue slot = StreamFactory.eINSTANCE.createSlotValue();
		slot.setFeatureUri("http://www.eclipse.org/emf/2002/Ecore#//ENamedElement/name");
		slot.setValue(name);
		entry.getState().add(slot);
		set.getEntries().add(entry);
		return set;
	}

	private static StreamMapping mapping() {
		StreamMapping mapping = StreamMappingFactory.eINSTANCE.createStreamMapping();
		mapping.setId("eclass-names");
		mapping.setTarget(EcorePackage.Literals.ECLASS);
		IdentityResolution identity = MappingFactory.eINSTANCE.createIdentityResolution();
		identity.setSourceClass(EcorePackage.Literals.ECLASS);
		identity.setResolver(path());
		mapping.getIdentities().add(identity);
		StreamSlot slot = StreamMappingFactory.eINSTANCE.createStreamSlot();
		slot.setTargetFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME);
		slot.setValue(path());
		mapping.getSlots().add(slot);
		return mapping;
	}

	private static ValueSource path() {
		ValueSource source = MappingFactory.eINSTANCE.createValueSource();
		source.getFeaturePath().add(EcorePackage.Literals.ENAMED_ELEMENT__NAME);
		return source;
	}

	/** {@code tcp://host:port} answers a TCP connect. */
	private static boolean reachable(String uri) {
		String authority = uri.substring(uri.indexOf("://") + 3);
		int colon = authority.lastIndexOf(':');
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(authority.substring(0, colon), Integer.parseInt(authority.substring(colon + 1))),
					500);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void factory(String pid, Object... keyValues) throws Exception {
		factoryAs(pid, "test", keyValues);
	}

	private void factoryAs(String pid, String alias, Object... keyValues) throws Exception {
		Configuration configuration = configAdmin.getFactoryConfiguration(pid, alias, "?");
		configuration.update(dictionary(keyValues));
		configurations.add(configuration);
	}

	private static Dictionary<String, Object> dictionary(Object... keyValues) {
		Dictionary<String, Object> dictionary = new Hashtable<>();
		for (int i = 0; i < keyValues.length; i += 2) {
			dictionary.put((String) keyValues[i], keyValues[i + 1]);
		}
		return dictionary;
	}

	private static void await(BooleanSupplier condition) throws InterruptedException {
		long end = System.currentTimeMillis() + 15_000;
		while (!condition.getAsBoolean()) {
			if (System.currentTimeMillis() > end) {
				throw new AssertionError("Condition not met in time");
			}
			Thread.sleep(20);
		}
	}
}
