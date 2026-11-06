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
package org.eclipse.fennec.model.stream.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class and feature addressing of the stream model (requirements MOD-02, MOD-03).
 */
class StreamAddressesTest {

	private PoolModel model;
	private EPackage.Registry registry;

	@BeforeEach
	void setUp() {
		model = new PoolModel();
		registry = new EPackageRegistryImpl();
		registry.put(PoolModel.NS_URI, model.ePackage);
	}

	@Test
	void classUriIsBuiltFromTheNsUri() {
		assertThat(StreamAddresses.classUri(model.pool)).isEqualTo("https://example.org/pool#//Pool");
	}

	@Test
	void featureUriNamesTheDeclaringClass() {
		assertThat(StreamAddresses.featureUri(model.currentTemperature))
				.isEqualTo("https://example.org/pool#//Pool/currentTemperature");
		// 'name' is inherited by Pool but declared by Asset
		assertThat(StreamAddresses.featureUri(model.name)).isEqualTo("https://example.org/pool#//Asset/name");
	}

	@Test
	void classUriResolvesAgainstTheRegistry() {
		assertThat(StreamAddresses.resolveClass(registry, "https://example.org/pool#//Pool")).contains(model.pool);
	}

	@Test
	void unknownOrMalformedClassUrisResolveToEmpty() {
		assertThat(StreamAddresses.resolveClass(registry, "https://example.org/other#//Pool")).isEmpty();
		assertThat(StreamAddresses.resolveClass(registry, "https://example.org/pool#//Lake")).isEmpty();
		assertThat(StreamAddresses.resolveClass(registry, "https://example.org/pool")).isEmpty();
		assertThat(StreamAddresses.resolveClass(registry, "https://example.org/pool#//")).isEmpty();
		assertThat(StreamAddresses.resolveClass(registry, "https://example.org/pool#//Pool/name")).isEmpty();
		assertThat(StreamAddresses.resolveClass(registry, null)).isEmpty();
	}

	@Test
	void featureIdAndFeatureUriResolveToTheSameFeature() {
		int id = model.pool.getFeatureID(model.currentPh);
		String uri = StreamAddresses.featureUri(model.currentPh);

		assertThat(StreamAddresses.resolveFeature(model.pool, null, id)).contains(model.currentPh);
		assertThat(StreamAddresses.resolveFeature(model.pool, uri, StreamAddresses.UNSET_ID)).contains(model.currentPh);
	}

	@Test
	void inheritedFeaturesResolveByIdAndUri() {
		int id = model.pool.getFeatureID(model.name);
		assertThat(StreamAddresses.resolveFeature(model.pool, null, id)).contains(model.name);
		assertThat(StreamAddresses.resolveFeature(model.pool, "https://example.org/pool#//Asset/name",
				StreamAddresses.UNSET_ID)).contains(model.name);
	}

	@Test
	void theUriWinsOverTheIdAndDoesNotFallBack() {
		int phId = model.pool.getFeatureID(model.currentPh);
		String temperatureUri = StreamAddresses.featureUri(model.currentTemperature);

		assertThat(StreamAddresses.resolveFeature(model.pool, temperatureUri, phId)).contains(model.currentTemperature);
		assertThat(StreamAddresses.resolveFeature(model.pool, "https://example.org/pool#//Pool/depth", phId)).isEmpty();
	}

	@Test
	void noAddressOrAnUnknownIdResolvesToEmpty() {
		assertThat(StreamAddresses.resolveFeature(model.pool, null, StreamAddresses.UNSET_ID)).isEmpty();
		assertThat(StreamAddresses.resolveFeature(model.pool, "", StreamAddresses.UNSET_ID)).isEmpty();
		assertThat(StreamAddresses.resolveFeature(model.pool, null, 99)).isEmpty();
	}

	@Test
	void entriesAndSlotsResolveTheirFeature() {
		ChangeEntry entry = StreamFactory.eINSTANCE.createChangeEntry();
		entry.setKind(DeltaKind.SET);
		entry.setFeatureId(model.pool.getFeatureID(model.currentTemperature));
		SlotValue slot = StreamFactory.eINSTANCE.createSlotValue();
		slot.setFeatureUri(StreamAddresses.featureUri(model.currentPh));

		assertThat(StreamAddresses.resolveFeature(model.pool, entry)).contains(model.currentTemperature);
		assertThat(StreamAddresses.resolveFeature(model.pool, slot)).contains(model.currentPh);
	}

	@Test
	void anEntryWithoutItsOwnTimeUsesTheBatchTime() {
		ChangeSet changeSet = StreamFactory.eINSTANCE.createChangeSet();
		changeSet.setTimestamp(5_000L);
		ChangeEntry own = StreamFactory.eINSTANCE.createChangeEntry();
		own.setTimestamp(4_200L);
		ChangeEntry inherited = StreamFactory.eINSTANCE.createChangeEntry();
		changeSet.getEntries().add(own);
		changeSet.getEntries().add(inherited);

		assertThat(StreamAddresses.eventTime(own)).isEqualTo(4_200L);
		assertThat(StreamAddresses.eventTime(inherited)).isEqualTo(5_000L);
	}

	@Test
	void aDetachedEntryWithoutTimeHasNoEventTime() {
		ChangeEntry detached = StreamFactory.eINSTANCE.createChangeEntry();
		assertThatIllegalArgumentException().isThrownBy(() -> StreamAddresses.eventTime(detached));
	}

	@Test
	void aClassWithoutPackageHasNoAddress() {
		EClass orphan = EcoreFactory.eINSTANCE.createEClass();
		orphan.setName("Orphan");
		assertThatIllegalArgumentException().isThrownBy(() -> StreamAddresses.classUri(orphan));
	}
}
