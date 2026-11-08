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
import java.util.Date;

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
 * An implementation of the model object '<em><b>Historical Location</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl#getTime <em>Time</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl#getThing <em>Thing</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl#getLocations <em>Locations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HistoricalLocationImpl extends MinimalEObjectImpl.Container implements HistoricalLocation {
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
	 * The default value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected static final Date TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected Date time = TIME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getThing() <em>Thing</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThing()
	 * @generated
	 * @ordered
	 */
	protected Thing thing;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HistoricalLocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StaPackage.Literals.HISTORICAL_LOCATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.HISTORICAL_LOCATION__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getTime() {
		return time;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTime(Date newTime) {
		Date oldTime = time;
		time = newTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.HISTORICAL_LOCATION__TIME, oldTime, time));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Thing getThing() {
		if (thing != null && thing.eIsProxy()) {
			InternalEObject oldThing = (InternalEObject)thing;
			thing = (Thing)eResolveProxy(oldThing);
			if (thing != oldThing) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StaPackage.HISTORICAL_LOCATION__THING, oldThing, thing));
			}
		}
		return thing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Thing basicGetThing() {
		return thing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThing(Thing newThing, NotificationChain msgs) {
		Thing oldThing = thing;
		thing = newThing;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StaPackage.HISTORICAL_LOCATION__THING, oldThing, newThing);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setThing(Thing newThing) {
		if (newThing != thing) {
			NotificationChain msgs = null;
			if (thing != null)
				msgs = ((InternalEObject)thing).eInverseRemove(this, StaPackage.THING__HISTORICAL_LOCATIONS, Thing.class, msgs);
			if (newThing != null)
				msgs = ((InternalEObject)newThing).eInverseAdd(this, StaPackage.THING__HISTORICAL_LOCATIONS, Thing.class, msgs);
			msgs = basicSetThing(newThing, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.HISTORICAL_LOCATION__THING, newThing, newThing));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Location> getLocations() {
		if (locations == null) {
			locations = new EObjectWithInverseResolvingEList.ManyInverse<Location>(Location.class, this, StaPackage.HISTORICAL_LOCATION__LOCATIONS, StaPackage.LOCATION__HISTORICAL_LOCATIONS);
		}
		return locations;
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
			case StaPackage.HISTORICAL_LOCATION__THING:
				if (thing != null)
					msgs = ((InternalEObject)thing).eInverseRemove(this, StaPackage.THING__HISTORICAL_LOCATIONS, Thing.class, msgs);
				return basicSetThing((Thing)otherEnd, msgs);
			case StaPackage.HISTORICAL_LOCATION__LOCATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLocations()).basicAdd(otherEnd, msgs);
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
			case StaPackage.HISTORICAL_LOCATION__THING:
				return basicSetThing(null, msgs);
			case StaPackage.HISTORICAL_LOCATION__LOCATIONS:
				return ((InternalEList<?>)getLocations()).basicRemove(otherEnd, msgs);
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
			case StaPackage.HISTORICAL_LOCATION__ID:
				return getId();
			case StaPackage.HISTORICAL_LOCATION__TIME:
				return getTime();
			case StaPackage.HISTORICAL_LOCATION__THING:
				if (resolve) return getThing();
				return basicGetThing();
			case StaPackage.HISTORICAL_LOCATION__LOCATIONS:
				return getLocations();
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
			case StaPackage.HISTORICAL_LOCATION__ID:
				setId((String)newValue);
				return;
			case StaPackage.HISTORICAL_LOCATION__TIME:
				setTime((Date)newValue);
				return;
			case StaPackage.HISTORICAL_LOCATION__THING:
				setThing((Thing)newValue);
				return;
			case StaPackage.HISTORICAL_LOCATION__LOCATIONS:
				getLocations().clear();
				getLocations().addAll((Collection<? extends Location>)newValue);
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
			case StaPackage.HISTORICAL_LOCATION__ID:
				setId(ID_EDEFAULT);
				return;
			case StaPackage.HISTORICAL_LOCATION__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case StaPackage.HISTORICAL_LOCATION__THING:
				setThing((Thing)null);
				return;
			case StaPackage.HISTORICAL_LOCATION__LOCATIONS:
				getLocations().clear();
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
			case StaPackage.HISTORICAL_LOCATION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case StaPackage.HISTORICAL_LOCATION__TIME:
				return TIME_EDEFAULT == null ? time != null : !TIME_EDEFAULT.equals(time);
			case StaPackage.HISTORICAL_LOCATION__THING:
				return thing != null;
			case StaPackage.HISTORICAL_LOCATION__LOCATIONS:
				return locations != null && !locations.isEmpty();
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
		result.append(", time: ");
		result.append(time);
		result.append(')');
		return result.toString();
	}

} //HistoricalLocationImpl
