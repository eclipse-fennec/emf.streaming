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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.eclipse.fennec.odata.wire.api.WireCondition.and;
import static org.eclipse.fennec.odata.wire.api.WireCondition.compare;
import static org.eclipse.fennec.odata.wire.api.WireCondition.constant;
import static org.eclipse.fennec.odata.wire.api.WireCondition.isNull;
import static org.eclipse.fennec.odata.wire.api.WireCondition.not;
import static org.eclipse.fennec.odata.wire.api.WireCondition.or;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.StaFactory;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.odata.wire.api.OperandKind;
import org.eclipse.fennec.odata.wire.api.WireJson;
import org.eclipse.fennec.odata.wire.api.WireOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class StaResultHandlerTest {

	private static final EAttribute NUMBER = StaPackage.Literals.OBSERVATION__RESULT_NUMBER;
	private static final EAttribute BOOL = StaPackage.Literals.OBSERVATION__RESULT_BOOLEAN;
	private static final EAttribute TEXT = StaPackage.Literals.OBSERVATION__RESULT_TEXT;

	private final StaResultHandler handler = new StaResultHandler(NUMBER, BOOL, TEXT);

	@ParameterizedTest
	@EnumSource(WireOperator.class)
	void theOperandPicksThePart(WireOperator operator) {
		assertThat(handler.compare(operator, OperandKind.NUMBER)).isEqualTo(compare(NUMBER, operator));
		assertThat(handler.compare(operator, OperandKind.STRING)).isEqualTo(compare(TEXT, operator));
		assertThat(handler.compare(operator, OperandKind.BOOLEAN)).isEqualTo(compare(BOOL, operator));
	}

	@ParameterizedTest
	@EnumSource(value = OperandKind.class, names = { "DATE_TIME", "DATE", "TIME_OF_DAY", "OTHER" })
	void anOperandWithoutAPartMatchesNothing(OperandKind operand) {
		assertThat(handler.compare(WireOperator.EQ, operand)).isEqualTo(constant(false));
		assertThat(handler.compare(WireOperator.GT, operand)).isEqualTo(constant(false));
		assertThat(handler.compare(WireOperator.NE, operand)).isEqualTo(constant(true));
	}

	@Test
	void nullIsNoPartSet() {
		assertThat(handler.compare(WireOperator.EQ, OperandKind.NULL)).isEqualTo(and(isNull(NUMBER), isNull(BOOL), isNull(TEXT)));
		assertThat(handler.compare(WireOperator.NE, OperandKind.NULL))
				.isEqualTo(or(not(isNull(NUMBER)), not(isNull(BOOL)), not(isNull(TEXT))));
		assertThat(handler.compare(WireOperator.LT, OperandKind.NULL)).isEqualTo(constant(false));
	}

	@Test
	void anUntypedOperandIsABadRequest() {
		assertThatThrownBy(() -> handler.compare(WireOperator.EQ, OperandKind.UNKNOWN))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void functionsPickThePartOfTheirType() {
		for (String function : List.of("contains", "startswith", "endswith", "length", "indexof", "substring", "tolower",
				"toupper", "trim", "concat")) {
			assertThat(handler.operandOf(function)).as(function).isEqualTo(TEXT);
		}
		for (String function : List.of("add", "sub", "mul", "div", "divby", "mod", "round", "floor", "ceiling")) {
			assertThat(handler.operandOf(function)).as(function).isEqualTo(NUMBER);
		}
		assertThat(handler.operandOf("year")).isNull();
	}

	@Test
	void sortsTextBooleanNumber() {
		assertThat(handler.orderBy()).containsExactly(TEXT, BOOL, NUMBER);
	}

	@Test
	void writesThePartThatIsSet() {
		Observation observation = StaFactory.eINSTANCE.createObservation();
		assertThat(handler.write(observation)).isNull();

		observation.setResultText("5");
		assertThat(handler.write(observation)).isEqualTo("5");
		observation.setResultBoolean(true);
		assertThat(handler.write(observation)).isEqualTo(true);
		observation.setResultNumber(27.5);
		assertThat(handler.write(observation)).isEqualTo(27.5);
	}

	@Test
	void readsByJsonTypeAndClearsTheOtherParts() {
		assertThat(handler.read(27)).isEqualTo(parts(27.0, null, null));
		assertThat(handler.read(true)).isEqualTo(parts(null, true, null));
		assertThat(handler.read("5")).isEqualTo(parts(null, null, "5"));
		assertThat(handler.read(null)).isEqualTo(parts(null, null, null));
	}

	@Test
	void refusesOtherValues() {
		assertThatThrownBy(() -> handler.read(List.of(1))).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> handler.read(new WireJson("{\"a\":1}"))).isInstanceOf(IllegalArgumentException.class);
	}

	private static Map<EAttribute, Object> parts(Double number, Boolean bool, String text) {
		Map<EAttribute, Object> parts = new HashMap<>();
		parts.put(NUMBER, number);
		parts.put(BOOL, bool);
		parts.put(TEXT, text);
		return parts;
	}
}
