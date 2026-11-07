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
package org.eclipse.fennec.stream.messaging;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.support.StreamAddresses;
import org.eclipse.fennec.stream.StreamCore;
import org.eclipse.fennec.stream.StreamId;
import org.gecko.messaging.api.Message;
import org.gecko.messaging.api.MessageContextBuilder;
import org.gecko.messaging.api.MessagePublisher;
import org.gecko.messaging.api.MessagingConstants;
import org.gecko.messaging.api.cloudevents.CloudEventMessageContextBuilder;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.Promises;
import org.osgi.util.pushstream.PushStream;

/**
 * Publishes change sets over Gecko Messaging (requirements TRN-01, TRN-02, TRN-05): one message per
 * change set on the channel of its object ({@link StreamChannels}), the payload encoded by a
 * {@link ChangeSetCodec}. Where the messaging instance speaks CloudEvents the message is one:
 * {@code id} = change set id, {@code source} = stream id, {@code type} = {@value #CLOUD_EVENT_TYPE},
 * {@code subject} = object id, {@code dataschema} = model fingerprint, {@code time} = commit time and
 * the extension {@value #SEQUENCE_EXTENSION}.
 */
public final class StreamPublisher {

	/** The CloudEvent type of a change set. */
	public static final String CLOUD_EVENT_TYPE = "org.eclipse.fennec.stream.changeset";

	/** The CloudEvent extension carrying the sequence. */
	public static final String SEQUENCE_EXTENSION = "sequence";

	private static final Logger LOGGER = Logger.getLogger(StreamPublisher.class.getName());

	private final MessagePublisher publisher;
	private final ChangeSetCodec codec;
	private final StreamChannels channels;
	private final EPackage.Registry models;
	private final boolean retainKeyframes;

	/**
	 * @param publisher the messaging instance
	 * @param codec encodes the payload
	 * @param channels the channel scheme
	 * @param models resolves the class of a stream from its class URI
	 * @param retainKeyframes whether change sets holding a keyframe are published retained, so a new
	 *        subscriber of an object's channel gets its latest keyframe at once. Only for instances
	 *        offering the {@code retain} feature (MQTT, in-memory) - others refuse retained messages.
	 */
	public StreamPublisher(MessagePublisher publisher, ChangeSetCodec codec, StreamChannels channels,
			EPackage.Registry models, boolean retainKeyframes) {
		this.publisher = requireNonNull(publisher, "MessagePublisher is required");
		this.codec = requireNonNull(codec, "ChangeSetCodec is required");
		this.channels = requireNonNull(channels, "StreamChannels are required");
		this.models = requireNonNull(models, "EPackage registry is required");
		this.retainKeyframes = retainKeyframes;
	}

	/**
	 * A publisher that retains nothing.
	 *
	 * @param publisher the messaging instance
	 * @param codec encodes the payload
	 * @param channels the channel scheme
	 * @param models resolves the class of a stream from its class URI
	 */
	public StreamPublisher(MessagePublisher publisher, ChangeSetCodec codec, StreamChannels channels,
			EPackage.Registry models) {
		this(publisher, codec, channels, models, false);
	}

	/**
	 * Publishes every change set the core writes from now on, one after the other. A change set the
	 * instance refuses is logged and skipped. To ride out a connect or reconnect, configure the
	 * instance with {@code publishAwaitConnection} (Gecko Messaging): the publish then waits for the
	 * connection instead of failing. During a long outage the core's output buffer drops its oldest
	 * change sets - receivers then go stale and come back with the next keyframe.
	 *
	 * @param core the core
	 * @return closing it stops publishing
	 */
	public AutoCloseable attach(StreamCore core) {
		PushStream<ChangeSet> changes = core.changes();
		changes.forEach(this::publishOrLog);
		return changes::close;
	}

	private void publishOrLog(ChangeSet changeSet) {
		try {
			publish(changeSet).getValue();
		} catch (InvocationTargetException e) {
			LOGGER.log(Level.WARNING, e.getCause(), () -> "Change set " + changeSet.getId() + " was not published - skipped");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Publishes one change set.
	 *
	 * @param changeSet the change set
	 * @return resolved when the messaging instance took it
	 */
	public Promise<Void> publish(ChangeSet changeSet) {
		requireNonNull(changeSet, "ChangeSet is required");
		Optional<StreamId> streamId = StreamId.parse(changeSet.getStreamId());
		Optional<EClass> eClass = streamId.flatMap(id -> StreamAddresses.resolveClass(models, id.classUri()));
		if (eClass.isEmpty()) {
			return Promises.failed(new IllegalArgumentException("Stream " + changeSet.getStreamId() + " names no known class"));
		}
		ByteBuffer payload;
		try {
			payload = codec.encode(changeSet);
		} catch (IOException e) {
			return Promises.failed(e);
		}
		String channel = channels.channel(eClass.get(), streamId.get().objectId());
		return publisher.publish(builder -> message(builder, channel, payload, changeSet, streamId.get()));
	}

	private Message message(MessageContextBuilder builder, String channel, ByteBuffer payload,
			ChangeSet changeSet, StreamId streamId) {
		builder.channel(channel).contentType(codec.contentType()).content(payload);
		if (retainKeyframes && changeSet.getEntries().stream().anyMatch(e -> e.getKind() == DeltaKind.KEYFRAME)) {
			builder.extensionEntry(MessagingConstants.EXTENSION_RETAIN, Boolean.TRUE);
		}
		if (builder.isAdaptable(CloudEventMessageContextBuilder.class)) {
			builder.adapt(CloudEventMessageContextBuilder.class).id(changeSet.getId()).source(changeSet.getStreamId())
					.type(CLOUD_EVENT_TYPE).subject(streamId.objectId()).dataSchema(changeSet.getContextFingerprint())
					.time(OffsetDateTime.ofInstant(Instant.ofEpochMilli(changeSet.getCommitTime()), ZoneOffset.UTC));
			// the headers of a CloudEvent message are its extension attributes
			builder.header(SEQUENCE_EXTENSION, changeSet.getSequence());
		}
		return builder.buildMessage();
	}
}
