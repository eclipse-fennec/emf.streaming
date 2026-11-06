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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * The channel a change set travels on - part of the stream's wire specification, so that clients
 * without Fennec (an MQTT client, a dashboard) know where to subscribe (requirement TRN-03).
 * <p>
 * {@code <root>/<nsPrefix>/<EClass>/<objectId>}, default root {@value #DEFAULT_ROOT}: one channel per
 * object, so the protocol's wildcards select by model, by class or by object
 * ({@code stream/pool/#}, {@code stream/pool/Pool/+}). The object id is URL-encoded (UTF-8), so ids
 * holding {@code /}, {@code +} or {@code #} stay one segment.
 */
public final class StreamChannels {

	/** The default first segment. */
	public static final String DEFAULT_ROOT = "stream";

	private final String root;

	/**
	 * @param root the first segment of every channel
	 */
	public StreamChannels(String root) {
		this.root = requireNonNull(root, "Root is required");
	}

	/**
	 * Channels below {@value #DEFAULT_ROOT}.
	 */
	public StreamChannels() {
		this(DEFAULT_ROOT);
	}

	/**
	 * @param eClass the class of the object
	 * @param objectId its id
	 * @return the channel of the object
	 */
	public String channel(EClass eClass, String objectId) {
		return classChannel(eClass) + "/" + URLEncoder.encode(objectId, StandardCharsets.UTF_8);
	}

	/**
	 * @param eClass a class
	 * @return the channel prefix of its objects (append {@code /+} or {@code /#} to subscribe)
	 */
	public String classChannel(EClass eClass) {
		return packageChannel(eClass.getEPackage()) + "/" + eClass.getName();
	}

	/**
	 * @param ePackage a package
	 * @return the channel prefix of its classes
	 */
	public String packageChannel(EPackage ePackage) {
		return root + "/" + ePackage.getNsPrefix();
	}

	/**
	 * @param channel a channel this scheme produced
	 * @return the object id in its last segment
	 */
	public static String objectIdOf(String channel) {
		return URLDecoder.decode(channel.substring(channel.lastIndexOf('/') + 1), StandardCharsets.UTF_8);
	}
}
