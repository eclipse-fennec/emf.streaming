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

import java.nio.file.Path;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.stream.EventCore;
import org.eclipse.fennec.stream.Projection;
import org.eclipse.fennec.stream.StreamCore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;

/**
 * The StreamCore service in a framework: mappings from an EObject registry, an in-memory store and no
 * projection, both configured explicitly (#3, #9).
 */
class StreamCoreOsgiTest {

	@Test
	void theCoreWritesChangeSetsOfItsMappings(@InjectService(timeout = 5000) ConfigurationAdmin configAdmin,
			@InjectService(timeout = 5000) ResourceSetFactory resourceSets,
			@InjectService(cardinality = 0) ServiceAware<StreamCore> cores, @TempDir Path dir) throws Exception {
		Path file = dir.resolve("names.xmi");
		Resource resource = resourceSets.createResourceSet().createResource(URI.createFileURI(file.toString()));
		resource.getContents().add(mapping());
		resource.save(Map.of());

		List<Configuration> configurations = List.of(
				factory(configAdmin, "FileEObjectProvider", dictionary("emf.eobject.provider.name", "mapping-files",
						"locations", new String[] { file.toString() }, "key.feature", "id")),
				factory(configAdmin, "EObjectRegistry",
						dictionary("name", "fennec-mappings", "initialProvider.target", "(emf.eobject.provider.name=mapping-files)")));
		try {
			StreamCore core = cores.waitForService(10_000);
			assertThat(core).as("the core appears without configuration: in-memory store, no projection").isNotNull();

			EClass pool = EcoreFactory.eINSTANCE.createEClass();
			pool.setName("Pool");
			core.offer(pool, 1_000, "test");
			String stream = EventCore.streamId(EcorePackage.Literals.ECLASS, "Pool");
			long deadline = System.currentTimeMillis() + 10_000;
			while (core.store().lastSequence(stream) == 0 && System.currentTimeMillis() < deadline) {
				Thread.sleep(10);
			}

			ChangeSet first = core.store().read(stream, 1, 1).get(0);
			ChangeEntry entry = first.getEntries().get(0);
			assertThat(entry.getKind()).isEqualTo(DeltaKind.SET);
			assertThat(entry.getValueNew()).isEqualTo("Pool");
			assertThat(first.getAuthor()).isEqualTo("test");
			assertThat(first.getContextFingerprint()).as("stamped by the metadata service").isNotBlank();
		} finally {
			for (Configuration configuration : configurations) {
				configuration.delete();
			}
		}
	}

	@Test
	void everyLaneThreadGetsItsOwnProjectionAndGivesItBack(@InjectBundleContext BundleContext context,
			@InjectService(timeout = 5000) ConfigurationAdmin configAdmin,
			@InjectService(timeout = 5000) ResourceSetFactory resourceSets,
			@InjectService(cardinality = 0) ServiceAware<StreamCore> cores, @TempDir Path dir) throws Exception {
		Path file = dir.resolve("names.xmi");
		Resource resource = resourceSets.createResourceSet().createResource(URI.createFileURI(file.toString()));
		resource.getContents().add(mapping());
		resource.save(Map.of());
		AtomicInteger gets = new AtomicInteger();
		AtomicInteger ungets = new AtomicInteger();
		Set<String> projected = ConcurrentHashMap.newKeySet();
		ServiceRegistration<Projection> registration = context.registerService(Projection.class,
				new PrototypeServiceFactory<Projection>() {
					@Override
					public Projection getService(Bundle bundle, ServiceRegistration<Projection> reg) {
						gets.incrementAndGet();
						return (targetClass, targetId, feature, value) -> projected.add(targetId);
					}

					@Override
					public void ungetService(Bundle bundle, ServiceRegistration<Projection> reg, Projection service) {
						ungets.incrementAndGet();
					}
				}, dictionary("fennec.stream.projection", "counting"));
		Configuration coreConfig = configAdmin.getConfiguration("FennecStreamCore", "?");
		coreConfig.update(dictionary("projection.target", "(fennec.stream.projection=counting)"));
		List<Configuration> configurations = List.of(
				factory(configAdmin, "FileEObjectProvider", dictionary("emf.eobject.provider.name", "mapping-files",
						"locations", new String[] { file.toString() }, "key.feature", "id")),
				factory(configAdmin, "EObjectRegistry",
						dictionary("name", "fennec-mappings", "initialProvider.target", "(emf.eobject.provider.name=mapping-files)")));
		try {
			StreamCore core = cores.waitForService(10_000);
			for (int i = 0; i < 20; i++) {
				EClass eClass = EcoreFactory.eINSTANCE.createEClass();
				eClass.setName("C" + i);
				core.offer(eClass, 1_000, "test");
			}
			long deadline = System.currentTimeMillis() + 10_000;
			while (projected.size() < 20 && System.currentTimeMillis() < deadline) {
				Thread.sleep(10);
			}
			assertThat(projected).hasSize(20);
			assertThat(gets.get()).as("one instance per lane thread of the default group, not per call")
					.isBetween(1, Runtime.getRuntime().availableProcessors());

			coreConfig.delete(); // the configured core stops; a core with the defaults takes its place
			deadline = System.currentTimeMillis() + 10_000;
			// DS unregisters the service before deactivate() closes the core: wait for the releases themselves
			while (ungets.get() < gets.get() && System.currentTimeMillis() < deadline) {
				Thread.sleep(10);
			}
			assertThat(ungets.get()).as("every instance is given back when the core stops").isEqualTo(gets.get());
		} finally {
			for (Configuration configuration : configurations) {
				configuration.delete();
			}
			registration.unregister();
		}
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

	private static Configuration factory(ConfigurationAdmin configAdmin, String pid, Dictionary<String, Object> properties)
			throws Exception {
		Configuration configuration = configAdmin.getFactoryConfiguration(pid, "test", "?");
		configuration.update(properties);
		return configuration;
	}

	private static Dictionary<String, Object> dictionary(Object... keyValues) {
		Dictionary<String, Object> dictionary = new Hashtable<>();
		for (int i = 0; i < keyValues.length; i += 2) {
			dictionary.put((String) keyValues[i], keyValues[i + 1]);
		}
		return dictionary;
	}
}
