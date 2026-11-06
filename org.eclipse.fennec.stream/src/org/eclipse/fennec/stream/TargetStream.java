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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamSlot;

/**
 * Everything the event core keeps about the stream of one target object: sequence, slot states,
 * pending batches, keyframe bookkeeping. Touched only from the target's lane, so not thread-safe;
 * the lane hand-over between mailboxes is ordered by the core's concurrent maps.
 */
final class TargetStream {

	/** One value that passed its rules and waits in a batch. */
	static final class Pending {
		final StreamSlot slot;
		final String address;
		Object value;
		String valueNew;
		final String valueOld;
		long eventTime;
		final RecordingMode recording;
		final EObject source;

		Pending(StreamSlot slot, String address, Object value, String valueNew, String valueOld, long eventTime,
				RecordingMode recording, EObject source) {
			this.slot = slot;
			this.address = address;
			this.value = value;
			this.valueNew = valueNew;
			this.valueOld = valueOld;
			this.eventTime = eventTime;
			this.recording = recording;
			this.source = source;
		}
	}

	/** A batch being filled. */
	static final class Batch {
		final List<Pending> pending = new ArrayList<>();
		boolean flushScheduled;
	}

	final String streamId;
	final String targetId;
	final StreamMapping mapping;
	final String group;
	long sequence;
	long lastKeyframeTime = -1;
	int setsSinceKeyframe;
	final Map<String, SlotState> states = new LinkedHashMap<>();
	final Map<String, StreamSlot> slots = new LinkedHashMap<>();
	final Map<String, Long> projectedTimes = new LinkedHashMap<>();
	final Map<String, Batch> batches = new LinkedHashMap<>();

	TargetStream(String streamId, String targetId, StreamMapping mapping, String group, long sequence) {
		this.streamId = streamId;
		this.targetId = targetId;
		this.mapping = mapping;
		this.group = group;
		this.sequence = sequence;
	}
}
