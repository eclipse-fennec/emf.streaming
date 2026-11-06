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
package org.eclipse.fennec.stream.messaging.impl;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.stream.messaging.ChangeSetCodec;
import org.eclipse.fennec.stream.messaging.XmiChangeSetCodec;
import org.osgi.service.component.annotations.Component;

/**
 * The XMI {@link ChangeSetCodec} service. No references, no configuration.
 */
@Component(service = ChangeSetCodec.class, property = ChangeSetCodec.CONTENT_TYPE_PROPERTY + "=" + XmiChangeSetCodec.CONTENT_TYPE)
public class XmiChangeSetCodecComponent implements ChangeSetCodec {

	private final XmiChangeSetCodec codec = new XmiChangeSetCodec();

	@Override
	public String contentType() {
		return codec.contentType();
	}

	@Override
	public ByteBuffer encode(ChangeSet changeSet) throws IOException {
		return codec.encode(changeSet);
	}

	@Override
	public ChangeSet decode(ByteBuffer payload) throws IOException {
		return codec.decode(payload);
	}
}
