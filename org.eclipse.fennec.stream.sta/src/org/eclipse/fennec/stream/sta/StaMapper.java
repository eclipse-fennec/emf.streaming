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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import org.eclipse.fennec.model.sta.HistoricalLocation;
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
 * Turns a change set into STA entities (requirement MOD-09, D-25). Every entity has a key; its
 * {@code @iot.id} is a number derived from it ({@link #id(String)}), the key itself is kept in
 * {@code properties} ({@code parameters} of an observation) as {@value #KEY}:
 * <ul>
 * <li>target object → Thing ({@code <Class>:<targetId>}), its location → Location, a
 * HistoricalLocation (since the first change set) and the FeatureOfInterest (all with the same key);</li>
 * <li>TIMESERIES slot of a target → Datastream ({@code <thing>.<feature>});</li>
 * <li>target feature → ObservedProperty ({@code <Class>.<feature>}, definition = feature URI);</li>
 * <li>source class of the slot → Sensor ({@code <mapping>:<SourceClass>});</li>
 * <li>SET entry → Observation ({@code <changeSet>:<index>}): phenomenonTime = the entry's time,
 * else the change set's; resultTime = the change set's time; the result typed by the feature.</li>
 * </ul>
 * Keyframes, changelog entries and entries without a target feature yield nothing. The unit of a
 * datastream comes, first match wins, from the units given by feature URI, from the neutral
 * annotation {@value #UNIT_ANNOTATION} on the feature ({@code name}, {@code symbol},
 * {@code definition}), or from the deprecated annotation {@value #ANNOTATION}
 * ({@code unit.name}, {@code unit.symbol}, {@code unit.definition}).
 * <p>
 * Thread-safe; the target is described once per thing.
 */
public final class StaMapper {

	/**
	 * Source of the neutral annotation on a target feature that gives the unit of its values
	 * ({@code name}, {@code symbol}, {@code definition}). The unit is part of the meaning of a
	 * value, not of SensorThings.
	 */
	public static final String UNIT_ANNOTATION = "https://eclipse.org/fennec/unit";

	/**
	 * Source of the former STA-specific unit annotation ({@code unit.name}, {@code unit.symbol},
	 * {@code unit.definition}).
	 *
	 * @deprecated use {@link #UNIT_ANNOTATION}; STA metadata does not belong into a domain model
	 */
	@Deprecated
	public static final String ANNOTATION = "https://eclipse.org/fennec/sta";

	/** The member of {@code properties} (of {@code parameters}) holding the key of an entity. */
	public static final String KEY = "fennec.key";

	/** The encoding of locations and features of interest. */
	public static final String GEO_JSON = "application/geo+json";

	private static final String OM = "http://www.opengis.net/def/observationType/OGC-OM/2.0/";
	private static final StaFactory STA = StaFactory.eINSTANCE;

	private final TargetDescriber describer;
	private final Map<String, Unit> units;
	private final Map<String, TargetDescriber.Description> described = new ConcurrentHashMap<>();

	/**
	 * The unit of the datastreams of a feature.
	 *
	 * @param name e.g. {@code degree Celsius}
	 * @param symbol e.g. {@code °C}
	 * @param definition a URI defining the unit, may be {@code null}
	 */
	public record Unit(String name, String symbol, String definition) {

		/**
		 * @param text {@code featureUri=name|symbol|definition}, symbol and definition may be left out
		 * @return the feature URI and its unit
		 * @throws IllegalArgumentException if the text has no {@code =}
		 */
		public static Map.Entry<String, Unit> parse(String text) {
			int equals = text.indexOf('=');
			if (equals <= 0) {
				throw new IllegalArgumentException("Unit '" + text + "' is not featureUri=name|symbol|definition");
			}
			String[] parts = text.substring(equals + 1).split("\\|", -1);
			return Map.entry(text.substring(0, equals).trim(), new Unit(part(parts, 0), part(parts, 1), part(parts, 2)));
		}

		private static String part(String[] parts, int index) {
			return parts.length > index && !parts[index].isBlank() ? parts[index].trim() : null;
		}
	}

	/**
	 * @param describer names and locates the target objects
	 */
	public StaMapper(TargetDescriber describer) {
		this(describer, Map.of());
	}

	/**
	 * @param describer names and locates the target objects
	 * @param units the units of features by feature URI, ahead of their annotations - for features
	 *        of models that cannot be annotated
	 */
	public StaMapper(TargetDescriber describer, Map<String, Unit> units) {
		this.describer = requireNonNull(describer, "TargetDescriber is required");
		this.units = Map.copyOf(requireNonNull(units, "Units are required"));
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
			String thingKey = thingId(target, entry.getObjectId());
			Thing thing = thing(target, entry.getObjectId(), changeSet.getTimestamp(), entities);
			Datastream datastream = datastream(mapping, slot, thing, thingKey, entities);
			Observation observation = STA.createObservation();
			String key = changeSet.getId() + ":" + i;
			observation.setId(id(key));
			observation.setParameters(keyJson(key));
			observation.setPhenomenonTimeStart(new Date(StreamAddresses.eventTime(entry)));
			observation.setResultTime(new Date(changeSet.getTimestamp()));
			result(observation, StreamValues.decode(entry.getValueNew(), slot.getTargetFeature().getEType()));
			observation.setDatastream(datastream);
			observation.setFeatureOfInterest((FeatureOfInterest) entities.get(foiKey(thingKey)));
			observations.add(observation);
		}
		return new StaBatch(new ArrayList<>(entities.values()), observations);
	}

	/**
	 * The {@code @iot.id} of an entity: the first 63 bits of the name-based UUID of its key, never
	 * 0. Stable, so a lane writes the same entity again without looking it up; STA clients and the
	 * conformance suite expect numeric ids.
	 *
	 * @param key the key of the entity
	 * @return its id
	 */
	public static long id(String key) {
		long id = UUID.nameUUIDFromBytes(key.getBytes(UTF_8)).getMostSignificantBits() & Long.MAX_VALUE;
		return id == 0 ? 1 : id;
	}

	/**
	 * @param targetClass the class of a target object
	 * @param targetId its id
	 * @return the key of its thing
	 */
	public static String thingId(EClass targetClass, String targetId) {
		return targetClass.getName() + ":" + targetId;
	}

	/**
	 * @param thingId the key of a thing
	 * @param feature a target feature
	 * @return the key of the datastream of that feature
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

	private Thing thing(EClass target, String targetId, long since, Map<String, EObject> entities) {
		String id = thingId(target, targetId);
		if (entities.get(id) instanceof Thing known) {
			return known;
		}
		TargetDescriber.Description description = described.computeIfAbsent(id, key -> describe(target, targetId));
		Thing thing = STA.createThing();
		thing.setId(id(id));
		thing.setProperties(keyJson(id));
		thing.setName(description.name());
		thing.setDescription(description.description());
		FeatureOfInterest foi = STA.createFeatureOfInterest();
		foi.setId(id(id));
		foi.setProperties(keyJson(id));
		foi.setName(description.name());
		foi.setEncodingType(GEO_JSON);
		foi.setFeature(description.geoJson());
		if (description.geoJson() != null) {
			Location location = STA.createLocation();
			location.setId(id(id));
			location.setProperties(keyJson(id));
			location.setName(description.name());
			location.setEncodingType(GEO_JSON);
			location.setLocation(description.geoJson());
			entities.put(locationKey(id), location);
			thing.getLocations().add(location);
			// STA: setting the location of a thing records it in its history
			HistoricalLocation history = STA.createHistoricalLocation();
			history.setId(id(id));
			history.setTime(new Date(since));
			history.setThing(thing);
			history.getLocations().add(location);
			entities.put("history:" + id, history);
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

	private Datastream datastream(StreamMapping mapping, StreamSlot slot, Thing thing, String thingKey,
			Map<String, EObject> entities) {
		EStructuralFeature feature = slot.getTargetFeature();
		String id = datastreamId(thingKey, feature);
		if (entities.get(id) instanceof Datastream known) {
			return known;
		}
		ObservedProperty property = (ObservedProperty) entities.computeIfAbsent("property:" + StreamSlots.address(slot), key -> {
			ObservedProperty created = STA.createObservedProperty();
			String propertyKey = feature.getEContainingClass().getName() + "." + feature.getName();
			created.setId(id(propertyKey));
			created.setProperties(keyJson(propertyKey));
			created.setName(feature.getName());
			created.setDefinition(StreamSlots.address(slot));
			return created;
		});
		EClass source = Mappings.sourceClassOf(mapping, slot);
		String sensorId = mapping.getId() + ":" + (source == null ? "?" : source.getName());
		Sensor sensor = (Sensor) entities.computeIfAbsent("sensor:" + sensorId, key -> {
			Sensor created = STA.createSensor();
			created.setId(id(sensorId));
			created.setProperties(keyJson(sensorId));
			created.setName(source == null ? sensorId : source.getName());
			created.setEncodingType("text/plain");
			created.setMetadata("Stream mapping " + mapping.getId() + (source == null ? "" : ", source " + Mappings.uriOf(source)));
			return created;
		});
		Datastream datastream = STA.createDatastream();
		datastream.setId(id(id));
		datastream.setProperties(keyJson(id));
		datastream.setName(thing.getName() + " " + feature.getName());
		datastream.setObservationType(observationType(feature));
		datastream.setUnitOfMeasurement(unit(feature, units.get(StreamSlots.address(slot))));
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

	private static UnitOfMeasurement unit(EStructuralFeature feature, Unit given) {
		UnitOfMeasurement unit = STA.createUnitOfMeasurement();
		EAnnotation neutral = feature.getEAnnotation(UNIT_ANNOTATION);
		EAnnotation legacy = feature.getEAnnotation(ANNOTATION);
		if (given != null) {
			unit.setName(given.name());
			unit.setSymbol(given.symbol());
			unit.setDefinition(given.definition());
		} else if (neutral != null) {
			unit.setName(neutral.getDetails().get("name"));
			unit.setSymbol(neutral.getDetails().get("symbol"));
			unit.setDefinition(neutral.getDetails().get("definition"));
		} else if (legacy != null) {
			unit.setName(legacy.getDetails().get("unit.name"));
			unit.setSymbol(legacy.getDetails().get("unit.symbol"));
			unit.setDefinition(legacy.getDetails().get("unit.definition"));
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

	private static String keyJson(String key) {
		return "{" + StaJson.string(KEY) + ":" + StaJson.string(key) + "}";
	}

	private static String locationKey(String thingId) {
		return "location:" + thingId;
	}

	private static String foiKey(String thingId) {
		return "foi:" + thingId;
	}
}
