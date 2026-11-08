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
package org.eclipse.fennec.stream.sta.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fennec.stream.sta.TargetDescriber;
import org.osgi.service.component.annotations.Component;

/**
 * The default {@link TargetDescriber}: a thing is named by its target id and has no location. No
 * configuration; carries {@value #PROPERTY}={@code ids}.
 */
@Component(property = IdsTargetDescriberComponent.PROPERTY + "=ids")
public class IdsTargetDescriberComponent implements TargetDescriber {

	/** Service property naming a describer. */
	public static final String PROPERTY = "fennec.sta.describer";

	/** The filter selecting this describer. */
	public static final String FILTER = "(" + PROPERTY + "=ids)";

	@Override
	public Description describe(EClass targetClass, String targetId) {
		return IDS.describe(targetClass, targetId);
	}
}
