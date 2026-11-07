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

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.Projection;
import org.eclipse.fennec.stream.persistence.RepositoryProjection;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The {@link RepositoryProjection} as a service. Prototype-scoped, like the repository: the core
 * takes one projection per lane thread, and every projection instance gets a repository instance of
 * its own; DS releases the repository with the projection instance.
 * <p>
 * Configuration required (PID {@value #PID}): {@code repository.target} names the repository. The
 * service carries {@code fennec.stream.projection=repository}; the core selects it with
 * {@code projection.target=(fennec.stream.projection=repository)}.
 */
@Component(name = RepositoryProjectionComponent.PID, scope = ServiceScope.PROTOTYPE,
		configurationPolicy = ConfigurationPolicy.REQUIRE, property = "fennec.stream.projection=repository")
@Designate(ocd = RepositoryProjectionComponent.Config.class)
public class RepositoryProjectionComponent implements Projection {

	/** The configuration PID and component name. */
	public static final String PID = "FennecRepositoryProjection";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Fennec repository projection")
	public @interface Config {

		/**
		 * @return the filter selecting the repository
		 */
		@AttributeDefinition(description = "Filter selecting the repository, e.g. (persistence.repository.id=bath)")
		String repository_target();
	}

	private final RepositoryProjection projection;

	/**
	 * @param repository the repository holding the target objects, an instance for this projection only
	 */
	@Activate
	public RepositoryProjectionComponent(
			@Reference(name = "repository", scope = ReferenceScope.PROTOTYPE_REQUIRED) Repository repository) {
		this.projection = new RepositoryProjection(repository);
	}

	@Override
	public boolean project(EClass targetClass, String targetId, EStructuralFeature feature, Object value) {
		return projection.project(targetClass, targetId, feature, value);
	}

	@Override
	public boolean project(EClass targetClass, String targetId, Map<EStructuralFeature, Object> values) {
		return projection.project(targetClass, targetId, values);
	}
}
