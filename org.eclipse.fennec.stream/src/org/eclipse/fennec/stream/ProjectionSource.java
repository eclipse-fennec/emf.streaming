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

import static java.util.Objects.requireNonNull;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * Hands out {@link Projection} instances to the lanes of the core. A projection need not be
 * thread-safe: every lane thread acquires its own instance on first use and releases it when the
 * thread ends - at the latest when the core closes. Every {@link #acquire()} is paired with exactly
 * one {@link #release(Projection)}.
 * <p>
 * In OSGi this is backed by the service objects of a prototype-scoped projection service.
 */
@ConsumerType
public interface ProjectionSource {

	/**
	 * @return a projection for the calling lane thread
	 */
	Projection acquire();

	/**
	 * @param projection a projection acquired before; it is no longer used
	 */
	void release(Projection projection);

	/**
	 * @param projection one projection for all lanes - it must be thread-safe
	 * @return a source handing out that projection, releasing nothing
	 */
	static ProjectionSource shared(Projection projection) {
		requireNonNull(projection, "Projection is required");
		return new ProjectionSource() {

			@Override
			public Projection acquire() {
				return projection;
			}

			@Override
			public void release(Projection released) {
				// shared: nothing to give back
			}
		};
	}
}
