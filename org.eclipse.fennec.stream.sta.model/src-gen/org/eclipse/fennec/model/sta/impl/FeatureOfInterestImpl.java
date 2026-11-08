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
package org.eclipse.fennec.model.sta.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fennec.model.sta.FeatureOfInterest;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.StaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Of Interest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl#getEncodingType <em>Encoding Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl#getObservations <em>Observations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureOfInterestImpl extends MinimalEObjectImpl.Container implements FeatureOfInterest {
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEncodingType() <em>Encoding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncodingType()
	 * @generated
	 * @ordered
	 */
	protected static final String ENCODING_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEncodingType() <em>Encoding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncodingType()
	 * @generated
	 * @ordered
	 */
	protected String encodingType = ENCODING_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFeature() <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected static final String FEATURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected String feature = FEATURE_EDEFAULT;

	/**
	 * The default value of the '{@link #getProperties() <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected String properties = PROPERTIES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getObservations() <em>Observations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObservations()
	 * @generated
	 * @ordered
	 */
	protected EList<Observation> observations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureOfInterestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StaPackage.Literals.FEATURE_OF_INTEREST;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.FEATURE_OF_INTEREST__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.FEATURE_OF_INTEREST__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.FEATURE_OF_INTEREST__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEncodingType() {
		return encodingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEncodingType(String newEncodingType) {
		String oldEncodingType = encodingType;
		encodingType = newEncodingType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.FEATURE_OF_INTEREST__ENCODING_TYPE, oldEncodingType, encodingType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeature(String newFeature) {
		String oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.FEATURE_OF_INTEREST__FEATURE, oldFeature, feature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProperties(String newProperties) {
		String oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.FEATURE_OF_INTEREST__PROPERTIES, oldProperties, properties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Observation> getObservations() {
		if (observations == null) {
			observations = new EObjectWithInverseResolvingEList<Observation>(Observation.class, this, StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS, StaPackage.OBSERVATION__FEATURE_OF_INTEREST);
		}
		return observations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getObservations()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS:
				return ((InternalEList<?>)getObservations()).basicRemove(otherEnd, msgs);
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
			case StaPackage.FEATURE_OF_INTEREST__ID:
				return getId();
			case StaPackage.FEATURE_OF_INTEREST__NAME:
				return getName();
			case StaPackage.FEATURE_OF_INTEREST__DESCRIPTION:
				return getDescription();
			case StaPackage.FEATURE_OF_INTEREST__ENCODING_TYPE:
				return getEncodingType();
			case StaPackage.FEATURE_OF_INTEREST__FEATURE:
				return getFeature();
			case StaPackage.FEATURE_OF_INTEREST__PROPERTIES:
				return getProperties();
			case StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS:
				return getObservations();
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
			case StaPackage.FEATURE_OF_INTEREST__ID:
				setId((String)newValue);
				return;
			case StaPackage.FEATURE_OF_INTEREST__NAME:
				setName((String)newValue);
				return;
			case StaPackage.FEATURE_OF_INTEREST__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case StaPackage.FEATURE_OF_INTEREST__ENCODING_TYPE:
				setEncodingType((String)newValue);
				return;
			case StaPackage.FEATURE_OF_INTEREST__FEATURE:
				setFeature((String)newValue);
				return;
			case StaPackage.FEATURE_OF_INTEREST__PROPERTIES:
				setProperties((String)newValue);
				return;
			case StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS:
				getObservations().clear();
				getObservations().addAll((Collection<? extends Observation>)newValue);
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
			case StaPackage.FEATURE_OF_INTEREST__ID:
				setId(ID_EDEFAULT);
				return;
			case StaPackage.FEATURE_OF_INTEREST__NAME:
				setName(NAME_EDEFAULT);
				return;
			case StaPackage.FEATURE_OF_INTEREST__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case StaPackage.FEATURE_OF_INTEREST__ENCODING_TYPE:
				setEncodingType(ENCODING_TYPE_EDEFAULT);
				return;
			case StaPackage.FEATURE_OF_INTEREST__FEATURE:
				setFeature(FEATURE_EDEFAULT);
				return;
			case StaPackage.FEATURE_OF_INTEREST__PROPERTIES:
				setProperties(PROPERTIES_EDEFAULT);
				return;
			case StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS:
				getObservations().clear();
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
			case StaPackage.FEATURE_OF_INTEREST__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case StaPackage.FEATURE_OF_INTEREST__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case StaPackage.FEATURE_OF_INTEREST__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case StaPackage.FEATURE_OF_INTEREST__ENCODING_TYPE:
				return ENCODING_TYPE_EDEFAULT == null ? encodingType != null : !ENCODING_TYPE_EDEFAULT.equals(encodingType);
			case StaPackage.FEATURE_OF_INTEREST__FEATURE:
				return FEATURE_EDEFAULT == null ? feature != null : !FEATURE_EDEFAULT.equals(feature);
			case StaPackage.FEATURE_OF_INTEREST__PROPERTIES:
				return PROPERTIES_EDEFAULT == null ? properties != null : !PROPERTIES_EDEFAULT.equals(properties);
			case StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS:
				return observations != null && !observations.isEmpty();
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
		result.append(", name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", encodingType: ");
		result.append(encodingType);
		result.append(", feature: ");
		result.append(feature);
		result.append(", properties: ");
		result.append(properties);
		result.append(')');
		return result.toString();
	}

} //FeatureOfInterestImpl
