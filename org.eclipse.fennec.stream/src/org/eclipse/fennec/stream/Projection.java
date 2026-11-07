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

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Writes a slot value into its target object (requirement CAP-07) - into a loaded instance, or as a
 * partial update of the store without loading it (JPA column update, Mongo {@code $set}; those
 * implementations live in emf.persistence).
 * <p>
 * Called from the lane of the target object only; an instance need not be thread-safe when it comes
 * from a {@link ProjectionSource} (one per lane thread). The value is already converted and coerced to
 * the feature's type.
 */
@ConsumerType
@FunctionalInterface
public interface Projection {

	/** A projection that writes nothing - for runtimes where every slot is audit-only. */
	Projection NONE = (targetClass, targetId, feature, value) -> false;

	/**
	 * @param targetClass the class of the target object
	 * @param targetId its id
	 * @param feature the feature to set
	 * @param value the value; {@code null} unsets the feature (an undo back to "no value")
	 * @return whether a target was changed (false when it is unknown)
	 */
	boolean project(EClass targetClass, String targetId, EStructuralFeature feature, Object value);

	/**
	 * Writes several features of one target object at once - what a batch of the core yields. A
	 * store implementation turns it into one update (e.g. one {@code UpdateCommand} with a template
	 * entry per feature); the default writes feature by feature.
	 *
	 * @param targetClass the class of the target object
	 * @param targetId its id
	 * @param values the values by feature, in the order they were written; {@code null} unsets
	 * @return whether the target was changed
	 */
	default boolean project(EClass targetClass, String targetId, Map<EStructuralFeature, Object> values) {
		boolean changed = false;
		for (Map.Entry<EStructuralFeature, Object> value : values.entrySet()) {
			changed |= project(targetClass, targetId, value.getKey(), value.getValue());
		}
		return changed;
	}
}
