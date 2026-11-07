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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.Recording;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.DeletionRule;

/**
 * The in-memory reference {@link StreamStore}. Like a real backend it keeps no stream objects: a
 * change set is taken apart into a header and flat rows on {@link #append} and built anew on
 * {@link #read} - so the round trip through a store's own layout is exercised here first
 * (emf.persistence-jpa#209). Retention removes rows; a change set without rows goes. Thread-safe.
 */
public final class InMemoryStreamStore implements StreamStore {

	private static final StreamFactory FACTORY = StreamFactory.eINSTANCE;

	/** The header of a stored change set. */
	private record Header(String id, long sequence, long timestamp, long commitTime, String fingerprint, String author,
			String reason, String transactionId, boolean manifest) {
	}

	/** One addressed slot of a stored keyframe. */
	private record Slot(int featureId, String featureUri, int index, String mapKey, int[] coords, String value) {
	}

	/** One stored entry. */
	private record Row(DeltaKind kind, String objectId, String classUri, int classId, int featureId, String featureUri,
			Recording recording, int index, int toIndex, String mapKey, int[] coords, String valueOld, String valueNew,
			Long timestamp, List<Slot> state) {
	}

	/** A stored change set: header and rows. */
	private record Batch(Header header, List<Row> rows) {
	}

	private final Map<String, TreeMap<Long, Batch>> streams = new ConcurrentHashMap<>();
	private final Map<String, Map<String, StoredValue>> lastValues = new ConcurrentHashMap<>();

	@Override
	public void append(ChangeSet changeSet) {
		requireNonNull(changeSet, "ChangeSet is required");
		Header header = new Header(changeSet.getId(), changeSet.getSequence(), changeSet.getTimestamp(),
				changeSet.getCommitTime(), changeSet.getContextFingerprint(), changeSet.getAuthor(), changeSet.getReason(),
				changeSet.getTransactionId(), changeSet.isManifest());
		List<Row> rows = new ArrayList<>();
		Map<String, StoredValue> values = lastValues.computeIfAbsent(changeSet.getStreamId(), id -> new ConcurrentHashMap<>());
		for (ChangeEntry entry : changeSet.getEntries()) {
			Row row = row(entry);
			rows.add(row);
			long time = row.timestamp() != null ? row.timestamp() : header.timestamp();
			if (row.kind() == DeltaKind.KEYFRAME) {
				for (Slot slot : row.state()) {
					if (slot.featureUri() != null) {
						values.merge(slot.featureUri(), new StoredValue(slot.value(), time), InMemoryStreamStore::newer);
					}
				}
			} else if (row.featureUri() != null) {
				values.merge(row.featureUri(), new StoredValue(row.valueNew(), time), InMemoryStreamStore::newer);
			}
		}
		TreeMap<Long, Batch> stream = streams.computeIfAbsent(changeSet.getStreamId(), id -> new TreeMap<>());
		synchronized (stream) {
			stream.put(header.sequence(), new Batch(header, List.copyOf(rows)));
		}
	}

	@Override
	public long lastSequence(String streamId) {
		TreeMap<Long, Batch> stream = streams.get(streamId);
		if (stream == null) {
			return 0;
		}
		synchronized (stream) {
			return stream.isEmpty() ? 0 : stream.lastKey();
		}
	}

	@Override
	public Optional<StoredValue> lastValue(String streamId, String slotAddress) {
		Map<String, StoredValue> values = lastValues.get(streamId);
		return Optional.ofNullable(values == null ? null : values.get(slotAddress));
	}

