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
package org.eclipse.fennec.stream.sta;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.persistence.RepositoryAccess;

/**
 * Writes STA entities into a Fennec persistence repository; the repository's unit maps the STA
 * model, emf.odata serves it from there. A batch is saved in one {@code saveAll}: the observations
 * with the entities the repository does not hold yet, their references to entities it holds already
 * pointing at the stored instances.
 * <p>
 * Entities are remembered once stored; new ones are stored one lane at a time, observations in
 * parallel. Thread-safe through the {@link RepositoryAccess}.
 */
public final class RepositoryStaWriter implements StaWriter {

	private final RepositoryAccess repositories;
	private final Set<String> stored = ConcurrentHashMap.newKeySet();

	/**
	 * @param repositories the repository holding the STA model
	 */
	public RepositoryStaWriter(RepositoryAccess repositories) {
		this.repositories = requireNonNull(repositories, "RepositoryAccess is required");
	}

	@Override
	public void write(StaBatch batch) {
		requireNonNull(batch, "StaBatch is required");
		try {
			repositories.call(repository -> {
				if (batch.entities().stream().allMatch(entity -> stored.contains(key(entity)))) {
					save(new LinkedHashMap<>(), batch.observations(), repository);
				} else {
					store(batch, repository);
				}
				return null;
			});
		} catch (IOException e) {
			throw new UncheckedIOException("Writing " + batch.observations().size() + " observations failed", e);
		}
	}

	/**
	 * Stores the entities not stored yet together with the observations. One lane at a time:
	 * lanes of different targets share sensors and observed properties, and two of them inserting
	 * the same one would collide.
	 */
	private void store(StaBatch batch, Repository repository) throws IOException {
		synchronized (stored) {
			Map<String, EObject> fresh = new LinkedHashMap<>();
			for (EObject entity : batch.entities()) {
				if (!stored.contains(key(entity)) && !repository.exist(entity.eClass(), EcoreUtil.getID(entity))) {
					fresh.put(key(entity), entity);
				}
			}
			save(fresh, batch.observations(), repository);
			for (EObject entity : batch.entities()) {
				stored.add(key(entity));
			}
		}
	}

	/** Saves the new entities and the observations at once, their other references on stored instances. */
	private static void save(Map<String, EObject> fresh, List<Observation> observations, Repository repository)
			throws IOException {
		for (Observation observation : observations) {
			fresh.put(key(observation), observation);
		}
		if (fresh.isEmpty()) {
			return;
		}
		Map<String, EObject> loaded = new HashMap<>();
		for (EObject object : fresh.values()) {
			attach(object, fresh, repository, loaded);
		}
		repository.saveAll(new ArrayList<>(fresh.values()));
	}

	/** Points the references of a new entity that lead to entities stored already at the stored instances. */
	private static void attach(EObject entity, Map<String, EObject> fresh, Repository repository,
			Map<String, EObject> loaded) throws IOException {
		for (EReference reference : entity.eClass().getEAllReferences()) {
			if (reference.isContainment() || reference.isContainer() || !entity.eIsSet(reference)) {
				continue;
			}
			if (reference.isMany()) {
				@SuppressWarnings("unchecked")
				List<EObject> values = (List<EObject>) entity.eGet(reference);
				for (EObject value : new ArrayList<>(values)) {
					if (!isFresh(value, fresh)) {
						EObject target = stored(value, repository, loaded);
						if (target != value) {
							values.remove(value);
							if (!values.contains(target)) {
								values.add(target);
							}
						}
					}
				}
			} else {
				EObject value = (EObject) entity.eGet(reference);
				if (!isFresh(value, fresh)) {
					entity.eSet(reference, stored(value, repository, loaded));
				}
			}
		}
	}

	private static boolean isFresh(EObject value, Map<String, EObject> fresh) {
		return value == null || EcoreUtil.getID(value) == null || fresh.containsKey(key(value));
	}

	private static EObject stored(EObject entity, Repository repository, Map<String, EObject> loaded) throws IOException {
		if (entity == null || EcoreUtil.getID(entity) == null) {
			return entity;
		}
		String key = key(entity);
		EObject stored = loaded.get(key);
		if (stored == null) {
			stored = repository.getEObject(entity.eClass(), EcoreUtil.getID(entity));
			if (stored == null) {
				throw new IOException("STA " + key + " is not stored");
			}
			loaded.put(key, stored);
		}
		return stored;
	}

	private static String key(EObject entity) {
		return entity.eClass().getName() + "#" + EcoreUtil.getID(entity);
	}
}
