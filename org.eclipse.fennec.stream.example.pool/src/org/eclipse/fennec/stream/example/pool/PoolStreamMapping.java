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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.model.mapping.IdentityResolution;
import org.eclipse.fennec.model.mapping.MappingFactory;
import org.eclipse.fennec.model.mapping.ValueSource;
import org.eclipse.fennec.model.streammapping.AbsoluteChange;
import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.ogc.features.example.bath.BathPackage;
import org.eclipse.fennec.stream.example.pool.sensor.SensorPackage;

/**
 * The stream mapping of the example: water temperature from {@code TempSensor.value} (the sensor
 * names its pool), pH from {@code PhSensor.reading} (the function {@value #PH_TO_POOL} finds the
 * pool). Small changes are held back (0.2 °C, 0.05 pH); a two-second window puts temperature and pH
 * of a pool into one change set, so the pool gets one update for both.
 */
public final class PoolStreamMapping {

	/** The id of the mapping. */
	public static final String ID = "pool-streams";

	/** The function resolving the pool of a pH probe. */
	public static final String PH_TO_POOL = "ph-to-pool";

	private PoolStreamMapping() {
	}

	/**
	 * @return a new mapping
	 */
	public static StreamMapping create() {
		StreamMappingFactory streams = StreamMappingFactory.eINSTANCE;
		StreamMapping mapping = streams.createStreamMapping();
		mapping.setId(ID);
		mapping.setName("Pool sensors");
		mapping.setTarget(BathPackage.Literals.POOL);
		BatchStrategy window = streams.createBatchStrategy();
		window.setKind(BatchKind.WINDOW);
		window.setWindow(2);
		window.setUnit(DurationUnit.SECONDS);
		mapping.setBatch(window);

		IdentityResolution temperatureToPool = MappingFactory.eINSTANCE.createIdentityResolution();
		temperatureToPool.setSourceClass(SensorPackage.Literals.TEMP_SENSOR);
		temperatureToPool.setResolver(path(SensorPackage.Literals.TEMP_SENSOR__POOL_ID));
		mapping.getIdentities().add(temperatureToPool);
		IdentityResolution phToPool = MappingFactory.eINSTANCE.createIdentityResolution();
		phToPool.setSourceClass(SensorPackage.Literals.PH_SENSOR);
		ValueSource function = MappingFactory.eINSTANCE.createValueSource();
		function.setFunctionId(PH_TO_POOL);
		phToPool.setResolver(function);
		mapping.getIdentities().add(phToPool);

		mapping.getSlots().add(slot(BathPackage.Literals.POOL__WATER_TEMPERATURE, SensorPackage.Literals.TEMP_SENSOR__VALUE, 0.2));
		mapping.getSlots().add(slot(BathPackage.Literals.POOL__PH, SensorPackage.Literals.PH_SENSOR__READING, 0.05));
		return mapping;
	}

	private static StreamSlot slot(EStructuralFeature target, EStructuralFeature source, double delta) {
		StreamSlot slot = StreamMappingFactory.eINSTANCE.createStreamSlot();
		slot.setTargetFeature(target);
		slot.setSourceClass(source.getEContainingClass());
		slot.setValue(path(source));
		AbsoluteChange change = StreamMappingFactory.eINSTANCE.createAbsoluteChange();
		change.setDelta(delta);
		slot.setRule(change);
		return slot;
	}

	private static ValueSource path(EStructuralFeature feature) {
		ValueSource source = MappingFactory.eINSTANCE.createValueSource();
		source.getFeaturePath().add(feature);
		return source;
	}
}
