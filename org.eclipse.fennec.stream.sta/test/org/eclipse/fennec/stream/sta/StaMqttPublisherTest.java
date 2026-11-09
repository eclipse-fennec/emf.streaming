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

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.FeatureOfInterest;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.StaFactory;
import org.gecko.messaging.api.Message;
import org.gecko.messaging.api.MessageContext;
import org.gecko.messaging.api.MessageContextBuilder;
import org.gecko.messaging.api.MessagePublisher;
import org.gecko.messaging.api.MessagingConstants;
import org.gecko.messaging.api.runtime.ConnectionState;
import org.gecko.messaging.core.MessagingConfiguration;
import org.gecko.messaging.core.memory.InMemoryBroker;
import org.gecko.messaging.core.memory.InMemoryInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.osgi.util.promise.Promise;
import org.osgi.util.pushstream.PushStream;

/**
 * The STA MQTT extension over Gecko Messaging's in-memory broker (#53).
 */
class StaMqttPublisherTest {

	private final String broker = "sta-" + UUID.randomUUID();
	private InMemoryInstance instance;

	@AfterEach
	void tearDown() {
		if (instance != null) {
			instance.stop();
		}
		InMemoryBroker.remove(broker);
	}

	@Test
	void anObservationIsWrittenAsStaJson() {
		Observation observation = observation(42, 27.5);
		observation.setPhenomenonTimeEnd(new Date(2_000));

		assertThat(StaJson.observation(observation, "http://host/sta/v1.1")).isEqualTo("{\"@iot.id\":42,"
				+ "\"@iot.selfLink\":\"http://host/sta/v1.1/Observations(42)\","
				+ "\"phenomenonTime\":\"1970-01-01T00:00:01Z/1970-01-01T00:00:02Z\",\"resultTime\":\"1970-01-01T00:00:05Z\","
				+ "\"result\":27.5,\"Datastream@iot.navigationLink\":\"http://host/sta/v1.1/Observations(42)/Datastream\","
				+ "\"FeatureOfInterest@iot.navigationLink\":\"http://host/sta/v1.1/Observations(42)/FeatureOfInterest\"}");
		observation.setResultNumber(null);
		observation.setResultText("say \"hi\"\n");
		assertThat(StaJson.observation(observation, "r")).contains("\"result\":\"say \\\"hi\\\"\\n\"");
		assertThat(StaJson.key(7L)).isEqualTo("7");
	}

	@Test
	void everyStoredObservationIsPublishedOnItsTopics() throws Exception {
		instance = start();
		List<String> received = new CopyOnWriteArrayList<>();
		for (String topic : List.of("v1.1/Observations", "v1.1/Datastreams(11)/Observations",
				"v1.1/FeaturesOfInterest(12)/Observations")) {
			instance.subscribe(topic).forEach(message -> received
					.add(topic + " " + StandardCharsets.UTF_8.decode(message.payload()).toString()));
		}
		await(() -> instance.getRuntimeDTO().subscriptions.length == 3);
		StaMqttPublisher publisher = new StaMqttPublisher(publisher(instance), "http://host/sta/v1.1", "v1.1");

		publisher.stored(new StaBatch(List.of(), List.of(observation(42, 27.5))));

		await(() -> received.size() == 3);
		assertThat(received).allSatisfy(message -> assertThat(message).contains("\"@iot.id\":42", "\"result\":27.5"));
	}

	private static Observation observation(long id, double result) {
		Datastream datastream = StaFactory.eINSTANCE.createDatastream();
		datastream.setId(11);
		FeatureOfInterest feature = StaFactory.eINSTANCE.createFeatureOfInterest();
		feature.setId(12);
		Observation observation = StaFactory.eINSTANCE.createObservation();
		observation.setId(id);
		observation.setPhenomenonTimeStart(new Date(1_000));
		observation.setResultTime(new Date(5_000));
		observation.setResultNumber(result);
		observation.setDatastream(datastream);
		observation.setFeatureOfInterest(feature);
		return observation;
	}

	private InMemoryInstance start() throws Exception {
		Map<String, Object> props = new HashMap<>();
		props.put(MessagingConstants.MESSAGING_NAME_PROPERTY, "sta");
		props.put("uri", "memory://" + broker);
		InMemoryInstance started = new InMemoryInstance(new MessagingConfiguration(props));
		started.startStandalone();
		await(() -> started.getConnectionState() == ConnectionState.CONNECTED);
		return started;
	}

	private static void await(BooleanSupplier condition) throws InterruptedException {
		long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
		while (!condition.getAsBoolean() && System.nanoTime() < deadline) {
			TimeUnit.MILLISECONDS.sleep(10);
		}
		assertThat(condition.getAsBoolean()).isTrue();
	}

	/** The instance as the API service it registers in OSGi. */
	private static MessagePublisher publisher(InMemoryInstance instance) {
		return new MessagePublisher() {
			@Override
			public Promise<Void> publish(Message message) {
				return instance.publish(message, null);
			}

			@Override
			public Promise<Void> publish(Message message, String channel) {
				return instance.publish(message, instance.newBuilder().channel(channel).buildContext());
			}

			@Override
			public Promise<Void> publish(Message message, MessageContext context) {
				return instance.publish(message, context);
			}

			@Override
			public Promise<Void> publish(Function<MessageContextBuilder, Message> builderFunction) {
				return instance.publish(builderFunction.apply(instance.newBuilder()), null);
			}

			@Override
			public Promise<Long> publish(PushStream<? extends Message> messages) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Promise<Long> publish(PushStream<? extends Message> messages, MessageContext context) {
				throw new UnsupportedOperationException();
			}
		};
	}
}
