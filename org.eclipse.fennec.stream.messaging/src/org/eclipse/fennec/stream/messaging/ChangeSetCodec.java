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

import java.io.IOException;
import java.nio.ByteBuffer;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Turns a change set into the payload of a message and back. The stream defines no encoding of
 * its own: implementations delegate to EMF serializations - JSON through emf.codec (the default),
 * Protobuf through emf.util, XMI ({@link XmiChangeSetCodec}).
 * <p>
 * Implementations must be thread-safe.
 */
@ConsumerType
public interface ChangeSetCodec {

	/** Service property naming the content type of a codec service, e.g. {@code application/json}. */
	String CONTENT_TYPE_PROPERTY = "fennec.stream.codec.contentType";

	/**
	 * @return the content type of the payload, e.g. {@code application/json}
	 */
	String contentType();

	/**
	 * @param changeSet the change set; not changed
	 * @return the payload
	 * @throws IOException if it cannot be written
	 */
	ByteBuffer encode(ChangeSet changeSet) throws IOException;

	/**
	 * @param payload the payload
	 * @return the change set
	 * @throws IOException if the payload is no change set of this codec
	 */
	ChangeSet decode(ByteBuffer payload) throws IOException;
}
