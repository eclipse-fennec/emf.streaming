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
package org.eclipse.fennec.stream.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fennec.model.command.UpdateCommand;
import org.eclipse.fennec.model.command.UpdateEntry;
import org.eclipse.fennec.model.command.UpdateKind;
import org.junit.jupiter.api.Test;

/**
 * One update command per target and batch: selector on the id, one entry per feature.
 */
class RepositoryUpdatesTest {

	private final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
	private final EClass pool = EcoreFactory.eINSTANCE.createEClass();
	private final EAttribute id = attribute("id", EcorePackage.Literals.ESTRING);
	private final EAttribute temperature = attribute("waterTemperature", EcorePackage.Literals.EDOUBLE);
	private final EAttribute ph = attribute("ph", EcorePackage.Literals.EDOUBLE);

	RepositoryUpdatesTest() {
		ePackage.setName("bath");
		ePackage.setNsURI("urn:test:bath");
		pool.setName("Pool");
		ePackage.getEClassifiers().add(pool);
		id.setID(true);
		pool.getEStructuralFeatures().add(id);
		pool.getEStructuralFeatures().add(temperature);
		pool.getEStructuralFeatures().add(ph);
	}

	@Test
	void theUpdateSelectsTheTargetByIdAndSetsEveryFeature() {
		Map<EStructuralFeature, Object> values = new LinkedHashMap<>();
		values.put(temperature, 28.4);
		values.put(ph, null);

		UpdateCommand update = RepositoryUpdates.update(pool, "wellenbecken", values);

		assertThat(update.getSelector()).isNotNull();
		assertThat(update.getTemplate().getEntries()).extracting(UpdateEntry::getKind)
				.containsExactly(UpdateKind.SET, UpdateKind.UNSET);
		assertThat(update.getTemplate().getEntries()).extracting(UpdateEntry::getFeature).containsExactly(temperature, ph);
		assertThat(update.getTemplate().getEntries()).extracting(UpdateEntry::getValue).containsExactly("28.4", null);
	}

	@Test
	void aClassWithoutIdCannotBeSelected() {
		EClass sensor = EcoreFactory.eINSTANCE.createEClass();
		sensor.setName("Sensor");
		assertThatIllegalArgumentException().isThrownBy(() -> RepositoryUpdates.update(sensor, "s1", Map.of()))
				.withMessageContaining("no id attribute");
	}

	private static EAttribute attribute(String name, EDataType type) {
		EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(name);
		attribute.setEType(type);
		return attribute;
	}
}
