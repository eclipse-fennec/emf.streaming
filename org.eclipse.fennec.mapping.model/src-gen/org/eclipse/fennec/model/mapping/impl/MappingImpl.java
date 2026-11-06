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
package org.eclipse.fennec.model.mapping.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fennec.atlas.configuration.impl.DataTransformationImpl;

import org.eclipse.fennec.model.mapping.IdentityResolution;
import org.eclipse.fennec.model.mapping.Mapping;
import org.eclipse.fennec.model.mapping.MappingPackage;
import org.eclipse.fennec.model.mapping.Slot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.MappingImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.MappingImpl#getIdentities <em>Identities</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.MappingImpl#getSlots <em>Slots</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.MappingImpl#getIncludeUnmapped <em>Include Unmapped</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingImpl extends DataTransformationImpl implements Mapping {
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EClass target;

	/**
	 * The cached value of the '{@link #getIdentities() <em>Identities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentities()
	 * @generated
	 * @ordered
	 */
	protected EList<IdentityResolution> identities;

	/**
	 * The cached value of the '{@link #getSlots() <em>Slots</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlots()
	 * @generated
	 * @ordered
	 */
	protected EList<Slot> slots;

	/**
	 * The default value of the '{@link #getIncludeUnmapped() <em>Include Unmapped</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeUnmapped()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean INCLUDE_UNMAPPED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIncludeUnmapped() <em>Include Unmapped</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeUnmapped()
	 * @generated
	 * @ordered
	 */
	protected Boolean includeUnmapped = INCLUDE_UNMAPPED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MappingPackage.Literals.MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (EClass)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MappingPackage.MAPPING__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(EClass newTarget) {
		EClass oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.MAPPING__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IdentityResolution> getIdentities() {
		if (identities == null) {
			identities = new EObjectContainmentEList<IdentityResolution>(IdentityResolution.class, this, MappingPackage.MAPPING__IDENTITIES);
		}
		return identities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Slot> getSlots() {
		if (slots == null) {
			slots = new EObjectContainmentEList<Slot>(Slot.class, this, MappingPackage.MAPPING__SLOTS);
		}
		return slots;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getIncludeUnmapped() {
		return includeUnmapped;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIncludeUnmapped(Boolean newIncludeUnmapped) {
		Boolean oldIncludeUnmapped = includeUnmapped;
		includeUnmapped = newIncludeUnmapped;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.MAPPING__INCLUDE_UNMAPPED, oldIncludeUnmapped, includeUnmapped));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MappingPackage.MAPPING__IDENTITIES:
				return ((InternalEList<?>)getIdentities()).basicRemove(otherEnd, msgs);
			case MappingPackage.MAPPING__SLOTS:
				return ((InternalEList<?>)getSlots()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MappingPackage.MAPPING__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case MappingPackage.MAPPING__IDENTITIES:
				return getIdentities();
			case MappingPackage.MAPPING__SLOTS:
				return getSlots();
			case MappingPackage.MAPPING__INCLUDE_UNMAPPED:
				return getIncludeUnmapped();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MappingPackage.MAPPING__TARGET:
				setTarget((EClass)newValue);
				return;
			case MappingPackage.MAPPING__IDENTITIES:
				getIdentities().clear();
				getIdentities().addAll((Collection<? extends IdentityResolution>)newValue);
				return;
			case MappingPackage.MAPPING__SLOTS:
				getSlots().clear();
				getSlots().addAll((Collection<? extends Slot>)newValue);
				return;
			case MappingPackage.MAPPING__INCLUDE_UNMAPPED:
				setIncludeUnmapped((Boolean)newValue);
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
			case MappingPackage.MAPPING__TARGET:
				setTarget((EClass)null);
				return;
			case MappingPackage.MAPPING__IDENTITIES:
				getIdentities().clear();
				return;
			case MappingPackage.MAPPING__SLOTS:
				getSlots().clear();
				return;
			case MappingPackage.MAPPING__INCLUDE_UNMAPPED:
				setIncludeUnmapped(INCLUDE_UNMAPPED_EDEFAULT);
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
			case MappingPackage.MAPPING__TARGET:
				return target != null;
			case MappingPackage.MAPPING__IDENTITIES:
				return identities != null && !identities.isEmpty();
			case MappingPackage.MAPPING__SLOTS:
				return slots != null && !slots.isEmpty();
			case MappingPackage.MAPPING__INCLUDE_UNMAPPED:
				return INCLUDE_UNMAPPED_EDEFAULT == null ? includeUnmapped != null : !INCLUDE_UNMAPPED_EDEFAULT.equals(includeUnmapped);
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
		result.append(" (includeUnmapped: ");
		result.append(includeUnmapped);
		result.append(')');
		return result.toString();
	}

} //MappingImpl
