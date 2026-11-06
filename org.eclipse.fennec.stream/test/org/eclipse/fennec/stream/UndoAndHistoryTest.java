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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Optional undo/redo with stack semantics and the as-of engine (requirements APL-06, APL-07, APL-09).
 */
class UndoAndHistoryTest {

	private static final Duration WAIT = Duration.ofSeconds(10);

	private PoolStreams fx;
	private InMemoryStreamStore store;
	private EventCore core;
	private EObject pool;

	@BeforeEach
	void setUp() {
		fx = new PoolStreams();
		store = new InMemoryStreamStore();
		pool = fx.pool("pool-1");
	}

	@AfterEach
	void tearDown() {
		core.close();
	}

	@Test
	void undoTakesBackTheNewestChangeAndRedoTakesBackTheUndo() throws Exception {
		start(true, RecordingMode.CHANGELOG);
		offer(20.0, 1);
		offer(21.0, 2);

		ChangeSet undo = core.undo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS).orElseThrow();
		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(20.0);
		assertThat(undo.getReason()).isEqualTo("undo:3");
		assertThat(undo.getEntries()).singleElement().satisfies(e -> {
			assertThat(e.getValueOld()).isEqualTo("21.0");
			assertThat(e.getValueNew()).isEqualTo("20.0");
		});

		core.undo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS).orElseThrow();
		// the first change had no value before it: undoing it unsets the feature
		assertThat(pool.eIsSet(fx.currentTemperature)).isFalse();
		assertThat(core.undo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS)).isEmpty();

		core.redo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS).orElseThrow();
		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(20.0);
		ChangeSet redo = core.redo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS).orElseThrow();
		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(21.0);
		assertThat(redo.getReason()).startsWith("redo:");

		// the history only grew: value, keyframe, value, undo, undo, redo, redo
		assertThat(store.lastSequence(fx.stream("pool-1"))).isEqualTo(7);
	}

	@Test
	void aNewChangeClearsTheRedoStack() throws Exception {
		start(true, RecordingMode.CHANGELOG);
		offer(20.0, 1);
		offer(21.0, 2);
		core.undo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS);
		offer(22.0, 3);

		assertThat(core.redo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS)).isEmpty();
		assertThat(pool.eGet(fx.currentTemperature)).isEqualTo(22.0);
	}

	@Test
	void undoIsOptionalAndOnlyForChangelogs() throws Exception {
		start(false, RecordingMode.CHANGELOG);
		offer(20.0, 1);
		assertThatThrownBy(() -> core.undo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS))
				.isInstanceOf(ExecutionException.class).hasCauseInstanceOf(UnsupportedOperationException.class);
		core.close();

		store = new InMemoryStreamStore();
		start(true, RecordingMode.TIMESERIES);
		offer(20.0, 1);
		assertThatThrownBy(() -> core.undo(fx.pool, "pool-1").get(10, TimeUnit.SECONDS))
				.isInstanceOf(ExecutionException.class).hasCauseInstanceOf(IllegalStateException.class);
	}

	@Test
	void theStateAtASequenceStartsFromTheKeyframeBeforeIt() throws Exception {
		start(false, RecordingMode.TIMESERIES);
		offer(20.0, 1_000); // seq 1, keyframe seq 2
		offer(21.0, 2_000); // seq 3
		offer(22.0, 3_000); // seq 4
		String stream = fx.stream("pool-1");

		assertThat(temperature(StreamHistory.stateAt(store, stream, 1, fx.pool))).isEqualTo(20.0);
		assertThat(temperature(StreamHistory.stateAt(store, stream, 3, fx.pool))).isEqualTo(21.0);
		assertThat(temperature(StreamHistory.stateAt(store, stream, 4, fx.pool))).isEqualTo(22.0);
		assertThat(StreamHistory.stateAt(store, stream, 4, fx.pool).orElseThrow().eGet(fx.poolId)).isEqualTo("pool-1");
		assertThat(StreamHistory.stateAt(store, "nothing", 4, fx.pool)).isEmpty();
	}

	@Test
	void theStateAtATimeTakesTheLatestEventTimeAtOrBeforeIt() throws Exception {
		start(false, RecordingMode.TIMESERIES);
		offer(20.0, 1_000);
		offer(22.0, 3_000);
		offer(21.0, 2_000); // late sample
		String stream = fx.stream("pool-1");

		assertThat(temperature(StreamHistory.stateAtTime(store, stream, 2_500, fx.pool))).isEqualTo(21.0);
		assertThat(temperature(StreamHistory.stateAtTime(store, stream, 3_500, fx.pool))).isEqualTo(22.0);
		assertThat(temperature(StreamHistory.stateAtTime(store, stream, 1_500, fx.pool))).isEqualTo(20.0);
	}

	private void start(boolean undoable, RecordingMode recording) {
		StreamMapping mapping = fx.mapping();
		mapping.setId("pool-" + undoable + "-" + recording);
		mapping.setUndoable(undoable);
		mapping.setRecording(recording);
		fx.registry.all().forEach(m -> fx.registry.unregister(m.getId()));
		fx.registry.register(mapping);
		core = fx.core(store).build();
	}

	private void offer(double value, long time) throws InterruptedException {
		core.offer(fx.temp("t1", "pool-1", value), time, null);
		assertThat(core.awaitIdle(WAIT)).isTrue();
	}

	private Object temperature(Optional<EObject> state) {
		return state.orElseThrow().eGet(fx.currentTemperature);
	}

	static boolean isKeyframe(ChangeSet set) {
		return set.getEntries().get(0).getKind() == DeltaKind.KEYFRAME;
	}
}
