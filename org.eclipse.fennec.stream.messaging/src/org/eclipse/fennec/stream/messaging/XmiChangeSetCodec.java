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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.StreamPackage;

/**
 * Change sets as XMI - EMF's own serialization, readable without anything but the stream model.
 * The payload names no other model: values are strings in the stream model.
 */
public final class XmiChangeSetCodec implements ChangeSetCodec {

	/** The content type of the payload. */
	public static final String CONTENT_TYPE = "application/xmi+xml";

	private static final URI URI_OF_PAYLOAD = URI.createURI("changeset.xmi");
	private static final Map<String, Object> OPTIONS = Map.of(XMLResource.OPTION_ENCODING, "UTF-8");

	@Override
	public String contentType() {
		return CONTENT_TYPE;
	}

	@Override
	public ByteBuffer encode(ChangeSet changeSet) throws IOException {
		Resource resource = new XMIResourceImpl(URI_OF_PAYLOAD);
		// a copy: the change set is shared with other consumers and stays in its own container
		resource.getContents().add(EcoreUtil.copy(changeSet));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		resource.save(out, OPTIONS);
		return ByteBuffer.wrap(out.toByteArray()).asReadOnlyBuffer();
	}

	@Override
	public ChangeSet decode(ByteBuffer payload) throws IOException {
		StreamPackage.eINSTANCE.eClass(); // the stream model must be registered to read it
		byte[] bytes = new byte[payload.remaining()];
		payload.duplicate().get(bytes);
		Resource resource = new XMIResourceImpl(URI_OF_PAYLOAD);
		resource.load(new ByteArrayInputStream(bytes), OPTIONS);
		if (resource.getContents().size() != 1 || !(resource.getContents().get(0) instanceof ChangeSet changeSet)) {
			throw new IOException("Payload holds no single change set");
		}
		resource.getContents().clear();
		return changeSet;
	}
}
