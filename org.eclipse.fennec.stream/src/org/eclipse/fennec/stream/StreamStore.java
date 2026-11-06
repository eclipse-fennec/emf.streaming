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

import java.util.List;
import java.util.Optional;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Where change sets are kept (requirement STO-01). The core ships {@link InMemoryStreamStore};
 * durable stores (JPA/TimescaleDB, Mongo) live in emf.persistence and implement this interface.
 * <p>
 * Streams are addressed by their {@code streamId}; slots within a stream by their address (the
 * feature URI of the slot, or its name), which is the {@code featureUri} of the entries the core writes.
 * <p>
 * Called from the lane of the stream only, so one stream is never written concurrently; different
 * streams are. Implementations must be thread-safe across streams.
 */
@ConsumerType
public interface StreamStore {

	/**
	 * Stores a change set. It is final: the store must not change it.
	 *
	 * @param changeSet the change set, with its sequence assigned
	 */
	void append(ChangeSet changeSet);

	/**
	 * @param streamId the stream
	 * @return the highest stored sequence, 0 for an unknown stream
	 */
	long lastSequence(String streamId);

	/**
	 * The comparison base of a slot after a restart (requirement STO-02): the last value that entered
	 * the stream for it, as written (encoded), and its event time.
	 *
	 * @param streamId the stream
	 * @param slotAddress the slot address
	 * @return the last stored value, or empty
	 */
	Optional<StoredValue> lastValue(String streamId, String slotAddress);

	/**
	 * @param streamId the stream
	 * @param fromSequence first sequence, inclusive
	 * @param toSequence last sequence, inclusive
	 * @return the stored change sets in sequence order
	 */
	List<ChangeSet> read(String streamId, long fromSequence, long toSequence);

	/**
	 * Applies a retention rule to the values of one slot (requirement STO-05). Stores that cannot
	 * delete on their own keep the default and report it.
	 *
	 * @param streamId the stream
	 * @param slotAddress the slot
	 * @param rule the rule
	 * @param now the current time, epoch millis
	 * @return what the store did
	 */
	default RetentionOutcome retain(String streamId, String slotAddress, DeletionRule rule, long now) {
		return RetentionOutcome.UNSUPPORTED;
	}

	/**
	 * A value as the store holds it.
	 *
	 * @param value the encoded value
	 * @param eventTime its event time, epoch millis
	 */
	record StoredValue(String value, long eventTime) {
	}

	/** What a store did with a retention rule. */
	enum RetentionOutcome {
		/** The rule was applied. */
		APPLIED,
		/** The store cannot delete on its own. */
		UNSUPPORTED
	}
}
