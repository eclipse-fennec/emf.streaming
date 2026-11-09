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
import static org.eclipse.fennec.odata.wire.api.WireCondition.and;
import static org.eclipse.fennec.odata.wire.api.WireCondition.constant;
import static org.eclipse.fennec.odata.wire.api.WireCondition.isNull;
import static org.eclipse.fennec.odata.wire.api.WireCondition.not;
import static org.eclipse.fennec.odata.wire.api.WireCondition.or;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.odata.wire.api.OperandKind;
import org.eclipse.fennec.odata.wire.api.WireCondition;
import org.eclipse.fennec.odata.wire.api.WireOperator;
import org.eclipse.fennec.odata.wire.api.WirePropertyHandler;

/**
 * The SensorThings {@code result} over a number, a boolean and a text attribute (#71,
 * emf.odata#119 E5–E8): one value, held in the part of its type.
 * <p>
 * In {@code $filter} the operand picks the part: a number compares with the number, a string
 * with the text, a boolean with the boolean (so a text result {@code "5"} matches
 * {@code result eq '5'}, not {@code result eq 5}, as in FROST). An operand without a part matches
 * nothing. {@code $orderby} sorts text, boolean, number — numbers first ascending.
 */
public final class StaResultHandler implements WirePropertyHandler {

	private static final Set<String> STRING_FUNCTIONS = Set.of("contains", "startswith", "endswith", "length",
			"indexof", "substring", "tolower", "toupper", "trim", "concat");

	private static final Set<String> NUMBER_FUNCTIONS = Set.of("add", "sub", "mul", "div", "divby", "mod", "round",
			"floor", "ceiling");

	private final EAttribute number;
	private final EAttribute bool;
	private final EAttribute text;

	/**
	 * @param number  the attribute of a number result, an {@code EDoubleObject}
	 * @param bool    the attribute of a boolean result, an {@code EBooleanObject}
	 * @param text    the attribute of a text result, an {@code EString}
	 */
	public StaResultHandler(EAttribute number, EAttribute bool, EAttribute text) {
		this.number = requireNonNull(number, "Number part is required");
		this.bool = requireNonNull(bool, "Boolean part is required");
		this.text = requireNonNull(text, "Text part is required");
	}

	@Override
	public Object write(EObject entity) {
		for (EAttribute part : List.of(number, bool, text)) {
			Object value = entity.eGet(part);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	@Override
	public Map<EAttribute, Object> read(Object value) {
		Map<EAttribute, Object> parts = new LinkedHashMap<>();
		parts.put(number, value instanceof Number given ? Double.valueOf(given.doubleValue()) : null);
		parts.put(bool, value instanceof Boolean given ? given : null);
		parts.put(text, value instanceof String given ? given : null);
		if (value != null && !(value instanceof Number || value instanceof Boolean || value instanceof String)) {
			throw new IllegalArgumentException("A result is a number, a boolean or a string, not " + value);
		}
		return parts;
	}

	@Override
	public WireCondition compare(WireOperator operator, OperandKind operand) {
		return switch (operand) {
		case NUMBER -> WireCondition.compare(number, operator);
		case STRING -> WireCondition.compare(text, operator);
		case BOOLEAN -> WireCondition.compare(bool, operator);
		case NULL -> switch (operator) {
			case EQ -> and(isNull(number), isNull(bool), isNull(text));
			case NE -> or(not(isNull(number)), not(isNull(bool)), not(isNull(text)));
			default -> constant(false);
			};
		case UNKNOWN -> throw new IllegalArgumentException("A result compares with a typed value, not with an untyped operand");
		default -> constant(operator == WireOperator.NE);
		};
	}

	@Override
	public EAttribute operandOf(String function) {
		if (STRING_FUNCTIONS.contains(function)) {
			return text;
		}
		return NUMBER_FUNCTIONS.contains(function) ? number : null;
	}

	@Override
	public List<EAttribute> orderBy() {
		return List.of(text, bool, number);
	}
}
