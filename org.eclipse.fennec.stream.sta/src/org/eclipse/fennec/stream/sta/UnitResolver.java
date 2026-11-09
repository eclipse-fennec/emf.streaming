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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.fennec.model.units.Unit;
import org.eclipse.fennec.model.units.UnitsPackage;
import org.eclipse.fennec.model.units.catalog.UnitCatalogs;

/**
 * The unit of a feature from the shared unit annotation {@value UnitCatalogs#ANNOTATION_SOURCE}
 * (fennec.models#18): a reference to a unit of the catalog, else the UCUM {@code code} looked
 * up in the catalog (a code it does not list is its own name and symbol), and the details
 * {@code name}, {@code symbol}, {@code definition} on top. The catalog is loaded on first use
 * into a resource set of its own; lookups are cached and thread-safe.
 */
final class UnitResolver {

	private final Map<String, Optional<StaMapper.Unit>> byCode = new ConcurrentHashMap<>();

	private ResourceSet catalog;

	/**
	 * @param code a UCUM code
	 * @return the catalog unit of the code, empty when the catalog does not list it
	 */
	Optional<StaMapper.Unit> code(String code) {
		return byCode.computeIfAbsent(code, this::lookup);
	}

	/**
	 * @param feature the target feature
	 * @return its unit by the shared unit annotation, empty without one
	 */
	Optional<StaMapper.Unit> annotated(EStructuralFeature feature) {
		EAnnotation annotation = feature.getEAnnotation(UnitCatalogs.ANNOTATION_SOURCE);
		if (annotation == null) {
			return Optional.empty();
		}
		Map<String, String> details = annotation.getDetails().map();
		String code = details.get(UnitCatalogs.DETAIL_CODE);
		StaMapper.Unit base = annotation.getReferences().stream().map(this::referenced).flatMap(Optional::stream)
				.findFirst()
				.or(() -> code == null ? Optional.empty()
						: Optional.of(code(code).orElse(new StaMapper.Unit(code, code, null))))
				.orElse(new StaMapper.Unit(null, null, null));
		return Optional.of(new StaMapper.Unit(
				or(details.get(UnitCatalogs.DETAIL_NAME), base.name()),
				or(details.get(UnitCatalogs.DETAIL_SYMBOL), base.symbol()),
				or(details.get(UnitCatalogs.DETAIL_DEFINITION), base.definition())));
	}

	private Optional<StaMapper.Unit> referenced(EObject reference) {
		EObject target = reference;
		if (reference.eIsProxy()) {
			synchronized (this) {
				target = EcoreUtil.resolve(reference, catalog());
			}
		}
		return target instanceof Unit unit && !unit.eIsProxy() ? Optional.of(of(unit)) : Optional.empty();
	}

	private synchronized Optional<StaMapper.Unit> lookup(String code) {
		return UnitCatalogs.findUnit(catalog(), code).map(UnitResolver::of);
	}

	private ResourceSet catalog() {
		if (catalog == null) {
			ResourceSet created = new ResourceSetImpl();
			created.getPackageRegistry().put(UnitsPackage.eNS_URI, UnitsPackage.eINSTANCE);
			created.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
					new XMIResourceFactoryImpl());
			UnitCatalogs.register(created);
			catalog = created;
		}
		return catalog;
	}

	private static StaMapper.Unit of(Unit unit) {
		return new StaMapper.Unit(unit.getName(), unit.getSymbol(), unit.getDefinition());
	}

	private static String or(String value, String fallback) {
		return value != null && !value.isBlank() ? value : fallback;
	}
}
