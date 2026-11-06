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
package org.eclipse.fennec.model.streammapping;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Batch Strategy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * How values of a target object are grouped into change sets.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getCount <em>Count</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getWindow <em>Window</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getBatchStrategy()
 * @model
 * @generated
 */
@ProviderType
public interface BatchStrategy extends EObject {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The default value is <code>"IMMEDIATE"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.BatchKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The strategy.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.BatchKind
	 * @see #setKind(BatchKind)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getBatchStrategy_Kind()
	 * @model default="IMMEDIATE"
	 * @generated
	 */
	BatchKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.BatchKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(BatchKind value);

	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * COUNT: values per change set.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(int)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getBatchStrategy_Count()
	 * @model default="1"
	 * @generated
	 */
	int getCount();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(int value);

	/**
	 * Returns the value of the '<em><b>Window</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * WINDOW: length of the time window.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Window</em>' attribute.
	 * @see #setWindow(long)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getBatchStrategy_Window()
	 * @model default="0"
	 * @generated
	 */
	long getWindow();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getWindow <em>Window</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window</em>' attribute.
	 * @see #getWindow()
	 * @generated
	 */
	void setWindow(long value);

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * The default value is <code>"SECONDS"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.DurationUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * WINDOW: unit of the window.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #setUnit(DurationUnit)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getBatchStrategy_Unit()
	 * @model default="SECONDS"
	 * @generated
	 */
	DurationUnit getUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(DurationUnit value);

} // BatchStrategy
