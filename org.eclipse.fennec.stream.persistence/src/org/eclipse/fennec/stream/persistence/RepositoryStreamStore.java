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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.query.Query;
import org.eclipse.fennec.model.query.builder.QueryBuilder;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamPackage;
import org.eclipse.fennec.persistence.query.api.QueryResult;
import org.eclipse.fennec.stream.StreamStore;

/**
 * A {@link StreamStore} on a Fennec persistence repository (JPA or Mongo): a change set is saved as
 * an ordinary model, with its entries and keyframe slots; reading is a query on stream id and
 * sequence. The repository needs the stream model ({@code stream.ecore}) in its unit or database.
 * <p>
 * A copy is saved, so the change set the core hands on to its consumers stays untouched.
 * {@link #lastValue} walks the stream backwards in pages and stops at the first entry or keyframe
 * slot of the address - at the latest at the stream's last keyframe. Retention is not applied yet
 * ({@link RetentionOutcome#UNSUPPORTED}).
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
