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

import org.eclipse.fennec.stream.sta.StaBatch;
import org.eclipse.fennec.stream.sta.StaListener;
import org.eclipse.fennec.stream.sta.StaMqttPublisher;
import org.gecko.messaging.api.MessagePublisher;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The STA MQTT extension as a {@link StaListener} service; the STA sink hands it every stored batch.
 * Configuration required (PID {@value #PID}): {@code publisher.target} names the Gecko Messaging
 * instance, {@code serviceRoot} the STA root the links point to.
 */
@Component(name = StaMqttPublisherComponent.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = StaMqttPublisherComponent.Config.class)
public class StaMqttPublisherComponent implements StaListener {

	/** The configuration PID and component name. */
	public static final String PID = "FennecStaMqttPublisher";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Fennec SensorThings MQTT extension")
	public @interface Config {

		/**
		 * @return the filter selecting the messaging instance
		 */
		@AttributeDefinition(description = "Filter selecting the Gecko Messaging instance, e.g. (messaging.name=mqtt)")
		String publisher_target();

		/**
		 * @return the STA service root
		 */
		@AttributeDefinition(description = "The STA service root the links point to, e.g. http://localhost:8080/sta/v1.1")
		String serviceRoot();

		/**
		 * @return the topic prefix
		 */
		@AttributeDefinition(description = "Topic prefix; v1.1 in SensorThings 1.1")
		String topicPrefix() default "v1.1";
	}

	private final StaMqttPublisher publisher;

	/**
	 * @param publisher the messaging instance
	 * @param config the configuration
	 */
	@Activate
	public StaMqttPublisherComponent(@Reference(name = "publisher") MessagePublisher publisher, Config config) {
		this.publisher = new StaMqttPublisher(publisher, config.serviceRoot(), config.topicPrefix());
	}

	@Override
	public void stored(StaBatch batch) {
		publisher.stored(batch);
	}
}
