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
 * A representation of the model object '<em><b>Ph Sensor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A pH probe. It does not know its pool: a table maps probe to pool.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getReading <em>Reading</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorPackage#getPhSensor()
 * @model
 * @generated
 */
@ProviderType
public interface PhSensor extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Probe id.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorPackage#getPhSensor_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Reading</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * pH value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reading</em>' attribute.
	 * @see #setReading(double)
	 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorPackage#getPhSensor_Reading()
	 * @model
	 * @generated
	 */
	double getReading();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getReading <em>Reading</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reading</em>' attribute.
	 * @see #getReading()
	 * @generated
	 */
	void setReading(double value);

} // PhSensor
