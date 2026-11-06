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
package org.eclipse.fennec.stream.example.pool.sensor.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.stream.example.pool.sensor.SensorPackage;
import org.eclipse.fennec.stream.example.pool.sensor.TempSensor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Temp Sensor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.impl.TempSensorImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.impl.TempSensorImpl#getPoolId <em>Pool Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.stream.example.pool.sensor.impl.TempSensorImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TempSensorImpl extends MinimalEObjectImpl.Container implements TempSensor {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getPoolId() <em>Pool Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPoolId()
	 * @generated
	 * @ordered
	 */
	protected static final String POOL_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPoolId() <em>Pool Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPoolId()
	 * @generated
	 * @ordered
	 */
	protected String poolId = POOL_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final double VALUE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected double value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TempSensorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SensorPackage.Literals.TEMP_SENSOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SensorPackage.TEMP_SENSOR__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPoolId() {
		return poolId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPoolId(String newPoolId) {
		String oldPoolId = poolId;
		poolId = newPoolId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SensorPackage.TEMP_SENSOR__POOL_ID, oldPoolId, poolId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(double newValue) {
		double oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SensorPackage.TEMP_SENSOR__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SensorPackage.TEMP_SENSOR__ID:
				return getId();
			case SensorPackage.TEMP_SENSOR__POOL_ID:
				return getPoolId();
			case SensorPackage.TEMP_SENSOR__VALUE:
				return getValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SensorPackage.TEMP_SENSOR__ID:
				setId((String)newValue);
				return;
			case SensorPackage.TEMP_SENSOR__POOL_ID:
				setPoolId((String)newValue);
				return;
			case SensorPackage.TEMP_SENSOR__VALUE:
				setValue((Double)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SensorPackage.TEMP_SENSOR__ID:
				setId(ID_EDEFAULT);
				return;
			case SensorPackage.TEMP_SENSOR__POOL_ID:
				setPoolId(POOL_ID_EDEFAULT);
				return;
			case SensorPackage.TEMP_SENSOR__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SensorPackage.TEMP_SENSOR__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SensorPackage.TEMP_SENSOR__POOL_ID:
				return POOL_ID_EDEFAULT == null ? poolId != null : !POOL_ID_EDEFAULT.equals(poolId);
			case SensorPackage.TEMP_SENSOR__VALUE:
				return value != VALUE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", poolId: ");
		result.append(poolId);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //TempSensorImpl
