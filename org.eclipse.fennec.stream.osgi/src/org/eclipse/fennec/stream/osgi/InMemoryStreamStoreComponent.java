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
package org.eclipse.fennec.stream.osgi;

import java.util.List;
import java.util.Optional;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.stream.InMemoryStreamStore;
import org.eclipse.fennec.stream.StreamStore;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * An in-memory {@link StreamStore} service. Configuration required (PID {@value #PID}, may be empty):
 * keeping a stream in memory must be a decision, not what a runtime falls back to.
 */
@Component(name = InMemoryStreamStoreComponent.PID, service = StreamStore.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class InMemoryStreamStoreComponent implements StreamStore {

	/** The configuration PID and component name. */
	public static final String PID = "FennecInMemoryStreamStore";

	private final InMemoryStreamStore store = new InMemoryStreamStore();

	@Override
	public void append(ChangeSet changeSet) {
		store.append(changeSet);
	}

	@Override
	public long lastSequence(String streamId) {
		return store.lastSequence(streamId);
	}

	@Override
	public Optional<StoredValue> lastValue(String streamId, String slotAddress) {
		return store.lastValue(streamId, slotAddress);
	}

	@Override
	public List<ChangeSet> read(String streamId, long fromSequence, long toSequence) {
		return store.read(streamId, fromSequence, toSequence);
	}

	@Override
	public RetentionOutcome retain(String streamId, String slotAddress, DeletionRule rule, long now) {
		return store.retain(streamId, slotAddress, rule, now);
	}
}
