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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.junit.jupiter.api.Test;

/**
 * Deletion rules go to the store, by hand or on the core's schedule (requirement STO-05).
 */
class RetentionTest {

	private static final Duration WAIT = Duration.ofSeconds(10);

	@Test
	void theStoreKeepsWhatTheRuleAllows() throws Exception {
		PoolStreams fx = new PoolStreams();
		InMemoryStreamStore store = new InMemoryStreamStore();
		fx.registry.register(mapping(fx, 2));
		fx.pool("pool-1");
		try (EventCore core = fx.core(store).retentionInterval(Duration.ZERO).build()) {
			for (int i = 0; i < 5; i++) {
				core.offer(fx.temp("t1", "pool-1", 20.0 + i), i, null);
			}
			assertThat(core.awaitIdle(WAIT)).isTrue();
			assertThat(values(store, fx)).hasSize(5);

			core.sweepRetention();
			assertThat(core.awaitIdle(WAIT)).isTrue();

			assertThat(values(store, fx)).containsExactly("23.0", "24.0");
			assertThat(core.metrics().retentionRuns()).isEqualTo(1);
		}
	}

	@Test
	void theCoreSweepsOnItsOwn() throws Exception {
		PoolStreams fx = new PoolStreams();
		InMemoryStreamStore store = new InMemoryStreamStore();
		fx.registry.register(mapping(fx, 1));
		fx.pool("pool-1");
		try (EventCore core = fx.core(store).retentionInterval(Duration.ofMillis(30)).build()) {
			core.offer(fx.temp("t1", "pool-1", 20.0), 1, null);
			core.offer(fx.temp("t1", "pool-1", 21.0), 2, null);
			assertThat(core.awaitIdle(WAIT)).isTrue();
			long deadline = System.nanoTime() + WAIT.toNanos();
			while (!values(store, fx).equals(List.of("21.0")) && System.nanoTime() < deadline) {
				Thread.sleep(10);
			}
			assertThat(values(store, fx)).containsExactly("21.0");
		}
	}

	@Test
	void aStoreThatCannotDeleteKeepsEverything() throws Exception {
		PoolStreams fx = new PoolStreams();
		InMemoryStreamStore memory = new InMemoryStreamStore();
		AtomicInteger asked = new AtomicInteger();
		StreamStore cannotDelete = new StreamStore() {
			@Override
			public void append(ChangeSet changeSet) {
				memory.append(changeSet);
			}

			@Override
			public long lastSequence(String streamId) {
				return memory.lastSequence(streamId);
			}

			@Override
			public Optional<StoredValue> lastValue(String streamId, String slotAddress) {
				return memory.lastValue(streamId, slotAddress);
			}

			@Override
			public List<ChangeSet> read(String streamId, long fromSequence, long toSequence) {
				asked.incrementAndGet();
				return memory.read(streamId, fromSequence, toSequence);
			}
		};
		fx.registry.register(mapping(fx, 1));
		fx.pool("pool-1");
		try (EventCore core = fx.core(cannotDelete).retentionInterval(Duration.ZERO).build()) {
			core.offer(fx.temp("t1", "pool-1", 20.0), 1, null);
			core.offer(fx.temp("t1", "pool-1", 21.0), 2, null);
			core.sweepRetention();
			assertThat(core.awaitIdle(WAIT)).isTrue();

			assertThat(values(memory, fx)).containsExactly("20.0", "21.0");
		}
	}

	private static StreamMapping mapping(PoolStreams fx, int maxCount) {
		StreamMapping mapping = fx.mapping();
		DeletionRule rule = StreamMappingFactory.eINSTANCE.createDeletionRule();
		rule.setMaxCount(maxCount);
		fx.temperatureSlot(mapping).setDeletionRule(rule);
		return mapping;
	}

	private static List<String> values(InMemoryStreamStore store, PoolStreams fx) {
		return store.read(fx.stream("pool-1"), 1, Long.MAX_VALUE).stream().flatMap(s -> s.getEntries().stream())
				.filter(e -> e.getKind() == DeltaKind.SET).map(ChangeEntry::getValueNew).toList();
	}
}
