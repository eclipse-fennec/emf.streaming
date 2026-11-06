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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.junit.jupiter.api.Test;

/**
 * The reference store round-trips through its own layout (emf.persistence-jpa#209).
 */
class InMemoryStreamStoreTest {

	private static final StreamFactory F = StreamFactory.eINSTANCE;

	@Test
	void everyFieldSurvivesTheRoundTripButNoObjectIsKept() {
		InMemoryStreamStore store = new InMemoryStreamStore();
		ChangeSet original = full();

		store.append(original);
		original.setAuthor("changed after append");
		List<ChangeSet> read = store.read("s", 1, 1);

		ChangeSet expected = full();
		assertThat(read).singleElement().satisfies(copy -> {
			assertThat(EcoreUtil.equals(expected, copy)).isTrue();
			assertThat(copy).isNotSameAs(original);
		});
		assertThat(store.read("s", 1, 1).get(0)).as("built anew on every read").isNotSameAs(read.get(0));
	}

	@Test
	void readsAreBySequenceRange() {
		InMemoryStreamStore store = new InMemoryStreamStore();
		for (long sequence = 1; sequence <= 5; sequence++) {
			ChangeSet set = full();
			set.setSequence(sequence);
			store.append(set);
		}
		assertThat(store.read("s", 2, 4)).extracting(ChangeSet::getSequence).containsExactly(2L, 3L, 4L);
		assertThat(store.read("s", 4, 2)).isEmpty();
		assertThat(store.lastSequence("s")).isEqualTo(5);
		assertThat(store.lastValue("s", "f#//C/v")).hasValueSatisfying(v -> assertThat(v.value()).isEqualTo("2"));
	}

	private static ChangeSet full() {
		ChangeSet set = F.createChangeSet();
		set.setId("cs");
		set.setStreamId("s");
		set.setSequence(1);
		set.setTimestamp(10);
		set.setCommitTime(11);
		set.setContextFingerprint("fp");
		set.setAuthor("a");
		set.setReason("r");
		set.setTransactionId("tx");
		set.setManifest(true);
		ChangeEntry put = F.createChangeEntry();
		put.setKind(DeltaKind.PUT);
		put.setObjectId("o");
		put.setClassId(3);
		put.setFeatureId(4);
		put.setFeatureUri("f#//C/m");
		put.setMapKey("k");
		put.setValueOld("1");
		put.setValueNew("2");
		put.setTimestamp(9L);
		ChangeEntry move = F.createChangeEntry();
		move.setKind(DeltaKind.MOVE);
		move.setObjectId("o");
		move.setFeatureId(5);
		move.setIndex(1);
		move.setToIndex(2);
		ChangeEntry setAt = F.createChangeEntry();
		setAt.setKind(DeltaKind.SET_AT);
		setAt.setObjectId("o");
		setAt.setFeatureUri("f#//C/v");
		setAt.getCoords().addAll(List.of(1, 2, 3));
		setAt.setValueNew("2");
		ChangeEntry keyframe = F.createChangeEntry();
		keyframe.setKind(DeltaKind.KEYFRAME);
		keyframe.setObjectId("o");
		keyframe.setClassUri("f#//C");
		SlotValue slot = F.createSlotValue();
		slot.setFeatureId(6);
		slot.setFeatureUri("f#//C/a");
		slot.setIndex(0);
		slot.setMapKey("x");
		slot.getCoords().add(7);
		slot.setValue("v");
		keyframe.getState().add(slot);
		set.getEntries().addAll(List.of(put, move, setAt, keyframe));
		return set;
	}
}
