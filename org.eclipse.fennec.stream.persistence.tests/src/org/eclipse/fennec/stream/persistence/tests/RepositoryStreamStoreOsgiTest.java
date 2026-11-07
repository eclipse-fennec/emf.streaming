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
package org.eclipse.fennec.stream.persistence.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.emf.osgi.ResourceSetFactory;
import org.eclipse.fennec.model.mapping.IdentityResolution;
import org.eclipse.fennec.model.mapping.MappingFactory;
import org.eclipse.fennec.model.mapping.ValueSource;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.stream.EventCore;
import org.eclipse.fennec.stream.StreamCore;
import org.eclipse.fennec.stream.StreamStore;
import org.eclipse.fennec.stream.StreamStore.RetentionOutcome;
import org.eclipse.fennec.stream.StreamStore.StoredValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;

/**
 * The repository stream store on JPA (EclipseLink, H2): change sets are saved as ordinary models of
 * the stream model and read back by stream and sequence.
 */
class RepositoryStreamStoreOsgiTest {

	private static final String STREAM_NS_URI = "https://org.eclipse/fennec/stream/1.0.0";
	private static final StreamFactory STREAM = StreamFactory.eINSTANCE;

	@InjectService(timeout = 5000)
	ConfigurationAdmin configAdmin;

	@InjectService(cardinality = 0, filter = "(fennec.stream.store=repository)")
	ServiceAware<StreamStore> stores;

	private final List<Configuration> configurations = new ArrayList<>();

	@BeforeEach
	void repository() throws Exception {
		Path database = Files.createTempDirectory("fennec-streams").resolve("streams-" + UUID.randomUUID());
		factory("daanse.jdbc.datasource.h2.DataSource", "identifier", database.toString());
		factory("fennec.jpa.EORMMappingService", "fennec.jpa.eorm.mappingName", "streams", "fennec.jpa.eorm.model.target",
				"(emf.nsURI=" + STREAM_NS_URI + ")");
		factory("fennec.jpa.EMPersistenceUnit", "fennec.jpa.persistenceUnitName", "streams", "fennec.jpa.mapping.target",
				"(fennec.jpa.eorm.mapping=streams)", "fennec.jpa.dataSource.target", "(subprotocol=*)",
				"fennec.jpa.ext.eclipselink.ddl-generation", "create-tables",
				// every read goes to the database, so the test sees what was stored
				"fennec.jpa.ext.eclipselink.cache.shared.default", "false");
		factory("fennec.repository.jpa", "repositoryId", "streams", "unit.target", "(osgi.unit.name=streams)");
		single("FennecRepositoryStreamStore", "repository.target", "(persistence.repository.id=streams)");
	}

