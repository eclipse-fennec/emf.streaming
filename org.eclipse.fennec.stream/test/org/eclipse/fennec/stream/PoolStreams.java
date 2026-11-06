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
package org.eclipse.fennec.stream;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
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
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;

/**
 * The pool scenario for the event core: {@code Pool} (id, name, currentTemperature, currentPh,
 * depth: EInt), {@code TempSensor} (id, poolId, value) finding its pool by key, {@code PhSensor}
 * (id, reading) finding its pool through the function {@code ph-to-pool}. Pools live in a map, the
 * projection writes into them.
 */
final class PoolStreams {

	final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
	final EClass pool = eClass("Pool");
	final EAttribute poolId = id(pool);
	final EAttribute poolName = attribute(pool, "name", EcorePackage.Literals.ESTRING);
	final EAttribute currentTemperature = attribute(pool, "currentTemperature", EcorePackage.Literals.EDOUBLE);
	final EAttribute currentPh = attribute(pool, "currentPh", EcorePackage.Literals.EDOUBLE);
	final EAttribute depth = attribute(pool, "depth", EcorePackage.Literals.EINT);
	final EClass tempSensor = eClass("TempSensor");
	final EAttribute tempSensorId = id(tempSensor);
	final EAttribute tempSensorPoolId = attribute(tempSensor, "poolId", EcorePackage.Literals.ESTRING);
	final EAttribute tempSensorValue = attribute(tempSensor, "value", EcorePackage.Literals.EDOUBLE);
	final EClass phSensor = eClass("PhSensor");
	final EAttribute phSensorId = id(phSensor);
	final EAttribute phSensorReading = attribute(phSensor, "reading", EcorePackage.Literals.EDOUBLE);

	final Map<String, EObject> pools = new ConcurrentHashMap<>();
	final FunctionRegistry functions = new FunctionRegistry();
	final ConverterRegistry converters = new ConverterRegistry();
	final ValueExtractor extractor = new ValueExtractor(functions, converters, null);
	final Mapper mapper = new Mapper(extractor, new IdentityResolver(extractor));
	final InMemoryMappingRegistry registry = new InMemoryMappingRegistry();
	final ObjectProjection projection = new ObjectProjection((eClass, id) -> pools.get(id));

	PoolStreams() {
		ePackage.setName("pool");
		ePackage.setNsPrefix("pool");
		ePackage.setNsURI("https://example.org/pool");
		Map<String, String> phTable = Map.of("ph-7", "pool-1", "ph-8", "pool-2");
		functions.register("ph-to-pool", input -> phTable.get(EcoreUtil.getID((EObject) input)));
	}

	EObject pool(String id) {
		EObject pool = EcoreUtil.create(this.pool);
		pool.eSet(poolId, id);
		pools.put(id, pool);
		return pool;
	}

	EObject temp(String id, String poolKey, double value) {
		EObject sensor = EcoreUtil.create(tempSensor);
		sensor.eSet(tempSensorId, id);
		sensor.eSet(tempSensorPoolId, poolKey);
		sensor.eSet(tempSensorValue, value);
		return sensor;
	}

	EObject ph(String id, double value) {
		EObject sensor = EcoreUtil.create(phSensor);
		sensor.eSet(phSensorId, id);
		sensor.eSet(phSensorReading, value);
		return sensor;
	}

	/** Temperature (by poolId) and pH (by function) onto Pool, all defaults. */
	StreamMapping mapping() {
		StreamMapping mapping = StreamMappingFactory.eINSTANCE.createStreamMapping();
		mapping.setId("pool-streams");
		mapping.setTarget(pool);
		mapping.getIdentities().add(identity(tempSensor, path(tempSensorPoolId)));
		ValueSource phToPool = MappingFactory.eINSTANCE.createValueSource();
		phToPool.setFunctionId("ph-to-pool");
		mapping.getIdentities().add(identity(phSensor, phToPool));
		mapping.getSlots().add(slot(currentTemperature, tempSensor, path(tempSensorValue)));
		mapping.getSlots().add(slot(currentPh, phSensor, path(phSensorReading)));
		return mapping;
	}

	StreamSlot temperatureSlot(StreamMapping mapping) {
		return (StreamSlot) mapping.getSlots().get(0);
	}

	EventCore.Builder core(StreamStore store) {
		return EventCore.builder().mappings(registry).mapper(mapper).projection(projection).store(store)
				.group(new EventCore.ExecutionGroup("high", 2, 8)).group(new EventCore.ExecutionGroup("default", 4, 8));
	}

	String stream(String poolId) {
		return EventCore.streamId(pool, poolId);
	}

	String temperatureAddress() {
		return "https://example.org/pool#//Pool/currentTemperature";
	}

	static StreamSlot slot(EStructuralFeature target, EClass source, ValueSource value) {
		StreamSlot slot = StreamMappingFactory.eINSTANCE.createStreamSlot();
		slot.setTargetFeature(target);
		slot.setSourceClass(source);
		slot.setValue(value);
		return slot;
	}

	static ValueSource path(EStructuralFeature... features) {
		ValueSource source = MappingFactory.eINSTANCE.createValueSource();
		source.getFeaturePath().addAll(List.of(features));
		return source;
	}

	private static IdentityResolution identity(EClass source, ValueSource resolver) {
		IdentityResolution identity = MappingFactory.eINSTANCE.createIdentityResolution();
		identity.setSourceClass(source);
		identity.setResolver(resolver);
		return identity;
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
