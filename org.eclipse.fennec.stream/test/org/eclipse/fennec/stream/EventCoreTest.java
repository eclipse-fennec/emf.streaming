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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.support.ChangeSetValidator;
import org.eclipse.fennec.model.streammapping.AbsoluteChange;
import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.util.pushstream.PushStream;

/**
 * The event core end to end with the pool scenario (requirements CAP-01..08, COR-01, COR-02,
 * COR-07, COR-08, COR-10, STR-06, STR-07, STR-08, CON-01, STO-02).
 */
class EventCoreTest {

	private static final Duration WAIT = Duration.ofSeconds(10);

	private PoolStreams fx;
	private InMemoryStreamStore store;
	private EventCore core;

	@BeforeEach
	void setUp() {
		fx = new PoolStreams();
		store = new InMemoryStreamStore();
	}

	@AfterEach
	void tearDown() {
		if (core != null) {
			core.close();
		}
	}

	@Test
	void twoSensorsFeedOnePoolAndItsStream() throws Exception {
		fx.registry.register(fx.mapping());
		EObject pool = fx.pool("pool-1");
		core = fx.core(store).build();

		core.offer(fx.temp("t1", "pool-1", 24.5), 1_000, null);
		core.offer(fx.ph("ph-7", 7.2), 2_000, "device:ph-7");
		assertThat(core.awaitIdle(WAIT)).isTrue();

		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(24.5);
		assertThat(pool.eGet(fx.currentPh)).isEqualTo(7.2);

		List<ChangeSet> sets = store.read(fx.stream("pool-1"), 1, Long.MAX_VALUE);
		// value, keyframe (the stream's first), value
		assertThat(sets).extracting(ChangeSet::getSequence).containsExactly(1L, 2L, 3L);
		assertThat(sets).allSatisfy(set -> assertThat(ChangeSetValidator.isValid(set)).isTrue());
		ChangeEntry temperature = sets.get(0).getEntries().get(0);
		assertThat(temperature.getKind()).isEqualTo(DeltaKind.SET);
		assertThat(temperature.getObjectId()).isEqualTo("pool-1");
		assertThat(temperature.getFeatureUri()).isEqualTo(fx.temperatureAddress());
		assertThat(temperature.getFeatureId()).isEqualTo(fx.pool.getFeatureID(fx.currentTemperature));
		assertThat(temperature.getValueNew()).isEqualTo("24.5");
		assertThat(temperature.getValueOld()).isNull();
		assertThat(temperature.getTimestamp()).isEqualTo(1_000L);
		assertThat(sets.get(0).getAuthor()).isEqualTo("mapping:pool-streams");
		assertThat(sets.get(0).getContextFingerprint()).isEqualTo("https://example.org/pool");
		assertThat(sets.get(1).getEntries().get(0).getKind()).isEqualTo(DeltaKind.KEYFRAME);
		assertThat(sets.get(1).getEntries().get(0).getClassUri()).isEqualTo("https://example.org/pool#//Pool");
		assertThat(sets.get(2).getAuthor()).isEqualTo("device:ph-7");
	}

	@Test
	void theRulesHoldBackSmallChanges() throws Exception {
		StreamMapping mapping = fx.mapping();
		AbsoluteChange half = StreamMappingFactory.eINSTANCE.createAbsoluteChange();
		half.setDelta(0.5);
		fx.temperatureSlot(mapping).setRule(half);
		fx.registry.register(mapping);
		EObject pool = fx.pool("pool-1");
		core = fx.core(store).build();

		for (double value : new double[] { 24.0, 24.2, 24.4, 24.6, 24.7 }) {
			core.offer(fx.temp("t1", "pool-1", value), 1_000, null);
		}
		assertThat(core.awaitIdle(WAIT)).isTrue();

		assertThat(values(fx.stream("pool-1"))).containsExactly("24.0", "24.6");
		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(24.6);
		assertThat(core.metrics().passed()).isEqualTo(2);
		assertThat(core.metrics().rejected()).isEqualTo(3);
	}