	@AfterEach
	void cleanUp() throws Exception {
		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	@Test
	void changeSetsAreStoredAndReadBackByStreamAndSequence() throws Exception {
		StreamStore store = stores.waitForService(20_000);
		assertThat(store).as("the repository store appears once its repository is there").isNotNull();
		String pool = "https://eclipse.org/bath#Pool/wellenbecken";
		String other = "https://eclipse.org/bath#Pool/sportbecken";
		String temperature = "https://eclipse.org/bath#//Pool/waterTemperature";
		String ph = "https://eclipse.org/bath#//Pool/ph";

		store.append(changeSet(pool, 1, 1_000, set(temperature, "27.0", 1_000L)));
		store.append(changeSet(pool, 2, 2_000, keyframe(2_000L, slot(temperature, "27.5"), slot(ph, "7.2"))));
		store.append(changeSet(pool, 3, 3_000, set(temperature, "28.1", 3_000L)));
		store.append(changeSet(other, 1, 1_500, set(temperature, "24.0", 1_500L)));

		assertThat(store.lastSequence(pool)).isEqualTo(3);
		assertThat(store.lastSequence(other)).isEqualTo(1);
		assertThat(store.lastSequence("unknown")).isZero();

		List<ChangeSet> all = store.read(pool, 1, 3);
		assertThat(all).extracting(ChangeSet::getSequence).containsExactly(1L, 2L, 3L);
		assertThat(all).extracting(ChangeSet::getAuthor).containsOnly("test");
		ChangeEntry keyframe = all.get(1).getEntries().get(0);
		assertThat(keyframe.getKind()).isEqualTo(DeltaKind.KEYFRAME);
		assertThat(keyframe.getState()).extracting(SlotValue::getFeatureUri, SlotValue::getValue)
				.containsExactlyInAnyOrder(tuple(temperature, "27.5"),
						tuple(ph, "7.2"));
		assertThat(store.read(pool, 2, 2)).extracting(ChangeSet::getSequence).containsExactly(2L);
		assertThat(store.read(pool, 3, 1)).isEmpty();

		assertThat(store.lastValue(pool, temperature)).contains(new StoredValue("28.1", 3_000L));
		assertThat(store.lastValue(pool, ph)).as("from the keyframe").contains(new StoredValue("7.2", 2_000L));
		assertThat(store.lastValue(other, ph)).isEmpty();
	}

	@Test
	@Disabled("Blocked by emf.persistence-jpa#403: deleting a change set fails on the synthetic PK of its entries (#42)")
	void retentionRemovesTheEntriesOfASlotBeyondCountAndHorizon() throws Exception {
		StreamStore store = stores.waitForService(20_000);
		assertThat(store).isNotNull();
		String pool = "https://eclipse.org/bath#Pool/kinderland";
		String temperature = "https://eclipse.org/bath#//Pool/waterTemperature";
		String ph = "https://eclipse.org/bath#//Pool/ph";
		store.append(changeSet(pool, 1, 1_000, set(temperature, "27.0", 1_000L)));
		store.append(changeSet(pool, 2, 2_000, set(temperature, "27.5", 2_000L), set(ph, "7.1", 2_000L)));
		store.append(changeSet(pool, 3, 3_000, keyframe(3_000L, slot(temperature, "27.5"), slot(ph, "7.1"))));
		store.append(changeSet(pool, 4, 4_000, set(temperature, "28.0", 4_000L)));
		store.append(changeSet(pool, 5, 5_000, set(temperature, "28.5", 5_000L)));

		DeletionRule keepTwo = StreamMappingFactory.eINSTANCE.createDeletionRule();
		keepTwo.setMaxCount(2);
		assertThat(store.retain(pool, temperature, keepTwo, 6_000)).isEqualTo(RetentionOutcome.APPLIED);

		List<ChangeSet> kept = store.read(pool, 1, 5);
		assertThat(kept).as("the set holding only the dropped value goes").extracting(ChangeSet::getSequence)
				.containsExactly(2L, 3L, 4L, 5L);
		assertThat(kept.get(0).getEntries()).extracting(ChangeEntry::getFeatureUri).containsExactly(ph);
		assertThat(kept.get(1).getEntries()).as("keyframes stay").singleElement()
				.satisfies(e -> assertThat(e.getKind()).isEqualTo(DeltaKind.KEYFRAME));
		assertThat(store.lastValue(pool, temperature)).contains(new StoredValue("28.5", 5_000L));

		DeletionRule horizon = StreamMappingFactory.eINSTANCE.createDeletionRule();
		horizon.setRetention(1);
		horizon.setRetentionUnit(DurationUnit.SECONDS);
		store.retain(pool, temperature, horizon, 6_000);

		assertThat(store.read(pool, 1, 5)).extracting(ChangeSet::getSequence).containsExactly(2L, 3L, 5L);
	}

	@Test
	void theCoreWritesIntoTheRepositoryStoreWhenSelected(@InjectService(timeout = 5000) ResourceSetFactory resourceSets,
			@InjectService(cardinality = 0) ServiceAware<StreamCore> cores) throws Exception {
		assertThat(stores.waitForService(20_000)).isNotNull();
		Path file = Files.createTempDirectory("fennec-mappings").resolve("names.xmi");
		Resource resource = resourceSets.createResourceSet().createResource(URI.createFileURI(file.toString()));
		resource.getContents().add(mapping());
		resource.save(Map.of());
		factory("FileEObjectProvider", "emf.eobject.provider.name", "mapping-files", "locations",
				new String[] { file.toString() }, "key.feature", "id");
		factory("EObjectRegistry", "name", "fennec-mappings", "initialProvider.target",
				"(emf.eobject.provider.name=mapping-files)");
		single("FennecStreamCore", "store.target", "(fennec.stream.store=repository)");

		String stream = EventCore.streamId(EcorePackage.Literals.ECLASS, "Lake");
		StreamCore core = awaitCoreOnRepositoryStore(cores, stream);
		EClass lake = EcoreFactory.eINSTANCE.createEClass();
		lake.setName("Lake");
		core.offer(lake, 1_000, "test");

		StreamStore store = stores.getService();
		long deadline = System.currentTimeMillis() + 10_000;
		while (store.lastSequence(stream) == 0 && System.currentTimeMillis() < deadline) {
			Thread.sleep(20);
		}
		assertThat(store.read(stream, 1, store.lastSequence(stream))).isNotEmpty()
				.allSatisfy(changeSet -> assertThat(changeSet.getStreamId()).isEqualTo(stream));
	}

	/** Waits for the core configured on the repository store (a default core may come first). */
	private StreamCore awaitCoreOnRepositoryStore(ServiceAware<StreamCore> cores, String stream) throws Exception {
		long deadline = System.currentTimeMillis() + 20_000;
		while (System.currentTimeMillis() < deadline) {
			StreamCore core = cores.getService();
			if (core != null && core.store().getClass().getName().contains("Repository")) {
				return core;
			}
			Thread.sleep(20);
		}
		throw new AssertionError("no core on the repository store");
	}

	private static ChangeSet changeSet(String streamId, long sequence, long time, ChangeEntry... entries) {
		ChangeSet changeSet = STREAM.createChangeSet();
		changeSet.setId(streamId + "@" + sequence);
		changeSet.setStreamId(streamId);
		changeSet.setSequence(sequence);
		changeSet.setTimestamp(time);
		changeSet.setCommitTime(time + 5);
		changeSet.setContextFingerprint("fp");
		changeSet.setAuthor("test");
		changeSet.getEntries().addAll(List.of(entries));
		return changeSet;
	}

	private static ChangeEntry set(String featureUri, String value, Long time) {
		ChangeEntry entry = STREAM.createChangeEntry();
		entry.setKind(DeltaKind.SET);
		entry.setObjectId("o");
		entry.setFeatureUri(featureUri);
		entry.setValueNew(value);
		entry.setTimestamp(time);
		return entry;
	}

	private static ChangeEntry keyframe(Long time, SlotValue... slots) {
		ChangeEntry entry = STREAM.createChangeEntry();
		entry.setKind(DeltaKind.KEYFRAME);
		entry.setObjectId("o");
		entry.setTimestamp(time);
		entry.getState().addAll(List.of(slots));
		return entry;
	}

	private static SlotValue slot(String featureUri, String value) {
		SlotValue slot = STREAM.createSlotValue();
		slot.setFeatureUri(featureUri);
		slot.setValue(value);
		return slot;
	}

	/** EClass names stream into ENamedElement.name; an EClass finds its stream by its name. */
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
		Configuration configuration = configAdmin.getFactoryConfiguration(pid, "streams", "?");
		configuration.update(dictionary(keyValues));
		configurations.add(configuration);
	}

	private void single(String pid, Object... keyValues) throws Exception {
		Configuration configuration = configAdmin.getConfiguration(pid, "?");
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
}
