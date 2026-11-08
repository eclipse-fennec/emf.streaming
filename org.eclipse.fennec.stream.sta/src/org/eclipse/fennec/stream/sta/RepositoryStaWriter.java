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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.persistence.RepositoryAccess;

/**
 * Writes STA entities into a Fennec persistence repository; the repository's unit maps the STA
 * model, emf.odata serves it from there. Entities the repository does not hold yet are written in
 * stages, because a new graph whose types reference each other cannot be saved at once
 * (emf.persistence-jpa#406, workaround #57):
 * <ol>
 * <li>the new entities without references (attributes and contained values only);</li>
 * <li>their references, set on the stored instances;</li>
 * <li>the observations, referring to stored instances only.</li>
 * </ol>
 * Entities are remembered once stored. Thread-safe through the {@link RepositoryAccess}.
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
				Map<String, EObject> fresh = new LinkedHashMap<>();
				for (EObject entity : batch.entities()) {
					if (!stored.contains(key(entity)) && !repository.exist(entity.eClass(), EcoreUtil.getID(entity))) {
						fresh.put(key(entity), entity);
					}
				}
				if (!fresh.isEmpty()) {
					List<EObject> bare = new ArrayList<>();
					for (EObject entity : fresh.values()) {
						bare.add(withoutReferences(entity));
					}
					repository.saveAll(bare);
					Map<String, EObject> loaded = new HashMap<>();
					List<EObject> linked = new ArrayList<>();
					for (EObject entity : fresh.values()) {
						linked.add(link(entity, repository, loaded));
					}
					repository.saveAll(linked);
				}
				for (EObject entity : batch.entities()) {
					stored.add(key(entity));
				}
				Map<String, EObject> loaded = new HashMap<>();
				List<EObject> observations = new ArrayList<>();
				for (Observation observation : batch.observations()) {
					observations.add(link(observation, repository, loaded));
				}
				if (!observations.isEmpty()) {
					repository.saveAll(observations);
				}
				return null;
			});
		} catch (IOException e) {
			throw new UncheckedIOException("Writing " + batch.observations().size() + " observations failed", e);
		}
	}

	/** A new object with the attributes and contained values of the given one, and no references. */
	private static EObject withoutReferences(EObject object) {
		EObject bare = EcoreUtil.create(object.eClass());
		for (EAttribute attribute : object.eClass().getEAllAttributes()) {
			if (object.eIsSet(attribute) && attribute.isChangeable() && !attribute.isDerived()) {
				bare.eSet(attribute, object.eGet(attribute));
			}
		}
		for (EReference reference : object.eClass().getEAllContainments()) {
			if (object.eIsSet(reference)) {
				bare.eSet(reference, EcoreUtil.copy((EObject) object.eGet(reference)));
			}
		}
		return bare;
	}

	/**
	 * The object to save with its references on stored instances: an entity is replaced by its
	 * stored instance, an observation is new and only refers to them. Only the owning side of a
	 * one-to-many pair is set; the opposite follows.
	 */
	private static EObject link(EObject object, Repository repository, Map<String, EObject> loaded) throws IOException {
		EObject target = object instanceof Observation ? withoutReferences(object) : stored(object, repository, loaded);
		for (EReference reference : object.eClass().getEAllReferences()) {
			if (reference.isContainment() || reference.isContainer() || !object.eIsSet(reference)) {
				continue;
			}
			EReference opposite = reference.getEOpposite();
			if (reference.isMany() && opposite != null && !opposite.isMany()) {
				continue; // the inverse side: set from the other end
			}
			if (reference.isMany()) {
				@SuppressWarnings("unchecked")
				List<EObject> values = (List<EObject>) object.eGet(reference);
				@SuppressWarnings("unchecked")
				List<EObject> targets = (List<EObject>) target.eGet(reference);
				for (EObject value : values) {
					EObject linked = stored(value, repository, loaded);
					if (!targets.contains(linked)) {
						targets.add(linked);
					}
				}
			} else {
				EObject value = (EObject) object.eGet(reference);
				target.eSet(reference, stored(value, repository, loaded));
			}
		}
		return target;
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
