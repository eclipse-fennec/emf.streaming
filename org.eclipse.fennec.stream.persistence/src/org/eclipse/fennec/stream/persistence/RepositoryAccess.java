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

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.fennec.persistence.repository.api.Repository;

/**
 * Repository instances for callers on several threads. A repository is not thread-safe, so a call
 * borrows an instance of its own and hands it back afterwards. At most {@code size} instances exist;
 * a call beyond that waits for one. Instances are acquired on demand, reused while the access is
 * open, and every acquired instance is released exactly once - on {@link #close()} at the latest.
 * <p>
 * In OSGi the instances come from the service objects of the prototype-scoped repository service.
 */
public final class RepositoryAccess implements AutoCloseable {

	/**
	 * A call on a repository.
	 *
	 * @param <T> the result
	 */
	@FunctionalInterface
	public interface RepositoryCall<T> {

		/**
		 * @param repository the repository, for this call only
		 * @return the result
		 * @throws IOException if the repository fails
		 */
		T call(Repository repository) throws IOException;
	}

	private final Supplier<Repository> acquire;
	private final Consumer<Repository> release;
	private final Semaphore permits;
	/** Guarded by this. */
	private final Deque<Repository> idle = new ArrayDeque<>();
	/** Guarded by this. */
	private boolean closed;

	/**
	 * @param acquire hands out a new repository instance
	 * @param release takes an instance back that is no longer used
	 * @param size the most instances in use at once
	 */
	public RepositoryAccess(Supplier<Repository> acquire, Consumer<Repository> release, int size) {
		this.acquire = requireNonNull(acquire, "Acquire is required");
		this.release = requireNonNull(release, "Release is required");
		if (size < 1) {
			throw new IllegalArgumentException("At least one repository instance is needed, not " + size);
		}
		this.permits = new Semaphore(size, true);
	}

	/**
	 * @param repository one repository instance, used by one call at a time
	 * @return an access serializing all calls on it; closing it releases nothing
	 */
	public static RepositoryAccess of(Repository repository) {
		requireNonNull(repository, "Repository is required");
		return new RepositoryAccess(() -> repository, released -> {
			// owned by the caller
		}, 1);
	}

	/**
	 * Runs a call on a repository instance of its own.
	 *
	 * @param <T> the result
	 * @param call the call
	 * @return its result
	 * @throws IOException if the repository fails
	 * @throws IllegalStateException if the access is closed
	 */
	public <T> T call(RepositoryCall<T> call) throws IOException {
		try {
			permits.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException("Interrupted while waiting for a repository instance", e);
		}
		try {
			Repository repository = borrow();
			try {
				return call.call(repository);
			} finally {
				giveBack(repository);
			}
		} finally {
			permits.release();
		}
	}

	private Repository borrow() {
		synchronized (this) {
			if (closed) {
				throw new IllegalStateException("The repository access is closed");
			}
			Repository repository = idle.poll();
			if (repository != null) {
				return repository;
			}
		}
		return acquire.get();
	}

	private void giveBack(Repository repository) {
		synchronized (this) {
			if (!closed) {
				idle.push(repository);
				return;
			}
		}
		release.accept(repository);
	}

	/**
	 * Releases the idle instances; an instance in use is released when its call ends.
	 */
	@Override
	public void close() {
		Deque<Repository> released;
		synchronized (this) {
			closed = true;
			released = new ArrayDeque<>(idle);
			idle.clear();
		}
		released.forEach(release);
	}
}
