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
package org.eclipse.fennec.stream.sta;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.mapping.Mappings;
import org.eclipse.fennec.model.mapping.Slot;
import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.FeatureOfInterest;
import org.eclipse.fennec.model.sta.Location;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.ObservedProperty;
import org.eclipse.fennec.model.sta.Sensor;
import org.eclipse.fennec.model.sta.StaFactory;
import org.eclipse.fennec.model.sta.Thing;
import org.eclipse.fennec.model.sta.UnitOfMeasurement;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.Recording;
import org.eclipse.fennec.model.stream.support.StreamAddresses;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.stream.StreamSlots;
import org.eclipse.fennec.stream.StreamValues;

/**
 * Turns a change set into STA entities (requirement MOD-09, D-25):
 * <ul>
 * <li>target object → Thing ({@code <Class>:<targetId>}), its location → Location and
 * FeatureOfInterest (same id);</li>
 * <li>TIMESERIES slot of a target → Datastream ({@code <thing>.<feature>});</li>
 * <li>target feature → ObservedProperty ({@code <Class>.<feature>}, definition = feature URI);</li>
 * <li>source class of the slot → Sensor ({@code <mapping>:<SourceClass>});</li>
 * <li>SET entry → Observation ({@code <changeSet>:<index>}): phenomenonTime = the entry's time,
 * else the change set's; resultTime = the change set's time; the result typed by the feature.</li>
 * </ul>
 * Keyframes, changelog entries and entries without a target feature yield nothing. The unit of a
 * datastream comes from the feature's annotation {@value #ANNOTATION} ({@code unit.name},
 * {@code unit.symbol}, {@code unit.definition}).
 * <p>
 * Thread-safe; the target is described once per thing.
 */
public final class StaMapper {

	/** Source of the annotation on a target feature that gives the unit of its datastream. */
	public static final String ANNOTATION = "https://eclipse.org/fennec/sta";

	/** The encoding of locations and features of interest. */
	public static final String GEO_JSON = "application/geo+json";

	private static final String OM = "http://www.opengis.net/def/observationType/OGC-OM/2.0/";
	private static final StaFactory STA = StaFactory.eINSTANCE;

	private final TargetDescriber describer;
	private final Map<String, TargetDescriber.Description> described = new ConcurrentHashMap<>();

	/**
	 * @param describer names and locates the target objects
	 */
	public StaMapper(TargetDescriber describer) {
		this.describer = requireNonNull(describer, "TargetDescriber is required");
	}

