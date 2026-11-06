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
package org.eclipse.fennec.stream.codec.json.impl;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.stream.codec.json.JsonChangeSetCodec;
import org.eclipse.fennec.stream.messaging.ChangeSetCodec;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The JSON {@link ChangeSetCodec} service, on the resource factory the Fennec codec registers for
 * {@code application/json} (mandatory). No configuration.
 */
@Component(service = ChangeSetCodec.class, property = ChangeSetCodec.CONTENT_TYPE_PROPERTY + "=" + JsonChangeSetCodec.CONTENT_TYPE)
public class JsonChangeSetCodecComponent implements ChangeSetCodec {

	private final JsonChangeSetCodec codec;

	/**
	 * @param factory the codec's JSON resource factory
	 */
	@Activate
	public JsonChangeSetCodecComponent(
			@Reference(name = "factory", target = "(emf.contentType=application/json)") Resource.Factory factory) {
		this.codec = new JsonChangeSetCodec(factory);
	}

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
