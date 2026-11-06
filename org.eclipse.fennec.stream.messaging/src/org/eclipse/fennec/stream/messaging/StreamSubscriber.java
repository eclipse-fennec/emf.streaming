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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.stream.StreamReceiver;
import org.gecko.messaging.api.Message;
import org.gecko.messaging.api.MessageSubscription;
import org.osgi.util.pushstream.PushStream;

/**
 * Receives change sets over Gecko Messaging into a {@link StreamReceiver} (requirements TRN-01,
 * TRN-05, TRN-06). Messages are untrusted input: a payload over the size limit, one the codec
 * cannot read or one that is no valid change set is dropped and counted, never applied.
 */
public final class StreamSubscriber {

	/** The default size limit of a payload: 1 MiB. */
	public static final int DEFAULT_MAX_PAYLOAD = 1 << 20;

	private static final Logger LOGGER = Logger.getLogger(StreamSubscriber.class.getName());

	private final MessageSubscription subscription;
	private final ChangeSetCodec codec;
	private final StreamReceiver receiver;
	private final int maxPayload;
	private volatile long dropped;

	/**
	 * @param subscription the messaging instance
	 * @param codec decodes the payload
	 * @param receiver applies the change sets
	 * @param maxPayload the largest payload accepted, in bytes
	 */
	public StreamSubscriber(MessageSubscription subscription, ChangeSetCodec codec, StreamReceiver receiver,
			int maxPayload) {
		this.subscription = requireNonNull(subscription, "MessageSubscription is required");
		this.codec = requireNonNull(codec, "ChangeSetCodec is required");
		this.receiver = requireNonNull(receiver, "StreamReceiver is required");
		this.maxPayload = maxPayload;
	}

	/**
	 * @param subscription the messaging instance
	 * @param codec decodes the payload
	 * @param receiver applies the change sets
	 */
	public StreamSubscriber(MessageSubscription subscription, ChangeSetCodec codec, StreamReceiver receiver) {
		this(subscription, codec, receiver, DEFAULT_MAX_PAYLOAD);
	}

	/**
	 * Subscribes to a channel (protocol wildcards allowed, e.g. {@code stream/pool/#}).
	 *
	 * @param channel the channel or pattern
	 * @return closing it ends the subscription
	 */
	public AutoCloseable subscribe(String channel) {
		PushStream<Message> messages = subscription.subscribe(requireNonNull(channel, "Channel is required"));
		messages.forEach(this::accept);
		return messages::close;
	}

	/**
	 * @return how many messages were dropped as untrusted input
	 */
	public long dropped() {
		return dropped;
	}

	private void accept(Message message) {
		if (message.payload().remaining() > maxPayload) {
			drop("payload of " + message.payload().remaining() + " bytes exceeds the limit of " + maxPayload, null);
			return;
		}
		ChangeSet changeSet;
		try {
			changeSet = codec.decode(message.payload());
		} catch (IOException | RuntimeException e) {
			drop("payload on " + message.getContext().getChannel() + " is no change set", e);
			return;
		}
		receiver.receive(changeSet).thenAccept(result -> {
			if (result.outcome() == StreamReceiver.Outcome.REJECTED) {
				LOGGER.warning(() -> "Change set " + changeSet.getId() + " refused: " + result.diagnostic().getMessage());
			}
		});
	}

	private synchronized void drop(String why, Exception cause) {
		dropped++;
		LOGGER.log(Level.WARNING, "Message dropped: " + why, cause);
	}
}
