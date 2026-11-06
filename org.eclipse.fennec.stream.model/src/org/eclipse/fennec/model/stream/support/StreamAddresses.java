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
package org.eclipse.fennec.model.stream.support;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.SlotValue;

/**
 * How the stream model addresses classes and features, in both directions.
 * <p>
 * A class is addressed by its URI {@code nsURI#//Name}. The classifier order of a package is not
 * part of the model fingerprint, so a classifier index would not be stable across two nodes that
 * hold the same model version.
 * <p>
 * A feature is addressed either by its EMF feature ID ({@link EClass#getFeatureID}) or by its URI
 * {@code nsURI#//DeclaringClass/name}. The declared feature order is part of the fingerprint, so
 * the ID is stable for one model version. When both are given, the URI wins.
 * <p>
 * The URIs are built from the package's nsURI, never from the resource the package was loaded
 * from, so a model read from a file and the same model as generated code yield the same address.
 */
public final class StreamAddresses {

	/** Separator between the nsURI and the fragment of a class or feature URI. */
	public static final String FRAGMENT_SEPARATOR = "#//";

	/** The value of an unset numeric feature id. */
	public static final int UNSET_ID = -1;

	private StreamAddresses() {
	}

	/**
	 * Returns the stream address of a class.
	 *
	 * @param eClass the class, must belong to a package with an nsURI
	 * @return {@code nsURI#//Name}
	 */
	public static String classUri(EClass eClass) {
		requireNonNull(eClass, "EClass is required");
		return nsUri(eClass) + FRAGMENT_SEPARATOR + eClass.getName();
	}

	/**
	 * Returns the stream address of a feature: the URI of its declaring class plus its name.
	 *
	 * @param feature the feature
	 * @return {@code nsURI#//DeclaringClass/name}
	 */
	public static String featureUri(EStructuralFeature feature) {
		requireNonNull(feature, "EStructuralFeature is required");
		return classUri(feature.getEContainingClass()) + "/" + feature.getName();
	}

	/**
	 * Resolves a class URI against a package registry.
	 *
	 * @param registry the registry holding the model version the stream was written under
	 * @param classUri {@code nsURI#//Name}
	 * @return the class, or empty when the package or the class is unknown or the URI is malformed
	 */
	public static Optional<EClass> resolveClass(EPackage.Registry registry, String classUri) {
		requireNonNull(registry, "EPackage registry is required");
		if (classUri == null) {
			return Optional.empty();
		}
		int separator = classUri.indexOf(FRAGMENT_SEPARATOR);
		if (separator <= 0) {
			return Optional.empty();
		}
		String name = classUri.substring(separator + FRAGMENT_SEPARATOR.length());
		if (name.isEmpty() || name.contains("/")) {
			return Optional.empty();
		}
		EPackage ePackage = registry.getEPackage(classUri.substring(0, separator));
		if (ePackage == null) {
			return Optional.empty();
		}
		EClassifier classifier = ePackage.getEClassifier(name);
		return classifier instanceof EClass eClass ? Optional.of(eClass) : Optional.empty();
	}

	/**
	 * Resolves a feature address within a class. The URI wins over the ID when both are set; a
	 * URI that names no feature of the class (including inherited ones) resolves to empty, without
	 * falling back to the ID.
	 *
	 * @param eClass the class of the addressed object
	 * @param featureUri the feature URI, may be {@code null}
	 * @param featureId the EMF feature ID, {@link #UNSET_ID} when unset
	 * @return the feature, or empty
	 */
	public static Optional<EStructuralFeature> resolveFeature(EClass eClass, String featureUri, int featureId) {
		requireNonNull(eClass, "EClass is required");
		if (featureUri != null && !featureUri.isEmpty()) {
			for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
				if (featureUri.equals(featureUri(feature))) {
					return Optional.of(feature);
				}
			}
			return Optional.empty();
		}
		if (featureId < 0) {
			return Optional.empty();
		}
		return Optional.ofNullable(eClass.getEStructuralFeature(featureId));
	}

	/**
	 * Resolves the feature an entry addresses.
	 *
	 * @param eClass the class of the entry's object
	 * @param entry the entry
	 * @return the feature, or empty when the entry addresses none or an unknown one
	 */
	public static Optional<EStructuralFeature> resolveFeature(EClass eClass, ChangeEntry entry) {
		requireNonNull(entry, "ChangeEntry is required");
		return resolveFeature(eClass, entry.getFeatureUri(), entry.getFeatureId());
	}

	/**
	 * Resolves the feature a keyframe slot addresses.
	 *
	 * @param eClass the class of the keyframe's object
	 * @param slot the slot
	 * @return the feature, or empty when the slot addresses none or an unknown one
	 */
	public static Optional<EStructuralFeature> resolveFeature(EClass eClass, SlotValue slot) {
		requireNonNull(slot, "SlotValue is required");
		return resolveFeature(eClass, slot.getFeatureUri(), slot.getFeatureId());
	}

	/**
	 * Returns the event time of an entry: its own timestamp, or the batch time when it has none.
	 *
	 * @param entry the entry, contained in a change set
	 * @return epoch millis
	 */
	public static long eventTime(ChangeEntry entry) {
		requireNonNull(entry, "ChangeEntry is required");
		Long own = entry.getTimestamp();
		if (own != null) {
			return own.longValue();
		}
		if (entry.eContainer() instanceof ChangeSet changeSet) {
			return changeSet.getTimestamp();
		}
		throw new IllegalArgumentException("Entry has no timestamp and is not contained in a ChangeSet");
	}

	private static String nsUri(EClass eClass) {
		EPackage ePackage = eClass.getEPackage();
		if (ePackage == null || ePackage.getNsURI() == null || ePackage.getNsURI().isEmpty()) {
			throw new IllegalArgumentException("EClass '" + eClass.getName() + "' belongs to no package with an nsURI");
		}
		return ePackage.getNsURI();
	}
}
