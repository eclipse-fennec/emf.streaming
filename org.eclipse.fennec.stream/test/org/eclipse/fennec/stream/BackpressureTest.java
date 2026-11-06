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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.junit.jupiter.api.Test;

/**
 * Full lanes (requirement COR-06): time-series offers evict the oldest waiting one, changelog
 * offers make the producer wait and are never lost.
 */
class BackpressureTest {

	private static final Duration WAIT = Duration.ofSeconds(10);

	@Test
	void aFullLaneDropsTheOldestTimeSeriesValues() throws Exception {
		PoolStreams fx = new PoolStreams();
		StreamMapping mapping = fx.mapping();
		mapping.setKeyframeEverySets(0);
		fx.registry.register(mapping);
		InMemoryStreamStore store = new InMemoryStreamStore();
		CountDownLatch release = new CountDownLatch(1);
		CountDownLatch blocked = new CountDownLatch(1);
		try (EventCore core = core(fx, store, 3, release, blocked)) {
			core.offer(fx.temp("t1", "pool-1", 0.0), 0, null);
			assertThat(blocked.await(10, TimeUnit.SECONDS)).isTrue();
			for (int i = 1; i <= 10; i++) {
				core.offer(fx.temp("t1", "pool-1", i), i, null);
			}
			release.countDown();
			assertThat(core.awaitIdle(WAIT)).isTrue();

			assertThat(values(store, fx)).containsExactly("0.0", "8.0", "9.0", "10.0");
			assertThat(core.metrics().droppedOffers()).isEqualTo(7);
		}
	}

	@Test
	void aFullLaneMakesChangelogProducersWait() throws Exception {
		PoolStreams fx = new PoolStreams();
		StreamMapping mapping = fx.mapping();
		mapping.setRecording(RecordingMode.CHANGELOG);
		mapping.setKeyframeEverySets(0);
		fx.registry.register(mapping);
		InMemoryStreamStore store = new InMemoryStreamStore();
		CountDownLatch release = new CountDownLatch(1);
		CountDownLatch blocked = new CountDownLatch(1);
		try (EventCore core = core(fx, store, 2, release, blocked)) {
			core.offer(fx.temp("t1", "pool-1", 0.0), 0, null);
			assertThat(blocked.await(10, TimeUnit.SECONDS)).isTrue();
			Thread producer = new Thread(() -> {
				for (int i = 1; i <= 5; i++) {
					core.offer(fx.temp("t1", "pool-1", i), i, null);
				}
			});
			producer.start();
			producer.join(300);
			assertThat(producer.isAlive()).as("the producer waits for space").isTrue();

			release.countDown();
			producer.join(10_000);
			assertThat(producer.isAlive()).isFalse();
			assertThat(core.awaitIdle(WAIT)).isTrue();

			assertThat(values(store, fx)).containsExactly("0.0", "1.0", "2.0", "3.0", "4.0", "5.0");
			assertThat(core.metrics().droppedOffers()).isZero();
		}
	}

	private static EventCore core(PoolStreams fx, StreamStore store, int maxQueued, CountDownLatch release,
			CountDownLatch blocked) {
		fx.pool("pool-1");
		return EventCore.builder().mappings(fx.registry).mapper(fx.mapper).store(store)
				.group(new EventCore.ExecutionGroup("default", 1, 1, maxQueued)).projection((c, id, feature, value) -> {
					blocked.countDown();
					try {
						release.await(10, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					return true;
				}).build();
	}

	private static List<String> values(InMemoryStreamStore store, PoolStreams fx) {
		return store.read(fx.stream("pool-1"), 1, Long.MAX_VALUE).stream().flatMap(s -> s.getEntries().stream())
				.filter(e -> e.getKind() == DeltaKind.SET).map(ChangeEntry::getValueNew).toList();
	}
}
