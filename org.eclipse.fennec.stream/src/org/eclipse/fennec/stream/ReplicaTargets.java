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
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Where a receiver finds and creates the objects it rebuilds from change sets. Called from the
 * lane of the object only.
 */
@ConsumerType
public interface ReplicaTargets {

	/**
	 * @param eClass the class
	 * @param id the object id
	 * @return the object, or {@code null} when it is unknown here
	 */
	EObject find(EClass eClass, String id);

	/**
	 * Creates an object; its id attribute is set by the caller.
	 *
	 * @param eClass the class
	 * @param id the object id
	 * @return the new object, already findable
	 */
	EObject create(EClass eClass, String id);

	/**
	 * Removes an object.
	 *
	 * @param eClass the class
	 * @param id the object id
	 */
	void delete(EClass eClass, String id);

	/**
	 * Called after a change set was applied to an object - the place to persist it.
	 *
	 * @param object the object
	 */
	default void changed(EObject object) {
	}

	/**
	 * Replica objects in memory, keyed by class and id.
	 *
	 * @return the targets
	 */
	static ReplicaTargets inMemory() {
		Map<String, EObject> objects = new ConcurrentHashMap<>();
		return new ReplicaTargets() {
			@Override
			public EObject find(EClass eClass, String id) {
				return objects.get(EventCore.streamId(eClass, id));
			}

			@Override
			public EObject create(EClass eClass, String id) {
				EObject object = EcoreUtil.create(eClass);
				objects.put(EventCore.streamId(eClass, id), object);
				return object;
			}

			@Override
			public void delete(EClass eClass, String id) {
				objects.remove(EventCore.streamId(eClass, id));
			}
		};
	}
}
