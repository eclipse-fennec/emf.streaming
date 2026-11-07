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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.Recording;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.GuardPolicy;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.stream.StreamReceiver.GapPolicy;
import org.eclipse.fennec.stream.StreamReceiver.Outcome;
import org.eclipse.fennec.stream.StreamReceiver.State;
import org.eclipse.fennec.stream.StreamReceiver.UnknownObjectPolicy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Applying received change sets (requirements APL-01..05, OPS-01..03; UC-29).
 */
class StreamReceiverTest {

	private static final Duration WAIT = Duration.ofSeconds(10);
	private static final String FP = "https://example.org/pool";

	private PoolStreams fx;
	private EventCore replica;
	private ReplicaTargets targets;
	private EPackage.Registry models;

	@BeforeEach
	void setUp() {
		fx = new PoolStreams();
		replica = fx.core(new InMemoryStreamStore()).build();
		targets = ReplicaTargets.inMemory();
		models = new EPackageRegistryImpl();
		models.put(fx.ePackage.getNsURI(), fx.ePackage);
	}

	@AfterEach
	void tearDown() {
		replica.close();
	}

	@Test
	void aReplicaFollowsItsOriginFromTheFirstKeyframe() throws Exception {
		fx.registry.register(fx.mapping());
		fx.pool("pool-1");
		InMemoryStreamStore originStore = new InMemoryStreamStore();
		try (EventCore origin = fx.core(originStore).build()) {
			origin.offer(fx.temp("t1", "pool-1", 24.5), 1_000, null);
			origin.offer(fx.ph("ph-7", 7.2), 2_000, null);
			origin.offer(fx.temp("t1", "pool-1", 25.0), 3_000, null);
			assertThat(origin.awaitIdle(WAIT)).isTrue();
		}
		StreamReceiver receiver = receiver().build();
		String stream = fx.stream("pool-1");

		List<Outcome> outcomes = originStore.read(stream, 1, Long.MAX_VALUE).stream()
				.map(set -> outcome(receiver.receive(set))).toList();

		// value before the keyframe dropped; keyframe and the rest applied
		assertThat(outcomes).containsExactly(Outcome.DROPPED, Outcome.APPLIED, Outcome.APPLIED, Outcome.APPLIED);
		EObject copy = targets.find(fx.pool, "pool-1");
		assertThat(copy.eGet(fx.poolId)).isEqualTo("pool-1");
		assertThat(copy.eGet(fx.currentTemperature)).isEqualTo(25.0);
		assertThat(copy.eGet(fx.currentPh)).isEqualTo(7.2);
		assertThat(receiver.state(stream)).isEqualTo(State.COMPLETE);
		assertThat(replica.store().lastSequence(stream)).isEqualTo(4);
	}

	@Test
	void fromDeltasAnObjectIsIncompleteUntilItsKeyframe() throws Exception {
		StreamReceiver receiver = receiver().unknownObjects(UnknownObjectPolicy.CREATE_FROM_DELTAS).build();
		String stream = fx.stream("pool-1");

		assertThat(outcome(receiver.receive(set(1, sample("currentTemperature", "24.5", null))))).isEqualTo(Outcome.APPLIED);
		assertThat(receiver.state(stream)).isEqualTo(State.INCOMPLETE);
		assertThat(targets.find(fx.pool, "pool-1").eGet(fx.currentTemperature)).isEqualTo(24.5);

		assertThat(outcome(receiver.receive(set(2, keyframe("currentTemperature", "24.5", "currentPh", "7.0"))))).isEqualTo(Outcome.APPLIED);
		assertThat(receiver.state(stream)).isEqualTo(State.COMPLETE);
		assertThat(targets.find(fx.pool, "pool-1").eGet(fx.currentPh)).isEqualTo(7.0);
	}

	@Test
	void aDuplicateIsIgnored() throws Exception {
		StreamReceiver receiver = receiver().build();
		ChangeSet first = set(1, keyframe("currentTemperature", "24.5"));

		assertThat(outcome(receiver.receive(first))).isEqualTo(Outcome.APPLIED);
		assertThat(outcome(receiver.receive(first))).isEqualTo(Outcome.DUPLICATE);
	}

	@Test
	void aGapThatClosesInTimeIsApplied() throws Exception {
		StreamReceiver receiver = receiver().build();
		receiver.receive(set(1, keyframe("currentTemperature", "20.0"))).get(10, TimeUnit.SECONDS);

		CompletableFuture<StreamReceiver.Result> third = receiver.receive(set(3, sample("currentTemperature", "22.0", null)));
		assertThat(outcome(receiver.receive(set(2, sample("currentTemperature", "21.0", null))))).isEqualTo(Outcome.APPLIED);

		assertThat(outcome(third)).isEqualTo(Outcome.APPLIED);
		assertThat(targets.find(fx.pool, "pool-1").eGet(fx.currentTemperature)).isEqualTo(22.0);
	}