	@Test
	void anAuditOnlySlotLeavesTheTargetAlone() throws Exception {
		StreamMapping mapping = fx.mapping();
		fx.temperatureSlot(mapping).setMode(ProjectionMode.AUDIT_ONLY);
		fx.registry.register(mapping);
		core = fx.core(store).build(); // no pool registered: nothing could be loaded anyway

		core.offer(fx.temp("t1", "pool-9", 24.5), 1_000, null);
		assertThat(core.awaitIdle(WAIT)).isTrue();

		assertThat(values(fx.stream("pool-9"))).containsExactly("24.5");
		assertThat(fx.pools).isEmpty();
	}

	@Test
	void aWindowCollectsSamplesWithTheirOwnTimes() throws Exception {
		StreamMapping mapping = fx.mapping();
		BatchStrategy window = StreamMappingFactory.eINSTANCE.createBatchStrategy();
		window.setKind(BatchKind.WINDOW);
		window.setWindow(200);
		window.setUnit(DurationUnit.MILLISECONDS);
		mapping.setBatch(window);
		fx.registry.register(mapping);
		fx.pool("pool-1");
		core = fx.core(store).build();

		for (int i = 0; i < 5; i++) {
			core.offer(fx.temp("t1", "pool-1", 20.0 + i), 1_000 + i, null);
		}
		awaitSets(fx.stream("pool-1"), 2);

		ChangeSet window1 = store.read(fx.stream("pool-1"), 1, 1).get(0);
		assertThat(window1.getEntries()).extracting(ChangeEntry::getValueNew).containsExactly("20.0", "21.0", "22.0",
				"23.0", "24.0");
		assertThat(window1.getEntries()).extracting(ChangeEntry::getTimestamp).containsExactly(1_000L, 1_001L, 1_002L,
				1_003L, 1_004L);
	}

	@Test
	void aChangelogCarriesTheOldValueAndCoalescesPerBatch() throws Exception {
		StreamMapping mapping = fx.mapping();
		mapping.setRecording(RecordingMode.CHANGELOG);
		BatchStrategy count = StreamMappingFactory.eINSTANCE.createBatchStrategy();
		count.setKind(BatchKind.COUNT);
		count.setCount(3);
		fx.temperatureSlot(mapping).setBatch(count);
		fx.registry.register(mapping);
		fx.pool("pool-1");
		core = fx.core(store).build();

		core.offer(fx.temp("t1", "pool-1", 20.0), 1, null);
		core.flush();
		for (double value : new double[] { 21.0, 22.0, 23.0 }) {
			core.offer(fx.temp("t1", "pool-1", value), 2, null);
		}
		core.flush();
		assertThat(core.awaitIdle(WAIT)).isTrue();

		List<ChangeEntry> entries = entries(fx.stream("pool-1"));
		assertThat(entries).extracting(ChangeEntry::getValueOld).containsExactly(null, "20.0");
		assertThat(entries).extracting(ChangeEntry::getValueNew).containsExactly("20.0", "23.0");
	}

	@Test
	void aUnitOfWorkIsOneChangeSet() throws Exception {
		StreamMapping mapping = fx.mapping();
		BatchStrategy commit = StreamMappingFactory.eINSTANCE.createBatchStrategy();
		commit.setKind(BatchKind.COMMIT);
		mapping.setBatch(commit);
		mapping.setKeyframeEverySets(0);
		fx.registry.register(mapping);
		fx.pool("pool-1");
		core = fx.core(store).build();

		core.offerUnitOfWork(List.of(fx.temp("t1", "pool-1", 24.5), fx.ph("ph-7", 7.2)), 5_000, "editor");
		assertThat(core.awaitIdle(WAIT)).isTrue();

		ChangeSet first = store.read(fx.stream("pool-1"), 1, 1).get(0);
		assertThat(first.getEntries()).hasSize(2);
		assertThat(first.getAuthor()).isEqualTo("editor");
	}

