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
package org.eclipse.fennec.model.streammapping.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.StreamMappingPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deletion Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl#getRetention <em>Retention</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl#getRetentionUnit <em>Retention Unit</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl#getMaxCount <em>Max Count</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl#getCleanupInterval <em>Cleanup Interval</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl#getCleanupIntervalUnit <em>Cleanup Interval Unit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeletionRuleImpl extends MinimalEObjectImpl.Container implements DeletionRule {
	/**
	 * The default value of the '{@link #getRetention() <em>Retention</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetention()
	 * @generated
	 * @ordered
	 */
	protected static final long RETENTION_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getRetention() <em>Retention</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetention()
	 * @generated
	 * @ordered
	 */
	protected long retention = RETENTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getRetentionUnit() <em>Retention Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetentionUnit()
	 * @generated
	 * @ordered
	 */
	protected static final DurationUnit RETENTION_UNIT_EDEFAULT = DurationUnit.DAYS;

	/**
	 * The cached value of the '{@link #getRetentionUnit() <em>Retention Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetentionUnit()
	 * @generated
	 * @ordered
	 */
	protected DurationUnit retentionUnit = RETENTION_UNIT_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxCount() <em>Max Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxCount()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxCount() <em>Max Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxCount()
	 * @generated
	 * @ordered
	 */
	protected int maxCount = MAX_COUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getCleanupInterval() <em>Cleanup Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCleanupInterval()
	 * @generated
	 * @ordered
	 */
	protected static final long CLEANUP_INTERVAL_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getCleanupInterval() <em>Cleanup Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCleanupInterval()
	 * @generated
	 * @ordered
	 */
	protected long cleanupInterval = CLEANUP_INTERVAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCleanupIntervalUnit() <em>Cleanup Interval Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCleanupIntervalUnit()
	 * @generated
	 * @ordered
	 */
	protected static final DurationUnit CLEANUP_INTERVAL_UNIT_EDEFAULT = DurationUnit.HOURS;

	/**
	 * The cached value of the '{@link #getCleanupIntervalUnit() <em>Cleanup Interval Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCleanupIntervalUnit()
	 * @generated
	 * @ordered
	 */
	protected DurationUnit cleanupIntervalUnit = CLEANUP_INTERVAL_UNIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeletionRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StreamMappingPackage.Literals.DELETION_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getRetention() {
		return retention;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRetention(long newRetention) {
		long oldRetention = retention;
		retention = newRetention;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.DELETION_RULE__RETENTION, oldRetention, retention));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DurationUnit getRetentionUnit() {
		return retentionUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRetentionUnit(DurationUnit newRetentionUnit) {
		DurationUnit oldRetentionUnit = retentionUnit;
		retentionUnit = newRetentionUnit == null ? RETENTION_UNIT_EDEFAULT : newRetentionUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.DELETION_RULE__RETENTION_UNIT, oldRetentionUnit, retentionUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaxCount(int newMaxCount) {
		int oldMaxCount = maxCount;
		maxCount = newMaxCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.DELETION_RULE__MAX_COUNT, oldMaxCount, maxCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getCleanupInterval() {
		return cleanupInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCleanupInterval(long newCleanupInterval) {
		long oldCleanupInterval = cleanupInterval;
		cleanupInterval = newCleanupInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL, oldCleanupInterval, cleanupInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DurationUnit getCleanupIntervalUnit() {
		return cleanupIntervalUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCleanupIntervalUnit(DurationUnit newCleanupIntervalUnit) {
		DurationUnit oldCleanupIntervalUnit = cleanupIntervalUnit;
		cleanupIntervalUnit = newCleanupIntervalUnit == null ? CLEANUP_INTERVAL_UNIT_EDEFAULT : newCleanupIntervalUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL_UNIT, oldCleanupIntervalUnit, cleanupIntervalUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StreamMappingPackage.DELETION_RULE__RETENTION:
				return getRetention();
			case StreamMappingPackage.DELETION_RULE__RETENTION_UNIT:
				return getRetentionUnit();
			case StreamMappingPackage.DELETION_RULE__MAX_COUNT:
				return getMaxCount();
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL:
				return getCleanupInterval();
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL_UNIT:
				return getCleanupIntervalUnit();
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
			case StreamMappingPackage.DELETION_RULE__RETENTION:
				setRetention((Long)newValue);
				return;
			case StreamMappingPackage.DELETION_RULE__RETENTION_UNIT:
				setRetentionUnit((DurationUnit)newValue);
				return;
			case StreamMappingPackage.DELETION_RULE__MAX_COUNT:
				setMaxCount((Integer)newValue);
				return;
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL:
				setCleanupInterval((Long)newValue);
				return;
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL_UNIT:
				setCleanupIntervalUnit((DurationUnit)newValue);
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
			case StreamMappingPackage.DELETION_RULE__RETENTION:
				setRetention(RETENTION_EDEFAULT);
				return;
			case StreamMappingPackage.DELETION_RULE__RETENTION_UNIT:
				setRetentionUnit(RETENTION_UNIT_EDEFAULT);
				return;
			case StreamMappingPackage.DELETION_RULE__MAX_COUNT:
				setMaxCount(MAX_COUNT_EDEFAULT);
				return;
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL:
				setCleanupInterval(CLEANUP_INTERVAL_EDEFAULT);
				return;
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL_UNIT:
				setCleanupIntervalUnit(CLEANUP_INTERVAL_UNIT_EDEFAULT);
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
			case StreamMappingPackage.DELETION_RULE__RETENTION:
				return retention != RETENTION_EDEFAULT;
			case StreamMappingPackage.DELETION_RULE__RETENTION_UNIT:
				return retentionUnit != RETENTION_UNIT_EDEFAULT;
			case StreamMappingPackage.DELETION_RULE__MAX_COUNT:
				return maxCount != MAX_COUNT_EDEFAULT;
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL:
				return cleanupInterval != CLEANUP_INTERVAL_EDEFAULT;
			case StreamMappingPackage.DELETION_RULE__CLEANUP_INTERVAL_UNIT:
				return cleanupIntervalUnit != CLEANUP_INTERVAL_UNIT_EDEFAULT;
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
		result.append(" (retention: ");
		result.append(retention);
		result.append(", retentionUnit: ");
		result.append(retentionUnit);
		result.append(", maxCount: ");
		result.append(maxCount);
		result.append(", cleanupInterval: ");
		result.append(cleanupInterval);
		result.append(", cleanupIntervalUnit: ");
		result.append(cleanupIntervalUnit);
		result.append(')');
		return result.toString();
	}

} //DeletionRuleImpl
