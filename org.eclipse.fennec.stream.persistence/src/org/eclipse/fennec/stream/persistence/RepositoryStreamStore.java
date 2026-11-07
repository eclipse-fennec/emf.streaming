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
package org.eclipse.fennec.stream.persistence;

import static java.util.Objects.requireNonNull;
import static org.eclipse.fennec.model.query.builder.Expressions.and;
import static org.eclipse.fennec.model.query.builder.Expressions.path;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.command.CommandFactory;
import org.eclipse.fennec.model.command.DeleteCommand;
import org.eclipse.fennec.model.query.Query;
import org.eclipse.fennec.model.query.builder.QueryBuilder;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamPackage;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.persistence.query.api.QueryResult;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.ChangeRules;
import org.eclipse.fennec.stream.StreamStore;

/**
 * A {@link StreamStore} on a Fennec persistence repository (JPA or Mongo): a change set is saved as
 * an ordinary model, with its entries and keyframe slots; reading is a query on stream id and
 * sequence. The repository needs the stream model ({@code stream.ecore}) in its unit or database.
 * <p>
 * A copy is saved, so the change set the core hands on to its consumers stays untouched.
 * {@link #lastValue} walks the stream backwards in pages and stops at the first entry or keyframe
 * slot of the address - at the latest at the stream's last keyframe.
 * <p>
 * {@link #retain} has the semantics of the in-memory store: it removes the entries of one slot that
 * are beyond the rule's count or older than its horizon (keyframes stay), saves the change sets it
 * changed and deletes those left without entries. It reads the whole stream in pages, so it is meant
 * for the periodic sweep, not for every change set.
 * <p>
 * Thread-safe: every call borrows a repository instance of its own from the {@link RepositoryAccess}.
 */
public final class RepositoryStreamStore implements StreamStore {

	private static final StreamPackage STREAM = StreamPackage.eINSTANCE;
	private static final int PAGE = 20;

	private final RepositoryAccess repositories;

	/**
	 * @param repositories the repository instances
	 */
	public RepositoryStreamStore(RepositoryAccess repositories) {
		this.repositories = requireNonNull(repositories, "RepositoryAccess is required");
	}

	@Override
	public void append(ChangeSet changeSet) {
		requireNonNull(changeSet, "ChangeSet is required");
		ChangeSet copy = EcoreUtil.copy(changeSet);
		call(repository -> {
			repository.save(copy);
			return null;
		});
	}

	@Override
	public long lastSequence(String streamId) {
		Query newest = stream(streamId).orderByDesc(STREAM.getChangeSet_Sequence()).top(1).build();
		return call(repository -> {
			try (QueryResult result = repository.find(newest)) {
				return changeSets(result.objects()).findFirst().map(ChangeSet::getSequence).orElse(0L);
			}
		});
	}

	@Override
	public List<ChangeSet> read(String streamId, long fromSequence, long toSequence) {
		if (fromSequence > toSequence) {
			return List.of();
		}
		Query range = QueryBuilder.from(STREAM.getChangeSet())
				.where(and(path(STREAM.getChangeSet_StreamId()).eq(streamId),
						path(STREAM.getChangeSet_Sequence()).ge(fromSequence),
						path(STREAM.getChangeSet_Sequence()).le(toSequence)))
				.orderByAsc(STREAM.getChangeSet_Sequence()).build();
		return call(repository -> {
			try (QueryResult result = repository.find(range)) {
				return changeSets(result.objects()).toList();
			}
		});
	}

	@Override
	public Optional<StoredValue> lastValue(String streamId, String slotAddress) {
		requireNonNull(slotAddress, "Slot address is required");
		for (int skip = 0;; skip += PAGE) {
			Query page = stream(streamId).orderByDesc(STREAM.getChangeSet_Sequence()).skip(skip).top(PAGE).build();
			List<ChangeSet> newestFirst = call(repository -> {
				try (QueryResult result = repository.find(page)) {
					return changeSets(result.objects()).toList();
				}
			});
			for (ChangeSet changeSet : newestFirst) {
				Optional<StoredValue> value = valueIn(changeSet, slotAddress);
				if (value.isPresent()) {
					return value;
				}
			}
			if (newestFirst.size() < PAGE) {
				return Optional.empty();
			}
		}
	}

