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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.stream.Projection;
import org.osgi.service.component.annotations.Component;

/**
 * A {@link Projection} that writes nothing, the default projection of the core (audit only): it runs
 * without configuration and carries {@code fennec.stream.projection=none}, which the core selects
 * unless its {@code projection.target} names another projection.
 */
@Component(name = NoProjectionComponent.PID, service = Projection.class,
		property = StreamCoreComponent.PROJECTION_PROPERTY + "=" + StreamCoreComponent.NONE)
public class NoProjectionComponent implements Projection {

	/** The configuration PID and component name. */
	public static final String PID = "FennecNoProjection";

	@Override
	public boolean project(EClass targetClass, String targetId, EStructuralFeature feature, Object value) {
		return false;
	}
}
