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
package org.eclipse.fennec.stream.osgi.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
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
 * Publisher and subscriber components on Gecko Messaging's in-memory broker (#3, #9).
 */
class StreamMessagingOsgiTest {

	@InjectService(timeout = 5000)
	ConfigurationAdmin configAdmin;

	@InjectService(timeout = 5000)
	ServiceComponentRuntime scr;

	@InjectService(timeout = 5000)
	ResourceSetFactory resourceSets;

	@TempDir
	Path dir;

	private final List<Configuration> configurations = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		Path file = dir.resolve("names.xmi");
		Resource resource = resourceSets.createResourceSet().createResource(URI.createFileURI(file.toString()));
		resource.getContents().add(mapping());
		resource.save(Map.of());
		factory("FileEObjectProvider", "emf.eobject.provider.name", "mapping-files", "locations",
				new String[] { file.toString() }, "key.feature", "id");
		factory("EObjectRegistry", "name", "fennec-mappings", "initialProvider.target",
				"(emf.eobject.provider.name=mapping-files)");
		factory("org.gecko.messaging.memory", "osgi.messaging.name", "broker", "uri", "memory://stream-test", "cloudEvents",
				"binary");
	}

	@AfterEach
	void tearDown() throws Exception {
		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	@Test
	void thePublisherPutsTheCoresChangeSetsOnTheBroker(@InjectService(cardinality = 0) ServiceAware<StreamCore> cores,
			@InjectService(cardinality = 0, filter = "(osgi.messaging.name=broker)") ServiceAware<MessageSubscription> subscriptions)
			throws Exception {
		factory("FennecStreamPublisher", "publisher.target", "(osgi.messaging.name=broker)");
		// the publisher takes the core's change sets from the moment it is active, not before
		awaitActive("FennecStreamPublisher");
		MessageSubscription subscription = subscriptions.waitForService(10_000);
		List<Message> received = new CopyOnWriteArrayList<>();
		PushStream<Message> raw = subscription.subscribe("stream/ecore/EClass/+");
		raw.forEach(received::add);
		StreamCore core = cores.waitForService(10_000);
		Thread.sleep(200); // the subscription is in place before anything is published

		EClass lake = EcoreFactory.eINSTANCE.createEClass();
		lake.setName("Lake");
		core.offer(lake, 1_000, "test");

		await(() -> !received.isEmpty());
		assertThat(received.get(0).getContext().getChannel()).isEqualTo("stream/ecore/EClass/Lake");
		assertThat(received.get(0).getContext().getContentType()).isEqualTo("application/json");
		raw.close();
	}

	@Test
	void theSubscriberAppliesChangeSetsFromTheBroker(@InjectService(cardinality = 0) ServiceAware<ReplicaTargets> targetsAware,
			@InjectService(cardinality = 0, filter = "(osgi.messaging.name=broker)") ServiceAware<MessagePublisher> publishers,
			@InjectService(timeout = 5000, filter = "(fennec.stream.codec.contentType=application/json)") ChangeSetCodec codec,
			@InjectService(timeout = 5000) MetadataService metadata,
			@InjectService(cardinality = 0) ServiceAware<StreamCore> cores) throws Exception {
		factory("FennecStreamSubscriber", "subscription.target", "(osgi.messaging.name=broker)", "channel",
				"stream/ecore/#");
		ReplicaTargets targets = targetsAware.waitForService(10_000);
		MessagePublisher publisher = publishers.waitForService(10_000);
		assertThat(cores.waitForService(10_000)).isNotNull();
		awaitActive("FennecStreamSubscriber"); // it subscribes when it activates

		String fingerprint = metadata.getPackageMetadata(EcorePackage.eINSTANCE).map(PackageMetadata::getModelFingerprint)
				.orElseThrow();
		ChangeSet keyframe = keyframe("River", fingerprint);
		publisher.publish(builder -> builder.channel("stream/ecore/EClass/River").contentType(codec.contentType())
				.content(encode(codec, keyframe)).buildMessage()).getValue();

		await(() -> targets.find(EcorePackage.Literals.ECLASS, "River") != null
				&& "River".equals(((EClass) targets.find(EcorePackage.Literals.ECLASS, "River")).getName()));
		EObject copy = targets.find(EcorePackage.Literals.ECLASS, "River");
		assertThat(((EClass) copy).getName()).isEqualTo("River");
	}

	private static ByteBuffer encode(ChangeSetCodec codec, ChangeSet changeSet) {
		try {
			return codec.encode(changeSet);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
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

	private void factory(String pid, Object... keyValues) throws Exception {
		Configuration configuration = configAdmin.getFactoryConfiguration(pid, "test", "?");
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

	private void awaitActive(String component) throws InterruptedException {
		await(() -> scr.getComponentDescriptionDTOs().stream().filter(d -> component.equals(d.name))
				.flatMap(d -> scr.getComponentConfigurationDTOs(d).stream())
				.anyMatch(c -> c.state == ComponentConfigurationDTO.ACTIVE));
	}

	private static void await(BooleanSupplier condition) throws InterruptedException {
		long end = System.currentTimeMillis() + 10_000;
		while (!condition.getAsBoolean()) {
			if (System.currentTimeMillis() > end) {
				throw new AssertionError("Condition not met in time");
			}
			Thread.sleep(10);
		}
	}
}
