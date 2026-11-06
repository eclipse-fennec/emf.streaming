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

import static java.util.Objects.requireNonNull;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.command.CommandFactory;
import org.eclipse.fennec.model.command.UpdateCommand;
import org.eclipse.fennec.model.query.builder.Expressions;
import org.eclipse.fennec.model.query.builder.QueryBuilder;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.StreamFactory;

/**
 * Builds the update command that writes projected slot values into one stored object without
 * loading it: the selector picks the object by its id attribute, the template holds one SET (or
 * UNSET) entry per feature - the persistence update template, addressed by EMF feature id.
 */
public final class PoolUpdates {

	private PoolUpdates() {
	}

	/**
	 * @param targetClass the class of the object; needs an id attribute
	 * @param targetId the object's id
	 * @param values the values by feature; {@code null} unsets
	 * @return the update command
	 */
	public static UpdateCommand update(EClass targetClass, String targetId, Map<EStructuralFeature, Object> values) {
		requireNonNull(targetClass, "Target class is required");
		EAttribute id = targetClass.getEIDAttribute();
		if (id == null) {
			throw new IllegalArgumentException(targetClass.getName() + " has no id attribute to select by");
		}
		ChangeSet template = StreamFactory.eINSTANCE.createChangeSet();
		for (Map.Entry<EStructuralFeature, Object> value : values.entrySet()) {
			EStructuralFeature feature = value.getKey();
			ChangeEntry entry = StreamFactory.eINSTANCE.createChangeEntry();
			entry.setObjectId(targetId);
			entry.setFeatureId(targetClass.getFeatureID(feature));
			if (value.getValue() == null) {
				entry.setKind(DeltaKind.UNSET);
			} else {
				entry.setKind(DeltaKind.SET);
				entry.setValueNew(literal(feature, value.getValue()));
			}
			template.getEntries().add(entry);
		}
		UpdateCommand update = CommandFactory.eINSTANCE.createUpdateCommand();
		update.setName("project " + targetClass.getName() + " " + targetId);
		update.setSelector(QueryBuilder.from(targetClass).where(Expressions.path(id).eq(EcoreUtil.createFromString(id.getEAttributeType(), targetId))).build());
		update.setTemplate(template);
		return update;
	}

	private static String literal(EStructuralFeature feature, Object value) {
		return feature.getEType() instanceof EDataType type ? EcoreUtil.convertToString(type, value) : String.valueOf(value);
	}
}
