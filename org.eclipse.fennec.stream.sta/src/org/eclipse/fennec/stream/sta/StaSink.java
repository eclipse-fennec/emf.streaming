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
package org.eclipse.fennec.stream.sta;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.stream.StreamSink;

/**
 * The STA projection: every change set the core writes becomes observations (and, the first time,
 * the things, datastreams, sensors and observed properties behind them). Once stored, the
 * {@link StaListener}s hear about them; one that fails is logged and skipped.
 */
public final class StaSink implements StreamSink {

	private static final Logger LOGGER = Logger.getLogger(StaSink.class.getName());

	private final StaMapper mapper;
	private final StaWriter writer;
	private final List<StaListener> listeners = new CopyOnWriteArrayList<>();

	/**
	 * @param mapper turns change sets into STA entities
	 * @param writer stores them
	 */
	public StaSink(StaMapper mapper, StaWriter writer) {
		this.mapper = requireNonNull(mapper, "StaMapper is required");
		this.writer = requireNonNull(writer, "StaWriter is required");
	}

	@Override
	public void written(StreamMapping mapping, ChangeSet changeSet) {
		StaBatch batch = mapper.map(mapping, changeSet);
		if (batch.isEmpty()) {
			return;
		}
		writer.write(batch);
		for (StaListener listener : listeners) {
			try {
				listener.stored(batch);
			} catch (RuntimeException e) {
				LOGGER.log(Level.WARNING, e, () -> "STA listener " + listener + " failed - skipped");
			}
		}
	}

	/**
	 * @param listener hears about every batch stored from now on
	 */
	public void addListener(StaListener listener) {
		listeners.add(requireNonNull(listener, "StaListener is required"));
	}

	/**
	 * @param listener a listener added before
	 */
	public void removeListener(StaListener listener) {
		listeners.remove(listener);
	}
}
