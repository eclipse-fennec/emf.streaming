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
package org.eclipse.fennec.stream.example.pool;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.mapping.ValueFunction;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Which pool a pH probe sits in - the identity resolver of the probes, a table outside the model.
 * Configuration required (PID {@value #PID}): {@code assignments} as {@code probeId=poolId}.
 */
@Component(name = PhProbeTable.PID, configurationPolicy = ConfigurationPolicy.REQUIRE,
		property = ValueFunction.FUNCTION_ID + "=" + PoolStreamMapping.PH_TO_POOL)
@Designate(ocd = PhProbeTable.Config.class)
public class PhProbeTable implements ValueFunction {

	/** The configuration PID and component name. */
	public static final String PID = "FennecPoolPhProbes";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "pH probes of the pools")
	public @interface Config {

		/**
		 * @return the assignments
		 */
		@AttributeDefinition(description = "probeId=poolId")
		String[] assignments();
	}

	private final Map<String, String> pools = new HashMap<>();

	/**
	 * @param config the assignments
	 */
	@Activate
	public PhProbeTable(Config config) {
		for (String assignment : config.assignments()) {
			int eq = assignment.indexOf('=');
			if (eq > 0) {
				pools.put(assignment.substring(0, eq).trim(), assignment.substring(eq + 1).trim());
			}
		}
	}

	@Override
	public Object apply(Object input) {
		return input instanceof EObject probe ? pools.get(EcoreUtil.getID(probe)) : null;
	}
}
