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

import java.util.EnumSet;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;

/**
 * Checks the structural rules of a {@link ChangeSet} that the metamodel alone cannot express:
 * which fields an entry needs for its kind. It does not resolve anything against a model version;
 * whether a class or feature exists is {@link StreamAddresses}' job, done where the model is known.
 * <p>
 * The rules:
 * <ul>
 * <li>a set needs {@code id}, {@code streamId}, {@code contextFingerprint} and {@code author};</li>
 * <li>{@code CREATE} and {@code KEYFRAME} need a {@code classUri};</li>
 * <li>every feature delta needs a feature address ({@code featureUri} or {@code featureId &gt;= 0});</li>
 * <li>{@code MOVE} needs {@code index} and {@code toIndex}; {@code PUT} and {@code REMOVE_KEY} need a {@code key};
 * {@code SET_AT} needs {@code coords};</li>
 * <li>every keyframe slot needs a feature address.</li>
 * </ul>
 * Plain Java, stateless and thread-safe.
 */
public final class ChangeSetValidator {

	/** The diagnostic source of every diagnostic this validator produces. */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.fennec.model.stream";

	/** Kinds that address a feature of the object. */
	private static final Set<DeltaKind> FEATURE_KINDS = EnumSet.of(DeltaKind.SET, DeltaKind.UNSET, DeltaKind.ADD,
			DeltaKind.REMOVE, DeltaKind.MOVE, DeltaKind.PUT, DeltaKind.REMOVE_KEY, DeltaKind.SET_AT, DeltaKind.RESHAPE,
			DeltaKind.TOUCH);

	/** Kinds that introduce an object and therefore need its class. */
	private static final Set<DeltaKind> CLASS_KINDS = EnumSet.of(DeltaKind.CREATE, DeltaKind.KEYFRAME);

	private ChangeSetValidator() {
	}

	/**
	 * Validates a change set.
	 *
	 * @param changeSet the change set
	 * @return {@link Diagnostic#OK_INSTANCE}-like OK diagnostic, or an ERROR diagnostic with one child per violation
	 */
	public static Diagnostic validate(ChangeSet changeSet) {
		requireNonNull(changeSet, "ChangeSet is required");
		BasicDiagnostic result = new BasicDiagnostic(DIAGNOSTIC_SOURCE, 0, "ChangeSet " + changeSet.getId(),
				new Object[] { changeSet });
		requireText(result, changeSet, changeSet.getId(), "id");
		requireText(result, changeSet, changeSet.getStreamId(), "streamId");
		requireText(result, changeSet, changeSet.getContextFingerprint(), "contextFingerprint");
		requireText(result, changeSet, changeSet.getAuthor(), "author");
		for (int i = 0; i < changeSet.getEntries().size(); i++) {
			validateEntry(result, changeSet.getEntries().get(i), i);
		}
		return result;
	}

	/**
	 * Shortcut for callers that only need a yes or no.
	 *
	 * @param changeSet the change set
	 * @return {@code true} when the set has no violation
	 */
	public static boolean isValid(ChangeSet changeSet) {
		return validate(changeSet).getSeverity() == Diagnostic.OK;
	}

	private static void validateEntry(BasicDiagnostic result, ChangeEntry entry, int position) {
		String where = "entry " + position + " (" + entry.getKind() + ", object " + entry.getObjectId() + ")";
		if (isBlank(entry.getObjectId())) {
			error(result, entry, where + ": objectId is required");
		}
		DeltaKind kind = entry.getKind();
		if (CLASS_KINDS.contains(kind) && isBlank(entry.getClassUri())) {
			error(result, entry, where + ": classUri is required for " + kind);
		}
		if (FEATURE_KINDS.contains(kind) && !hasFeatureAddress(entry.getFeatureUri(), entry.getFeatureId())) {
			error(result, entry, where + ": a feature address (featureUri or featureId) is required for " + kind);
		}
		switch (kind) {
		case MOVE -> {
			if (entry.getIndex() < 0 || entry.getToIndex() < 0) {
				error(result, entry, where + ": index and toIndex are required for MOVE");
			}
		}
		case PUT, REMOVE_KEY -> {
			if (entry.getKey() == null) {
				error(result, entry, where + ": key is required for " + kind);
			}
		}
		case SET_AT -> {
			if (entry.getCoords().isEmpty()) {
				error(result, entry, where + ": coords are required for SET_AT");
			}
		}
		default -> {
			// no address beyond the feature
		}
		}
		if (kind == DeltaKind.KEYFRAME) {
			for (int i = 0; i < entry.getState().size(); i++) {
				SlotValue slot = entry.getState().get(i);
				if (!hasFeatureAddress(slot.getFeatureUri(), slot.getFeatureId())) {
					error(result, slot, where + ", slot " + i + ": a feature address (featureUri or featureId) is required");
				}
			}
		} else if (!entry.getState().isEmpty()) {
			error(result, entry, where + ": state is only allowed on KEYFRAME");
		}
	}

	private static boolean hasFeatureAddress(String featureUri, int featureId) {
		return !isBlank(featureUri) || featureId >= 0;
	}

	private static void requireText(BasicDiagnostic result, ChangeSet changeSet, String value, String field) {
		if (isBlank(value)) {
			error(result, changeSet, field + " is required");
		}
	}

	private static void error(BasicDiagnostic result, Object subject, String message) {
		result.add(new BasicDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 0, message, new Object[] { subject }));
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