	@Test
	void aGapThatStaysMakesTheObjectStaleUntilTheNextKeyframe() throws Exception {
		StreamReceiver receiver = receiver().gapBuffer(Duration.ofMillis(50)).build();
		String stream = fx.stream("pool-1");
		receiver.receive(set(1, keyframe("currentTemperature", "20.0"))).get(10, TimeUnit.SECONDS);

		assertThat(outcome(receiver.receive(set(3, sample("currentTemperature", "22.0", null))))).isEqualTo(Outcome.SKIPPED_STALE);
		assertThat(receiver.state(stream)).isEqualTo(State.STALE);
		assertThat(outcome(receiver.receive(set(4, sample("currentTemperature", "23.0", null))))).isEqualTo(Outcome.SKIPPED_STALE);
		assertThat(targets.find(fx.pool, "pool-1").eGet(fx.currentTemperature)).isEqualTo(20.0);

		assertThat(outcome(receiver.receive(set(5, keyframe("currentTemperature", "24.0"))))).isEqualTo(Outcome.APPLIED);
		assertThat(receiver.state(stream)).isEqualTo(State.COMPLETE);
		assertThat(targets.find(fx.pool, "pool-1").eGet(fx.currentTemperature)).isEqualTo(24.0);
	}

	@Test
	void underSkipAndMarkAGapIsCountedAndSkipped() throws Exception {
		StreamReceiver receiver = receiver().gapBuffer(Duration.ofMillis(50)).gapPolicy(GapPolicy.SKIP_AND_MARK).build();
		String stream = fx.stream("pool-1");
		receiver.receive(set(1, keyframe("currentTemperature", "20.0"))).get(10, TimeUnit.SECONDS);

		assertThat(outcome(receiver.receive(set(3, sample("currentTemperature", "22.0", null))))).isEqualTo(Outcome.APPLIED);
		assertThat(receiver.gaps(stream)).isEqualTo(1);
		assertThat(receiver.state(stream)).isEqualTo(State.COMPLETE);
	}

	@Test
	void afterAGapSamplesAreAppliedAndChangelogEntriesWaitForTheKeyframe() throws Exception {
		StreamReceiver receiver = receiver().gapBuffer(Duration.ofMillis(50)).build();
		String stream = fx.stream("pool-1");
		receiver.receive(set(1, keyframe("currentTemperature", "20.0", "name", "Becken"))).get(10, TimeUnit.SECONDS);

		ChangeEntry sample = recorded(sample("currentTemperature", "22.0", null), Recording.TIMESERIES);
		ChangeEntry renamed = recorded(changelog("pool-1", "name", "Becken", "Wellenbecken"), Recording.CHANGELOG);
		StreamReceiver.Result result = receiver.receive(set(3, sample, renamed)).get(10, TimeUnit.SECONDS);

		assertThat(result.outcome()).isEqualTo(Outcome.APPLIED);
		assertThat(result.diagnostic().getChildren()).singleElement()
				.satisfies(d -> assertThat(d.getMessage()).contains("changelog entry after a gap"));
		assertThat(receiver.gaps(stream)).isEqualTo(1);
		assertThat(receiver.state(stream)).isEqualTo(State.STALE);
		EObject pool = targets.find(fx.pool, "pool-1");
		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(22.0);
		assertThat(pool.eGet(fx.poolName)).isEqualTo("Becken");

		ChangeEntry renamedAgain = recorded(changelog("pool-1", "name", "Wellenbecken", "Sportbecken"), Recording.CHANGELOG);
		assertThat(outcome(receiver.receive(set(4, renamedAgain)))).isEqualTo(Outcome.SKIPPED_STALE);

		assertThat(outcome(receiver.receive(set(5, keyframe("currentTemperature", "23.0", "name", "Sportbecken")))))
				.isEqualTo(Outcome.APPLIED);
		assertThat(receiver.state(stream)).isEqualTo(State.COMPLETE);
		ChangeEntry renamedLater = recorded(changelog("pool-1", "name", "Sportbecken", "Kinderland"), Recording.CHANGELOG);
		assertThat(outcome(receiver.receive(set(6, renamedLater)))).isEqualTo(Outcome.APPLIED);
		assertThat(pool.eGet(fx.poolName)).isEqualTo("Kinderland");
	}

