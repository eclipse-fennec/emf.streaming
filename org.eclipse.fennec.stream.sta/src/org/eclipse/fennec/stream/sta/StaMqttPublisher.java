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

import static java.util.Objects.requireNonNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.fennec.model.sta.Observation;
import org.gecko.messaging.api.MessagePublisher;

/**
 * The STA 1.1 MQTT extension (#53): every stored observation is published as STA JSON on
 * {@code <prefix>/Observations}, {@code <prefix>/Datastreams(<id>)/Observations} and
 * {@code <prefix>/FeaturesOfInterest(<id>)/Observations}. A publish that fails is logged; the
 * observation stays stored and readable over HTTP.
 */
public final class StaMqttPublisher implements StaListener {

	/** The content type of the messages. */
	public static final String CONTENT_TYPE = "application/json";

	private static final Logger LOGGER = Logger.getLogger(StaMqttPublisher.class.getName());

	private final MessagePublisher publisher;
	private final String serviceRoot;
	private final String prefix;

	/**
	 * @param publisher the messaging instance (MQTT)
	 * @param serviceRoot the STA service root for the links, e.g. {@code https://host/sta/v1.1}
	 * @param prefix the topic prefix, {@code v1.1} in STA 1.1
	 */
	public StaMqttPublisher(MessagePublisher publisher, String serviceRoot, String prefix) {
		this.publisher = requireNonNull(publisher, "MessagePublisher is required");
		this.serviceRoot = requireNonNull(serviceRoot, "Service root is required");
		this.prefix = requireNonNull(prefix, "Topic prefix is required");
	}

	@Override
	public void stored(StaBatch batch) {
		for (Observation observation : batch.observations()) {
			byte[] json = StaJson.observation(observation, serviceRoot).getBytes(StandardCharsets.UTF_8);
			for (String topic : topics(observation)) {
				publisher.publish(builder -> builder.channel(topic).contentType(CONTENT_TYPE).content(ByteBuffer.wrap(json))
						.buildMessage()).onFailure(e -> LOGGER.log(Level.WARNING, e,
								() -> "Observation " + observation.getId() + " was not published on " + topic));
			}
		}
	}

	/**
	 * @param observation an observation
	 * @return the topics it is published on
	 */
	public List<String> topics(Observation observation) {
		String datastream = observation.getDatastream() == null ? null : observation.getDatastream().getId();
		String feature = observation.getFeatureOfInterest() == null ? null : observation.getFeatureOfInterest().getId();
		List<String> topics = new ArrayList<>();
		topics.add(prefix + "/Observations");
		if (datastream != null) {
			topics.add(prefix + "/Datastreams(" + StaJson.key(datastream) + ")/Observations");
		}
		if (feature != null) {
			topics.add(prefix + "/FeaturesOfInterest(" + StaJson.key(feature) + ")/Observations");
		}
		return topics;
	}
}
