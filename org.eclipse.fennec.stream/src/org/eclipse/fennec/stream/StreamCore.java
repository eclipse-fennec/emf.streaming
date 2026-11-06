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

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.osgi.annotation.versioning.ProviderType;
import org.osgi.util.pushstream.PushStream;

/**
 * The change stream of a runtime: source objects go in, change sets come out. {@link EventCore} is
 * the implementation; in OSGi this is the service applications use.
 */
@ProviderType
public interface StreamCore {

	/**
	 * Offers a source object now, with the default author.
	 *
	 * @param source the source object
	 */
	void offer(EObject source);

	/**
	 * Offers a source object.
	 *
	 * @param source the source object
	 * @param eventTime the event time of its values, epoch millis
	 * @param author who emits the change; {@code null} = {@code mapping:<id>}
	 */
	void offer(EObject source, long eventTime, String author);

	/**
	 * Offers several source objects as one unit of work.
	 *
	 * @param sources the source objects
	 * @param eventTime the event time, epoch millis
	 * @param author who emits the change; {@code null} = {@code mapping:<id>}
	 */
	void offerUnitOfWork(Collection<? extends EObject> sources, long eventTime, String author);

	/**
	 * @return a new stream of the change sets written from now on
	 */
	PushStream<ChangeSet> changes();

	/**
	 * Writes every pending batch now.
	 */
	void flush();

	/**
	 * @param targetClass the class of the target object
	 * @param targetId its id
	 * @return the undo change set, or empty when there is nothing to undo
	 */
	CompletableFuture<Optional<ChangeSet>> undo(EClass targetClass, String targetId);

	/**
	 * @param targetClass the class of the target object
	 * @param targetId its id
	 * @return the redo change set, or empty when there is nothing to redo
	 */
	CompletableFuture<Optional<ChangeSet>> redo(EClass targetClass, String targetId);

	/**
	 * @return a receiver builder bound to this core: received change sets run in its lanes
	 */
	StreamReceiver.Builder receiver();

	/**
	 * @return the store the core writes to
	 */
	StreamStore store();

	/**
	 * @return the counters
	 */
	EventCore.Metrics metrics();
}
