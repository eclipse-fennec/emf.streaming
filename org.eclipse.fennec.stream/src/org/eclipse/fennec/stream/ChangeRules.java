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

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.mapping.ExpressionEvaluator;
import org.eclipse.fennec.model.streammapping.AbsoluteChange;
import org.eclipse.fennec.model.streammapping.ChangeRule;
import org.eclipse.fennec.model.streammapping.CompositeRule;
import org.eclipse.fennec.model.streammapping.CountChange;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.ExpressionRule;
import org.eclipse.fennec.model.streammapping.PercentageChange;
import org.eclipse.fennec.model.streammapping.TimeThrottle;

/**
 * Decides whether a value enters the stream (requirements STR-03, STR-04, STR-05).
 * <p>
 * {@link #test} is free of side effects; {@link #offer} tests and then records the outcome in the
 * slot's state - the passed value becomes the comparison base, a rejected one only moves the offer
 * counter. The first value of a slot always passes. A rule that cannot be applied to a value (a
 * numeric rule on text, an expression without an expression language, a malformed composite) lets
 * the value pass: storing too much is better than losing data over a mapping mistake.
 * <p>
 * Thread-safe; the slot state is not and belongs to one lane of the event core.
 */
public final class ChangeRules {

	/** Variable naming the new value in an expression rule. */
	public static final String VALUE = "value";

	/** Variable naming the last passed value in an expression rule ({@code null} for the first). */
	public static final String LAST = "last";

	private static final Logger LOGGER = Logger.getLogger(ChangeRules.class.getName());

	private final ExpressionEvaluator evaluator;

	/**
	 * @param evaluator evaluates expression rules; {@code null} lets every expression rule pass
	 */
	public ChangeRules(ExpressionEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	/**
	 * Tests a value and records the outcome in the state.
	 *
	 * @param rule the slot's rule; {@code null} lets every value pass
	 * @param state the slot's state, updated
	 * @param value the new value
	 * @param eventTime its event time
	 * @param self the source object, for expression rules
	 * @return whether the value enters the stream
	 */
	public boolean offer(ChangeRule rule, SlotState state, Object value, long eventTime, EObject self) {
		requireNonNull(state, "SlotState is required");
		boolean passes = !state.hasLast() || test(rule, state, value, eventTime, self);
		if (passes) {
			state.passed(value, eventTime);
		} else {
			state.rejected();
		}
		return passes;
	}

	/**
	 * Tests a value against a rule without changing anything.
	 *
	 * @param rule the rule; {@code null} passes
	 * @param state the slot's state
	 * @param value the new value
	 * @param eventTime its event time
	 * @param self the source object, for expression rules
	 * @return whether the rule lets the value pass
	 */
	public boolean test(ChangeRule rule, SlotState state, Object value, long eventTime, EObject self) {
		requireNonNull(state, "SlotState is required");
		if (rule == null || !state.hasLast()) {
			return true;
		}
		if (rule instanceof CompositeRule composite) {
			return composite(composite, state, value, eventTime, self);
		}
		if (rule instanceof AbsoluteChange absolute) {
			return numeric(state.lastValue(), value, (last, now) -> Math.abs(now - last) >= absolute.getDelta());
		}
		if (rule instanceof PercentageChange percentage) {
			return numeric(state.lastValue(), value, (last, now) -> last == 0d ? now != 0d
					: Math.abs(now - last) / Math.abs(last) * 100d >= percentage.getPercent());
		}
		if (rule instanceof CountChange count) {
			return state.offeredSinceLastPass() + 1 >= Math.max(1, count.getN());
		}
		if (rule instanceof TimeThrottle throttle) {
			return eventTime - state.lastTime() >= millis(throttle.getInterval(), throttle.getUnit());
		}
		if (rule instanceof ExpressionRule expression) {
			return expression(expression, state, value, self);
		}
		LOGGER.fine(() -> "Unknown change rule " + rule.eClass().getName() + " - the value passes");
		return true;
	}

	/**
	 * @param amount an amount
	 * @param unit its unit
	 * @return the amount in milliseconds
	 */
	public static long millis(long amount, DurationUnit unit) {
		return switch (unit) {
		case MILLISECONDS -> amount;
		case SECONDS -> TimeUnit.SECONDS.toMillis(amount);
		case MINUTES -> TimeUnit.MINUTES.toMillis(amount);
		case HOURS -> TimeUnit.HOURS.toMillis(amount);
		case DAYS -> TimeUnit.DAYS.toMillis(amount);
		};
	}

	private boolean composite(CompositeRule composite, SlotState state, Object value, long eventTime, EObject self) {
		List<ChangeRule> rules = composite.getRules();
		switch (composite.getOperator()) {
		case AND:
			for (ChangeRule rule : rules) {
				if (!test(rule, state, value, eventTime, self)) {
					return false;
				}
			}
			return true;
		case OR:
			if (rules.isEmpty()) {
				return true;
			}
			for (ChangeRule rule : rules) {
				if (test(rule, state, value, eventTime, self)) {
					return true;
				}
			}
			return false;
		case NOT:
			if (rules.size() != 1) {
				LOGGER.fine(() -> "A NOT rule needs exactly one rule, has " + rules.size() + " - the value passes");
				return true;
			}
			return !test(rules.get(0), state, value, eventTime, self);
		default:
			return true;
		}
	}

	private boolean expression(ExpressionRule rule, SlotState state, Object value, EObject self) {
		if (evaluator == null || self == null) {
			LOGGER.fine(() -> "Expression rule without expression language or source - the value passes");
			return true;
		}
		Map<String, Object> variables = new HashMap<>();
		variables.put(VALUE, value);
		variables.put(LAST, state.lastValue());
		Optional<Object> result = evaluator.evaluate(rule.getExpression(), self, variables);
		if (result.isPresent() && result.get() instanceof Boolean passes) {
			return passes.booleanValue();
		}
		LOGGER.fine(() -> "Expression rule '" + rule.getExpression() + "' gave no boolean - the value passes");
		return true;
	}

	private static boolean numeric(Object last, Object now, NumericTest test) {
		if (last instanceof Number lastNumber && now instanceof Number nowNumber) {
			return test.test(lastNumber.doubleValue(), nowNumber.doubleValue());
		}
		LOGGER.fine(() -> "Numeric change rule on non-numeric values " + last + " / " + now + " - the value passes");
		return true;
	}

	@FunctionalInterface
	private interface NumericTest {
		boolean test(double last, double now);
	}
}
