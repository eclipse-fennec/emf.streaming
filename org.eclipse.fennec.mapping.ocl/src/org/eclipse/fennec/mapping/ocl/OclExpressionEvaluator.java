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
package org.eclipse.fennec.mapping.ocl;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.m2x.ocl.api.OclContext;
import org.eclipse.fennec.m2x.ocl.api.OclEngine;
import org.eclipse.fennec.m2x.ocl.api.OclInvalid;
import org.eclipse.fennec.m2x.ocl.api.OclParseException;
import org.eclipse.fennec.mapping.ExpressionEvaluator;

/**
 * The {@link ExpressionEvaluator} of the mapping, backed by the Fennec OCL engine. {@code self} is
 * the source object: {@code self.value * 1.8 + 32}, {@code self.readings->size()},
 * {@code if self.battery < 10 then 'low' else 'ok' endif}.
 * <p>
 * Nothing here throws at the mapping: an expression that does not parse, fails, or yields OCL
 * {@code null} or {@code invalid} produces no value. A parse failure is remembered per model
 * version and expression, so it costs one log line, not one per value; an evaluation failure can
 * depend on the value and stays in use, logged once.
 * <p>
 * Ported from the event atlas mapping ({@code OclExpressionEvaluator}). The model version that keys
 * the parse verdicts is a function here, so the class runs without OSGi; in OSGi it is the model
 * fingerprint of the metadata service.
 */
public final class OclExpressionEvaluator implements ExpressionEvaluator {

	/** Keys parse verdicts by nsURI - weaker than a fingerprint, never wrong. */
	public static final Function<EPackage, String> NS_URI = ePackage -> String.valueOf(ePackage.getNsURI());

	private static final Logger LOGGER = Logger.getLogger(OclExpressionEvaluator.class.getName());

	private final OclEngine engine;
	private final Function<EPackage, String> modelVersion;
	private final Set<String> unparseable = ConcurrentHashMap.newKeySet();
	private final Set<String> reported = ConcurrentHashMap.newKeySet();

	/**
	 * @param engine the OCL engine
	 * @param modelVersion names the version of a package (its fingerprint), so a changed model is parsed again
	 */
	public OclExpressionEvaluator(OclEngine engine, Function<EPackage, String> modelVersion) {
		this.engine = requireNonNull(engine, "OclEngine is required");
		this.modelVersion = requireNonNull(modelVersion, "Model version function is required");
	}

	/**
	 * @param engine the OCL engine; parse verdicts are keyed by nsURI
	 */
	public OclExpressionEvaluator(OclEngine engine) {
		this(engine, NS_URI);
	}

	@Override
	public Optional<Object> evaluate(String expression, EObject self) {
		if (expression == null || expression.isBlank() || self == null) {
			return Optional.empty();
		}
		String key = key(expression, self.eClass());
		if (unparseable.contains(key)) {
			return Optional.empty();
		}
		try {
			Object result = engine.evaluate(expression, context(self));
			if (result == null) {
				return Optional.empty();
			}
			if (OclInvalid.INSTANCE.equals(result)) {
				report(key, Level.FINE, "Expression '" + expression + "' is invalid on a " + self.eClass().getName(), null);
				return Optional.empty();
			}
			return Optional.of(result);
		} catch (OclParseException e) {
			unparseable.add(key);
			LOGGER.warning(() -> "Expression '" + expression + "' does not parse against " + self.eClass().getName()
					+ ": " + e.getMessage() + " - it produces no value until the mapping or the model is corrected");
			return Optional.empty();
		} catch (RuntimeException e) {
			report(key, Level.WARNING, "Expression '" + expression + "' failed on a " + self.eClass().getName(), e);
			return Optional.empty();
		}
	}

	private static OclContext context(EObject self) {
		Resource resource = self.eResource();
		ResourceSet resourceSet = resource == null ? null : resource.getResourceSet();
		return resourceSet == null ? OclContext.of(self) : OclContext.of(self, resourceSet);
	}

	private String key(String expression, EClass contextType) {
		EPackage ePackage = contextType.getEPackage();
		String version = "";
		if (ePackage != null) {
			try {
				version = modelVersion.apply(ePackage);
			} catch (RuntimeException e) {
				version = NS_URI.apply(ePackage);
			}
		}
		return version + '#' + contextType.getName() + '\u0000' + expression;
	}

	private void report(String key, Level level, String message, Exception cause) {
		LOGGER.log(reported.add(key) ? level : Level.FINE, message, cause);
	}
}
