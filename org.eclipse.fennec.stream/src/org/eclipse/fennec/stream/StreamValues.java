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
package org.eclipse.fennec.stream;

import java.util.Date;
import java.util.function.Function;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * How values travel in the stream model, whose value fields are strings: literals in the EMF
 * string form of their type, references as the id of the referenced object. The typing is not in
 * the entry; it resolves from (fingerprint, feature) on the receiving side.
 */
public final class StreamValues {

	private StreamValues() {
	}

	/**
	 * @param value a value
	 * @param idOf the id of a referenced object
	 * @return its string form, {@code null} for {@code null}
	 */
	public static String encode(Object value, Function<EObject, String> idOf) {
		if (value == null) {
			return null;
		}
		if (value instanceof String text) {
			return text;
		}
		if (value instanceof EObject eObject) {
			return idOf.apply(eObject);
		}
		if (value instanceof Enumerator enumerator) {
			return enumerator.getName();
		}
		if (value instanceof Date date) {
			return EcoreUtil.convertToString(EcorePackage.Literals.EDATE, date);
		}
		return String.valueOf(value);
	}

	/**
	 * Reads a value back into a type. Without a data type, numbers come back as {@link Double} and
	 * anything else as the string itself - enough for the change rules to compare.
	 *
	 * @param literal the string form
	 * @param type the type to read into; may be {@code null}
	 * @return the value, or the literal when it does not fit the type
	 */
	public static Object decode(String literal, EClassifier type) {
		if (literal == null) {
			return null;
		}
		if (type instanceof EDataType dataType) {
			try {
				return EcoreUtil.createFromString(dataType, literal);
			} catch (RuntimeException e) {
				// does not fit the type: fall through
			}
		}
		try {
			return Double.valueOf(literal);
		} catch (NumberFormatException e) {
			return literal;
		}
	}
}
