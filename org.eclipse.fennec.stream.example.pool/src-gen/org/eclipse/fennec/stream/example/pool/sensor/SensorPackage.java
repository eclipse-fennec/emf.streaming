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


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.fennec.emf.osgi.annotation.provide.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Simulated sensors of the pool example: they feed water temperature and pH of the pools of the leisure pool demo.
 * <!-- end-model-doc -->
 * @see org.eclipse.fennec.stream.example.pool.sensor.SensorFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = SensorPackage.eNS_URI, fingerprint = "fp1:85abb850e66dd7ede1b94daf85a1be2714a8420597c8114482bb871d42142d3f", genModel = "/model/sensor.genmodel", genModelSourceLocations = {"model/sensor.genmodel","org.eclipse.fennec.stream.example.pool/model/sensor.genmodel"}, ecore = "/model/sensor.ecore", ecoreSourceLocations = "/model/sensor.ecore")
public interface SensorPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sensor";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://eclipse.org/fennec/stream/example/sensor/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sensor";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SensorPackage eINSTANCE = org.eclipse.fennec.stream.example.pool.sensor.impl.SensorPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.stream.example.pool.sensor.impl.TempSensorImpl <em>Temp Sensor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.TempSensorImpl
	 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.SensorPackageImpl#getTempSensor()
	 * @generated
	 */
	int TEMP_SENSOR = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_SENSOR__ID = 0;

	/**
	 * The feature id for the '<em><b>Pool Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_SENSOR__POOL_ID = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_SENSOR__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Temp Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_SENSOR_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Temp Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_SENSOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.stream.example.pool.sensor.impl.PhSensorImpl <em>Ph Sensor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.PhSensorImpl
	 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.SensorPackageImpl#getPhSensor()
	 * @generated
	 */
	int PH_SENSOR = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PH_SENSOR__ID = 0;

	/**
	 * The feature id for the '<em><b>Reading</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PH_SENSOR__READING = 1;

	/**
	 * The number of structural features of the '<em>Ph Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PH_SENSOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Ph Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PH_SENSOR_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor <em>Temp Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Temp Sensor</em>'.
	 * @see org.eclipse.fennec.stream.example.pool.sensor.TempSensor
	 * @generated
	 */
	EClass getTempSensor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getId()
	 * @see #getTempSensor()
	 * @generated
	 */
	EAttribute getTempSensor_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getPoolId <em>Pool Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pool Id</em>'.
	 * @see org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getPoolId()
	 * @see #getTempSensor()
	 * @generated
	 */
	EAttribute getTempSensor_PoolId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fennec.stream.example.pool.sensor.TempSensor#getValue()
	 * @see #getTempSensor()
	 * @generated
	 */
	EAttribute getTempSensor_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.stream.example.pool.sensor.PhSensor <em>Ph Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ph Sensor</em>'.
	 * @see org.eclipse.fennec.stream.example.pool.sensor.PhSensor
	 * @generated
	 */
	EClass getPhSensor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getId()
	 * @see #getPhSensor()
	 * @generated
	 */
	EAttribute getPhSensor_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getReading <em>Reading</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reading</em>'.
	 * @see org.eclipse.fennec.stream.example.pool.sensor.PhSensor#getReading()
	 * @see #getPhSensor()
	 * @generated
	 */
	EAttribute getPhSensor_Reading();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SensorFactory getSensorFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.stream.example.pool.sensor.impl.TempSensorImpl <em>Temp Sensor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.TempSensorImpl
		 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.SensorPackageImpl#getTempSensor()
		 * @generated
		 */
		EClass TEMP_SENSOR = eINSTANCE.getTempSensor();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMP_SENSOR__ID = eINSTANCE.getTempSensor_Id();

		/**
		 * The meta object literal for the '<em><b>Pool Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMP_SENSOR__POOL_ID = eINSTANCE.getTempSensor_PoolId();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMP_SENSOR__VALUE = eINSTANCE.getTempSensor_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.stream.example.pool.sensor.impl.PhSensorImpl <em>Ph Sensor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.PhSensorImpl
		 * @see org.eclipse.fennec.stream.example.pool.sensor.impl.SensorPackageImpl#getPhSensor()
		 * @generated
		 */
		EClass PH_SENSOR = eINSTANCE.getPhSensor();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PH_SENSOR__ID = eINSTANCE.getPhSensor_Id();

		/**
		 * The meta object literal for the '<em><b>Reading</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PH_SENSOR__READING = eINSTANCE.getPhSensor_Reading();

	}

} //SensorPackage
