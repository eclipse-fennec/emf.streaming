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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.fennec.mapping.ExpressionEvaluator;
import org.eclipse.fennec.model.streammapping.AbsoluteChange;
import org.eclipse.fennec.model.streammapping.ChangeRule;
import org.eclipse.fennec.model.streammapping.CompositeRule;
import org.eclipse.fennec.model.streammapping.CountChange;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.ExpressionRule;
import org.eclipse.fennec.model.streammapping.PercentageChange;
import org.eclipse.fennec.model.streammapping.RuleOperator;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.TimeThrottle;
import org.junit.jupiter.api.Test;

/**
 * Change rules (requirements STR-03, STR-04, STR-05).
 */
class ChangeRulesTest {

	private static final StreamMappingFactory F = StreamMappingFactory.eINSTANCE;
	private static final EObject SELF = EcoreFactory.eINSTANCE.createEObject();

	private final ChangeRules rules = new ChangeRules(null);

	@Test
	void withoutRuleEveryValuePassesAndTheFirstAlways() {
		SlotState state = SlotState.empty();
		assertThat(rules.offer(null, state, 1.0, 0, SELF)).isTrue();
		assertThat(rules.offer(null, state, 1.0, 1, SELF)).isTrue();

		SlotState fresh = SlotState.empty();
		assertThat(rules.offer(absolute(100), fresh, 1.0, 0, SELF)).isTrue();
	}

	@Test
	void absoluteChangeComparesToTheLastPassedValue() {
		AbsoluteChange rule = absolute(0.5);
		SlotState state = SlotState.of(24.0, 0);

		assertThat(rules.offer(rule, state, 24.3, 1, SELF)).isFalse();
		assertThat(rules.offer(rule, state, 24.4, 2, SELF)).isFalse();
		// rejected values do not become the base: 24.5 is 0.5 away from 24.0
		assertThat(rules.offer(rule, state, 24.5, 3, SELF)).isTrue();
		assertThat(state.lastValue()).isEqualTo(24.5);
		assertThat(rules.offer(rule, state, 24.0, 4, SELF)).isTrue();
	}

	@Test
	void percentageChangeIsRelativeAndZeroPassesAnyChange() {
		PercentageChange rule = F.createPercentageChange();
		rule.setPercent(10);

		assertThat(rules.test(rule, SlotState.of(100.0, 0), 109.0, 1, SELF)).isFalse();
		assertThat(rules.test(rule, SlotState.of(100.0, 0), 90.0, 1, SELF)).isTrue();
		assertThat(rules.test(rule, SlotState.of(0.0, 0), 0.0, 1, SELF)).isFalse();
		assertThat(rules.test(rule, SlotState.of(0.0, 0), 0.1, 1, SELF)).isTrue();
	}

	@Test
	void countChangePassesEveryNthValue() {
		CountChange rule = F.createCountChange();
		rule.setN(3);
		SlotState state = SlotState.of(1, 0);

		assertThat(List.of(rules.offer(rule, state, 2, 1, SELF), rules.offer(rule, state, 3, 2, SELF),
				rules.offer(rule, state, 4, 3, SELF), rules.offer(rule, state, 5, 4, SELF))).containsExactly(false, false,
						true, false);
	}

	@Test
	void timeThrottleMeasuresOnTheEventTime() {
		TimeThrottle rule = F.createTimeThrottle();
		rule.setInterval(1);
		rule.setUnit(DurationUnit.MINUTES);
		SlotState state = SlotState.of(1.0, 0);

		assertThat(rules.offer(rule, state, 2.0, 59_999, SELF)).isFalse();
		assertThat(rules.offer(rule, state, 3.0, 60_000, SELF)).isTrue();
		assertThat(rules.offer(rule, state, 4.0, 90_000, SELF)).isFalse();
	}

