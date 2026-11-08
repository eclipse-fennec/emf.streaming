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

import org.osgi.annotation.versioning.ConsumerType;

/**
 * Stores what {@link StaMapper} yields. Thread-safe: lanes of different targets write at once.
 */
@ConsumerType
@FunctionalInterface
public interface StaWriter {

	/**
	 * Writes the entities not stored yet, then the observations.
	 *
	 * @param batch the entities of one change set
	 */
	void write(StaBatch batch);
}
