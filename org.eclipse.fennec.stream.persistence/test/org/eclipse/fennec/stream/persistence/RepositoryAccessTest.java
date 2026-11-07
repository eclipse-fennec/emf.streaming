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
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.fennec.persistence.repository.api.Repository;
import org.junit.jupiter.api.Test;

/**
 * Borrowing repository instances: one per call, reused, bounded, and every one released once.
 */
class RepositoryAccessTest {

	private final List<Repository> acquired = Collections.synchronizedList(new ArrayList<>());
	private final List<Repository> released = Collections.synchronizedList(new ArrayList<>());

	private Repository newRepository() {
		Repository repository = (Repository) Proxy.newProxyInstance(Repository.class.getClassLoader(),
				new Class<?>[] { Repository.class }, (proxy, method, args) -> switch (method.getName()) {
				case "hashCode" -> System.identityHashCode(proxy);
				case "equals" -> proxy == args[0];
				case "toString" -> "repository";
				default -> throw new UnsupportedOperationException(method.getName());
				});
		acquired.add(repository);
		return repository;
	}

	@Test
	void sequentialCallsReuseOneInstanceAndCloseReleasesIt() throws Exception {
		RepositoryAccess access = new RepositoryAccess(this::newRepository, released::add, 4);
		Repository first = access.call(repository -> repository);
		Repository second = access.call(repository -> repository);

		assertThat(second).isSameAs(first);
		assertThat(acquired).hasSize(1);
		assertThat(released).isEmpty();

		access.close();
		assertThat(released).containsExactly(first);
		assertThatIllegalStateException().isThrownBy(() -> access.call(repository -> null));
	}

	@Test
	void concurrentCallsGetInstancesOfTheirOwnUpToTheBound() throws Exception {
		RepositoryAccess access = new RepositoryAccess(this::newRepository, released::add, 3);
		ExecutorService threads = Executors.newFixedThreadPool(8);
		Set<Repository> inUse = ConcurrentHashMap.newKeySet();
		AtomicInteger shared = new AtomicInteger();
		AtomicInteger mostAtOnce = new AtomicInteger();
		CountDownLatch done = new CountDownLatch(40);
		for (int i = 0; i < 40; i++) {
			threads.execute(() -> {
				try {
					access.call(repository -> {
						if (!inUse.add(repository)) {
							shared.incrementAndGet();
						}
						mostAtOnce.accumulateAndGet(inUse.size(), Math::max);
						pause(new CountDownLatch(1), 2);
						inUse.remove(repository);
						return null;
					});
				} catch (Exception e) {
					throw new IllegalStateException(e);
				} finally {
					done.countDown();
				}
			});
		}
		assertThat(done.await(10, TimeUnit.SECONDS)).isTrue();
		threads.shutdown();

		assertThat(shared.get()).as("an instance is never used by two calls at once").isZero();
		assertThat(mostAtOnce.get()).isLessThanOrEqualTo(3);
		assertThat(acquired).hasSizeLessThanOrEqualTo(3);

		access.close();
		assertThat(released).as("every acquired instance is released exactly once")
				.containsExactlyInAnyOrderElementsOf(acquired);
	}

	@Test
	void anInstanceInUseWhenClosingIsReleasedWhenItsCallEnds() throws Exception {
		RepositoryAccess access = new RepositoryAccess(this::newRepository, released::add, 2);
		CountDownLatch inCall = new CountDownLatch(1);
		CountDownLatch closed = new CountDownLatch(1);
		Thread caller = new Thread(() -> {
			try {
				access.call(repository -> {
					inCall.countDown();
					pause(closed, 5000);
					return null;
				});
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		});
		caller.start();
		assertThat(inCall.await(5, TimeUnit.SECONDS)).isTrue();

		access.close();
		assertThat(released).isEmpty();
		closed.countDown();
		caller.join(5000);
		assertThat(released).containsExactlyElementsOf(acquired);
	}

	@Test
	void aSingleInstanceIsSharedSeriallyAndNotReleased() throws Exception {
		Repository single = newRepository();
		RepositoryAccess access = RepositoryAccess.of(single);
		Repository used = access.call(repository -> repository);
		assertThat(used).isSameAs(single);
		access.close();
		assertThat(released).isEmpty();
	}

	/** Waits within a repository call, which may throw IOException only. */
	private static void pause(CountDownLatch latch, long millis) throws IOException {
		try {
			latch.await(millis, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException(e);
		}
	}
}
