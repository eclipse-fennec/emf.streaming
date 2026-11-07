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
package org.eclipse.fennec.stream.persistence;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.Projection;

/**
 * Projects slot values into a Fennec persistence repository by update commands: the values of one
 * batch become one {@code UpdateCommand} on the target object (see {@link RepositoryUpdates}), and
 * the object is never loaded - JPA turns it into one {@code UPDATE ... SET}, Mongo into one
 * {@code $set}.
 * <p>
 * Not thread-safe, like its repository: the core uses one instance per lane thread.
 */
public final class RepositoryProjection implements Projection {

	private static final Logger LOGGER = Logger.getLogger(RepositoryProjection.class.getName());

	private final Repository repository;

	/**
	 * @param repository the repository holding the target objects, for this projection only
	 */
	public RepositoryProjection(Repository repository) {
		this.repository = requireNonNull(repository, "Repository is required");
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
			return repository.execute(RepositoryUpdates.update(targetClass, targetId, values)) > 0;
		} catch (IOException | RuntimeException e) {
			LOGGER.log(Level.WARNING, e, () -> "Update of " + targetClass.getName() + " " + targetId + " failed");
			return false;
		}
	}
}
