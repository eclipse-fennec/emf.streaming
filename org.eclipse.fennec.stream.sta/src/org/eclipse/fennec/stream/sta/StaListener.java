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
 * Hears about observations once they are stored - so a client told about one can follow its
 * {@code @iot.selfLink}. Called in the lane of the target object; must be thread-safe.
 */
@ConsumerType
@FunctionalInterface
public interface StaListener {

	/**
	 * @param batch the entities of one change set, stored
	 */
	void stored(StaBatch batch);
}