	@Test
	void aLateSampleEntersTheStreamButDoesNotOverwriteTheTarget() throws Exception {
		fx.registry.register(fx.mapping());
		EObject pool = fx.pool("pool-1");
		core = fx.core(store).build();

		core.offer(fx.temp("t1", "pool-1", 25.0), 2_000, null);
		core.offer(fx.temp("t1", "pool-1", 24.0), 1_000, null);
		assertThat(core.awaitIdle(WAIT)).isTrue();

		assertThat(values(fx.stream("pool-1"))).containsExactly("25.0", "24.0");
		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(25.0);
	}

	@Test
	void theTargetConverterTurnsTheStreamValueIntoTheTargetType() throws Exception {
		StreamMapping mapping = fx.mapping();
		StreamSlot depth = PoolStreams.slot(fx.depth, fx.tempSensor, PoolStreams.path(fx.tempSensorValue));
		depth.setTargetConverterId("round");
		mapping.getSlots().remove(0);
		mapping.getSlots().add(depth);
		fx.converters.register("round", value -> Math.round((Double) value));
		fx.registry.register(mapping);
		EObject pool = fx.pool("pool-1");
		core = fx.core(store).build();

		core.offer(fx.temp("t1", "pool-1", 2.6), 1, null);
		assertThat(core.awaitIdle(WAIT)).isTrue();

		assertThat(values(fx.stream("pool-1"))).containsExactly("2.6");
		assertThat(pool.eGet(fx.depth)).isEqualTo(3);
	}

	@Test
	void keyframesFollowTheCadence() throws Exception {
		StreamMapping mapping = fx.mapping();
		mapping.setKeyframeEverySets(2);
		fx.registry.register(mapping);
		fx.pool("pool-1");
		core = fx.core(store).build();

		for (int i = 0; i < 5; i++) {
			core.offer(fx.temp("t1", "pool-1", 20.0 + i), i, null);
		}
		assertThat(core.awaitIdle(WAIT)).isTrue();

		List<DeltaKind> kinds = new ArrayList<>();
		store.read(fx.stream("pool-1"), 1, Long.MAX_VALUE).forEach(s -> kinds.add(s.getEntries().get(0).getKind()));
		assertThat(kinds).containsExactly(DeltaKind.SET, DeltaKind.KEYFRAME, DeltaKind.SET, DeltaKind.SET,
				DeltaKind.KEYFRAME, DeltaKind.SET, DeltaKind.SET, DeltaKind.KEYFRAME);
		ChangeEntry last = store.read(fx.stream("pool-1"), 8, 8).get(0).getEntries().get(0);
		assertThat(last.getState()).singleElement()
				.satisfies(slot -> assertThat(slot.getValue()).isEqualTo("24.0"));
		assertThat(core.metrics().keyframes()).isEqualTo(3);
	}

	@Test
	void consumersGetTheChangeSetsAsAPushStream() throws Exception {
		fx.registry.register(fx.mapping());
		fx.pool("pool-1");
		core = fx.core(store).build();
		List<ChangeSet> received = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch three = new CountDownLatch(3);
		PushStream<ChangeSet> changes = core.changes();
		changes.forEach(set -> {
			received.add(set);
			three.countDown();
		});

		core.offer(fx.temp("t1", "pool-1", 24.5), 1, null);
		core.offer(fx.temp("t1", "pool-1", 24.6), 2, null);

		assertThat(three.await(10, TimeUnit.SECONDS)).isTrue();
		assertThat(received).extracting(ChangeSet::getSequence).containsExactly(1L, 2L, 3L);
		changes.close();
	}

