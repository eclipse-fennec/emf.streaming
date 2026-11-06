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

import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * A {@link Projection} onto loaded objects: finds the target by class and id and sets the feature.
 * Persisting the object afterwards is the caller's business (or use a store projection that
 * updates without loading).
 */
public final class ObjectProjection implements Projection {

	private final BiFunction<EClass, String, EObject> lookup;

	/**
	 * @param lookup finds the target object by class and id; returns {@code null} when unknown
	 */
	public ObjectProjection(BiFunction<EClass, String, EObject> lookup) {
		this.lookup = requireNonNull(lookup, "Lookup is required");
	}

	@Override
	public boolean project(EClass targetClass, String targetId, EStructuralFeature feature, Object value) {
		EObject target = lookup.apply(targetClass, targetId);
		if (target == null || !target.eClass().getEAllStructuralFeatures().contains(feature)) {
			return false;
		}
		if (value == null) {
			target.eUnset(feature);
		} else {
			target.eSet(feature, value);
		}
		return true;
	}
}
