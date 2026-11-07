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
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Projects slot values into a Fennec persistence repository by update commands: the values of one
 * batch become one {@code UpdateCommand} on the target object, one template entry per feature, and
 * the object is never loaded (JPA turns it into one {@code UPDATE ... SET}).
 * <p>
 * A repository instance is not thread-safe and is offered with prototype scope: every lane thread
 * gets its own instance; DS releases them on deactivation.
 * <p>
 * Configuration required (PID {@value #PID}): {@code repository.target} names the repository.
 */
@Component(name = RepositoryProjection.PID, service = Projection.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
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

	private final ThreadLocal<Repository> perThread;

	/**
	 * @param repositories the repository holding the target objects, one instance per thread
	 */
	@Activate
	public RepositoryProjection(
			@Reference(name = "repository", scope = ReferenceScope.PROTOTYPE_REQUIRED) ComponentServiceObjects<Repository> repositories) {
		this.perThread = ThreadLocal.withInitial(repositories::getService);
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
			return perThread.get().execute(PoolUpdates.update(targetClass, targetId, values)) > 0;
		} catch (IOException | RuntimeException e) {
			LOGGER.log(Level.WARNING, e, () -> "Update of " + targetClass.getName() + " " + targetId + " failed");
			return false;
		}
	}
}
