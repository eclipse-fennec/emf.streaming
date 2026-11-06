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
package org.eclipse.fennec.mapping.ocl.impl;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.emf.osgi.metadata.MetadataService;
import org.eclipse.fennec.emf.osgi.model.metadata.PackageMetadata;
import org.eclipse.fennec.m2x.ocl.api.OclEngine;
import org.eclipse.fennec.mapping.ExpressionEvaluator;
import org.eclipse.fennec.mapping.ocl.OclExpressionEvaluator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link ExpressionEvaluator} of a framework: OCL through the M2X {@link OclEngine} service.
 * <p>
 * Both references are mandatory, as in the event atlas: the engine is what this wraps, and the
 * metadata service names the model version (its fingerprint) that keys the parse verdicts - a
 * runtime without it fails to resolve rather than running with a weaker key. No configuration.
 */
@Component(service = ExpressionEvaluator.class)
public class OclExpressionEvaluatorComponent implements ExpressionEvaluator {

	private final OclExpressionEvaluator evaluator;

	/**
	 * @param engine the OCL engine
	 * @param metadata the metadata service, for model fingerprints
	 */
	@Activate
	public OclExpressionEvaluatorComponent(@Reference(name = "engine") OclEngine engine,
			@Reference(name = "metadata") MetadataService metadata) {
		this.evaluator = new OclExpressionEvaluator(engine, ePackage -> fingerprint(metadata, ePackage));
	}

	@Override
	public Optional<Object> evaluate(String expression, EObject self) {
		return evaluator.evaluate(expression, self);
	}

	@Override
	public Optional<Object> evaluate(String expression, EObject self, Map<String, Object> variables) {
		return evaluator.evaluate(expression, self, variables);
	}

	private static String fingerprint(MetadataService metadata, EPackage ePackage) {
		String fingerprint = metadata.getPackageMetadata(ePackage).map(PackageMetadata::getModelFingerprint).orElse(null);
		return fingerprint == null || fingerprint.isBlank() ? OclExpressionEvaluator.NS_URI.apply(ePackage) : fingerprint;
	}
}
