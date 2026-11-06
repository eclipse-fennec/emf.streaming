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

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Structural rules of a change set (requirements MOD-02, MOD-03, MOD-04, MOD-06).
 */
class ChangeSetValidatorTest {

	private static final StreamFactory FACTORY = StreamFactory.eINSTANCE;
	private static final String POOL = "https://example.org/pool#//Pool";
	private static final String TEMPERATURE = POOL + "/currentTemperature";

	@Test
	void aCompleteSetIsValid() {
		ChangeSet changeSet = header();
		changeSet.getEntries().add(create("pool-1"));
		changeSet.getEntries().add(set("pool-1", TEMPERATURE, -1));
		changeSet.getEntries().add(set("pool-1", null, 3));

		Diagnostic diagnostic = ChangeSetValidator.validate(changeSet);

		assertThat(diagnostic.getSeverity()).as(messages(diagnostic).toString()).isEqualTo(Diagnostic.OK);
		assertThat(ChangeSetValidator.isValid(changeSet)).isTrue();
	}

	@Test
	void theHeaderFieldsAreRequired() {
		ChangeSet changeSet = FACTORY.createChangeSet();

		List<String> messages = messages(ChangeSetValidator.validate(changeSet));

		assertThat(messages).containsExactlyInAnyOrder("id is required", "streamId is required",
				"contextFingerprint is required", "author is required");
	}

	@Test
	void theReasonIsOptional() {
		ChangeSet changeSet = header();
		changeSet.setReason(null);
		assertThat(ChangeSetValidator.isValid(changeSet)).isTrue();
	}

	@ParameterizedTest
	@EnumSource(value = DeltaKind.class, names = { "CREATE", "KEYFRAME" })
	void aCreateOrKeyframeWithoutClassUriIsRejected(DeltaKind kind) {
		ChangeSet changeSet = header();
		ChangeEntry entry = FACTORY.createChangeEntry();
		entry.setObjectId("pool-1");
		entry.setKind(kind);
		entry.setClassId(4); // a class id alone is not enough
		changeSet.getEntries().add(entry);

		assertThat(messages(ChangeSetValidator.validate(changeSet)))
				.containsExactly("entry 0 (" + kind + ", object pool-1): classUri is required for " + kind);
	}

	@ParameterizedTest
	@EnumSource(value = DeltaKind.class, names = { "SET", "UNSET", "ADD", "REMOVE", "RESHAPE", "TOUCH" })
	void aFeatureDeltaWithoutFeatureAddressIsRejected(DeltaKind kind) {
		ChangeSet changeSet = header();
		ChangeEntry entry = FACTORY.createChangeEntry();
		entry.setObjectId("pool-1");
		entry.setKind(kind);
		changeSet.getEntries().add(entry);

		assertThat(messages(ChangeSetValidator.validate(changeSet))).containsExactly(
				"entry 0 (" + kind + ", object pool-1): a feature address (featureUri or featureId) is required for "
						+ kind);
	}

	@Test
	void anEntryNeedsAnObjectId() {
		ChangeSet changeSet = header();
		changeSet.getEntries().add(set(null, TEMPERATURE, -1));

		assertThat(messages(ChangeSetValidator.validate(changeSet)))
				.containsExactly("entry 0 (SET, object null): objectId is required");
	}

	@Test
	void kindSpecificAddressesAreRequired() {
		ChangeSet changeSet = header();
		ChangeEntry move = set("pool-1", TEMPERATURE, -1);
		move.setKind(DeltaKind.MOVE);
		move.setIndex(2);
		ChangeEntry put = set("pool-1", TEMPERATURE, -1);
		put.setKind(DeltaKind.PUT);
		ChangeEntry setAt = set("pool-1", TEMPERATURE, -1);
		setAt.setKind(DeltaKind.SET_AT);
		changeSet.getEntries().addAll(List.of(move, put, setAt));

		assertThat(messages(ChangeSetValidator.validate(changeSet))).containsExactly(
				"entry 0 (MOVE, object pool-1): index and toIndex are required for MOVE",
				"entry 1 (PUT, object pool-1): key is required for PUT",
				"entry 2 (SET_AT, object pool-1): coords are required for SET_AT");
	}

	@Test
	void keyframeSlotsNeedAFeatureAddress() {
		ChangeSet changeSet = header();
		ChangeEntry keyframe = FACTORY.createChangeEntry();
		keyframe.setObjectId("pool-1");
		keyframe.setKind(DeltaKind.KEYFRAME);
		keyframe.setClassUri(POOL);
		SlotValue addressed = FACTORY.createSlotValue();
		addressed.setFeatureId(2);
		addressed.setValue("24.5");
		SlotValue unaddressed = FACTORY.createSlotValue();
		unaddressed.setValue("7.2");
		keyframe.getState().addAll(List.of(addressed, unaddressed));
		changeSet.getEntries().add(keyframe);

		assertThat(messages(ChangeSetValidator.validate(changeSet))).containsExactly(
				"entry 0 (KEYFRAME, object pool-1), slot 1: a feature address (featureUri or featureId) is required");
	}

	@Test
	void stateIsOnlyAllowedOnKeyframes() {
		ChangeSet changeSet = header();
		ChangeEntry entry = set("pool-1", TEMPERATURE, -1);
		entry.getState().add(FACTORY.createSlotValue());
		changeSet.getEntries().add(entry);

		assertThat(messages(ChangeSetValidator.validate(changeSet)))
				.containsExactly("entry 0 (SET, object pool-1): state is only allowed on KEYFRAME");
	}

	private static ChangeSet header() {
		ChangeSet changeSet = FACTORY.createChangeSet();
		changeSet.setId("cs-1");
		changeSet.setStreamId("pool-1");
		changeSet.setSequence(1);
		changeSet.setTimestamp(1_000L);
		changeSet.setContextFingerprint("fp-1");
		changeSet.setAuthor("sensor-temp-1");
		changeSet.setReason("ingest");
		return changeSet;
	}

	private static ChangeEntry create(String objectId) {
		ChangeEntry entry = FACTORY.createChangeEntry();
		entry.setObjectId(objectId);
		entry.setKind(DeltaKind.CREATE);
		entry.setClassUri(POOL);
		return entry;
	}

	private static ChangeEntry set(String objectId, String featureUri, int featureId) {
		ChangeEntry entry = FACTORY.createChangeEntry();
		entry.setObjectId(objectId);
		entry.setKind(DeltaKind.SET);
		entry.setFeatureUri(featureUri);
		entry.setFeatureId(featureId);
		entry.setValueNew("24.5");
		return entry;
	}

	private static List<String> messages(Diagnostic diagnostic) {
		return diagnostic.getChildren().stream().map(Diagnostic::getMessage).toList();
	}
}
