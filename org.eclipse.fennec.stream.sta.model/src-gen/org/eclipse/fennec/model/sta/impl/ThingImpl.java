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

import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.HistoricalLocation;
import org.eclipse.fennec.model.sta.Location;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.model.sta.Thing;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Thing</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ThingImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ThingImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ThingImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ThingImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ThingImpl#getLocations <em>Locations</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ThingImpl#getHistoricalLocations <em>Historical Locations</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ThingImpl#getDatastreams <em>Datastreams</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ThingImpl extends MinimalEObjectImpl.Container implements Thing {
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
	 * The cached value of the '{@link #getLocations() <em>Locations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocations()
	 * @generated
	 * @ordered
	 */
	protected EList<Location> locations;

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
	 * The cached value of the '{@link #getDatastreams() <em>Datastreams</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDatastreams()
	 * @generated
	 * @ordered
	 */
	protected EList<Datastream> datastreams;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ThingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StaPackage.Literals.THING;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.THING__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.THING__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.THING__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.THING__PROPERTIES, oldProperties, properties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Location> getLocations() {
		if (locations == null) {
			locations = new EObjectWithInverseResolvingEList.ManyInverse<Location>(Location.class, this, StaPackage.THING__LOCATIONS, StaPackage.LOCATION__THINGS);
		}
		return locations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<HistoricalLocation> getHistoricalLocations() {
		if (historicalLocations == null) {
			historicalLocations = new EObjectWithInverseResolvingEList<HistoricalLocation>(HistoricalLocation.class, this, StaPackage.THING__HISTORICAL_LOCATIONS, StaPackage.HISTORICAL_LOCATION__THING);
		}
		return historicalLocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Datastream> getDatastreams() {
		if (datastreams == null) {
			datastreams = new EObjectWithInverseResolvingEList<Datastream>(Datastream.class, this, StaPackage.THING__DATASTREAMS, StaPackage.DATASTREAM__THING);
		}
		return datastreams;
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
			case StaPackage.THING__LOCATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLocations()).basicAdd(otherEnd, msgs);
			case StaPackage.THING__HISTORICAL_LOCATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getHistoricalLocations()).basicAdd(otherEnd, msgs);
			case StaPackage.THING__DATASTREAMS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDatastreams()).basicAdd(otherEnd, msgs);
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
			case StaPackage.THING__LOCATIONS:
				return ((InternalEList<?>)getLocations()).basicRemove(otherEnd, msgs);
			case StaPackage.THING__HISTORICAL_LOCATIONS:
				return ((InternalEList<?>)getHistoricalLocations()).basicRemove(otherEnd, msgs);
			case StaPackage.THING__DATASTREAMS:
				return ((InternalEList<?>)getDatastreams()).basicRemove(otherEnd, msgs);
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
			case StaPackage.THING__ID:
				return getId();
			case StaPackage.THING__NAME:
				return getName();
			case StaPackage.THING__DESCRIPTION:
				return getDescription();
			case StaPackage.THING__PROPERTIES:
				return getProperties();
			case StaPackage.THING__LOCATIONS:
				return getLocations();
			case StaPackage.THING__HISTORICAL_LOCATIONS:
				return getHistoricalLocations();
			case StaPackage.THING__DATASTREAMS:
				return getDatastreams();
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
			case StaPackage.THING__ID:
				setId((String)newValue);
				return;
			case StaPackage.THING__NAME:
				setName((String)newValue);
				return;
			case StaPackage.THING__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case StaPackage.THING__PROPERTIES:
				setProperties((String)newValue);
				return;
			case StaPackage.THING__LOCATIONS:
				getLocations().clear();
				getLocations().addAll((Collection<? extends Location>)newValue);
				return;
			case StaPackage.THING__HISTORICAL_LOCATIONS:
				getHistoricalLocations().clear();
				getHistoricalLocations().addAll((Collection<? extends HistoricalLocation>)newValue);
				return;
			case StaPackage.THING__DATASTREAMS:
				getDatastreams().clear();
				getDatastreams().addAll((Collection<? extends Datastream>)newValue);
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
			case StaPackage.THING__ID:
				setId(ID_EDEFAULT);
				return;
			case StaPackage.THING__NAME:
				setName(NAME_EDEFAULT);
				return;
			case StaPackage.THING__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case StaPackage.THING__PROPERTIES:
				setProperties(PROPERTIES_EDEFAULT);
				return;
			case StaPackage.THING__LOCATIONS:
				getLocations().clear();
				return;
			case StaPackage.THING__HISTORICAL_LOCATIONS:
				getHistoricalLocations().clear();
				return;
			case StaPackage.THING__DATASTREAMS:
				getDatastreams().clear();
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
			case StaPackage.THING__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case StaPackage.THING__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case StaPackage.THING__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case StaPackage.THING__PROPERTIES:
				return PROPERTIES_EDEFAULT == null ? properties != null : !PROPERTIES_EDEFAULT.equals(properties);
			case StaPackage.THING__LOCATIONS:
				return locations != null && !locations.isEmpty();
			case StaPackage.THING__HISTORICAL_LOCATIONS:
				return historicalLocations != null && !historicalLocations.isEmpty();
			case StaPackage.THING__DATASTREAMS:
				return datastreams != null && !datastreams.isEmpty();
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
		result.append(", properties: ");
		result.append(properties);
		result.append(')');
		return result.toString();
	}

} //ThingImpl
