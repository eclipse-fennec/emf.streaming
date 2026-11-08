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

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fennec.model.sta.Observation;

/**
 * The STA entities one change set yields.
 *
 * @param entities the entities the observations refer to, each written only when it is not stored
 *        yet, in an order where an entity comes after those it refers to
 * @param observations the new observations
 */
public record StaBatch(List<EObject> entities, List<Observation> observations) {

	/** A change set without observations. */
	public static final StaBatch EMPTY = new StaBatch(List.of(), List.of());

	/**
	 * @param entities the entities
	 * @param observations the observations
	 */
	public StaBatch {
		entities = List.copyOf(entities);
		observations = List.copyOf(observations);
	}

	/**
	 * @return whether there is nothing to write
	 */
	public boolean isEmpty() {
		return observations.isEmpty();
	}
}
