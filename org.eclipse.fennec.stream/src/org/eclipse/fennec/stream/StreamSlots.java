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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.Lateness;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.mapping.Mappings;
import org.eclipse.fennec.model.mapping.MappingFactory;
import org.eclipse.fennec.model.mapping.Slot;
import org.eclipse.fennec.model.mapping.ValueSource;

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
	 * The mapping as the core streams it. With {@code includeUnmapped = true} every attribute of the
	 * target that no slot names and {@code excludedFeatures} does not list gets a slot of its own,
	 * read as-is from the source (which is then the target class itself) and with the mapping's
	 * defaults. Derived, transient and volatile attributes are left out, references need a slot of
	 * their own (#47).
	 *
	 * @param mapping the mapping as registered, not changed
	 * @return the mapping itself when it includes nothing unmapped, else an expanded copy
	 */
	public static StreamMapping expand(StreamMapping mapping) {
		requireNonNull(mapping, "StreamMapping is required");
		if (!Boolean.TRUE.equals(mapping.getIncludeUnmapped()) || mapping.getTarget() == null) {
			return mapping;
		}
		StreamMapping expanded = EcoreUtil.copy(mapping);
		Set<EStructuralFeature> named = new HashSet<>(mapping.getExcludedFeatures());
		for (Slot slot : mapping.getSlots()) {
			named.add(slot.getTargetFeature());
		}
		for (EAttribute attribute : mapping.getTarget().getEAllAttributes()) {
			if (named.contains(attribute) || attribute.isDerived() || attribute.isTransient() || attribute.isVolatile()) {
				continue;
			}
			ValueSource asIs = MappingFactory.eINSTANCE.createValueSource();
			asIs.getFeaturePath().add(attribute);
			StreamSlot slot = StreamMappingFactory.eINSTANCE.createStreamSlot();
			slot.setTargetFeature(attribute);
			slot.setValue(asIs);
			expanded.getSlots().add(slot);
		}
		return expanded;
	}

	/**
	 * The slots whose feature path follows a reference that is not a containment: they read another
	 * object, and a change there offers nothing, so the slot keeps its old value until the source
	 * itself is offered again (#49).
	 *
	 * @param mapping the mapping
	 * @return one message per such slot, empty when every path stays in the source's aggregate
	 */
	public static List<String> pathsLeavingTheAggregate(StreamMapping mapping) {
		requireNonNull(mapping, "StreamMapping is required");
		List<String> messages = new ArrayList<>();
		for (Slot slot : mapping.getSlots()) {
			if (slot.getValue() == null) {
				continue;
			}
			for (EStructuralFeature step : slot.getValue().getFeaturePath()) {
				if (step instanceof EReference reference && !reference.isContainment()) {
					messages.add("Stream mapping " + mapping.getId() + ", slot "
							+ (slot.getTargetFeature() == null ? slot.getName() : slot.getTargetFeature().getName())
							+ ": its path follows the reference '" + reference.getName()
							+ "' out of the source's aggregate; a change there is not streamed until the source is offered again");
					break;
				}
			}
		}
		return messages;
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
