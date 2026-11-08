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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.mapping.MappingFactory;
import org.eclipse.fennec.model.mapping.ValueSource;
import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.Location;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.Thing;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.Recording;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.junit.jupiter.api.Test;

/**
 * Change sets become STA entities (MOD-09, D-25).
 */
class StaMapperTest {

	private static final String NS = "https://example.org/pool";

	private final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
	private final EClass pool = eClass("Pool");
	private final EAttribute temperature = attribute(pool, "temperature", EcorePackage.Literals.EDOUBLE);
	private final EAttribute open = attribute(pool, "open", EcorePackage.Literals.EBOOLEAN);
	private final EAttribute state = attribute(pool, "state", EcorePackage.Literals.ESTRING);
	private final EClass probe = eClass("Probe");
	private final StreamMapping mapping = mapping();
	private final StaMapper mapper = new StaMapper(
			(targetClass, targetId) -> new TargetDescriber.Description("Kinderland", null, "{\"type\":\"Point\",\"coordinates\":[11.6,50.9]}"));

	StaMapperTest() {
		ePackage.setName("pool");
		ePackage.setNsPrefix("pool");
		ePackage.setNsURI(NS);
		EAnnotation unit = EcoreFactory.eINSTANCE.createEAnnotation();
		unit.setSource(StaMapper.ANNOTATION);
		unit.getDetails().put("unit.name", "degree Celsius");
		unit.getDetails().put("unit.symbol", "°C");
		temperature.getEAnnotations().add(unit);
	}

	@Test
	void anEntryOfATimeseriesSlotBecomesAnObservationOfTheThingsDatastream() {
		ChangeSet changeSet = changeSet(set(temperature, "27.5", 1_000L), set(open, "true", null));

		StaBatch batch = mapper.map(mapping, changeSet);

		assertThat(batch.observations()).hasSize(2);
		Observation observation = batch.observations().get(0);
		assertThat(observation.getId()).isEqualTo("cs-1:0");
		assertThat(observation.getResultNumber()).isEqualTo(27.5);
		assertThat(observation.getPhenomenonTimeStart()).isEqualTo(new Date(1_000));
		assertThat(observation.getResultTime()).isEqualTo(new Date(5_000));
		Datastream datastream = observation.getDatastream();
		assertThat(datastream.getId()).isEqualTo("Pool:kinderland.temperature");
		assertThat(datastream.getName()).isEqualTo("Kinderland temperature");
		assertThat(datastream.getObservationType()).endsWith("OM_Measurement");
		assertThat(datastream.getUnitOfMeasurement().getSymbol()).isEqualTo("°C");
		assertThat(datastream.getObservedProperty().getDefinition()).isEqualTo(NS + "#//Pool/temperature");
		assertThat(datastream.getSensor().getId()).isEqualTo("pool-sta:Probe");
		Thing thing = datastream.getThing();
		assertThat(thing.getId()).isEqualTo("Pool:kinderland");
		assertThat(thing.getLocations()).extracting(Location::getLocation).singleElement().asString().contains("Point");
		assertThat(observation.getFeatureOfInterest().getId()).isEqualTo("Pool:kinderland");

		Observation truth = batch.observations().get(1);
		assertThat(truth.getResultBoolean()).isTrue();
		assertThat(truth.getPhenomenonTimeStart()).as("no own time: the change set's").isEqualTo(new Date(5_000));
		assertThat(truth.getDatastream().getObservationType()).endsWith("OM_TruthObservation");

		assertThat(batch.entities()).extracting(EcoreUtil::getID).as("each entity once, referenced ones first")
				.containsExactly("Pool:kinderland", "Pool:kinderland", "Pool:kinderland", "Pool.temperature", "pool-sta:Probe",
						"Pool:kinderland.temperature", "Pool.open", "Pool:kinderland.open");
	}

