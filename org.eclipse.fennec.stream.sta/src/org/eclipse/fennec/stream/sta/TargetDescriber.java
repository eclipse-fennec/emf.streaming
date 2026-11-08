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

import org.eclipse.emf.ecore.EClass;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Describes the target object behind a Thing: its name and where it is. Asked once per target, when
 * its Thing is created.
 */
@ConsumerType
@FunctionalInterface
public interface TargetDescriber {

	/** Names a thing by its target id, without location. */
	TargetDescriber IDS = (targetClass, targetId) -> new Description(targetId, null, null);

	/**
	 * What STA shows of a target object.
	 *
	 * @param name the thing's name
	 * @param description its description, may be {@code null}
	 * @param geoJson its location as a GeoJSON geometry, {@code null} for none
	 */
	record Description(String name, String description, String geoJson) {
	}

	/**
	 * @param targetClass the class of the target object
	 * @param targetId its id
	 * @return its description; never {@code null}
	 */
	Description describe(EClass targetClass, String targetId);
}