	/**
	 * @param mapping the stream mapping of the target object
	 * @param changeSet a change set of its stream
	 * @return the entities it yields; empty for a keyframe or a changelog-only change set
	 */
	public StaBatch map(StreamMapping mapping, ChangeSet changeSet) {
		requireNonNull(mapping, "StreamMapping is required");
		requireNonNull(changeSet, "ChangeSet is required");
		EClass target = mapping.getTarget();
		if (target == null) {
			return StaBatch.EMPTY;
		}
		Map<String, EObject> entities = new LinkedHashMap<>();
		List<Observation> observations = new ArrayList<>();
		List<ChangeEntry> entries = changeSet.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			ChangeEntry entry = entries.get(i);
			if (entry.getKind() != DeltaKind.SET || entry.getRecording() == Recording.CHANGELOG) {
				continue;
			}
			StreamSlot slot = slotOf(mapping, entry.getFeatureUri());
			if (slot == null || slot.getTargetFeature() == null) {
				continue;
			}
			Thing thing = thing(target, entry.getObjectId(), entities);
			Datastream datastream = datastream(mapping, slot, thing, entities);
			Observation observation = STA.createObservation();
			observation.setId(changeSet.getId() + ":" + i);
			observation.setPhenomenonTimeStart(new Date(StreamAddresses.eventTime(entry)));
			observation.setResultTime(new Date(changeSet.getTimestamp()));
			result(observation, StreamValues.decode(entry.getValueNew(), slot.getTargetFeature().getEType()));
			observation.setDatastream(datastream);
			observation.setFeatureOfInterest((FeatureOfInterest) entities.get(foiKey(thing.getId())));
			observations.add(observation);
		}
		return new StaBatch(new ArrayList<>(entities.values()), observations);
	}

	/**
	 * @param targetClass the class of a target object
	 * @param targetId its id
	 * @return the id of its thing
	 */
	public static String thingId(EClass targetClass, String targetId) {
		return targetClass.getName() + ":" + targetId;
	}

	/**
	 * @param thingId the id of a thing
	 * @param feature a target feature
	 * @return the id of the datastream of that feature
	 */
	public static String datastreamId(String thingId, EStructuralFeature feature) {
		return thingId + "." + feature.getName();
	}

	private static StreamSlot slotOf(StreamMapping mapping, String address) {
		for (Slot slot : mapping.getSlots()) {
			if (slot instanceof StreamSlot streamSlot && address != null && address.equals(StreamSlots.address(streamSlot))) {
				return streamSlot;
			}
		}
		return null;
	}

	private Thing thing(EClass target, String targetId, Map<String, EObject> entities) {
		String id = thingId(target, targetId);
		if (entities.get(id) instanceof Thing known) {
			return known;
		}
		TargetDescriber.Description description = described.computeIfAbsent(id, key -> describe(target, targetId));
		Thing thing = STA.createThing();
		thing.setId(id);
		thing.setName(description.name());
		thing.setDescription(description.description());
		FeatureOfInterest foi = STA.createFeatureOfInterest();
		foi.setId(id);
		foi.setName(description.name());
		foi.setEncodingType(GEO_JSON);
		foi.setFeature(description.geoJson());
		if (description.geoJson() != null) {
			Location location = STA.createLocation();
			location.setId(id);
			location.setName(description.name());
			location.setEncodingType(GEO_JSON);
			location.setLocation(description.geoJson());
			entities.put(locationKey(id), location);
			thing.getLocations().add(location);
		}
		entities.put(id, thing);
		entities.put(foiKey(id), foi);
		return thing;
	}

	private TargetDescriber.Description describe(EClass target, String targetId) {
		TargetDescriber.Description description = describer.describe(target, targetId);
		if (description == null || description.name() == null) {
			return new TargetDescriber.Description(targetId, null, description == null ? null : description.geoJson());
		}
		return description;
	}

	private static Datastream datastream(StreamMapping mapping, StreamSlot slot, Thing thing, Map<String, EObject> entities) {
		EStructuralFeature feature = slot.getTargetFeature();
		String id = datastreamId(thing.getId(), feature);
		if (entities.get(id) instanceof Datastream known) {
			return known;
		}
		ObservedProperty property = (ObservedProperty) entities.computeIfAbsent("property:" + StreamSlots.address(slot), key -> {
			ObservedProperty created = STA.createObservedProperty();
			created.setId(feature.getEContainingClass().getName() + "." + feature.getName());
			created.setName(feature.getName());
			created.setDefinition(StreamSlots.address(slot));
			return created;
		});
		EClass source = Mappings.sourceClassOf(mapping, slot);
		String sensorId = mapping.getId() + ":" + (source == null ? "?" : source.getName());
		Sensor sensor = (Sensor) entities.computeIfAbsent("sensor:" + sensorId, key -> {
			Sensor created = STA.createSensor();
			created.setId(sensorId);
			created.setName(source == null ? sensorId : source.getName());
			created.setEncodingType("text/plain");
			created.setMetadata("Stream mapping " + mapping.getId() + (source == null ? "" : ", source " + Mappings.uriOf(source)));
			return created;
		});
		Datastream datastream = STA.createDatastream();
		datastream.setId(id);
		datastream.setName(thing.getName() + " " + feature.getName());
		datastream.setObservationType(observationType(feature));
		datastream.setUnitOfMeasurement(unit(feature));
		datastream.setThing(thing);
		datastream.setSensor(sensor);
		datastream.setObservedProperty(property);
		entities.put(id, datastream);
		return datastream;
	}

	private static String observationType(EStructuralFeature feature) {
		Class<?> type = feature.getEType().getInstanceClass();
		if (type == null || Enumerator.class.isAssignableFrom(type)) {
			return OM + "OM_CategoryObservation";
		}
		if (type == boolean.class || type == Boolean.class) {
			return OM + "OM_TruthObservation";
		}
		if (type == int.class || type == long.class || type == short.class || type == Integer.class || type == Long.class
				|| type == Short.class) {
			return OM + "OM_CountObservation";
		}
		if (type.isPrimitive() || Number.class.isAssignableFrom(type)) {
			return OM + "OM_Measurement";
		}
		return OM + "OM_Observation";
	}

	private static UnitOfMeasurement unit(EStructuralFeature feature) {
		UnitOfMeasurement unit = STA.createUnitOfMeasurement();
		EAnnotation annotation = feature.getEAnnotation(ANNOTATION);
		if (annotation != null) {
			unit.setName(annotation.getDetails().get("unit.name"));
			unit.setSymbol(annotation.getDetails().get("unit.symbol"));
			unit.setDefinition(annotation.getDetails().get("unit.definition"));
		}
		return unit;
	}

	private static void result(Observation observation, Object value) {
		if (value instanceof Number number) {
			observation.setResultNumber(number.doubleValue());
		} else if (value instanceof Boolean truth) {
			observation.setResultBoolean(truth);
		} else if (value instanceof Enumerator literal) {
			observation.setResultText(literal.getLiteral());
		} else if (value != null) {
			observation.setResultText(String.valueOf(value));
		}
	}

	private static String locationKey(String thingId) {
		return "location:" + thingId;
	}

	private static String foiKey(String thingId) {
		return "foi:" + thingId;
	}
}