	@Test
	void underSkipAndMarkAChangelogEntryStillWaitsForTheKeyframe() throws Exception {
		StreamReceiver receiver = receiver().gapBuffer(Duration.ofMillis(50)).gapPolicy(GapPolicy.SKIP_AND_MARK).build();
		receiver.receive(set(1, keyframe("name", "Becken"))).get(10, TimeUnit.SECONDS);

		ChangeEntry renamed = recorded(changelog("pool-1", "name", "Becken", "Wellenbecken"), Recording.CHANGELOG);
		assertThat(outcome(receiver.receive(set(3, renamed)))).isEqualTo(Outcome.SKIPPED_STALE);
		assertThat(targets.find(fx.pool, "pool-1").eGet(fx.poolName)).isEqualTo("Becken");
	}

	@Test
	void theStoreKeepsTheRecordingOfEachEntry() throws Exception {
		ChangeEntry entry = recorded(sample("currentTemperature", "1.0", null), Recording.TIMESERIES);
		InMemoryStreamStore store = new InMemoryStreamStore();
		store.append(set(1, entry, sample("currentPh", "7.0", null)));

		List<ChangeEntry> read = store.read(fx.stream("pool-1"), 1, 1).get(0).getEntries();

		assertThat(read.get(0).isSetRecording()).isTrue();
		assertThat(read.get(0).getRecording()).isEqualTo(Recording.TIMESERIES);
		assertThat(read.get(1).isSetRecording()).isFalse();
	}

	@Test
	void anUnknownModelVersionIsRefused() throws Exception {
		StreamReceiver receiver = receiver().build();
		ChangeSet foreign = set(1, keyframe("currentTemperature", "20.0"));
		foreign.setContextFingerprint("fp-unknown");

		StreamReceiver.Result result = receiver.receive(foreign).get(10, TimeUnit.SECONDS);

		assertThat(result.outcome()).isEqualTo(Outcome.REJECTED);
		assertThat(result.diagnostic().getMessage()).contains("fp-unknown");
		assertThat(targets.find(fx.pool, "pool-1")).isNull();
	}

	@Test
	void anInvalidChangeSetIsRefusedAtOnce() throws Exception {
		StreamReceiver receiver = receiver().build();
		ChangeSet invalid = set(1, sample("currentTemperature", "1.0", null));
		invalid.setAuthor(null);

		assertThat(outcome(receiver.receive(invalid))).isEqualTo(Outcome.REJECTED);
	}

	@Test
	void theGuardRefusesMergesOrOverwritesAMismatchingOldValue() throws Exception {
		for (GuardPolicy policy : GuardPolicy.values()) {
			targets = ReplicaTargets.inMemory();
			StreamReceiver receiver = receiver().guard(policy).build();
			String poolId = "pool-" + policy;
			receiver.receive(set(poolId, 1, keyframeFor(poolId, "currentTemperature", "20.0", "currentPh", "7.0"))).get(10,
					TimeUnit.SECONDS);

			// temperature claims an old value the replica does not hold, pH matches
			StreamReceiver.Result result = receiver.receive(set(poolId, 2, changelog(poolId, "currentTemperature", "19.0", "21.0"),
					changelog(poolId, "currentPh", "7.0", "7.5"))).get(10, TimeUnit.SECONDS);
			EObject copy = targets.find(fx.pool, poolId);

			switch (policy) {
			case FAIL -> {
				assertThat(result.outcome()).isEqualTo(Outcome.REJECTED);
				assertThat(copy.eGet(fx.currentTemperature)).isEqualTo(20.0);
				assertThat(copy.eGet(fx.currentPh)).isEqualTo(7.0);
				assertThat(receiver.state(fx.stream(poolId))).isEqualTo(State.STALE);
			}
			case MERGE -> {
				assertThat(result.outcome()).isEqualTo(Outcome.APPLIED);
				assertThat(copy.eGet(fx.currentTemperature)).isEqualTo(20.0);
				assertThat(copy.eGet(fx.currentPh)).isEqualTo(7.5);
			}
			case LAST_WRITER_WINS -> {
				assertThat(result.outcome()).isEqualTo(Outcome.APPLIED);
				assertThat(copy.eGet(fx.currentTemperature)).isEqualTo(21.0);
				assertThat(copy.eGet(fx.currentPh)).isEqualTo(7.5);
			}
			}
		}
	}