	@Override
	public RetentionOutcome retain(String streamId, String slotAddress, DeletionRule rule, long now) {
		requireNonNull(slotAddress, "Slot address is required");
		if (rule == null) {
			return RetentionOutcome.APPLIED;
		}
		long horizon = rule.getRetention() > 0 ? now - ChangeRules.millis(rule.getRetention(), rule.getRetentionUnit())
				: Long.MIN_VALUE;
		if (rule.getMaxCount() <= 0 && horizon == Long.MIN_VALUE) {
			return RetentionOutcome.APPLIED;
		}
		return call(repository -> {
			List<ChangeSet> oldestFirst = readAll(repository, streamId);
			int ofSlot = 0;
			for (ChangeSet changeSet : oldestFirst) {
				for (ChangeEntry entry : changeSet.getEntries()) {
					if (slotAddress.equals(entry.getFeatureUri())) {
						ofSlot++;
					}
				}
			}
			int toDrop = rule.getMaxCount() > 0 ? Math.max(0, ofSlot - rule.getMaxCount()) : 0;
			for (ChangeSet changeSet : oldestFirst) {
				List<ChangeEntry> dropped = new ArrayList<>();
				for (ChangeEntry entry : changeSet.getEntries()) {
					long time = entry.getTimestamp() != null ? entry.getTimestamp() : changeSet.getTimestamp();
					if (slotAddress.equals(entry.getFeatureUri()) && (toDrop > 0 || time < horizon)) {
						if (toDrop > 0) {
							toDrop--;
						}
						dropped.add(entry);
					}
				}
				if (dropped.size() == changeSet.getEntries().size()) {
					repository.execute(delete(changeSet));
				} else if (!dropped.isEmpty()) {
					changeSet.getEntries().removeAll(dropped);
					repository.save(changeSet);
				}
			}
			return RetentionOutcome.APPLIED;
		});
	}

	private static DeleteCommand delete(ChangeSet changeSet) {
		DeleteCommand delete = CommandFactory.eINSTANCE.createDeleteCommand();
		delete.setName("retain " + changeSet.getId());
		delete.setSelector(QueryBuilder.from(STREAM.getChangeSet()).where(path(STREAM.getChangeSet_Id()).eq(changeSet.getId())).build());
		return delete;
	}

	private static List<ChangeSet> readAll(Repository repository, String streamId) throws IOException {
		List<ChangeSet> all = new ArrayList<>();
		for (int skip = 0;; skip += PAGE) {
			Query page = stream(streamId).orderByAsc(STREAM.getChangeSet_Sequence()).skip(skip).top(PAGE).build();
			try (QueryResult result = repository.find(page)) {
				List<ChangeSet> changeSets = changeSets(result.objects()).toList();
				all.addAll(changeSets);
				if (changeSets.size() < PAGE) {
					return all;
				}
			}
		}
	}

	/** The newest value of the address in one change set: the last entry or keyframe slot naming it. */
	private static Optional<StoredValue> valueIn(ChangeSet changeSet, String slotAddress) {
		List<ChangeEntry> entries = changeSet.getEntries();
		for (int i = entries.size() - 1; i >= 0; i--) {
			ChangeEntry entry = entries.get(i);
			long time = entry.getTimestamp() != null ? entry.getTimestamp() : changeSet.getTimestamp();
			if (entry.getKind() == DeltaKind.KEYFRAME) {
				for (SlotValue slot : entry.getState()) {
					if (slotAddress.equals(slot.getFeatureUri())) {
						return Optional.of(new StoredValue(slot.getValue(), time));
					}
				}
			} else if (slotAddress.equals(entry.getFeatureUri())) {
				return Optional.of(new StoredValue(entry.getValueNew(), time));
			}
		}
		return Optional.empty();
	}

	private static QueryBuilder stream(String streamId) {
		return QueryBuilder.from(STREAM.getChangeSet()).where(path(STREAM.getChangeSet_StreamId()).eq(streamId));
	}

	private static Stream<ChangeSet> changeSets(Stream<EObject> objects) {
		return objects.filter(ChangeSet.class::isInstance).map(ChangeSet.class::cast);
	}

	private <T> T call(RepositoryAccess.RepositoryCall<T> call) {
		try {
			return repositories.call(call);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
