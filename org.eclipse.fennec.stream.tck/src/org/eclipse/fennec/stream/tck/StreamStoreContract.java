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
package org.eclipse.fennec.stream.tck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.List;

import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.Recording;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.stream.StreamStore;
import org.eclipse.fennec.stream.StreamStore.RetentionOutcome;
import org.eclipse.fennec.stream.StreamStore.StoredValue;
import org.junit.jupiter.api.Test;

/**
 * The contract of a {@link StreamStore}. A store's test extends this class and hands out the store
 * under test; every test asks for it once and starts from an empty store.
 * <p>
 * Retention is part of the contract only for stores that apply it: a store answering
 * {@link RetentionOutcome#UNSUPPORTED} skips those tests.
 */
public abstract class StreamStoreContract {

	/** A stream of the contract. */
	protected static final String POOL = "https://example.org/bath#//Pool/wellenbecken";
	/** A second stream. */
	protected static final String OTHER = "https://example.org/bath#//Pool/sportbecken";
	/** A slot address. */
	protected static final String TEMPERATURE = "https://example.org/bath#//Pool/waterTemperature";
	/** A second slot address. */
	protected static final String PH = "https://example.org/bath#//Pool/ph";

	private static final StreamFactory STREAM = StreamFactory.eINSTANCE;

	/**
	 * @return the store under test, empty
	 */
	protected abstract StreamStore store();

	@Test
	void changeSetsAreReadBackByStreamInSequenceOrder() {
		StreamStore store = store();
		store.append(changeSet(POOL, 2, 2_000, set(TEMPERATURE, "27.5", 2_000L)));
		store.append(changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));
		store.append(changeSet(POOL, 3, 3_000, set(TEMPERATURE, "28.0", 3_000L)));
		store.append(changeSet(OTHER, 1, 1_500, set(TEMPERATURE, "24.0", 1_500L)));

