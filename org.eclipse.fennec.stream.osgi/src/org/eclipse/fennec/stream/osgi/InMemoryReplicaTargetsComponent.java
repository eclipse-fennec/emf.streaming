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
package org.eclipse.fennec.stream.osgi;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.stream.ReplicaTargets;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * Replica objects in memory, as a service. Configuration required (PID {@value #PID}, may be empty):
 * replicas that vanish with the process must be a decision.
 */
@Component(name = InMemoryReplicaTargetsComponent.PID, service = ReplicaTargets.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class InMemoryReplicaTargetsComponent implements ReplicaTargets {

	/** The configuration PID and component name. */
	public static final String PID = "FennecInMemoryReplicaTargets";

	private final ReplicaTargets targets = ReplicaTargets.inMemory();

	@Override
	public EObject find(EClass eClass, String id) {
		return targets.find(eClass, id);
	}

	@Override
	public EObject create(EClass eClass, String id) {
		return targets.create(eClass, id);
	}

	@Override
	public void delete(EClass eClass, String id) {
		targets.delete(eClass, id);
	}
}
