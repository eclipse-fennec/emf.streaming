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

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.Lateness;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.mapping.Mappings;
import org.eclipse.fennec.model.mapping.Slot;

/**
 * The effective settings of a stream slot: its own value where it sets one, else its
 * {@link StreamMapping}'s, else the built-in default (requirement STR-02).
 * <p>
 * Built-in defaults: mode {@code PROJECT}, recording {@code TIMESERIES}, lateness by recording
 * ({@code TIMESERIES} → {@code EVENT_TIME}, {@code CHANGELOG} → {@code ARRIVAL}), batching
 * {@code IMMEDIATE}, execution group {@value #DEFAULT_GROUP}. A slot outside a stream mapping gets
 * the built-in defaults.
 */
public final class StreamSlots {

	/** The execution group of a slot and mapping that name none. */
	public static final String DEFAULT_GROUP = "default";

	private StreamSlots() {
	}

	/**
	 * @param slot the slot
	 * @return the stream mapping containing it, or {@code null}
	 */
	public static StreamMapping mappingOf(StreamSlot slot) {
		return slot.eContainer() instanceof StreamMapping mapping ? mapping : null;
	}

	/**
	 * @param slot the slot
	 * @return whether its value is written into the target object
	 */
	public static ProjectionMode mode(StreamSlot slot) {
		requireNonNull(slot, "StreamSlot is required");
		if (slot.isSetMode()) {
			return slot.getMode();
		}
		StreamMapping mapping = mappingOf(slot);
		return mapping == null ? ProjectionMode.PROJECT : mapping.getMode();
	}

	/**
	 * @param slot the slot
	 * @return how its values are recorded
	 */
	public static RecordingMode recording(StreamSlot slot) {
		requireNonNull(slot, "StreamSlot is required");
		if (slot.isSetRecording()) {
			return slot.getRecording();
		}
		StreamMapping mapping = mappingOf(slot);
		return mapping == null ? RecordingMode.TIMESERIES : mapping.getRecording();
	}

	/**
	 * @param slot the slot
	 * @return what a late value does
	 */
	public static Lateness lateness(StreamSlot slot) {
		requireNonNull(slot, "StreamSlot is required");
		if (slot.isSetLateness()) {
			return slot.getLateness();
		}
		StreamMapping mapping = mappingOf(slot);
		if (mapping != null && mapping.isSetLateness()) {
			return mapping.getLateness();
		}
		return recording(slot) == RecordingMode.TIMESERIES ? Lateness.EVENT_TIME : Lateness.ARRIVAL;
	}

	/**
	 * @param slot the slot
	 * @return its batching; a fresh {@code IMMEDIATE} strategy when neither slot nor mapping sets one
	 */
	public static BatchStrategy batch(StreamSlot slot) {
		requireNonNull(slot, "StreamSlot is required");
		if (slot.getBatch() != null) {
			return slot.getBatch();
		}
		StreamMapping mapping = mappingOf(slot);
		if (mapping != null && mapping.getBatch() != null) {
			return mapping.getBatch();
		}
		BatchStrategy immediate = StreamMappingFactory.eINSTANCE.createBatchStrategy();
		immediate.setKind(BatchKind.IMMEDIATE);
		return immediate;
	}

	/**
	 * @param slot the slot
	 * @return the name of the execution group its target objects run in
	 */
	public static String executionGroup(StreamSlot slot) {
		requireNonNull(slot, "StreamSlot is required");
		if (isSet(slot.getExecutionGroup())) {
			return slot.getExecutionGroup();
		}
		StreamMapping mapping = mappingOf(slot);
		return mapping != null && isSet(mapping.getExecutionGroup()) ? mapping.getExecutionGroup() : DEFAULT_GROUP;
	}

	/**
	 * The execution groups the slots of a mapping name; a target object of the mapping runs in the
	 * highest-ranked of them, as the event core ranks its groups (requirement STR-09).
	 *
	 * @param mapping the mapping
	 * @return the group names, in slot order, without duplicates
	 */
	public static Set<String> executionGroups(StreamMapping mapping) {
		requireNonNull(mapping, "StreamMapping is required");
		Set<String> groups = new LinkedHashSet<>();
		for (Slot slot : mapping.getSlots()) {
			if (slot instanceof StreamSlot streamSlot) {
				groups.add(executionGroup(streamSlot));
			}
		}
		if (groups.isEmpty()) {
			groups.add(isSet(mapping.getExecutionGroup()) ? mapping.getExecutionGroup() : DEFAULT_GROUP);
		}
		return groups;
	}

	/**
	 * @param slot the slot
	 * @return the uri of its target feature, or its name for a named slot
	 */
	public static String address(StreamSlot slot) {
		if (slot.getTargetFeature() == null) {
			return slot.getName();
		}
		return Mappings.uriOf(slot.getTargetFeature().getEContainingClass()) + "/" + slot.getTargetFeature().getName();
	}

	private static boolean isSet(String text) {
		return text != null && !text.isBlank();
	}
}
