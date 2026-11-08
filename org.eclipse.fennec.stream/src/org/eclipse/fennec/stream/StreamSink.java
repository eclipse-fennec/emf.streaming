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

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Receives every change set the core writes - value batches, keyframes, undo and redo - right after
 * the store took it, in the lane of its target object. Unlike {@link StreamCore#changes()} nothing is
 * dropped under load: a sink is a derived view that must see the whole stream, e.g. SensorThings
 * observations.
 * <p>
 * Lanes of different targets call at the same time, so an implementation must be thread-safe; calls
 * for one target come in sequence order. A sink that throws is logged and skipped: the store holds
 * the truth, the sink can catch up from it.
 */
@ConsumerType
@FunctionalInterface
public interface StreamSink {

	/**
	 * @param mapping the stream mapping of the target object, as the core streams it
	 * @param changeSet the change set as stored; read only
	 */
	void written(StreamMapping mapping, ChangeSet changeSet);
}
