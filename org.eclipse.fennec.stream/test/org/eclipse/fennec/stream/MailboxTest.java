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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.fennec.stream.impl.Mailbox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * The lane: serial per mailbox, fair between mailboxes (requirements COR-01, COR-02, COR-03).
 */
class MailboxTest {

	private final ExecutorService pool = Executors.newFixedThreadPool(4);

	@AfterEach
	void tearDown() {
		pool.shutdownNow();
	}

	@Test
	void tasksRunInOrderAndNeverTwoAtATime() throws Exception {
		Mailbox mailbox = new Mailbox(pool, 3, null);
		List<Integer> order = Collections.synchronizedList(new ArrayList<>());
		AtomicInteger running = new AtomicInteger();
		AtomicInteger overlaps = new AtomicInteger();
		int tasks = 5_000;
		CountDownLatch done = new CountDownLatch(tasks);

		List<Thread> producers = new ArrayList<>();
		Object lock = new Object();
		int[] next = { 0 };
		for (int t = 0; t < 4; t++) {
			Thread producer = new Thread(() -> {
				while (true) {
					int i;
					synchronized (lock) {
						// enqueue under a lock so the expected order is the enqueue order
						if (next[0] >= tasks) {
							return;
						}
						i = next[0]++;
						mailbox.enqueue(() -> {
							if (running.incrementAndGet() > 1) {
								overlaps.incrementAndGet();
							}
							order.add(i);
							running.decrementAndGet();
							done.countDown();
						});
					}
				}
			});
			producers.add(producer);
			producer.start();
		}
		assertThat(done.await(10, TimeUnit.SECONDS)).isTrue();

		assertThat(overlaps).hasValue(0);
		for (int i = 0; i < tasks; i++) {
			assertThat(order.get(i)).isEqualTo(i);
		}
	}

	@Test
	void aHotMailboxYieldsToAColdOneAfterItsSlice() throws Exception {
		ExecutorService single = Executors.newSingleThreadExecutor();
		try {
			Mailbox hot = new Mailbox(single, 10, null);
			Mailbox cold = new Mailbox(single, 10, null);
			List<String> trace = Collections.synchronizedList(new ArrayList<>());
			CountDownLatch gate = new CountDownLatch(1);
			CountDownLatch done = new CountDownLatch(1001);

			hot.enqueue(() -> {
				await(gate);
				trace.add("hot");
				done.countDown();
			});
			for (int i = 0; i < 999; i++) {
				hot.enqueue(() -> {
					trace.add("hot");
					done.countDown();
				});
			}
			cold.enqueue(() -> {
				trace.add("cold");
				done.countDown();
			});
			gate.countDown();
			assertThat(done.await(10, TimeUnit.SECONDS)).isTrue();

			// one slice of the hot mailbox, then the cold one gets its turn
			assertThat(trace.indexOf("cold")).isEqualTo(10);
		} finally {
			single.shutdownNow();
		}
	}

	@Test
	void anIdleMailboxReportsIdleAndCallsBack() throws Exception {
		CountDownLatch idle = new CountDownLatch(1);
		Mailbox mailbox = new Mailbox(pool, 8, idle::countDown);
		mailbox.enqueue(() -> {
		});

		assertThat(idle.await(5, TimeUnit.SECONDS)).isTrue();
		assertThat(mailbox.isIdle()).isTrue();
	}

	@Test
	void aFailingTaskDoesNotStopTheLane() throws Exception {
		Mailbox mailbox = new Mailbox(pool, 8, null);
		CountDownLatch after = new CountDownLatch(1);
		mailbox.enqueue(() -> {
			throw new IllegalStateException("boom");
		});
		mailbox.enqueue(after::countDown);

		assertThat(after.await(5, TimeUnit.SECONDS)).isTrue();
	}

	private static void await(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