	@Test
	void aRestartedCoreGoesOnFromTheStore() throws Exception {
		StreamMapping mapping = fx.mapping();
		AbsoluteChange half = StreamMappingFactory.eINSTANCE.createAbsoluteChange();
		half.setDelta(0.5);
		fx.temperatureSlot(mapping).setRule(half);
		fx.registry.register(mapping);
		fx.pool("pool-1");
		try (EventCore first = fx.core(store).build()) {
			first.offer(fx.temp("t1", "pool-1", 24.0), 1, null);
			assertThat(first.awaitIdle(WAIT)).isTrue();
		}

		core = fx.core(store).build();
		core.offer(fx.temp("t1", "pool-1", 24.3), 2, null); // compared to the stored 24.0: held back
		core.offer(fx.temp("t1", "pool-1", 24.5), 3, null);
		assertThat(core.awaitIdle(WAIT)).isTrue();

		assertThat(values(fx.stream("pool-1"))).containsExactly("24.0", "24.5");
		// the sequence goes on; a restarted core writes a keyframe after its first change set
		assertThat(store.read(fx.stream("pool-1"), 1, Long.MAX_VALUE)).extracting(ChangeSet::getSequence)
				.containsExactly(1L, 2L, 3L, 4L);
	}

	@Test
	void eachPoolKeepsItsOrderUnderLoad() throws Exception {
		StreamMapping mapping = fx.mapping();
		mapping.setKeyframeEverySets(0);
		mapping.setKeyframeEveryMinutes(0);
		fx.registry.register(mapping);
		int pools = 100;
		int perPool = 100;
		for (int p = 0; p < pools; p++) {
			fx.pool("pool-" + p);
		}
		core = fx.core(store).build();

		List<Thread> producers = new ArrayList<>();
		for (int t = 0; t < 4; t++) {
			int offset = t;
			Thread producer = new Thread(() -> {
				for (int p = offset; p < pools; p += 4) {
					for (int i = 0; i < perPool; i++) {
						core.offer(fx.temp("t" + p, "pool-" + p, i), i, null);
					}
				}
			});
			producers.add(producer);
			producer.start();
		}
		for (Thread producer : producers) {
			producer.join();
		}
		assertThat(core.awaitIdle(WAIT)).isTrue();

		for (int p = 0; p < pools; p++) {
			List<ChangeSet> sets = store.read(fx.stream("pool-" + p), 1, Long.MAX_VALUE);
			// the stream's first keyframe plus one set per value
			assertThat(sets).hasSize(perPool + 1);
			for (int i = 0; i < sets.size(); i++) {
				assertThat(sets.get(i).getSequence()).isEqualTo(i + 1L);
			}
			List<String> values = values(fx.stream("pool-" + p));
			for (int i = 0; i < perPool; i++) {
				assertThat(values.get(i)).isEqualTo(String.valueOf((double) i));
			}
		}
		assertThat(core.metrics().offered()).isEqualTo((long) pools * perPool);
	}

	@Test
	void aMappingsTargetsRunInItsHighestRankedGroup() throws Exception {
		StreamMapping mapping = fx.mapping();
		fx.temperatureSlot(mapping).setExecutionGroup("high");
		fx.registry.register(mapping);
		fx.pool("pool-1");
		List<String> threads = Collections.synchronizedList(new ArrayList<>());
		core = fx.core(store).projection((eClass, id, feature, value) -> threads.add(Thread.currentThread().getName()))
				.build();

		core.offer(fx.ph("ph-7", 7.0), 1, null); // a 'default' slot, but the pool's mapping names 'high'
		assertThat(core.awaitIdle(WAIT)).isTrue();

		assertThat(threads).singleElement().asString().startsWith("fennec-stream-high-");
	}

	private List<ChangeEntry> entries(String streamId) {
		List<ChangeEntry> entries = new ArrayList<>();
		for (ChangeSet set : store.read(streamId, 1, Long.MAX_VALUE)) {
			for (ChangeEntry entry : set.getEntries()) {
				if (entry.getKind() != DeltaKind.KEYFRAME) {
					entries.add(entry);
				}
			}
		}
		return entries;
	}

	private List<String> values(String streamId) {
		return entries(streamId).stream().map(ChangeEntry::getValueNew).toList();
	}

	private void awaitSets(String streamId, int count) throws InterruptedException {
		long deadline = System.nanoTime() + WAIT.toNanos();
		while (store.read(streamId, 1, Long.MAX_VALUE).size() < count && System.nanoTime() < deadline) {
			TimeUnit.MILLISECONDS.sleep(10);
		}
		assertThat(core.awaitIdle(WAIT)).isTrue();
	}
}
