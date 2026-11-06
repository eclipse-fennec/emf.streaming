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
package org.eclipse.fennec.stream.example.pool.sensor;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Temp Sensor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A water temperature sensor of a pool. It names the pool it measures (poolId).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getPoolId <em>Pool Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorPackage#getTempSensor()
 * @model
 * @generated
 */
@ProviderType
public interface TempSensor extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Sensor id.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorPackage#getTempSensor_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Pool Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The id of the pool the sensor measures.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pool Id</em>' attribute.
	 * @see #setPoolId(String)
	 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorPackage#getTempSensor_PoolId()
	 * @model
	 * @generated
	 */
	String getPoolId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getPoolId <em>Pool Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pool Id</em>' attribute.
	 * @see #getPoolId()
	 * @generated
	 */
	void setPoolId(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Water temperature in degrees Celsius.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(double)
	 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorPackage#getTempSensor_Value()
	 * @model
	 * @generated
	 */
	double getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(double value);

} // TempSensor
