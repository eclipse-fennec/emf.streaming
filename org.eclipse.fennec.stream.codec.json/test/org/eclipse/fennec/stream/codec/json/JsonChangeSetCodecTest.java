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
package org.eclipse.fennec.stream.codec.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.junit.jupiter.api.Test;

/**
 * The JSON payload (decision D-6, requirement TRN-04).
 */
class JsonChangeSetCodecTest {

	private final JsonChangeSetCodec codec = new JsonChangeSetCodec();

	@Test
	void aChangeSetSurvivesTheRoundTrip() throws IOException {
		ChangeSet original = changeSet();

		ByteBuffer payload = codec.encode(original);
		ChangeSet copy = codec.decode(payload);

		assertThat(EcoreUtil.equals(original, copy)).as(text(codec.encode(original))).isTrue();
		assertThat(original.eResource()).isNull();
		assertThat(codec.contentType()).isEqualTo("application/json");
	}

	@Test
	void thePayloadIsJson() throws IOException {
		String json = text(codec.encode(changeSet()));
		assertThat(json).startsWith("{").contains("\"streamId\"").contains("pool-1").contains("24.5");
	}

	@Test
	void whatIsNoChangeSetIsRefused() {
		assertThatExceptionOfType(IOException.class)
				.isThrownBy(() -> codec.decode(ByteBuffer.wrap("not json".getBytes(StandardCharsets.UTF_8))));
	}

	private static ChangeSet changeSet() {
		ChangeSet set = StreamFactory.eINSTANCE.createChangeSet();
		set.setId("cs-1");
		set.setStreamId("https://example.org/pool#//Pool/pool-1");
		set.setSequence(7);
		set.setTimestamp(1_000);
		set.setCommitTime(1_005);
		set.setContextFingerprint("fp-1");
		set.setAuthor("device:t1");
		set.setReason("ingest");
		ChangeEntry value = StreamFactory.eINSTANCE.createChangeEntry();
		value.setObjectId("pool-1");
		value.setKind(DeltaKind.SET);
		value.setFeatureId(2);
		value.setFeatureUri("https://example.org/pool#//Pool/currentTemperature");
		value.setValueOld("24.0");
		value.setValueNew("24.5");
		value.setTimestamp(999L);
		set.getEntries().add(value);
		ChangeEntry keyframe = StreamFactory.eINSTANCE.createChangeEntry();
		keyframe.setObjectId("pool-1");
		keyframe.setKind(DeltaKind.KEYFRAME);
		keyframe.setClassUri("https://example.org/pool#//Pool");
		SlotValue slot = StreamFactory.eINSTANCE.createSlotValue();
		slot.setFeatureUri("https://example.org/pool#//Pool/currentTemperature");
		slot.setValue("24.5");
		keyframe.getState().add(slot);
		set.getEntries().add(keyframe);
		return set;
	}

	private static String text(ByteBuffer payload) {
		byte[] bytes = new byte[payload.remaining()];
		payload.duplicate().get(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}
}
