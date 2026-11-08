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
package org.eclipse.fennec.stream.sta.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fennec.model.mapping.MappingFactory;
import org.eclipse.fennec.model.mapping.ValueSource;
import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.StreamSink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;

/**
 * The STA projection writes things, datastreams and observations into a JPA unit (H2) that maps the
 * STA model (#52).
 */
class StaSinkOsgiTest {

	private static final String STA_NS_URI = "https://eclipse.org/fennec/sta/1.1";

	@InjectService(timeout = 5000)
	ConfigurationAdmin configAdmin;

	@InjectService(cardinality = 0, filter = "(component.name=FennecStaSink)")
	ServiceAware<StreamSink> sinks;

	@InjectService(cardinality = 0, filter = "(persistence.repository.id=sta)")
	ServiceAware<Repository> repositories;

	private final List<Configuration> configurations = new ArrayList<>();
	private final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
	private final EClass pool = EcoreFactory.eINSTANCE.createEClass();
	private final EAttribute temperature = EcoreFactory.eINSTANCE.createEAttribute();

	@BeforeEach
	void setUp() throws Exception {
		ePackage.setName("pool");
		ePackage.setNsPrefix("pool");
		ePackage.setNsURI("https://example.org/pool");
		pool.setName("Pool");
		ePackage.getEClassifiers().add(pool);
		temperature.setName("temperature");
		temperature.setEType(EcorePackage.Literals.EDOUBLE);
		pool.getEStructuralFeatures().add(temperature);

		Path database = Files.createTempDirectory("fennec-sta").resolve("sta-" + UUID.randomUUID());
		factory("daanse.jdbc.datasource.h2.DataSource", "identifier", database.toString());
		factory("fennec.jpa.EORMMappingService", "fennec.jpa.eorm.mappingName", "sta", "fennec.jpa.eorm.model.target",
				"(emf.nsURI=" + STA_NS_URI + ")");
		factory("fennec.jpa.EMPersistenceUnit", "fennec.jpa.persistenceUnitName", "sta", "fennec.jpa.mapping.target",
				"(fennec.jpa.eorm.mapping=sta)", "fennec.jpa.dataSource.target", "(subprotocol=*)",
				"fennec.jpa.ext.eclipselink.ddl-generation", "create-tables",
				"fennec.jpa.ext.eclipselink.cache.shared.default", "false");
		factory("fennec.repository.jpa", "repositoryId", "sta", "unit.target", "(osgi.unit.name=sta)");
		single("FennecStaSink", "repository.target", "(persistence.repository.id=sta)");
	}

	@AfterEach
	void cleanUp() throws Exception {
		for (Configuration configuration : configurations) {
			configuration.delete();
		}
		// the next test must not get this test's sink or repository while they go away
		long deadline = System.currentTimeMillis() + 20_000;
		while ((!sinks.isEmpty() || !repositories.isEmpty()) && System.currentTimeMillis() < deadline) {
			Thread.sleep(20);
		}
		assertThat(sinks.isEmpty() && repositories.isEmpty()).as("sink and repository are gone").isTrue();
	}

	@Test
	void observationsLandInTheRepositoryWithTheirDatastreamAndThing() throws Exception {
		StreamSink sink = sinks.waitForService(20_000);
		assertThat(sink).as("the STA sink appears once its repository is there").isNotNull();
		StreamMapping mapping = mapping();

		sink.written(mapping, changeSet("cs-1", 1, set("27.5", 1_000L)));
		sink.written(mapping, changeSet("cs-2", 2, set("28.0", 2_000L)));

		Repository repository = repositories.waitForService(5_000);
		assertThat(repository.count(StaPackage.Literals.OBSERVATION)).isEqualTo(2);
		assertThat(repository.count(StaPackage.Literals.DATASTREAM)).isEqualTo(1);
		assertThat(repository.count(StaPackage.Literals.THING)).isEqualTo(1);
		Datastream datastream = (Datastream) repository.getEObject(StaPackage.Literals.DATASTREAM, "Pool:kinderland.temperature");
		assertThat(datastream.getThing().getId()).isEqualTo("Pool:kinderland");
		assertThat(datastream.getObservedProperty().getDefinition()).isEqualTo("https://example.org/pool#//Pool/temperature");
		assertThat(datastream.getUnitOfMeasurement()).isNotNull();
		Observation observation = (Observation) repository.getEObject(StaPackage.Literals.OBSERVATION, "cs-2:0");
		assertThat(observation.getResultNumber()).isEqualTo(28.0);
		assertThat(observation.getDatastream().getId()).isEqualTo(datastream.getId());
		assertThat(observation.getFeatureOfInterest().getId()).isEqualTo("Pool:kinderland");
	}

	@Test
	void lanesOfDifferentTargetsCreateTheSharedEntitiesOnce() throws Exception {
		StreamSink sink = sinks.waitForService(20_000);
		assertThat(sink).isNotNull();
		StreamMapping mapping = mapping();
		int pools = 8;
		ExecutorService lanes = Executors.newFixedThreadPool(pools);
		List<Future<?>> written = new ArrayList<>();
		for (int i = 0; i < pools; i++) {
			String pool = "pool-" + i;
			written.add(lanes.submit(() -> sink.written(mapping, changeSet("cs-" + pool, 1, set(pool, "27.5", 1_000L)))));
		}
		for (Future<?> future : written) {
			future.get(30, TimeUnit.SECONDS);
		}
		lanes.shutdown();

		Repository repository = repositories.waitForService(5_000);
		assertThat(repository.count(StaPackage.Literals.THING)).isEqualTo(pools);
		assertThat(repository.count(StaPackage.Literals.OBSERVED_PROPERTY)).isEqualTo(1);
		assertThat(repository.count(StaPackage.Literals.SENSOR)).isEqualTo(1);
		assertThat(repository.count(StaPackage.Literals.OBSERVATION)).isEqualTo(pools);
	}

	private StreamMapping mapping() {
		StreamMapping mapping = StreamMappingFactory.eINSTANCE.createStreamMapping();
		mapping.setId("pool-sta");
		mapping.setTarget(pool);
		StreamSlot slot = StreamMappingFactory.eINSTANCE.createStreamSlot();
		slot.setTargetFeature(temperature);
		ValueSource value = MappingFactory.eINSTANCE.createValueSource();
		value.getFeaturePath().add(temperature);
		slot.setValue(value);
		mapping.getSlots().add(slot);
		return mapping;
	}

	private static ChangeSet changeSet(String id, long sequence, ChangeEntry... entries) {
		ChangeSet changeSet = StreamFactory.eINSTANCE.createChangeSet();
		changeSet.setId(id);
		changeSet.setStreamId("pool");
		changeSet.setSequence(sequence);
		changeSet.setTimestamp(sequence * 1_000);
		changeSet.setAuthor("test");
		changeSet.getEntries().addAll(List.of(entries));
		return changeSet;
	}

	private static ChangeEntry set(String value, Long time) {
		return set("kinderland", value, time);
	}

	private static ChangeEntry set(String pool, String value, Long time) {
		ChangeEntry entry = StreamFactory.eINSTANCE.createChangeEntry();
		entry.setKind(DeltaKind.SET);
		entry.setObjectId(pool);
		entry.setFeatureUri("https://example.org/pool#//Pool/temperature");
		entry.setValueNew(value);
		entry.setTimestamp(time);
		return entry;
	}

	private void factory(String pid, Object... keyValues) throws Exception {
		Configuration configuration = configAdmin.getFactoryConfiguration(pid, "sta", "?");
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
