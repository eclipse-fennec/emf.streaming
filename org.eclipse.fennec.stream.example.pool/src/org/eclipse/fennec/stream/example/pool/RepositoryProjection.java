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
package org.eclipse.fennec.stream.example.pool;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.Projection;
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
 * Projects slot values into a Fennec persistence repository by update commands: the values of one
 * batch become one {@code UpdateCommand} on the target object, one template entry per feature, and
 * the object is never loaded (JPA turns it into one {@code UPDATE ... SET}).
 * <p>
 * Prototype-scoped, like the repository: the core takes one projection per lane thread, and every
 * projection instance gets a repository instance of its own. Neither needs to be thread-safe; DS
 * releases the repository with the projection instance.
 * <p>
 * Configuration required (PID {@value #PID}): {@code repository.target} names the repository. The
 * service carries {@code fennec.stream.projection=repository}; the core selects it with
 * {@code projection.target=(fennec.stream.projection=repository)}.
 */
@Component(name = RepositoryProjection.PID, service = Projection.class, scope = ServiceScope.PROTOTYPE,
		configurationPolicy = ConfigurationPolicy.REQUIRE, property = "fennec.stream.projection=repository")
@Designate(ocd = RepositoryProjection.Config.class)
public class RepositoryProjection implements Projection {

	/** The configuration PID and component name. */
	public static final String PID = "FennecRepositoryProjection";

	private static final Logger LOGGER = Logger.getLogger(RepositoryProjection.class.getName());

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

	private final Repository repository;

	/**
	 * @param repository the repository holding the target objects, an instance for this projection only
	 */
	@Activate
	public RepositoryProjection(
			@Reference(name = "repository", scope = ReferenceScope.PROTOTYPE_REQUIRED) Repository repository) {
		this.repository = repository;
	}

	@Override
	public boolean project(EClass targetClass, String targetId, EStructuralFeature feature, Object value) {
		Map<EStructuralFeature, Object> values = new LinkedHashMap<>();
		values.put(feature, value);
		return project(targetClass, targetId, values);
	}

	@Override
	public boolean project(EClass targetClass, String targetId, Map<EStructuralFeature, Object> values) {
		try {
			return repository.execute(PoolUpdates.update(targetClass, targetId, values)) > 0;
		} catch (IOException | RuntimeException e) {
			LOGGER.log(Level.WARNING, e, () -> "Update of " + targetClass.getName() + " " + targetId + " failed");
			return false;
		}
	}
}
