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
import org.eclipse.fennec.model.sta.ObservedProperty;
import org.eclipse.fennec.model.sta.StaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Observed Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl#getDefinition <em>Definition</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl#getDatastreams <em>Datastreams</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObservedPropertyImpl extends MinimalEObjectImpl.Container implements ObservedProperty {
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
	 * The default value of the '{@link #getDefinition() <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFINITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected String definition = DEFINITION_EDEFAULT;

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
	protected ObservedPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StaPackage.Literals.OBSERVED_PROPERTY;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVED_PROPERTY__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVED_PROPERTY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinition(String newDefinition) {
		String oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVED_PROPERTY__DEFINITION, oldDefinition, definition));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVED_PROPERTY__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVED_PROPERTY__PROPERTIES, oldProperties, properties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Datastream> getDatastreams() {
		if (datastreams == null) {
			datastreams = new EObjectWithInverseResolvingEList<Datastream>(Datastream.class, this, StaPackage.OBSERVED_PROPERTY__DATASTREAMS, StaPackage.DATASTREAM__OBSERVED_PROPERTY);
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
			case StaPackage.OBSERVED_PROPERTY__DATASTREAMS:
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
			case StaPackage.OBSERVED_PROPERTY__DATASTREAMS:
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
			case StaPackage.OBSERVED_PROPERTY__ID:
				return getId();
			case StaPackage.OBSERVED_PROPERTY__NAME:
				return getName();
			case StaPackage.OBSERVED_PROPERTY__DEFINITION:
				return getDefinition();
			case StaPackage.OBSERVED_PROPERTY__DESCRIPTION:
				return getDescription();
			case StaPackage.OBSERVED_PROPERTY__PROPERTIES:
				return getProperties();
			case StaPackage.OBSERVED_PROPERTY__DATASTREAMS:
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
			case StaPackage.OBSERVED_PROPERTY__ID:
				setId((Long)newValue);
				return;
			case StaPackage.OBSERVED_PROPERTY__NAME:
				setName((String)newValue);
				return;
			case StaPackage.OBSERVED_PROPERTY__DEFINITION:
				setDefinition((String)newValue);
				return;
			case StaPackage.OBSERVED_PROPERTY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case StaPackage.OBSERVED_PROPERTY__PROPERTIES:
				setProperties((String)newValue);
				return;
			case StaPackage.OBSERVED_PROPERTY__DATASTREAMS:
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
			case StaPackage.OBSERVED_PROPERTY__ID:
				setId(ID_EDEFAULT);
				return;
			case StaPackage.OBSERVED_PROPERTY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case StaPackage.OBSERVED_PROPERTY__DEFINITION:
				setDefinition(DEFINITION_EDEFAULT);
				return;
			case StaPackage.OBSERVED_PROPERTY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case StaPackage.OBSERVED_PROPERTY__PROPERTIES:
				setProperties(PROPERTIES_EDEFAULT);
				return;
			case StaPackage.OBSERVED_PROPERTY__DATASTREAMS:
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
			case StaPackage.OBSERVED_PROPERTY__ID:
				return id != ID_EDEFAULT;
			case StaPackage.OBSERVED_PROPERTY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case StaPackage.OBSERVED_PROPERTY__DEFINITION:
				return DEFINITION_EDEFAULT == null ? definition != null : !DEFINITION_EDEFAULT.equals(definition);
			case StaPackage.OBSERVED_PROPERTY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case StaPackage.OBSERVED_PROPERTY__PROPERTIES:
				return PROPERTIES_EDEFAULT == null ? properties != null : !PROPERTIES_EDEFAULT.equals(properties);
			case StaPackage.OBSERVED_PROPERTY__DATASTREAMS:
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
		result.append(", definition: ");
		result.append(definition);
		result.append(", description: ");
		result.append(description);
		result.append(", properties: ");
		result.append(properties);
		result.append(')');
		return result.toString();
	}

} //ObservedPropertyImpl
