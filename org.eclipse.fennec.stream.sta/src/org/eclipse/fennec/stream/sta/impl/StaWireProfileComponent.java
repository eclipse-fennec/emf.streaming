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

import java.util.List;

import org.eclipse.fennec.odata.wire.api.WireProfile;
import org.eclipse.fennec.stream.sta.StaWireProfile;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The SensorThings wire profile as a service: an emf.odata root selects it with
 * {@code "wireProfile.target": "(odata.wire.profile=sta)"}. Runs without configuration; the
 * MQTT endpoints are announced in the root document once configured.
 */
@Component(name = StaWireProfileComponent.PID, service = WireProfile.class,
		property = WireProfile.PROFILE_PROPERTY + "=" + StaWireProfile.NAME)
@Designate(ocd = StaWireProfileComponent.Config.class)
public class StaWireProfileComponent extends StaWireProfile {

	/** The configuration PID and component name. */
	public static final String PID = "FennecStaWireProfile";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Fennec SensorThings wire profile")
	public @interface Config {

		/**
		 * @return the broker URLs of the MQTT extension
		 */
		@AttributeDefinition(required = false, description = "Broker URLs of the MQTT extension, e.g. mqtt://localhost:1883; announced in the root document")
		String[] mqtt_endpoints() default {};
	}

	/**
	 * @param config the configuration
	 */
	@Activate
	public StaWireProfileComponent(Config config) {
		super(List.of(config.mqtt_endpoints()));
	}
}
