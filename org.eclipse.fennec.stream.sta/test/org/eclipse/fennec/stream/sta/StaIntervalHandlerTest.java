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

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.StaFactory;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.odata.wire.api.OperandKind;
import org.eclipse.fennec.odata.wire.api.WireCondition;
import org.eclipse.fennec.odata.wire.api.WireOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class StaIntervalHandlerTest {

	private static final EAttribute S = StaPackage.Literals.OBSERVATION__PHENOMENON_TIME_START;
	private static final EAttribute E = StaPackage.Literals.OBSERVATION__PHENOMENON_TIME_END;
	private static final Date T1 = Date.from(Instant.parse("2019-07-26T09:00:43.671Z"));
	private static final Date T2 = Date.from(Instant.parse("2023-02-08T13:42:08.909Z"));

	private final StaIntervalHandler handler = new StaIntervalHandler(S, E);

	@Test
	void comparesTheStartForGreater() {
		assertThat(handler.compare(WireOperator.GT, OperandKind.DATE_TIME)).isEqualTo(compare(S, WireOperator.GT));
		assertThat(handler.compare(WireOperator.GE, OperandKind.DATE_TIME)).isEqualTo(compare(S, WireOperator.GE));
	}

	@Test
	void needsTheWholeIntervalBeforeForLess() {
		assertThat(handler.compare(WireOperator.LT, OperandKind.DATE_TIME))
				.isEqualTo(and(compare(S, WireOperator.LT), or(isNull(E), compare(E, WireOperator.LE))));
		assertThat(handler.compare(WireOperator.LE, OperandKind.DATE_TIME))
				.isEqualTo(and(compare(S, WireOperator.LE), or(isNull(E), compare(E, WireOperator.LE))));
	}

	@Test
	void equalIsAnInstantAtXAndNotEqualItsNegation() {
		WireCondition equal = and(compare(S, WireOperator.EQ), or(isNull(E), compare(E, WireOperator.EQ)));

		assertThat(handler.compare(WireOperator.EQ, OperandKind.DATE_TIME)).isEqualTo(equal);
		assertThat(handler.compare(WireOperator.NE, OperandKind.DATE_TIME)).isEqualTo(not(equal));
	}

	@Test
	void nullIsAMissingStart() {
		assertThat(handler.compare(WireOperator.EQ, OperandKind.NULL)).isEqualTo(isNull(S));
		assertThat(handler.compare(WireOperator.NE, OperandKind.NULL)).isEqualTo(not(isNull(S)));
		assertThat(handler.compare(WireOperator.GT, OperandKind.NULL)).isEqualTo(constant(false));
	}

	@ParameterizedTest
	@EnumSource(value = OperandKind.class, names = { "DATE_TIME", "NULL" }, mode = EnumSource.Mode.EXCLUDE)
	void otherOperandsAreABadRequest(OperandKind operand) {
		assertThatThrownBy(() -> handler.compare(WireOperator.EQ, operand)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void dateFunctionsAndOrderUseTheStart() {
		assertThat(handler.operandOf("year")).isEqualTo(S);
		assertThat(handler.operandOf("totaloffsetminutes")).isEqualTo(S);
		assertThat(handler.operandOf("length")).isNull();
		assertThat(handler.orderBy()).containsExactly(S, E);
	}

	@Test
	void writesAnInstantOrAnInterval() {
		Observation observation = StaFactory.eINSTANCE.createObservation();
		assertThat(handler.write(observation)).isNull();

		observation.setPhenomenonTimeStart(T1);
		assertThat(handler.write(observation)).isEqualTo("2019-07-26T09:00:43.671Z");
		observation.setPhenomenonTimeEnd(T1);
		assertThat(handler.write(observation)).isEqualTo("2019-07-26T09:00:43.671Z");
		observation.setPhenomenonTimeEnd(T2);
		assertThat(handler.write(observation)).isEqualTo("2019-07-26T09:00:43.671Z/2023-02-08T13:42:08.909Z");
	}

	@Test
	void readsAnInstantAnIntervalAndNull() {
		assertThat(handler.read("2019-07-26T11:00:43.671+02:00")).isEqualTo(parts(T1, null));
		assertThat(handler.read("2019-07-26T09:00:43.671Z/2023-02-08T13:42:08.909Z")).isEqualTo(parts(T1, T2));
		assertThat(handler.read(null)).isEqualTo(parts(null, null));
	}

	@ParameterizedTest
	@ValueSource(strings = { "2023-02-08T13:42:08.909Z/2019-07-26T09:00:43.671Z", "2019-07-26", "yesterday",
			"2019-07-26T09:00:43.671Z/P1D", "P1D/2019-07-26T09:00:43.671Z", "a/b/c" })
	void refusesWhatIsNoInstantOrInterval(String value) {
		assertThatThrownBy(() -> handler.read(value)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void refusesANonString() {
		assertThatThrownBy(() -> handler.read(42)).isInstanceOf(IllegalArgumentException.class);
	}

	private static Map<EAttribute, Object> parts(Date start, Date end) {
		Map<EAttribute, Object> parts = new HashMap<>();
		parts.put(S, start);
		parts.put(E, end);
		return parts;
	}
}
