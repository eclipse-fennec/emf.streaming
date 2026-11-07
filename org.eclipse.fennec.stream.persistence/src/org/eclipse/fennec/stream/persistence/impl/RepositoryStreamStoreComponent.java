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
package org.eclipse.fennec.stream.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.StreamStore;
import org.eclipse.fennec.stream.persistence.RepositoryAccess;
import org.eclipse.fennec.stream.persistence.RepositoryStreamStore;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The {@link RepositoryStreamStore} as a service. Configuration required (PID {@value #PID}):
 * {@code repository.target} names the repository holding the stream model. The service carries
 * {@code fennec.stream.store=repository}; the core selects it with
 * {@code store.target=(fennec.stream.store=repository)}.
 * <p>
 * Calls come from several lanes at once; each borrows a repository instance of its own from the
 * prototype-scoped repository service, at most {@code repositories} at a time. Every instance taken
 * is given back with {@code ungetService} when the component deactivates.
 */
@Component(name = RepositoryStreamStoreComponent.PID, configurationPolicy = ConfigurationPolicy.REQUIRE,
		property = "fennec.stream.store=repository")
@Designate(ocd = RepositoryStreamStoreComponent.Config.class)
public class RepositoryStreamStoreComponent implements StreamStore {

	/** The configuration PID and component name. */
	public static final String PID = "FennecRepositoryStreamStore";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Fennec repository stream store")
	public @interface Config {

		/**
		 * @return the filter selecting the repository
		 */
		@AttributeDefinition(description = "Filter selecting the repository that holds the stream model, e.g. (persistence.repository.id=streams)")
		String repository_target();

		/**
		 * @return the most repository instances in use at once
		 */
		@AttributeDefinition(description = "Most repository instances in use at once")
		int repositories() default 4;
	}

	private final RepositoryAccess access;
	private final RepositoryStreamStore store;

	/**
	 * @param repositories the repository service objects
	 * @param config the configuration
	 */
	@Activate
	public RepositoryStreamStoreComponent(
			@Reference(name = "repository", scope = ReferenceScope.PROTOTYPE_REQUIRED) ComponentServiceObjects<Repository> repositories,
			Config config) {
		this.access = new RepositoryAccess(repositories::getService, repositories::ungetService, config.repositories());
		this.store = new RepositoryStreamStore(access);
	}

	/**
	 * Gives every repository instance back.
	 */
	@Deactivate
	void deactivate() {
		access.close();
	}

	@Override
	public void append(ChangeSet changeSet) {
		store.append(changeSet);
	}

	@Override
	public long lastSequence(String streamId) {
		return store.lastSequence(streamId);
	}

	@Override
	public Optional<StoredValue> lastValue(String streamId, String slotAddress) {
		return store.lastValue(streamId, slotAddress);
	}

	@Override
	public List<ChangeSet> read(String streamId, long fromSequence, long toSequence) {
		return store.read(streamId, fromSequence, toSequence);
	}

	@Override
	public RetentionOutcome retain(String streamId, String slotAddress, DeletionRule rule, long now) {
		return store.retain(streamId, slotAddress, rule, now);
	}
}
