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
package org.eclipse.fennec.stream;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fennec.mapping.Mappings;
import org.eclipse.fennec.model.stream.support.StreamAddresses;

/**
 * The id of the stream of one object: {@code <classUri>/<objectId>}, e.g.
 * {@code https://example.org/pool#//Pool/pool-1}. The object id may itself contain {@code /}.
 *
 * @param classUri the URI of the object's class ({@code nsURI#//Name})
 * @param objectId the object id
 */
public record StreamId(String classUri, String objectId) {

	/**
	 * Canonical constructor.
	 */
	public StreamId {
		requireNonNull(classUri, "Class URI is required");
		requireNonNull(objectId, "Object id is required");
	}

	/**
	 * @param eClass the class of the object
	 * @param objectId its id
	 * @return the stream id
	 */
	public static StreamId of(EClass eClass, String objectId) {
		return new StreamId(Mappings.uriOf(eClass), objectId);
	}

	/**
	 * @param streamId a stream id in its string form
	 * @return the parts, or empty when the string is no stream id
	 */
	public static Optional<StreamId> parse(String streamId) {
		if (streamId == null) {
			return Optional.empty();
		}
		int fragment = streamId.indexOf(StreamAddresses.FRAGMENT_SEPARATOR);
		if (fragment < 0) {
			return Optional.empty();
		}
		int slash = streamId.indexOf('/', fragment + StreamAddresses.FRAGMENT_SEPARATOR.length());
		if (slash < 0 || slash == streamId.length() - 1) {
			return Optional.empty();
		}
		return Optional.of(new StreamId(streamId.substring(0, slash), streamId.substring(slash + 1)));
	}

	@Override
	public String toString() {
		return classUri + "/" + objectId;
	}
}
