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
package org.eclipse.fennec.model.stream.support;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * A small dynamic model for the tests: {@code Asset} (id, name) and {@code Pool extends Asset}
 * (currentTemperature, currentPh). Inherited features let the tests check that a feature URI names
 * the declaring class.
 */
final class PoolModel {

	static final String NS_URI = "https://example.org/pool";

	final EPackage ePackage;
	final EClass asset;
	final EClass pool;
	final EAttribute id;
	final EAttribute name;
	final EAttribute currentTemperature;
	final EAttribute currentPh;

	PoolModel() {
		EcoreFactory factory = EcoreFactory.eINSTANCE;
		ePackage = factory.createEPackage();
		ePackage.setName("pool");
		ePackage.setNsPrefix("pool");
		ePackage.setNsURI(NS_URI);

		asset = factory.createEClass();
		asset.setName("Asset");
		asset.setAbstract(true);
		id = attribute("id", EcorePackage.Literals.ESTRING);
		name = attribute("name", EcorePackage.Literals.ESTRING);
		asset.getEStructuralFeatures().add(id);
		asset.getEStructuralFeatures().add(name);

		pool = factory.createEClass();
		pool.setName("Pool");
		pool.getESuperTypes().add(asset);
		currentTemperature = attribute("currentTemperature", EcorePackage.Literals.EDOUBLE);
		currentPh = attribute("currentPh", EcorePackage.Literals.EDOUBLE);
		pool.getEStructuralFeatures().add(currentTemperature);
		pool.getEStructuralFeatures().add(currentPh);

		ePackage.getEClassifiers().add(asset);
		ePackage.getEClassifiers().add(pool);
	}

	private static EAttribute attribute(String name, EDataType type) {
		EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(name);
		attribute.setEType(type);
		return attribute;
	}
}