	@Test
	void aLateSampleDoesNotOverwriteANewerOne() throws Exception {
		StreamReceiver receiver = receiver().build();
		receiver.receive(set(1, keyframe("currentTemperature", "20.0"))).get(10, TimeUnit.SECONDS);
		receiver.receive(set(2, sample("currentTemperature", "25.0", 2_000L))).get(10, TimeUnit.SECONDS);
		receiver.receive(set(3, sample("currentTemperature", "24.0", 1_000L))).get(10, TimeUnit.SECONDS);

		assertThat(targets.find(fx.pool, "pool-1").eGet(fx.currentTemperature)).isEqualTo(25.0);
	}

	@Test
	void aReplicatedObjectRefusesLocalValues() throws Exception {
		StreamMapping mapping = fx.mapping();
		mapping.setRecording(RecordingMode.CHANGELOG);
		fx.registry.register(mapping);
		StreamReceiver receiver = receiver().build();
		receiver.receive(set(1, keyframe("currentTemperature", "20.0"))).get(10, TimeUnit.SECONDS);

		replica.offer(fx.temp("t1", "pool-1", 99.0), 5, null);
		assertThat(replica.awaitIdle(WAIT)).isTrue();

		assertThat(replica.store().lastSequence(fx.stream("pool-1"))).isEqualTo(1);
	}

	@Test
	void streamIdsSplitIntoClassAndId() {
		assertThat(StreamId.parse("https://example.org/pool#//Pool/pool-1"))
				.contains(new StreamId("https://example.org/pool#//Pool", "pool-1"));
		assertThat(StreamId.parse("https://example.org/pool#//Pool/a/b"))
				.contains(new StreamId("https://example.org/pool#//Pool", "a/b"));
		assertThat(StreamId.parse("no-class")).isEmpty();
		assertThat(StreamId.parse("https://example.org/pool#//Pool/")).isEmpty();
		assertThat(StreamId.of(fx.pool, "x/y").toString()).isEqualTo("https://example.org/pool#//Pool/x/y");
	}

	private StreamReceiver.Builder receiver() {
		return StreamReceiver.builder().core(replica).targets(targets)
				.modelVersions(fingerprint -> FP.equals(fingerprint) ? models : null);
	}

	private static Outcome outcome(CompletableFuture<StreamReceiver.Result> result) {
		try {
			return result.get(10, TimeUnit.SECONDS).outcome();
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	private ChangeSet set(long sequence, ChangeEntry... entries) {
		return set("pool-1", sequence, entries);
	}

	private ChangeSet set(String poolId, long sequence, ChangeEntry... entries) {
		ChangeSet set = StreamFactory.eINSTANCE.createChangeSet();
		set.setId("cs-" + poolId + "-" + sequence);
		set.setStreamId(fx.stream(poolId));
		set.setSequence(sequence);
		set.setTimestamp(sequence * 100);
		set.setContextFingerprint(FP);
		set.setAuthor("origin");
		set.getEntries().addAll(List.of(entries));
		return set;
	}

	private ChangeEntry sample(String feature, String value, Long time) {
		ChangeEntry entry = entry("pool-1", feature, DeltaKind.SET);
		entry.setValueNew(value);
		entry.setTimestamp(time);
		return entry;
	}

	private ChangeEntry changelog(String poolId, String feature, String oldValue, String newValue) {
		ChangeEntry entry = entry(poolId, feature, DeltaKind.SET);
		entry.setValueOld(oldValue);
		entry.setValueNew(newValue);
		return entry;
	}

	private ChangeEntry keyframe(String... featureValuePairs) {
		return keyframeFor("pool-1", featureValuePairs);
	}

	private ChangeEntry keyframeFor(String poolId, String... featureValuePairs) {
		ChangeEntry entry = StreamFactory.eINSTANCE.createChangeEntry();
		entry.setObjectId(poolId);
		entry.setKind(DeltaKind.KEYFRAME);
		entry.setClassUri("https://example.org/pool#//Pool");
		for (int i = 0; i < featureValuePairs.length; i += 2) {
			SlotValue slot = StreamFactory.eINSTANCE.createSlotValue();
			slot.setFeatureUri("https://example.org/pool#//Pool/" + featureValuePairs[i]);
			slot.setValue(featureValuePairs[i + 1]);
			entry.getState().add(slot);
		}
		return entry;
	}

	private static ChangeEntry recorded(ChangeEntry entry, Recording recording) {
		entry.setRecording(recording);
		return entry;
	}

	private ChangeEntry entry(String poolId, String feature, DeltaKind kind) {
		ChangeEntry entry = StreamFactory.eINSTANCE.createChangeEntry();
		entry.setObjectId(poolId);
		entry.setKind(kind);
		entry.setFeatureUri("https://example.org/pool#//Pool/" + feature);
		return entry;
	}
}