		assertThat(store.read(POOL, 1, 3)).extracting(ChangeSet::getSequence).containsExactly(1L, 2L, 3L);
		assertThat(store.read(POOL, 2, 2)).extracting(ChangeSet::getSequence).containsExactly(2L);
		assertThat(store.read(POOL, 2, Long.MAX_VALUE)).extracting(ChangeSet::getSequence).containsExactly(2L, 3L);
		assertThat(store.read(POOL, 3, 1)).isEmpty();
		assertThat(store.read(OTHER, 1, 3)).extracting(ChangeSet::getStreamId).containsExactly(OTHER);
		assertThat(store.read("https://example.org/bath#//Pool/unknown", 1, 3)).isEmpty();
	}

	@Test
	void theLastSequenceIsTheHighestStored() {
		StreamStore store = store();
		assertThat(store.lastSequence(POOL)).isZero();

		store.append(changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));
		store.append(changeSet(POOL, 2, 2_000, set(TEMPERATURE, "27.5", 2_000L)));

		assertThat(store.lastSequence(POOL)).isEqualTo(2);
		assertThat(store.lastSequence(OTHER)).isZero();
	}

	@Test
	void appendingTheSameChangeSetAgainChangesNothing() {
		StreamStore store = store();
		ChangeSet changeSet = changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L));

		store.append(changeSet);
		store.append(changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));

		assertThat(store.read(POOL, 1, 1)).singleElement()
				.satisfies(stored -> assertThat(stored.getEntries()).hasSize(1));
	}

	@Test
	void everyFieldOfAChangeSetIsReadBackAsWritten() {
		StreamStore store = store();
		ChangeSet written = changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 900L));
		written.setCommitTime(1_005);
		written.setContextFingerprint("fp-1");
		written.setReason("calibration");
		written.setTransactionId("tx-1");
		written.setManifest(true);
		ChangeEntry changelog = set(PH, "7.2", null);
		changelog.setValueOld("7.1");
		changelog.setRecording(Recording.CHANGELOG);
		changelog.setFeatureId(3);
		ChangeEntry moved = entry(DeltaKind.MOVE, TEMPERATURE);
		moved.setIndex(1);
		moved.setToIndex(4);
		ChangeEntry put = entry(DeltaKind.PUT, TEMPERATURE);
		put.setMapKey("north");
		put.setValueNew("12");
		ChangeEntry setAt = entry(DeltaKind.SET_AT, TEMPERATURE);
		setAt.getCoords().addAll(List.of(2, 3));
		setAt.setValueNew("1.5");
		written.getEntries().addAll(List.of(changelog, moved, put, setAt));

		store.append(written);

		ChangeSet read = store.read(POOL, 1, 1).get(0);
		assertThat(read.getId()).isEqualTo(written.getId());
		assertThat(read.getStreamId()).isEqualTo(POOL);
		assertThat(read.getTimestamp()).isEqualTo(1_000);
		assertThat(read.getCommitTime()).isEqualTo(1_005);
		assertThat(read.getContextFingerprint()).isEqualTo("fp-1");
		assertThat(read.getAuthor()).isEqualTo("contract");
		assertThat(read.getReason()).isEqualTo("calibration");
		assertThat(read.getTransactionId()).isEqualTo("tx-1");
		assertThat(read.isManifest()).isTrue();
		assertThat(read.getEntries()).extracting(ChangeEntry::getKind).containsExactly(DeltaKind.SET, DeltaKind.SET,
				DeltaKind.MOVE, DeltaKind.PUT, DeltaKind.SET_AT);
		ChangeEntry sample = read.getEntries().get(0);
		assertThat(sample.getTimestamp()).isEqualTo(900L);
		assertThat(sample.getValueOld()).isNull();
		ChangeEntry readChangelog = read.getEntries().get(1);
		assertThat(readChangelog.getValueOld()).isEqualTo("7.1");
		assertThat(readChangelog.getRecording()).isEqualTo(Recording.CHANGELOG);
		assertThat(readChangelog.getFeatureId()).isEqualTo(3);
		assertThat(readChangelog.getTimestamp()).isNull();
		assertThat(read.getEntries().get(2)).extracting(ChangeEntry::getIndex, ChangeEntry::getToIndex)
				.containsExactly(1, 4);
		assertThat(read.getEntries().get(3).getMapKey()).isEqualTo("north");
		assertThat(read.getEntries().get(4).getCoords()).containsExactly(2, 3);
	}

	/** A store that cannot run this yet overrides it, disabled with the reason. */
	@Test
	protected void anUnsetRecordingIsReadBackUnset() {
		StreamStore store = store();
		store.append(changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));

		assertThat(store.read(POOL, 1, 1).get(0).getEntries().get(0).isSetRecording())
				.as("no recording means: the receiver's default").isFalse();
	}

	@Test
	void aKeyframeIsReadBackWithItsSlots() {
		StreamStore store = store();
		store.append(changeSet(POOL, 1, 2_000, keyframe(2_000L, slot(TEMPERATURE, "27.5"), slot(PH, "7.2"))));

		ChangeEntry keyframe = store.read(POOL, 1, 1).get(0).getEntries().get(0);

		assertThat(keyframe.getKind()).isEqualTo(DeltaKind.KEYFRAME);
		assertThat(keyframe.getClassUri()).isEqualTo("https://example.org/bath#//Pool");
		assertThat(keyframe.getState()).extracting(SlotValue::getFeatureUri, SlotValue::getValue)
				.containsExactlyInAnyOrder(tuple(TEMPERATURE, "27.5"), tuple(PH, "7.2"));
	}

	@Test
	void whatIsReadIsACopy() {
		StreamStore store = store();
		ChangeSet written = changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L));
		store.append(written);
		written.getEntries().get(0).setValueNew("99.0");

		ChangeSet read = store.read(POOL, 1, 1).get(0);
		read.getEntries().get(0).setValueNew("98.0");

		assertThat(store.read(POOL, 1, 1).get(0).getEntries().get(0).getValueNew()).isEqualTo("27.0");
	}

	@Test
	void theLastValueOfASlotComesFromEntriesAndKeyframes() {
		StreamStore store = store();
		store.append(changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));
		store.append(changeSet(POOL, 2, 2_000, keyframe(2_000L, slot(TEMPERATURE, "27.5"), slot(PH, "7.2"))));
		store.append(changeSet(POOL, 3, 3_000, set(TEMPERATURE, "28.1", 3_000L)));

		assertThat(store.lastValue(POOL, TEMPERATURE)).contains(new StoredValue("28.1", 3_000L));
		assertThat(store.lastValue(POOL, PH)).as("from the keyframe").contains(new StoredValue("7.2", 2_000L));
		assertThat(store.lastValue(OTHER, TEMPERATURE)).isEmpty();
		assertThat(store.lastValue(POOL, "https://example.org/bath#//Pool/depth")).isEmpty();
	}

	@Test
	void aLateSampleDoesNotBecomeTheLastValue() {
		StreamStore store = store();
		store.append(changeSet(POOL, 1, 5_000, set(TEMPERATURE, "28.0", 5_000L)));
		store.append(changeSet(POOL, 2, 6_000, set(TEMPERATURE, "26.0", 4_000L)));

		assertThat(store.lastValue(POOL, TEMPERATURE)).contains(new StoredValue("28.0", 5_000L));
	}

	@Test
	void anEntryWithoutTimeHasTheTimeOfItsChangeSet() {
		StreamStore store = store();
		store.append(changeSet(POOL, 1, 7_000, set(TEMPERATURE, "28.0", null)));

		assertThat(store.lastValue(POOL, TEMPERATURE)).contains(new StoredValue("28.0", 7_000L));
	}

	/** A store that applies retention but cannot run this yet overrides it, disabled with the reason. */
	@Test
	protected void retentionKeepsTheNewestEntriesOfASlotUpToTheCount() {
		StreamStore store = store();
		store.append(changeSet(POOL, 1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));
		store.append(changeSet(POOL, 2, 2_000, set(TEMPERATURE, "27.5", 2_000L), set(PH, "7.1", 2_000L)));
		store.append(changeSet(POOL, 3, 3_000, keyframe(3_000L, slot(TEMPERATURE, "27.5"))));
		store.append(changeSet(POOL, 4, 4_000, set(TEMPERATURE, "28.0", 4_000L)));
		store.append(changeSet(POOL, 5, 5_000, set(TEMPERATURE, "28.5", 5_000L)));
		DeletionRule keepTwo = StreamMappingFactory.eINSTANCE.createDeletionRule();
		keepTwo.setMaxCount(2);

		assumeTrue(store.retain(POOL, TEMPERATURE, keepTwo, 6_000) == RetentionOutcome.APPLIED,
				"the store applies retention");

		List<ChangeSet> kept = store.read(POOL, 1, 5);
		assertThat(kept).as("a change set left without entries goes").extracting(ChangeSet::getSequence)
				.containsExactly(2L, 3L, 4L, 5L);
		assertThat(kept.get(0).getEntries()).extracting(ChangeEntry::getFeatureUri).containsExactly(PH);
		assertThat(kept.get(1).getEntries()).as("keyframes stay").singleElement()
				.satisfies(e -> assertThat(e.getKind()).isEqualTo(DeltaKind.KEYFRAME));
		assertThat(store.lastValue(POOL, TEMPERATURE)).contains(new StoredValue("28.5", 5_000L));
	}

	/** A store that applies retention but cannot run this yet overrides it, disabled with the reason. */
	@Test
	protected void retentionRemovesTheEntriesOfASlotOlderThanTheHorizon() {
		StreamStore store = store();
		store.append(changeSet(POOL, 1, 5_500, set(TEMPERATURE, "27.0", 1_000L), set(TEMPERATURE, "27.4", 5_400L)));
		store.append(changeSet(POOL, 2, 3_000, set(PH, "7.0", 3_000L)));
		DeletionRule oneSecond = StreamMappingFactory.eINSTANCE.createDeletionRule();
		oneSecond.setRetention(1);
		oneSecond.setRetentionUnit(DurationUnit.SECONDS);

		assumeTrue(store.retain(POOL, TEMPERATURE, oneSecond, 6_000) == RetentionOutcome.APPLIED,
				"the store applies retention");

		assertThat(store.read(POOL, 1, 2)).extracting(ChangeSet::getSequence).containsExactly(1L, 2L);
		assertThat(store.read(POOL, 1, 1).get(0).getEntries()).as("by the entry's own time")
				.extracting(ChangeEntry::getValueNew).containsExactly("27.4");
		assertThat(store.read(POOL, 2, 2).get(0).getEntries()).as("other slots stay").hasSize(1);
	}

	/**
	 * @param streamId the stream
	 * @param sequence its sequence
	 * @param time the change set's time
	 * @param entries its entries
	 * @return a valid change set
	 */
	protected static ChangeSet changeSet(String streamId, long sequence, long time, ChangeEntry... entries) {
		ChangeSet changeSet = STREAM.createChangeSet();
		changeSet.setId(streamId + "@" + sequence);
		changeSet.setStreamId(streamId);
		changeSet.setSequence(sequence);
		changeSet.setTimestamp(time);
		changeSet.setCommitTime(time);
		changeSet.setContextFingerprint("fp");
		changeSet.setAuthor("contract");
		changeSet.getEntries().addAll(List.of(entries));
		return changeSet;
	}

	/**
	 * @param featureUri the slot
	 * @param value the new value
	 * @param time its event time, or null for the change set's
	 * @return a SET entry
	 */
	protected static ChangeEntry set(String featureUri, String value, Long time) {
		ChangeEntry entry = entry(DeltaKind.SET, featureUri);
		entry.setValueNew(value);
		entry.setTimestamp(time);
		return entry;
	}

	/**
	 * @param time the keyframe's event time
	 * @param slots its slots
	 * @return a KEYFRAME entry
	 */
	protected static ChangeEntry keyframe(Long time, SlotValue... slots) {
		ChangeEntry entry = STREAM.createChangeEntry();
		entry.setKind(DeltaKind.KEYFRAME);
		entry.setObjectId("wellenbecken");
		entry.setClassUri("https://example.org/bath#//Pool");
		entry.setTimestamp(time);
		entry.getState().addAll(List.of(slots));
		return entry;
	}

	/**
	 * @param featureUri the slot
	 * @param value its value
	 * @return a keyframe slot
	 */
	protected static SlotValue slot(String featureUri, String value) {
		SlotValue slot = STREAM.createSlotValue();
		slot.setFeatureUri(featureUri);
		slot.setValue(value);
		return slot;
	}

	private static ChangeEntry entry(DeltaKind kind, String featureUri) {
		ChangeEntry entry = STREAM.createChangeEntry();
		entry.setKind(kind);
		entry.setObjectId("wellenbecken");
		entry.setFeatureUri(featureUri);
		return entry;
	}
}
