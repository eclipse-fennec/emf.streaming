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
package org.eclipse.fennec.stream.codec.json;

import static java.util.Objects.requireNonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.config.ConfigurationResolver;
import org.eclipse.fennec.codec.resource.CodecResourceFactory;
import org.eclipse.fennec.codec.util.MetadataServiceFactory;
import org.eclipse.fennec.emf.osgi.metadata.MetadataWhiteboard;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.StreamPackage;
import org.eclipse.fennec.stream.messaging.ChangeSetCodec;

/**
 * Change sets as JSON, written and read by the Eclipse Fennec EMF codec with its default
 * configuration - the default payload of the stream transport (decision D-6).
 * <p>
 * The payload names only the stream model; values inside the change set are strings. Thread-safe:
 * every call uses its own resource set over a shared factory.
 */
public final class JsonChangeSetCodec implements ChangeSetCodec {

	/** The content type of the payload. */
	public static final String CONTENT_TYPE = "application/json";

	private static final URI URI_OF_PAYLOAD = URI.createURI("changeset.json");

	private final Resource.Factory factory;

	/**
	 * @param metadata the metadata service the codec reads its configuration from; the stream model
	 *        is registered with it
	 * @param configuration how the codec resolves its configuration
	 */
	public JsonChangeSetCodec(MetadataWhiteboard metadata, ConfigurationResolver configuration) {
		requireNonNull(metadata, "Metadata service is required");
		metadata.registerPackage(StreamPackage.eINSTANCE);
		this.factory = new CodecResourceFactory(metadata, requireNonNull(configuration, "ConfigurationResolver is required"));
	}

	/**
	 * A codec on a resource factory that already reads and writes codec JSON - in OSGi the one the
	 * Fennec codec registers for {@code application/json}.
	 *
	 * @param factory the resource factory
	 */
	public JsonChangeSetCodec(Resource.Factory factory) {
		this.factory = requireNonNull(factory, "Resource factory is required");
	}

	/**
	 * A codec with its own metadata service and the default configuration - for use outside a framework.
	 */
	public JsonChangeSetCodec() {
		this(MetadataServiceFactory.create(), ConfigurationResolver.defaults());
	}

	@Override
	public String contentType() {
		return CONTENT_TYPE;
	}

	@Override
	public ByteBuffer encode(ChangeSet changeSet) throws IOException {
		Resource resource = resourceSet().createResource(URI_OF_PAYLOAD);
		// a copy: the change set is shared with other consumers and stays in its own container
		resource.getContents().add(EcoreUtil.copy(changeSet));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		resource.save(out, Map.of());
		return ByteBuffer.wrap(out.toByteArray()).asReadOnlyBuffer();
	}

	@Override
	public ChangeSet decode(ByteBuffer payload) throws IOException {
		byte[] bytes = new byte[payload.remaining()];
		payload.duplicate().get(bytes);
		Resource resource = resourceSet().createResource(URI_OF_PAYLOAD);
		try {
			resource.load(new ByteArrayInputStream(bytes), Map.of());
		} catch (RuntimeException e) {
			throw new IOException("Payload is no JSON change set", e);
		}
		if (resource.getContents().size() != 1 || !(resource.getContents().get(0) instanceof ChangeSet changeSet)) {
			throw new IOException("Payload holds no single change set");
		}
		resource.getContents().clear();
		return changeSet;
	}

	private ResourceSet resourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(StreamPackage.eNS_URI, StreamPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", factory);
		return resourceSet;
	}
}
