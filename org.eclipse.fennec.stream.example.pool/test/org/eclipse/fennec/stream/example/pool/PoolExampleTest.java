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
package org.eclipse.fennec.stream.example.pool;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.mapping.ConverterRegistry;
import org.eclipse.fennec.mapping.FunctionRegistry;
import org.eclipse.fennec.mapping.IdentityResolver;
import org.eclipse.fennec.mapping.InMemoryMappingRegistry;
import org.eclipse.fennec.mapping.Mapper;
import org.eclipse.fennec.mapping.MappingValidator;
import org.eclipse.fennec.mapping.ValueExtractor;
import org.eclipse.fennec.model.command.UpdateCommand;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.ogc.features.example.bath.BathPackage;
import org.eclipse.fennec.stream.EventCore;
import org.eclipse.fennec.stream.InMemoryStreamStore;
import org.eclipse.fennec.stream.Projection;
import org.eclipse.fennec.stream.example.pool.sensor.PhSensor;
import org.eclipse.fennec.stream.example.pool.sensor.SensorFactory;
import org.eclipse.fennec.stream.example.pool.sensor.TempSensor;
import org.junit.jupiter.api.Test;

/**
 * The example without a framework: mapping, update command, probe table, and the core turning the
 * readings of a pool into one update for temperature and pH.
 */
class PoolExampleTest {

	@Test
	void theMappingIsValid() {
		Diagnostic diagnostic = new MappingValidator().validate(PoolStreamMapping.create());
		assertThat(diagnostic.getChildren()).isEmpty();
	}

	@Test
	void theUpdateSelectsThePoolByIdAndSetsBothFeatures() {
		Map<EStructuralFeature, Object> values = new LinkedHashMap<>();
		values.put(BathPackage.Literals.POOL__WATER_TEMPERATURE, 28.4);
		values.put(BathPackage.Literals.POOL__PH, 7.15);

		UpdateCommand update = PoolUpdates.update(BathPackage.Literals.POOL, "wellenbecken", values);

		assertThat(update.getSelector()).isNotNull();
		assertThat(update.getTemplate().getEntries()).extracting(ChangeEntry::getKind).containsExactly(DeltaKind.SET, DeltaKind.SET);
		assertThat(update.getTemplate().getEntries()).extracting(ChangeEntry::getFeatureId).containsExactly(
				BathPackage.Literals.POOL.getFeatureID(BathPackage.Literals.POOL__WATER_TEMPERATURE),
				BathPackage.Literals.POOL.getFeatureID(BathPackage.Literals.POOL__PH));
		assertThat(update.getTemplate().getEntries()).extracting(ChangeEntry::getValueNew).containsExactly("28.4", "7.15");
	}

	@Test
	void theProbeTableFindsThePool() {
		PhProbeTable table = new PhProbeTable(config(new String[] { "ph-wellenbecken = wellenbecken" }));
		PhSensor probe = SensorFactory.eINSTANCE.createPhSensor();
		probe.setId("ph-wellenbecken");
		assertThat(table.apply(probe)).isEqualTo("wellenbecken");
		probe.setId("ph-elsewhere");
		assertThat(table.apply(probe)).isNull();
	}

	@Test
	void temperatureAndPhOfAPoolBecomeOneUpdate() throws Exception {
		FunctionRegistry functions = new FunctionRegistry();
		functions.register(PoolStreamMapping.PH_TO_POOL, new PhProbeTable(config(new String[] { "ph-1=wellenbecken" })));
		ValueExtractor extractor = new ValueExtractor(functions, new ConverterRegistry(), null);
		InMemoryMappingRegistry mappings = new InMemoryMappingRegistry();
		mappings.register(PoolStreamMapping.create());
		List<Map<EStructuralFeature, Object>> updates = Collections.synchronizedList(new ArrayList<>());
		Projection projection = new Projection() {
			@Override
			public boolean project(EClass targetClass, String targetId, EStructuralFeature feature, Object value) {
				throw new AssertionError("one update per window expected");
			}

			@Override
			public boolean project(EClass targetClass, String targetId, Map<EStructuralFeature, Object> values) {
				updates.add(Map.copyOf(values));
				return true;
			}
		};
		try (EventCore core = EventCore.builder().mappings(mappings).mapper(new Mapper(extractor, new IdentityResolver(extractor)))
				.store(new InMemoryStreamStore()).projection(projection).build()) {
			TempSensor temperature = SensorFactory.eINSTANCE.createTempSensor();
			temperature.setId("temp-1");
			temperature.setPoolId("wellenbecken");
			temperature.setValue(28.1);
			PhSensor probe = SensorFactory.eINSTANCE.createPhSensor();
			probe.setId("ph-1");
			probe.setReading(7.2);

			core.offer(temperature, 1_000, null);
			core.offer(probe, 1_000, null);

			long deadline = System.nanoTime() + Duration.ofSeconds(10).toNanos();
			while (updates.isEmpty() && System.nanoTime() < deadline) {
				Thread.sleep(50);
			}
		}
		assertThat(updates).singleElement().satisfies(values -> assertThat(values)
				.containsEntry(BathPackage.Literals.POOL__WATER_TEMPERATURE, 28.1).containsEntry(BathPackage.Literals.POOL__PH, 7.2));
	}

	private static PhProbeTable.Config config(String[] assignments) {
		return new PhProbeTable.Config() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return PhProbeTable.Config.class;
			}

			@Override
			public String[] assignments() {
				return assignments;
			}
		};
	}
}