	@Test
	void keyframesChangelogEntriesAndUnknownFeaturesYieldNothing() {
		ChangeEntry keyframe = StreamFactory.eINSTANCE.createChangeEntry();
		keyframe.setKind(DeltaKind.KEYFRAME);
		keyframe.setObjectId("kinderland");
		ChangeEntry changelog = set(state, "open", 1_000L);
		changelog.setRecording(Recording.CHANGELOG);
		ChangeEntry unknown = set(temperature, "1", 1_000L);
		unknown.setFeatureUri(NS + "#//Pool/depth");

		assertThat(mapper.map(mapping, changeSet(keyframe, changelog, unknown)).isEmpty()).isTrue();
	}

	@Test
	void theTargetIsDescribedOnceAndAThingWithoutLocationHasAnEmptyFeatureOfInterest() {
		List<String> asked = new ArrayList<>();
		StaMapper plain = new StaMapper((targetClass, targetId) -> {
			asked.add(targetId);
			return TargetDescriber.IDS.describe(targetClass, targetId);
		});

		plain.map(mapping, changeSet(set(temperature, "27.5", 1_000L)));
		StaBatch second = plain.map(mapping, changeSet(set(temperature, "28.0", 2_000L)));

		assertThat(asked).containsExactly("kinderland");
		Thing thing = second.observations().get(0).getDatastream().getThing();
		assertThat(thing.getName()).isEqualTo("kinderland");
		assertThat(thing.getLocations()).isEmpty();
		assertThat(second.observations().get(0).getFeatureOfInterest().getFeature()).isNull();
	}

	@Test
	void theSinkWritesOnlyWhatYieldsObservations() {
		List<StaBatch> written = new ArrayList<>();
		StaSink sink = new StaSink(mapper, written::add);
		ChangeEntry keyframe = StreamFactory.eINSTANCE.createChangeEntry();
		keyframe.setKind(DeltaKind.KEYFRAME);

		sink.written(mapping, changeSet(keyframe));
		sink.written(mapping, changeSet(set(temperature, "27.5", 1_000L)));

		assertThat(written).singleElement().satisfies(batch -> assertThat(batch.observations()).hasSize(1));
	}

	private StreamMapping mapping() {
		StreamMapping created = StreamMappingFactory.eINSTANCE.createStreamMapping();
		created.setId("pool-sta");
		created.setTarget(pool);
		for (EAttribute feature : List.of(temperature, open, state)) {
			StreamSlot slot = StreamMappingFactory.eINSTANCE.createStreamSlot();
			slot.setTargetFeature(feature);
			slot.setSourceClass(probe);
			ValueSource value = MappingFactory.eINSTANCE.createValueSource();
			value.setConstant("x");
			slot.setValue(value);
			created.getSlots().add(slot);
		}
		return created;
	}

	private static ChangeSet changeSet(ChangeEntry... entries) {
		ChangeSet changeSet = StreamFactory.eINSTANCE.createChangeSet();
		changeSet.setId("cs-1");
		changeSet.setStreamId("pool");
		changeSet.setSequence(1);
		changeSet.setTimestamp(5_000);
		changeSet.setAuthor("test");
		changeSet.getEntries().addAll(List.of(entries));
		return changeSet;
	}

	private static ChangeEntry set(EAttribute feature, String value, Long time) {
		ChangeEntry entry = StreamFactory.eINSTANCE.createChangeEntry();
		entry.setKind(DeltaKind.SET);
		entry.setObjectId("kinderland");
		entry.setFeatureUri(NS + "#//" + feature.getEContainingClass().getName() + "/" + feature.getName());
		entry.setValueNew(value);
		entry.setTimestamp(time);
		return entry;
	}

	private EClass eClass(String name) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		ePackage.getEClassifiers().add(eClass);
		return eClass;
	}

	private static EAttribute attribute(EClass owner, String name, EClassifier type) {
		EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(name);
		attribute.setEType(type);
		owner.getEStructuralFeatures().add(attribute);
		return attribute;
	}
}
