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
package org.eclipse.fennec.model.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;

/**
 * The stream model itself: its catalogue, the corrections made before the first release
 * (event time per entry, class and feature URIs, {@code reason}, mandatory {@code author}) and a
 * serialization round trip.
 *
 * @author Mark Hoffmann
 */
class StreamModelTest {

	private static final StreamFactory FACTORY = StreamFactory.eINSTANCE;
	private static final StreamPackage PACKAGE = StreamPackage.eINSTANCE;

	@Test
	void changeSetWithEntriesBuilds() {
		ChangeSet changeSet = FACTORY.createChangeSet();
		changeSet.setId("cs-1");
		changeSet.setStreamId("s42");
		changeSet.setSequence(7);

		ChangeEntry set = FACTORY.createChangeEntry();
		set.setObjectId("42");
		set.setKind(DeltaKind.SET);
		set.setFeatureId(1);
		set.setValueOld("12");
		set.setValueNew("14");
		changeSet.getEntries().add(set);

		assertThat(changeSet.getEntries()).hasSize(1);
		assertThat(set.eContainer()).isSameAs(changeSet);
		assertThat(set.getKind()).isEqualTo(DeltaKind.SET);
	}

	@Test
	void deltaKindCatalogueIsCompleteAndKeepsItsValues() {
		// The reserved literals (TOUCH, MIGRATE_*) keep their numbers: additive evolution only.
		assertThat(DeltaKind.values()).hasSize(15);
		assertThat(DeltaKind.TOUCH.getValue()).isEqualTo(12);
		assertThat(DeltaKind.MIGRATE_OUT.getValue()).isEqualTo(13);
		assertThat(DeltaKind.MIGRATE_IN.getValue()).isEqualTo(14);
		assertThat(DeltaKind.KEYFRAME.getValue()).isEqualTo(11);
	}

	@Test
	void packageMetadata() {
		assertThat(PACKAGE.getNsURI()).isEqualTo("https://org.eclipse/fennec/stream/1.0.0");
	}

	@Test
	void reservedSnapshotClassesStayInTheModel() {
		assertThat(PACKAGE.getEClassifier("ContextSnapshot")).isNotNull();
		assertThat(PACKAGE.getEClassifier("CompositeSnapshot")).isNotNull();
		assertThat(PACKAGE.getEClassifier("PackageFingerprint")).isNotNull();
	}

	@Test
	void reasonReplacesCauseAndAuthorAndFingerprintAreMandatory() {
		EClass changeSet = PACKAGE.getChangeSet();
		assertThat(changeSet.getEStructuralFeature("cause")).isNull();
		assertThat(changeSet.getEStructuralFeature("reason")).isNotNull();
		assertThat(changeSet.getEStructuralFeature("reason").getLowerBound()).isZero();
		assertThat(changeSet.getEStructuralFeature("author").getLowerBound()).isEqualTo(1);
		assertThat(changeSet.getEStructuralFeature("contextFingerprint").getLowerBound()).isEqualTo(1);
	}

	@Test
	void entryCarriesClassUriFeatureUriAndAnOptionalEventTime() {
		EClass entry = PACKAGE.getChangeEntry();
		assertThat(entry.getEStructuralFeature("classUri")).isNotNull();
		assertThat(entry.getEStructuralFeature("featureUri")).isNotNull();
		EAttribute timestamp = (EAttribute) entry.getEStructuralFeature("timestamp");
		assertThat(timestamp.getLowerBound()).isZero();
		assertThat(timestamp.getEAttributeType()).isSameAs(EcorePackage.Literals.ELONG_OBJECT);

		ChangeEntry fresh = FACTORY.createChangeEntry();
		assertThat(fresh.getTimestamp()).isNull();
		assertThat(fresh.getClassId()).isEqualTo(-1);
		assertThat(fresh.getFeatureId()).isEqualTo(-1);
	}

	@Test
	void noClassLooksLikeAMapEntry() {
		// serializers read a class with features 'key' and 'value' as a Map.Entry
		for (EClassifier classifier : PACKAGE.getEClassifiers()) {
			if (classifier instanceof EClass eClass) {
				assertThat(eClass.getEStructuralFeature("key")).as(eClass.getName()).isNull();
			}
		}
		assertThat(PACKAGE.getSlotValue().getEStructuralFeature("mapKey")).isNotNull();
	}

	@Test
	void slotValueAddressesItsFeatureByIdOrUri() {
		EClass slot = PACKAGE.getSlotValue();
		assertThat(slot.getEStructuralFeature("featureId").getLowerBound()).isZero();
		assertThat(slot.getEStructuralFeature("featureUri")).isNotNull();
		assertThat(FACTORY.createSlotValue().getFeatureId()).isEqualTo(-1);
	}

	@Test
	void entriesWithTheirOwnEventTimeSurviveARoundTrip() throws IOException {
		ChangeSet changeSet = FACTORY.createChangeSet();
		changeSet.setId("cs-ts");
		changeSet.setStreamId("pool-1");
		changeSet.setSequence(3);
		changeSet.setTimestamp(1_000L);
		changeSet.setContextFingerprint("fp-1");
		changeSet.setAuthor("sensor-temp-1");
		changeSet.setReason("ingest");
		changeSet.getEntries().add(sample("24.1", 900L));
		changeSet.getEntries().add(sample("24.6", 950L));
		changeSet.getEntries().add(sample("24.7", null));

		ChangeSet read = roundTrip(changeSet);

		assertThat(read.getReason()).isEqualTo("ingest");
		assertThat(read.getAuthor()).isEqualTo("sensor-temp-1");
		assertThat(read.getEntries()).extracting(ChangeEntry::getTimestamp).containsExactly(900L, 950L, null);
		assertThat(read.getEntries()).extracting(ChangeEntry::getFeatureUri)
				.containsOnly("https://example.org/pool#//Pool/currentTemperature");
	}

	private static ChangeEntry sample(String value, Long eventTime) {
		ChangeEntry entry = FACTORY.createChangeEntry();
		entry.setObjectId("pool-1");
		entry.setKind(DeltaKind.SET);
		entry.setFeatureUri("https://example.org/pool#//Pool/currentTemperature");
		entry.setValueNew(value);
		entry.setTimestamp(eventTime);
		return entry;
	}

	private static ChangeSet roundTrip(ChangeSet changeSet) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(PACKAGE.getNsURI(), PACKAGE);

		Resource out = resourceSet.createResource(URI.createURI("out.xmi"));
		out.getContents().add(changeSet);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		out.save(bytes, Map.of());

		Resource in = resourceSet.createResource(URI.createURI("in.xmi"));
		in.load(new ByteArrayInputStream(bytes.toByteArray()), Map.of());
		return (ChangeSet) in.getContents().get(0);
	}
}
