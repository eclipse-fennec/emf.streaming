/*
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
 */
package org.eclipse.fennec.model.mapping;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.fennec.atlas.configuration.DataTransformation;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A mapping onto one target class. supportedEClasses (inherited) lists the source classes, resultEClasses the target; Mappings#normalize fills both from target, identities and slots, so atlas tooling can discover what a mapping reads and writes.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.mapping.Mapping#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.Mapping#getIdentities <em>Identities</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.Mapping#getSlots <em>Slots</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.Mapping#getIncludeUnmapped <em>Include Unmapped</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.mapping.MappingPackage#getMapping()
 * @model
 * @generated
 */
@ProviderType
public interface Mapping extends DataTransformation {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The target class. Unset for consumers whose target is no EClass (a search document): their slots are named.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EClass)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getMapping_Target()
	 * @model
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.Mapping#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EClass value);

	/**
	 * Returns the value of the '<em><b>Identities</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.mapping.IdentityResolution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How objects of each source class find their target object. At most one per (sourceClass, targetClass); shared by all slots reading that source class.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Identities</em>' containment reference list.
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getMapping_Identities()
	 * @model containment="true"
	 * @generated
	 */
	EList<IdentityResolution> getIdentities();

	/**
	 * Returns the value of the '<em><b>Slots</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.mapping.Slot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The slots of the target this mapping fills, each from exactly one source.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Slots</em>' containment reference list.
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getMapping_Slots()
	 * @model containment="true"
	 * @generated
	 */
	EList<Slot> getSlots();

	/**
	 * Returns the value of the '<em><b>Include Unmapped</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether features of the target that no slot names take part, as the consumer interprets it (search: indexed by convention; streaming: tracked as-is). Unset = the consumer's default.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Include Unmapped</em>' attribute.
	 * @see #setIncludeUnmapped(Boolean)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getMapping_IncludeUnmapped()
	 * @model
	 * @generated
	 */
	Boolean getIncludeUnmapped();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.Mapping#getIncludeUnmapped <em>Include Unmapped</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include Unmapped</em>' attribute.
	 * @see #getIncludeUnmapped()
	 * @generated
	 */
	void setIncludeUnmapped(Boolean value);

} // Mapping
