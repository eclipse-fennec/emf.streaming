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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.support.StreamAddresses;

/**
 * Rebuilds the state of an object from its stream (requirement APL-09): the last keyframe at or
 * before the target, then every value after it up to the target. The engine behind "as of" in an
 * audit view and behind a resync; the query integration lives in emf.persistence.
 * <p>
 * Single-valued attributes only, like the receiver. Stateless and thread-safe.
 */
public final class StreamHistory {

	private StreamHistory() {
	}

	/**
	 * The state after a sequence: the keyframe at or before it, and the entries of the change sets after
	 * the keyframe up to it, in sequence order.
	 *
	 * @param store the store
	 * @param streamId the stream
	 * @param sequence the last sequence to include
	 * @param eClass the class of the object
	 * @return the object as it was, or empty when the stream holds nothing up to the sequence
	 */
	public static Optional<EObject> stateAt(StreamStore store, String streamId, long sequence, EClass eClass) {
		return rebuild(store.read(streamId, 1, sequence), eClass, Long.MAX_VALUE);
	}

	/**
	 * The state at a point in time: per feature the value with the latest event time at or before it
	 * (later arrivals of earlier samples included), starting from the last keyframe written at or
	 * before it.
	 *
	 * @param store the store
	 * @param streamId the stream
	 * @param time epoch millis
	 * @param eClass the class of the object
	 * @return the object as it was, or empty when the stream holds nothing at that time
	 */
	public static Optional<EObject> stateAtTime(StreamStore store, String streamId, long time, EClass eClass) {
		return rebuild(store.read(streamId, 1, Long.MAX_VALUE), eClass, time);
	}

	private static Optional<EObject> rebuild(List<ChangeSet> sets, EClass eClass, long time) {
		requireNonNull(eClass, "EClass is required");
		int start = 0;
		for (int i = sets.size() - 1; i >= 0; i--) {
			ChangeSet set = sets.get(i);
			if (set.getTimestamp() <= time && hasKeyframe(set)) {
				start = i;
				break;
			}
		}
		EObject object = null;
		Map<EStructuralFeature, Long> times = new HashMap<>();
		for (int i = start; i < sets.size(); i++) {
			for (ChangeEntry entry : sets.get(i).getEntries()) {
				long entryTime = StreamAddresses.eventTime(entry);
				if (entryTime > time) {
					continue;
				}
				if (object == null) {
					object = EcoreUtil.create(eClass);
					if (eClass.getEIDAttribute() != null) {
						EcoreUtil.setID(object, entry.getObjectId());
					}
				}
				switch (entry.getKind()) {
				case KEYFRAME -> {
					for (SlotValue slot : entry.getState()) {
						EObject target = object;
						StreamAddresses.resolveFeature(eClass, slot).ifPresent(f -> {
							set(target, f, slot.getValue());
							times.put(f, entryTime);
						});
					}
				}
				case SET, UNSET -> {
					Optional<EStructuralFeature> feature = StreamAddresses.resolveFeature(eClass, entry);
					if (feature.isPresent() && entryTime >= times.getOrDefault(feature.get(), Long.MIN_VALUE)) {
						if (entry.getKind() == DeltaKind.UNSET) {
							object.eUnset(feature.get());
						} else {
							set(object, feature.get(), entry.getValueNew());
						}
						times.put(feature.get(), entryTime);
					}
				}
				case DELETE -> {
					object = null;
					times.clear();
				}
				default -> {
					// not rebuilt: references and the list/map/array kinds
				}
				}
			}
		}
		return Optional.ofNullable(object);
	}

	private static boolean hasKeyframe(ChangeSet set) {
		return set.getEntries().stream().anyMatch(e -> e.getKind() == DeltaKind.KEYFRAME);
	}

	private static void set(EObject object, EStructuralFeature feature, String literal) {
		if (feature instanceof EReference || feature.isMany() || !(feature.getEType() instanceof EDataType type)) {
			return;
		}
		if (literal == null) {
			object.eUnset(feature);
		} else {
			object.eSet(feature, EcoreUtil.createFromString(type, literal));
		}
	}
}
