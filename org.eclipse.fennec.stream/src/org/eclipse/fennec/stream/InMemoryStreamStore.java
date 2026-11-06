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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.support.StreamAddresses;
import org.eclipse.fennec.model.streammapping.DeletionRule;

/**
 * A {@link StreamStore} in memory, for tests and for runtimes that only forward. It keeps copies of
 * the change sets it is handed and returns those from {@link #read}. It applies
 * retention itself: values older than the retention or beyond {@code maxCount} are dropped, whole
 * change sets once all their entries are gone. Thread-safe.
 */
public final class InMemoryStreamStore implements StreamStore {

	private final Map<String, List<ChangeSet>> streams = new ConcurrentHashMap<>();
	private final Map<String, Map<String, StoredValue>> lastValues = new ConcurrentHashMap<>();

	@Override
	public void append(ChangeSet offered) {
		requireNonNull(offered, "ChangeSet is required");
		// keep a copy: the offered set is shared with consumers and must stay as it is
		ChangeSet changeSet = EcoreUtil.copy(offered);
		List<ChangeSet> sets = streams.computeIfAbsent(changeSet.getStreamId(), id -> new ArrayList<>());
		synchronized (sets) {
			sets.add(changeSet);
		}
		Map<String, StoredValue> values = lastValues.computeIfAbsent(changeSet.getStreamId(), id -> new ConcurrentHashMap<>());
		for (ChangeEntry entry : changeSet.getEntries()) {
			long time = StreamAddresses.eventTime(entry);
			if (entry.getKind() == DeltaKind.KEYFRAME) {
				for (SlotValue slot : entry.getState()) {
					if (slot.getFeatureUri() != null) {
						values.merge(slot.getFeatureUri(), new StoredValue(slot.getValue(), time), InMemoryStreamStore::newer);
					}
				}
			} else if (entry.getFeatureUri() != null) {
				values.merge(entry.getFeatureUri(), new StoredValue(entry.getValueNew(), time), InMemoryStreamStore::newer);
			}
		}
	}

	@Override
	public long lastSequence(String streamId) {
		List<ChangeSet> sets = streams.get(streamId);
		if (sets == null) {
			return 0;
		}
		synchronized (sets) {
			return sets.isEmpty() ? 0 : sets.get(sets.size() - 1).getSequence();
		}
	}

	@Override
	public Optional<StoredValue> lastValue(String streamId, String slotAddress) {
		Map<String, StoredValue> values = lastValues.get(streamId);
		return Optional.ofNullable(values == null ? null : values.get(slotAddress));
	}

	@Override
	public List<ChangeSet> read(String streamId, long fromSequence, long toSequence) {
		List<ChangeSet> sets = streams.get(streamId);
		if (sets == null) {
			return List.of();
		}
		List<ChangeSet> result = new ArrayList<>();
		synchronized (sets) {
			for (ChangeSet set : sets) {
				if (set.getSequence() >= fromSequence && set.getSequence() <= toSequence) {
					result.add(set);
				}
			}
		}
		return result;
	}

	/**
	 * @return the ids of all streams held
	 */
	public List<String> streamIds() {
		return List.copyOf(streams.keySet());
	}

	@Override
	public RetentionOutcome retain(String streamId, String slotAddress, DeletionRule rule, long now) {
		List<ChangeSet> sets = streams.get(streamId);
		if (sets == null || rule == null) {
			return RetentionOutcome.APPLIED;
		}
		long horizon = rule.getRetention() > 0 ? now - ChangeRules.millis(rule.getRetention(), rule.getRetentionUnit()) : Long.MIN_VALUE;
		synchronized (sets) {
			List<ChangeEntry> ofSlot = new ArrayList<>();
			for (ChangeSet set : sets) {
				for (ChangeEntry entry : set.getEntries()) {
					if (slotAddress.equals(entry.getFeatureUri())) {
						ofSlot.add(entry);
					}
				}
			}
			int keepFrom = rule.getMaxCount() > 0 ? Math.max(0, ofSlot.size() - rule.getMaxCount()) : 0;
			for (int i = 0; i < ofSlot.size(); i++) {
				ChangeEntry entry = ofSlot.get(i);
				if (i < keepFrom || StreamAddresses.eventTime(entry) < horizon) {
					((ChangeSet) entry.eContainer()).getEntries().remove(entry);
				}
			}
			sets.removeIf(set -> set.getEntries().isEmpty());
		}
		return RetentionOutcome.APPLIED;
	}

	private static StoredValue newer(StoredValue held, StoredValue offered) {
		return offered.eventTime() >= held.eventTime() ? offered : held;
	}
}