	@Test
	void andOrNotCombineAndNest() {
		SlotState state = SlotState.of(24.0, 0);
		// rules are contained, so every composite gets its own instances
		CompositeRule and = composite(RuleOperator.AND, absolute(1.0), throttle(60));
		CompositeRule or = composite(RuleOperator.OR, absolute(1.0), throttle(60));
		CompositeRule notBig = composite(RuleOperator.NOT, absolute(1.0));
		CompositeRule nested = composite(RuleOperator.AND, composite(RuleOperator.NOT, absolute(1.0)),
				composite(RuleOperator.OR, throttle(60)));

		// +2 degrees after 10 s: big change yes, minute no
		assertThat(rules.test(and, state, 26.0, 10_000, SELF)).isFalse();
		assertThat(rules.test(or, state, 26.0, 10_000, SELF)).isTrue();
		assertThat(rules.test(notBig, state, 26.0, 10_000, SELF)).isFalse();
		// +0.1 after 2 min: big change no, minute yes
		assertThat(rules.test(nested, state, 24.1, 120_000, SELF)).isTrue();
	}

	@Test
	void theStateMovesOnlyWhenTheSlotLetsTheValuePass() {
		CompositeRule or = composite(RuleOperator.OR, throttle(60), absolute(0.5));
		SlotState state = SlotState.of(24.0, 0);

		assertThat(rules.offer(or, state, 24.1, 10_000, SELF)).isFalse();
		assertThat(state.lastValue()).isEqualTo(24.0);
		assertThat(state.lastTime()).isZero();
		assertThat(state.offeredSinceLastPass()).isEqualTo(1);

		// passes by the absolute change; the throttle now measures from this value
		assertThat(rules.offer(or, state, 25.0, 20_000, SELF)).isTrue();
		assertThat(state.lastTime()).isEqualTo(20_000);
		assertThat(rules.offer(or, state, 25.1, 70_000, SELF)).isFalse();
		assertThat(rules.offer(or, state, 25.1, 80_000, SELF)).isTrue();
	}

	@Test
	void inapplicableRulesLetTheValuePass() {
		SlotState state = SlotState.of("open", 0);
		assertThat(rules.test(absolute(1), state, "closed", 1, SELF)).isTrue();
		assertThat(rules.test(composite(RuleOperator.NOT), state, "closed", 1, SELF)).isTrue();
		assertThat(rules.test(expression("value <> last"), state, "closed", 1, SELF)).isTrue();
	}

	@Test
	void anExpressionRuleSeesValueAndLast() {
		AtomicReference<Map<String, Object>> seen = new AtomicReference<>();
		ExpressionEvaluator evaluator = new ExpressionEvaluator() {
			@Override
			public Optional<Object> evaluate(String expression, EObject self) {
				return Optional.empty();
			}

			@Override
			public Optional<Object> evaluate(String expression, EObject self, Map<String, Object> variables) {
				seen.set(variables);
				return Optional.of(((Double) variables.get("value")) > 30.0);
			}
		};
		ChangeRules withExpressions = new ChangeRules(evaluator);
		SlotState state = SlotState.of(24.0, 0);

		assertThat(withExpressions.test(expression("value > 30.0"), state, 31.0, 1, SELF)).isTrue();
		assertThat(seen.get()).containsEntry("value", 31.0).containsEntry("last", 24.0);
		assertThat(withExpressions.test(expression("value > 30.0"), state, 29.0, 1, SELF)).isFalse();
	}

	private static AbsoluteChange absolute(double delta) {
		AbsoluteChange rule = F.createAbsoluteChange();
		rule.setDelta(delta);
		return rule;
	}

	private static TimeThrottle throttle(long seconds) {
		TimeThrottle rule = F.createTimeThrottle();
		rule.setInterval(seconds);
		rule.setUnit(DurationUnit.SECONDS);
		return rule;
	}

	private static CompositeRule composite(RuleOperator operator, ChangeRule... children) {
		CompositeRule rule = F.createCompositeRule();
		rule.setOperator(operator);
		rule.getRules().addAll(List.of(children));
		return rule;
	}

	private static ExpressionRule expression(String text) {
		ExpressionRule rule = F.createExpressionRule();
		rule.setExpression(text);
		return rule;
	}
}
