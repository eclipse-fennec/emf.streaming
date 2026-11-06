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
package org.eclipse.fennec.stream.impl;

import static java.util.Objects.requireNonNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The lane of one target object: tasks run one after the other, never two at a time, on a thread
 * borrowed from a shared pool (requirements COR-01, COR-02, COR-03).
 * <p>
 * States: {@code IDLE} (no thread, nothing queued), {@code SCHEDULED} (a run is handed to the pool),
 * {@code RUNNING}. Whoever enqueues into an idle mailbox schedules it; a run takes at most
 * {@code slice} tasks and then, if more are queued, schedules itself again behind the other
 * mailboxes of the pool - a hot object cannot starve the others. The lost-wake-up race (a task
 * arrives while the run decides to stop) is closed by re-checking the queue after going idle.
 */
public final class Mailbox {

	private static final Logger LOGGER = Logger.getLogger(Mailbox.class.getName());

	private static final int IDLE = 0;
	private static final int SCHEDULED = 1;
	private static final int RUNNING = 2;

	private final Executor executor;
	private final int slice;
	private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
	private final AtomicInteger state = new AtomicInteger(IDLE);
	private final Runnable onIdle;

	/**
	 * @param executor the pool of the lane's execution group
	 * @param slice how many tasks a run takes before it yields the thread (at least 1)
	 * @param onIdle called after the mailbox went idle with nothing queued, on the pool thread but
	 *        outside the lane's serial order - for bookkeeping such as dropping the mailbox; may be {@code null}
	 */
	public Mailbox(Executor executor, int slice, Runnable onIdle) {
		this.executor = requireNonNull(executor, "Executor is required");
		this.slice = Math.max(1, slice);
		this.onIdle = onIdle;
	}

	/**
	 * Enqueues a task; it runs after every task enqueued before it.
	 *
	 * @param task the task
	 */
	public void enqueue(Runnable task) {
		tasks.add(requireNonNull(task, "Task is required"));
		trySchedule();
	}

	/**
	 * @return whether nothing is queued and no run is pending or active
	 */
	public boolean isIdle() {
		return state.get() == IDLE && tasks.isEmpty();
	}

	private void trySchedule() {
		if (state.compareAndSet(IDLE, SCHEDULED)) {
			try {
				executor.execute(this::run);
			} catch (RejectedExecutionException e) {
				state.set(IDLE);
				LOGGER.log(Level.WARNING, "Lane pool rejected a run - tasks stay queued until the next one", e);
			}
		}
	}

	private void run() {
		state.set(RUNNING);
		int done = 0;
		Runnable task;
		while (done < slice && (task = tasks.poll()) != null) {
			try {
				task.run();
			} catch (RuntimeException e) {
				LOGGER.log(Level.WARNING, "A lane task failed - the lane goes on", e);
			}
			done++;
		}
		if (!tasks.isEmpty()) {
			// slice used up: yield the thread, queue behind the other mailboxes
			state.set(SCHEDULED);
			try {
				executor.execute(this::run);
				return;
			} catch (RejectedExecutionException e) {
				state.set(IDLE);
				LOGGER.log(Level.WARNING, "Lane pool rejected a run - tasks stay queued until the next one", e);
				return;
			}
		}
		state.set(IDLE);
		// a task may have arrived between the last poll and going idle
		if (!tasks.isEmpty()) {
			trySchedule();
		} else if (onIdle != null) {
			try {
				onIdle.run();
			} catch (RuntimeException e) {
				LOGGER.log(Level.WARNING, "Lane idle callback failed", e);
			}
		}
	}
}
