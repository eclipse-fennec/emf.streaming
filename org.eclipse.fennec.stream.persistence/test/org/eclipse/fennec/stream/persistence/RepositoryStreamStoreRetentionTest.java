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

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.model.command.DeleteCommand;
import org.eclipse.fennec.model.query.Query;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.persistence.query.api.QueryResult;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.StreamStore.RetentionOutcome;
import org.junit.jupiter.api.Test;

/**
 * The retention decisions of the repository store, against a repository that answers every query
 * with the stored change sets (one page) and records what is saved and deleted.
 */
class RepositoryStreamStoreRetentionTest {

	private static final StreamFactory STREAM = StreamFactory.eINSTANCE;
	private static final String POOL = "https://eclipse.org/bath#//Pool/kinderland";
	private static final String TEMPERATURE = "https://eclipse.org/bath#//Pool/waterTemperature";
	private static final String PH = "https://eclipse.org/bath#//Pool/ph";

	private final List<ChangeSet> stored = new ArrayList<>();
	private final List<ChangeSet> saved = new ArrayList<>();
	private final List<DeleteCommand> deleted = new ArrayList<>();
	private final RepositoryStreamStore store = new RepositoryStreamStore(RepositoryAccess.of(repository()));

	@Test
	void entriesBeyondTheCountGoOldestFirstAndKeyframesStay() {
		stored.add(changeSet(1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));
		stored.add(changeSet(2, 2_000, set(TEMPERATURE, "27.5", 2_000L), set(PH, "7.1", 2_000L)));
		stored.add(changeSet(3, 3_000, keyframe(slot(TEMPERATURE, "27.5"))));
		stored.add(changeSet(4, 4_000, set(TEMPERATURE, "28.0", 4_000L)));
		stored.add(changeSet(5, 5_000, set(TEMPERATURE, "28.5", 5_000L)));
		DeletionRule keepTwo = StreamMappingFactory.eINSTANCE.createDeletionRule();
		keepTwo.setMaxCount(2);

		assertThat(store.retain(POOL, TEMPERATURE, keepTwo, 6_000)).isEqualTo(RetentionOutcome.APPLIED);

		assertThat(deleted).singleElement().satisfies(d -> assertThat(d.getName()).endsWith(POOL + "@1"));
		assertThat(saved).singleElement().satisfies(changeSet -> {
			assertThat(changeSet.getSequence()).isEqualTo(2);
			assertThat(changeSet.getEntries()).extracting(ChangeEntry::getFeatureUri).containsExactly(PH);
		});
	}

	@Test
	void entriesOlderThanTheHorizonGoByTheirOwnTime() {
		// the batch is new, its entry an old sample
		stored.add(changeSet(1, 5_500, set(TEMPERATURE, "27.0", 1_000L), set(TEMPERATURE, "27.4", 5_400L)));
		stored.add(changeSet(2, 5_800, set(TEMPERATURE, "27.8", null)));
		DeletionRule oneSecond = StreamMappingFactory.eINSTANCE.createDeletionRule();
		oneSecond.setRetention(1);
		oneSecond.setRetentionUnit(DurationUnit.SECONDS);

		store.retain(POOL, TEMPERATURE, oneSecond, 6_000);

		assertThat(deleted).isEmpty();
		assertThat(saved).singleElement().satisfies(changeSet -> assertThat(changeSet.getEntries())
				.extracting(ChangeEntry::getValueNew).containsExactly("27.4"));
	}

	@Test
	void aRuleWithoutCountAndHorizonTouchesNothing() {
		stored.add(changeSet(1, 1_000, set(TEMPERATURE, "27.0", 1_000L)));

		assertThat(store.retain(POOL, TEMPERATURE, StreamMappingFactory.eINSTANCE.createDeletionRule(), 6_000))
				.isEqualTo(RetentionOutcome.APPLIED);
		assertThat(store.retain(POOL, TEMPERATURE, null, 6_000)).isEqualTo(RetentionOutcome.APPLIED);

		assertThat(saved).isEmpty();
		assertThat(deleted).isEmpty();
	}

	private Repository repository() {
		return (Repository) Proxy.newProxyInstance(Repository.class.getClassLoader(), new Class<?>[] { Repository.class },
				(proxy, method, args) -> switch (method.getName()) {
				case "find" -> result(args[0]);
				case "save" -> {
					saved.add((ChangeSet) args[0]);
					yield null;
				}
				case "execute" -> {
					deleted.add((DeleteCommand) args[0]);
					yield 1L;
				}
				case "toString" -> "repository";
				default -> throw new UnsupportedOperationException(method.getName());
				});
	}

	private QueryResult result(Object query) {
		assertThat(query).isInstanceOf(Query.class);
		List<EObject> objects = new ArrayList<>(stored);
		return (QueryResult) Proxy.newProxyInstance(QueryResult.class.getClassLoader(), new Class<?>[] { QueryResult.class },
				(proxy, method, args) -> switch (method.getName()) {
				case "objects" -> objects.stream();
				case "close" -> null;
				default -> Stream.empty();
				});
	}

	private static ChangeSet changeSet(long sequence, long time, ChangeEntry... entries) {
		ChangeSet changeSet = STREAM.createChangeSet();
		changeSet.setId(POOL + "@" + sequence);
		changeSet.setStreamId(POOL);
		changeSet.setSequence(sequence);
		changeSet.setTimestamp(time);
		changeSet.setAuthor("test");
		changeSet.getEntries().addAll(List.of(entries));
		return changeSet;
	}

	private static ChangeEntry set(String featureUri, String value, Long time) {
		ChangeEntry entry = STREAM.createChangeEntry();
		entry.setKind(DeltaKind.SET);
		entry.setObjectId("kinderland");
		entry.setFeatureUri(featureUri);
		entry.setValueNew(value);
		entry.setTimestamp(time);
		return entry;
	}

	private static ChangeEntry keyframe(SlotValue... slots) {
		ChangeEntry entry = STREAM.createChangeEntry();
		entry.setKind(DeltaKind.KEYFRAME);
		entry.setObjectId("kinderland");
		entry.getState().addAll(List.of(slots));
		return entry;
	}

	private static SlotValue slot(String featureUri, String value) {
		SlotValue slot = STREAM.createSlotValue();
		slot.setFeatureUri(featureUri);
		slot.setValue(value);
		return slot;
	}
}
