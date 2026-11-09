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

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
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
 * A SensorThings time interval ({@code phenomenonTime}, {@code resultTime}, {@code validTime})
 * over a start and an end attribute (#70, emf.odata#119 E3/E4/E7/E8). An instant is an interval
 * without end, or with end = start. On the wire it is ISO 8601 in UTC: {@code s} for an instant,
 * {@code s/e} for an interval.
 * <p>
 * In {@code $filter}, with s = start, e = end and an instant X: {@code gt}/{@code ge} compare the
 * start; {@code lt}/{@code le} need the whole interval before X; {@code eq} needs s = X and the
 * end null or X; {@code ne} is the negation of {@code eq} (FROST deviates there). Date functions
 * and {@code $orderby} use the start.
 */
public final class StaIntervalHandler implements WirePropertyHandler {

	private static final Set<String> DATE_FUNCTIONS = Set.of("year", "month", "day", "hour", "minute", "second",
			"fractionalseconds", "date", "time", "totaloffsetminutes");

	private final EAttribute start;
	private final EAttribute end;

	/**
	 * @param start the attribute of the start, an {@code EDate}
	 * @param end   the attribute of the end, an {@code EDate}
	 */
	public StaIntervalHandler(EAttribute start, EAttribute end) {
		this.start = requireNonNull(start, "Start is required");
		this.end = requireNonNull(end, "End is required");
	}

	@Override
	public Object write(EObject entity) {
		Date from = (Date) entity.eGet(start);
		if (from == null) {
			return null;
		}
		Date to = (Date) entity.eGet(end);
		return to == null || to.equals(from) ? format(from) : format(from) + "/" + format(to);
	}

	@Override
	public Map<EAttribute, Object> read(Object value) {
		Map<EAttribute, Object> parts = new LinkedHashMap<>();
		if (value == null) {
			parts.put(start, null);
			parts.put(end, null);
			return parts;
		}
		if (!(value instanceof String text)) {
			throw new IllegalArgumentException("A time interval is an ISO 8601 string, not " + value);
		}
		String[] bounds = text.split("/", -1);
		if (bounds.length > 2) {
			throw new IllegalArgumentException("Not an ISO 8601 instant or interval: " + text);
		}
		Date from = parse(bounds[0], text);
		Date to = bounds.length == 2 ? parse(bounds[1], text) : null;
		if (to != null && to.before(from)) {
			throw new IllegalArgumentException("The interval ends before it starts: " + text);
		}
		parts.put(start, from);
		parts.put(end, to);
		return parts;
	}

	@Override
	public WireCondition compare(WireOperator operator, OperandKind operand) {
		if (operand == OperandKind.NULL) {
			return switch (operator) {
			case EQ -> isNull(start);
			case NE -> not(isNull(start));
			default -> constant(false);
			};
		}
		if (operand != OperandKind.DATE_TIME) {
			throw new IllegalArgumentException("A time interval compares with a date and time, not with " + operand);
		}
		return switch (operator) {
		case GT, GE -> WireCondition.compare(start, operator);
		case LT, LE -> and(WireCondition.compare(start, operator), endAtMost());
		case EQ -> equal();
		case NE -> not(equal());
		};
	}

	@Override
	public EAttribute operandOf(String function) {
		return DATE_FUNCTIONS.contains(function) ? start : null;
	}

	@Override
	public List<EAttribute> orderBy() {
		return List.of(start, end);
	}

	private WireCondition equal() {
		return and(WireCondition.compare(start, WireOperator.EQ), or(isNull(end), WireCondition.compare(end, WireOperator.EQ)));
	}

	private WireCondition endAtMost() {
		return or(isNull(end), WireCondition.compare(end, WireOperator.LE));
	}

	private static String format(Date date) {
		return DateTimeFormatter.ISO_INSTANT.format(date.toInstant());
	}

	private static Date parse(String bound, String text) {
		if (bound.startsWith("P")) {
			throw new IllegalArgumentException("Durations are not supported in a time interval: " + text);
		}
		try {
			return Date.from(OffsetDateTime.parse(bound).toInstant());
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Not an ISO 8601 instant: " + bound, e);
		}
	}
}
