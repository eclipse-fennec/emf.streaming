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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * One slot of the target, filled from one source. The value is produced on the source object by 'value' and, when it is written into the target, converted by targetConverterId and coerced to the target feature's type.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.mapping.Slot#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.Slot#getTargetFeature <em>Target Feature</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.Slot#getSourceClass <em>Source Class</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.Slot#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.Slot#getTargetConverterId <em>Target Converter Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.mapping.MappingPackage#getSlot()
 * @model
 * @generated
 */
@ProviderType
public interface Slot extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The slot's name: required when there is no targetFeature (named slots), otherwise informational.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getSlot_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.Slot#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Target Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The feature of the target class this slot fills.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target Feature</em>' reference.
	 * @see #setTargetFeature(EStructuralFeature)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getSlot_TargetFeature()
	 * @model
	 * @generated
	 */
	EStructuralFeature getTargetFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.Slot#getTargetFeature <em>Target Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Feature</em>' reference.
	 * @see #getTargetFeature()
	 * @generated
	 */
	void setTargetFeature(EStructuralFeature value);

	/**
	 * Returns the value of the '<em><b>Source Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The class the value is read from; unset = the target class itself (source = target).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Class</em>' reference.
	 * @see #setSourceClass(EClass)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getSlot_SourceClass()
	 * @model
	 * @generated
	 */
	EClass getSourceClass();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.Slot#getSourceClass <em>Source Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Class</em>' reference.
	 * @see #getSourceClass()
	 * @generated
	 */
	void setSourceClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How the value is produced on the source object.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(ValueSource)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getSlot_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ValueSource getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.Slot#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(ValueSource value);

	/**
	 * Returns the value of the '<em><b>Target Converter Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Converter applied when the value is written into the target, after the value was produced (and, for streaming, after it entered the stream). Unknown id = unconverted.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target Converter Id</em>' attribute.
	 * @see #setTargetConverterId(String)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getSlot_TargetConverterId()
	 * @model
	 * @generated
	 */
	String getTargetConverterId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.Slot#getTargetConverterId <em>Target Converter Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Converter Id</em>' attribute.
	 * @see #getTargetConverterId()
	 * @generated
	 */
	void setTargetConverterId(String value);

} // Slot
