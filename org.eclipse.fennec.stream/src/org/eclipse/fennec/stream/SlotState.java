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

/**
 * What the change rules of one slot of one target object compare against: the last value that
 * entered the stream and its event time, plus the number of values offered since then.
 * <p>
 * Not thread-safe: it belongs to the target object's lane in the event core, which handles one
 * value at a time. It is also what a store hands back after a restart (requirement STO-02).
 */
public final class SlotState {

	private boolean hasLast;
	private Object lastValue;
	private long lastTime;
	private int offeredSinceLastPass;

	/**
	 * @return a state without history: the next value passes
	 */
	public static SlotState empty() {
		return new SlotState();
	}

	/**
	 * Restores a state, e.g. from a store after a restart.
	 *
	 * @param lastValue the last value that passed
	 * @param lastTime its event time
	 * @return the state
	 */
	public static SlotState of(Object lastValue, long lastTime) {
		SlotState state = new SlotState();
		state.passed(lastValue, lastTime);
		return state;
	}

	/**
	 * @return whether a value passed before
	 */
	public boolean hasLast() {
		return hasLast;
	}

	/**
	 * @return the last value that passed, {@code null} before the first
	 */
	public Object lastValue() {
		return lastValue;
	}

	/**
	 * @return the event time of the last value that passed
	 */
	public long lastTime() {
		return lastTime;
	}

	/**
	 * @return how many values were offered and rejected since the last one passed
	 */
	public int offeredSinceLastPass() {
		return offeredSinceLastPass;
	}

	/**
	 * Records that a value passed: it becomes the comparison base.
	 *
	 * @param value the value
	 * @param eventTime its event time
	 */
	public void passed(Object value, long eventTime) {
		hasLast = true;
		lastValue = value;
		lastTime = eventTime;
		offeredSinceLastPass = 0;
	}

	/**
	 * Records that a value was rejected; only the offer counter moves.
	 */
	public void rejected() {
		offeredSinceLastPass++;
	}
}
