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

import org.eclipse.fennec.model.sta.HistoricalLocation;
import org.eclipse.fennec.model.sta.Location;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.model.sta.Thing;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Location</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getEncodingType <em>Encoding Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getThings <em>Things</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.LocationImpl#getHistoricalLocations <em>Historical Locations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LocationImpl extends MinimalEObjectImpl.Container implements Location {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final long ID_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected long id = ID_EDEFAULT;

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
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected String location = LOCATION_EDEFAULT;

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
	 * The cached value of the '{@link #getThings() <em>Things</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThings()
	 * @generated
	 * @ordered
	 */
	protected EList<Thing> things;

	/**
	 * The cached value of the '{@link #getHistoricalLocations() <em>Historical Locations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistoricalLocations()
	 * @generated
	 * @ordered
	 */
	protected EList<HistoricalLocation> historicalLocations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StaPackage.Literals.LOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(long newId) {
		long oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.LOCATION__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.LOCATION__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.LOCATION__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.LOCATION__ENCODING_TYPE, oldEncodingType, encodingType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLocation(String newLocation) {
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.LOCATION__LOCATION, oldLocation, location));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.LOCATION__PROPERTIES, oldProperties, properties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Thing> getThings() {
		if (things == null) {
			things = new EObjectWithInverseResolvingEList.ManyInverse<Thing>(Thing.class, this, StaPackage.LOCATION__THINGS, StaPackage.THING__LOCATIONS);
		}
		return things;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<HistoricalLocation> getHistoricalLocations() {
		if (historicalLocations == null) {
			historicalLocations = new EObjectWithInverseResolvingEList.ManyInverse<HistoricalLocation>(HistoricalLocation.class, this, StaPackage.LOCATION__HISTORICAL_LOCATIONS, StaPackage.HISTORICAL_LOCATION__LOCATIONS);
		}
		return historicalLocations;
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
			case StaPackage.LOCATION__THINGS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getThings()).basicAdd(otherEnd, msgs);
			case StaPackage.LOCATION__HISTORICAL_LOCATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getHistoricalLocations()).basicAdd(otherEnd, msgs);
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
			case StaPackage.LOCATION__THINGS:
				return ((InternalEList<?>)getThings()).basicRemove(otherEnd, msgs);
			case StaPackage.LOCATION__HISTORICAL_LOCATIONS:
				return ((InternalEList<?>)getHistoricalLocations()).basicRemove(otherEnd, msgs);
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
			case StaPackage.LOCATION__ID:
				return getId();
			case StaPackage.LOCATION__NAME:
				return getName();
			case StaPackage.LOCATION__DESCRIPTION:
				return getDescription();
			case StaPackage.LOCATION__ENCODING_TYPE:
				return getEncodingType();
			case StaPackage.LOCATION__LOCATION:
				return getLocation();
			case StaPackage.LOCATION__PROPERTIES:
				return getProperties();
			case StaPackage.LOCATION__THINGS:
				return getThings();
			case StaPackage.LOCATION__HISTORICAL_LOCATIONS:
				return getHistoricalLocations();
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
			case StaPackage.LOCATION__ID:
				setId((Long)newValue);
				return;
			case StaPackage.LOCATION__NAME:
				setName((String)newValue);
				return;
			case StaPackage.LOCATION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case StaPackage.LOCATION__ENCODING_TYPE:
				setEncodingType((String)newValue);
				return;
			case StaPackage.LOCATION__LOCATION:
				setLocation((String)newValue);
				return;
			case StaPackage.LOCATION__PROPERTIES:
				setProperties((String)newValue);
				return;
			case StaPackage.LOCATION__THINGS:
				getThings().clear();
				getThings().addAll((Collection<? extends Thing>)newValue);
				return;
			case StaPackage.LOCATION__HISTORICAL_LOCATIONS:
				getHistoricalLocations().clear();
				getHistoricalLocations().addAll((Collection<? extends HistoricalLocation>)newValue);
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
			case StaPackage.LOCATION__ID:
				setId(ID_EDEFAULT);
				return;
			case StaPackage.LOCATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case StaPackage.LOCATION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case StaPackage.LOCATION__ENCODING_TYPE:
				setEncodingType(ENCODING_TYPE_EDEFAULT);
				return;
			case StaPackage.LOCATION__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case StaPackage.LOCATION__PROPERTIES:
				setProperties(PROPERTIES_EDEFAULT);
				return;
			case StaPackage.LOCATION__THINGS:
				getThings().clear();
				return;
			case StaPackage.LOCATION__HISTORICAL_LOCATIONS:
				getHistoricalLocations().clear();
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
			case StaPackage.LOCATION__ID:
				return id != ID_EDEFAULT;
			case StaPackage.LOCATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case StaPackage.LOCATION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case StaPackage.LOCATION__ENCODING_TYPE:
				return ENCODING_TYPE_EDEFAULT == null ? encodingType != null : !ENCODING_TYPE_EDEFAULT.equals(encodingType);
			case StaPackage.LOCATION__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case StaPackage.LOCATION__PROPERTIES:
				return PROPERTIES_EDEFAULT == null ? properties != null : !PROPERTIES_EDEFAULT.equals(properties);
			case StaPackage.LOCATION__THINGS:
				return things != null && !things.isEmpty();
			case StaPackage.LOCATION__HISTORICAL_LOCATIONS:
				return historicalLocations != null && !historicalLocations.isEmpty();
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
		result.append(", location: ");
		result.append(location);
		result.append(", properties: ");
		result.append(properties);
		result.append(')');
		return result.toString();
	}

} //LocationImpl
