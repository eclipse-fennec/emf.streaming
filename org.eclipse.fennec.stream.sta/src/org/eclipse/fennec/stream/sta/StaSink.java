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

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.stream.StreamSink;

/**
 * The STA projection: every change set the core writes becomes observations (and, the first time,
 * the things, datastreams, sensors and observed properties behind them).
 */
public final class StaSink implements StreamSink {

	private final StaMapper mapper;
	private final StaWriter writer;

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
		if (!batch.isEmpty()) {
			writer.write(batch);
		}
	}
}