	@Override
	public List<ChangeSet> read(String streamId, long fromSequence, long toSequence) {
		TreeMap<Long, Batch> stream = streams.get(streamId);
		if (stream == null || fromSequence > toSequence) {
			return List.of();
		}
		List<Batch> batches;
		synchronized (stream) {
			batches = new ArrayList<>(stream.subMap(fromSequence, true, toSequence, true).values());
		}
		List<ChangeSet> result = new ArrayList<>(batches.size());
		for (Batch batch : batches) {
			result.add(changeSet(streamId, batch));
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
		TreeMap<Long, Batch> stream = streams.get(streamId);
		if (stream == null || rule == null) {
			return RetentionOutcome.APPLIED;
		}
		long horizon = rule.getRetention() > 0 ? now - ChangeRules.millis(rule.getRetention(), rule.getRetentionUnit())
				: Long.MIN_VALUE;
		synchronized (stream) {
			int ofSlot = 0;
			for (Batch batch : stream.values()) {
				for (Row row : batch.rows()) {
					if (slotAddress.equals(row.featureUri())) {
						ofSlot++;
					}
				}
			}
			int toDrop = rule.getMaxCount() > 0 ? Math.max(0, ofSlot - rule.getMaxCount()) : 0;
			for (Iterator<Map.Entry<Long, Batch>> it = stream.entrySet().iterator(); it.hasNext();) {
				Map.Entry<Long, Batch> held = it.next();
				Batch batch = held.getValue();
				List<Row> kept = new ArrayList<>(batch.rows().size());
				for (Row row : batch.rows()) {
					boolean ofThisSlot = slotAddress.equals(row.featureUri());
					long time = row.timestamp() != null ? row.timestamp() : batch.header().timestamp();
					if (ofThisSlot && (toDrop > 0 || time < horizon)) {
						if (toDrop > 0) {
							toDrop--;
						}
						continue;
					}
					kept.add(row);
				}
				if (kept.isEmpty()) {
					it.remove();
				} else if (kept.size() != batch.rows().size()) {
					held.setValue(new Batch(batch.header(), List.copyOf(kept)));
				}
			}
		}
		return RetentionOutcome.APPLIED;
	}

	private static Row row(ChangeEntry entry) {
		List<Slot> state = new ArrayList<>(entry.getState().size());
		for (SlotValue slot : entry.getState()) {
			state.add(new Slot(slot.getFeatureId(), slot.getFeatureUri(), slot.getIndex(), slot.getMapKey(),
					ints(slot.getCoords()), slot.getValue()));
		}
		return new Row(entry.getKind(), entry.getObjectId(), entry.getClassUri(), entry.getClassId(), entry.getFeatureId(),
				entry.getFeatureUri(), entry.isSetRecording() ? entry.getRecording() : null, entry.getIndex(), entry.getToIndex(),
				entry.getMapKey(), ints(entry.getCoords()), entry.getValueOld(), entry.getValueNew(), entry.getTimestamp(),
				List.copyOf(state));
	}

	private static ChangeSet changeSet(String streamId, Batch batch) {
		Header header = batch.header();
		ChangeSet changeSet = FACTORY.createChangeSet();
		changeSet.setId(header.id());
		changeSet.setStreamId(streamId);
		changeSet.setSequence(header.sequence());
		changeSet.setTimestamp(header.timestamp());
		changeSet.setCommitTime(header.commitTime());
		changeSet.setContextFingerprint(header.fingerprint());
		changeSet.setAuthor(header.author());
		changeSet.setReason(header.reason());
		changeSet.setTransactionId(header.transactionId());
		changeSet.setManifest(header.manifest());
		for (Row row : batch.rows()) {
			ChangeEntry entry = FACTORY.createChangeEntry();
			entry.setKind(row.kind());
			entry.setObjectId(row.objectId());
			entry.setClassUri(row.classUri());
			entry.setClassId(row.classId());
			entry.setFeatureId(row.featureId());
			entry.setFeatureUri(row.featureUri());
			if (row.recording() != null) {
				entry.setRecording(row.recording());
			}
			entry.setIndex(row.index());
			entry.setToIndex(row.toIndex());
			entry.setMapKey(row.mapKey());
			addAll(entry.getCoords(), row.coords());
			entry.setValueOld(row.valueOld());
			entry.setValueNew(row.valueNew());
			entry.setTimestamp(row.timestamp());
			for (Slot slot : row.state()) {
				SlotValue value = FACTORY.createSlotValue();
				value.setFeatureId(slot.featureId());
				value.setFeatureUri(slot.featureUri());
				value.setIndex(slot.index());
				value.setMapKey(slot.mapKey());
				addAll(value.getCoords(), slot.coords());
				value.setValue(slot.value());
				entry.getState().add(value);
			}
			changeSet.getEntries().add(entry);
		}
		return changeSet;
	}

	private static int[] ints(List<Integer> values) {
		int[] result = new int[values.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = values.get(i);
		}
		return result;
	}

	private static void addAll(List<Integer> target, int[] values) {
		for (int value : values) {
			target.add(value);
		}
	}

	private static StoredValue newer(StoredValue held, StoredValue offered) {
		return offered.eventTime() >= held.eventTime() ? offered : held;
	}
}
