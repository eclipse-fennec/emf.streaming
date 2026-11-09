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

import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.ObservedProperty;
import org.eclipse.fennec.model.sta.Sensor;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.model.sta.Thing;
import org.eclipse.fennec.model.sta.UnitOfMeasurement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Datastream</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getObservationType <em>Observation Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getUnitOfMeasurement <em>Unit Of Measurement</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getObservedArea <em>Observed Area</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getResultTimeStart <em>Result Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getResultTimeEnd <em>Result Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getThing <em>Thing</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getSensor <em>Sensor</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getObservedProperty <em>Observed Property</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl#getObservations <em>Observations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DatastreamImpl extends MinimalEObjectImpl.Container implements Datastream {
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
	 * The default value of the '{@link #getObservationType() <em>Observation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObservationType()
	 * @generated
	 * @ordered
	 */
	protected static final String OBSERVATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObservationType() <em>Observation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObservationType()
	 * @generated
	 * @ordered
	 */
	protected String observationType = OBSERVATION_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUnitOfMeasurement() <em>Unit Of Measurement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnitOfMeasurement()
	 * @generated
	 * @ordered
	 */
	protected UnitOfMeasurement unitOfMeasurement;

	/**
	 * The default value of the '{@link #getObservedArea() <em>Observed Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObservedArea()
	 * @generated
	 * @ordered
	 */
	protected static final String OBSERVED_AREA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObservedArea() <em>Observed Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObservedArea()
	 * @generated
	 * @ordered
	 */
	protected String observedArea = OBSERVED_AREA_EDEFAULT;

	/**
	 * The default value of the '{@link #getPhenomenonTimeStart() <em>Phenomenon Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhenomenonTimeStart()
	 * @generated
	 * @ordered
	 */
	protected static final Date PHENOMENON_TIME_START_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPhenomenonTimeStart() <em>Phenomenon Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhenomenonTimeStart()
	 * @generated
	 * @ordered
	 */
	protected Date phenomenonTimeStart = PHENOMENON_TIME_START_EDEFAULT;

	/**
	 * The default value of the '{@link #getPhenomenonTimeEnd() <em>Phenomenon Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhenomenonTimeEnd()
	 * @generated
	 * @ordered
	 */
	protected static final Date PHENOMENON_TIME_END_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPhenomenonTimeEnd() <em>Phenomenon Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhenomenonTimeEnd()
	 * @generated
	 * @ordered
	 */
	protected Date phenomenonTimeEnd = PHENOMENON_TIME_END_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultTimeStart() <em>Result Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultTimeStart()
	 * @generated
	 * @ordered
	 */
	protected static final Date RESULT_TIME_START_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultTimeStart() <em>Result Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultTimeStart()
	 * @generated
	 * @ordered
	 */
	protected Date resultTimeStart = RESULT_TIME_START_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultTimeEnd() <em>Result Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultTimeEnd()
	 * @generated
	 * @ordered
	 */
	protected static final Date RESULT_TIME_END_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultTimeEnd() <em>Result Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultTimeEnd()
	 * @generated
	 * @ordered
	 */
	protected Date resultTimeEnd = RESULT_TIME_END_EDEFAULT;

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
	 * The cached value of the '{@link #getThing() <em>Thing</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThing()
	 * @generated
	 * @ordered
	 */
	protected Thing thing;

	/**
	 * The cached value of the '{@link #getSensor() <em>Sensor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSensor()
	 * @generated
	 * @ordered
	 */
	protected Sensor sensor;

	/**
	 * The cached value of the '{@link #getObservedProperty() <em>Observed Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObservedProperty()
	 * @generated
	 * @ordered
	 */
	protected ObservedProperty observedProperty;

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
	protected DatastreamImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StaPackage.Literals.DATASTREAM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getObservationType() {
		return observationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObservationType(String newObservationType) {
		String oldObservationType = observationType;
		observationType = newObservationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__OBSERVATION_TYPE, oldObservationType, observationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnitOfMeasurement(UnitOfMeasurement newUnitOfMeasurement, NotificationChain msgs) {
		UnitOfMeasurement oldUnitOfMeasurement = unitOfMeasurement;
		unitOfMeasurement = newUnitOfMeasurement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT, oldUnitOfMeasurement, newUnitOfMeasurement);
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
	public void setUnitOfMeasurement(UnitOfMeasurement newUnitOfMeasurement) {
		if (newUnitOfMeasurement != unitOfMeasurement) {
			NotificationChain msgs = null;
			if (unitOfMeasurement != null)
				msgs = ((InternalEObject)unitOfMeasurement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT, null, msgs);
			if (newUnitOfMeasurement != null)
				msgs = ((InternalEObject)newUnitOfMeasurement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT, null, msgs);
			msgs = basicSetUnitOfMeasurement(newUnitOfMeasurement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT, newUnitOfMeasurement, newUnitOfMeasurement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getObservedArea() {
		return observedArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObservedArea(String newObservedArea) {
		String oldObservedArea = observedArea;
		observedArea = newObservedArea;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__OBSERVED_AREA, oldObservedArea, observedArea));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getPhenomenonTimeStart() {
		return phenomenonTimeStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPhenomenonTimeStart(Date newPhenomenonTimeStart) {
		Date oldPhenomenonTimeStart = phenomenonTimeStart;
		phenomenonTimeStart = newPhenomenonTimeStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__PHENOMENON_TIME_START, oldPhenomenonTimeStart, phenomenonTimeStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getPhenomenonTimeEnd() {
		return phenomenonTimeEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPhenomenonTimeEnd(Date newPhenomenonTimeEnd) {
		Date oldPhenomenonTimeEnd = phenomenonTimeEnd;
		phenomenonTimeEnd = newPhenomenonTimeEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__PHENOMENON_TIME_END, oldPhenomenonTimeEnd, phenomenonTimeEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getResultTimeStart() {
		return resultTimeStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultTimeStart(Date newResultTimeStart) {
		Date oldResultTimeStart = resultTimeStart;
		resultTimeStart = newResultTimeStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__RESULT_TIME_START, oldResultTimeStart, resultTimeStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getResultTimeEnd() {
		return resultTimeEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultTimeEnd(Date newResultTimeEnd) {
		Date oldResultTimeEnd = resultTimeEnd;
		resultTimeEnd = newResultTimeEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__RESULT_TIME_END, oldResultTimeEnd, resultTimeEnd));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__PROPERTIES, oldProperties, properties));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StaPackage.DATASTREAM__THING, oldThing, thing));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__THING, oldThing, newThing);
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
				msgs = ((InternalEObject)thing).eInverseRemove(this, StaPackage.THING__DATASTREAMS, Thing.class, msgs);
			if (newThing != null)
				msgs = ((InternalEObject)newThing).eInverseAdd(this, StaPackage.THING__DATASTREAMS, Thing.class, msgs);
			msgs = basicSetThing(newThing, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__THING, newThing, newThing));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Sensor getSensor() {
		if (sensor != null && sensor.eIsProxy()) {
			InternalEObject oldSensor = (InternalEObject)sensor;
			sensor = (Sensor)eResolveProxy(oldSensor);
			if (sensor != oldSensor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StaPackage.DATASTREAM__SENSOR, oldSensor, sensor));
			}
		}
		return sensor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sensor basicGetSensor() {
		return sensor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSensor(Sensor newSensor, NotificationChain msgs) {
		Sensor oldSensor = sensor;
		sensor = newSensor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__SENSOR, oldSensor, newSensor);
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
	public void setSensor(Sensor newSensor) {
		if (newSensor != sensor) {
			NotificationChain msgs = null;
			if (sensor != null)
				msgs = ((InternalEObject)sensor).eInverseRemove(this, StaPackage.SENSOR__DATASTREAMS, Sensor.class, msgs);
			if (newSensor != null)
				msgs = ((InternalEObject)newSensor).eInverseAdd(this, StaPackage.SENSOR__DATASTREAMS, Sensor.class, msgs);
			msgs = basicSetSensor(newSensor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__SENSOR, newSensor, newSensor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ObservedProperty getObservedProperty() {
		if (observedProperty != null && observedProperty.eIsProxy()) {
			InternalEObject oldObservedProperty = (InternalEObject)observedProperty;
			observedProperty = (ObservedProperty)eResolveProxy(oldObservedProperty);
			if (observedProperty != oldObservedProperty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StaPackage.DATASTREAM__OBSERVED_PROPERTY, oldObservedProperty, observedProperty));
			}
		}
		return observedProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObservedProperty basicGetObservedProperty() {
		return observedProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetObservedProperty(ObservedProperty newObservedProperty, NotificationChain msgs) {
		ObservedProperty oldObservedProperty = observedProperty;
		observedProperty = newObservedProperty;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__OBSERVED_PROPERTY, oldObservedProperty, newObservedProperty);
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
	public void setObservedProperty(ObservedProperty newObservedProperty) {
		if (newObservedProperty != observedProperty) {
			NotificationChain msgs = null;
			if (observedProperty != null)
				msgs = ((InternalEObject)observedProperty).eInverseRemove(this, StaPackage.OBSERVED_PROPERTY__DATASTREAMS, ObservedProperty.class, msgs);
			if (newObservedProperty != null)
				msgs = ((InternalEObject)newObservedProperty).eInverseAdd(this, StaPackage.OBSERVED_PROPERTY__DATASTREAMS, ObservedProperty.class, msgs);
			msgs = basicSetObservedProperty(newObservedProperty, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.DATASTREAM__OBSERVED_PROPERTY, newObservedProperty, newObservedProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Observation> getObservations() {
		if (observations == null) {
			observations = new EObjectWithInverseResolvingEList<Observation>(Observation.class, this, StaPackage.DATASTREAM__OBSERVATIONS, StaPackage.OBSERVATION__DATASTREAM);
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
			case StaPackage.DATASTREAM__THING:
				if (thing != null)
					msgs = ((InternalEObject)thing).eInverseRemove(this, StaPackage.THING__DATASTREAMS, Thing.class, msgs);
				return basicSetThing((Thing)otherEnd, msgs);
			case StaPackage.DATASTREAM__SENSOR:
				if (sensor != null)
					msgs = ((InternalEObject)sensor).eInverseRemove(this, StaPackage.SENSOR__DATASTREAMS, Sensor.class, msgs);
				return basicSetSensor((Sensor)otherEnd, msgs);
			case StaPackage.DATASTREAM__OBSERVED_PROPERTY:
				if (observedProperty != null)
					msgs = ((InternalEObject)observedProperty).eInverseRemove(this, StaPackage.OBSERVED_PROPERTY__DATASTREAMS, ObservedProperty.class, msgs);
				return basicSetObservedProperty((ObservedProperty)otherEnd, msgs);
			case StaPackage.DATASTREAM__OBSERVATIONS:
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
			case StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT:
				return basicSetUnitOfMeasurement(null, msgs);
			case StaPackage.DATASTREAM__THING:
				return basicSetThing(null, msgs);
			case StaPackage.DATASTREAM__SENSOR:
				return basicSetSensor(null, msgs);
			case StaPackage.DATASTREAM__OBSERVED_PROPERTY:
				return basicSetObservedProperty(null, msgs);
			case StaPackage.DATASTREAM__OBSERVATIONS:
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
			case StaPackage.DATASTREAM__ID:
				return getId();
			case StaPackage.DATASTREAM__NAME:
				return getName();
			case StaPackage.DATASTREAM__DESCRIPTION:
				return getDescription();
			case StaPackage.DATASTREAM__OBSERVATION_TYPE:
				return getObservationType();
			case StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT:
				return getUnitOfMeasurement();
			case StaPackage.DATASTREAM__OBSERVED_AREA:
				return getObservedArea();
			case StaPackage.DATASTREAM__PHENOMENON_TIME_START:
				return getPhenomenonTimeStart();
			case StaPackage.DATASTREAM__PHENOMENON_TIME_END:
				return getPhenomenonTimeEnd();
			case StaPackage.DATASTREAM__RESULT_TIME_START:
				return getResultTimeStart();
			case StaPackage.DATASTREAM__RESULT_TIME_END:
				return getResultTimeEnd();
			case StaPackage.DATASTREAM__PROPERTIES:
				return getProperties();
			case StaPackage.DATASTREAM__THING:
				if (resolve) return getThing();
				return basicGetThing();
			case StaPackage.DATASTREAM__SENSOR:
				if (resolve) return getSensor();
				return basicGetSensor();
			case StaPackage.DATASTREAM__OBSERVED_PROPERTY:
				if (resolve) return getObservedProperty();
				return basicGetObservedProperty();
			case StaPackage.DATASTREAM__OBSERVATIONS:
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
			case StaPackage.DATASTREAM__ID:
				setId((Long)newValue);
				return;
			case StaPackage.DATASTREAM__NAME:
				setName((String)newValue);
				return;
			case StaPackage.DATASTREAM__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case StaPackage.DATASTREAM__OBSERVATION_TYPE:
				setObservationType((String)newValue);
				return;
			case StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT:
				setUnitOfMeasurement((UnitOfMeasurement)newValue);
				return;
			case StaPackage.DATASTREAM__OBSERVED_AREA:
				setObservedArea((String)newValue);
				return;
			case StaPackage.DATASTREAM__PHENOMENON_TIME_START:
				setPhenomenonTimeStart((Date)newValue);
				return;
			case StaPackage.DATASTREAM__PHENOMENON_TIME_END:
				setPhenomenonTimeEnd((Date)newValue);
				return;
			case StaPackage.DATASTREAM__RESULT_TIME_START:
				setResultTimeStart((Date)newValue);
				return;
			case StaPackage.DATASTREAM__RESULT_TIME_END:
				setResultTimeEnd((Date)newValue);
				return;
			case StaPackage.DATASTREAM__PROPERTIES:
				setProperties((String)newValue);
				return;
			case StaPackage.DATASTREAM__THING:
				setThing((Thing)newValue);
				return;
			case StaPackage.DATASTREAM__SENSOR:
				setSensor((Sensor)newValue);
				return;
			case StaPackage.DATASTREAM__OBSERVED_PROPERTY:
				setObservedProperty((ObservedProperty)newValue);
				return;
			case StaPackage.DATASTREAM__OBSERVATIONS:
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
			case StaPackage.DATASTREAM__ID:
				setId(ID_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__OBSERVATION_TYPE:
				setObservationType(OBSERVATION_TYPE_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT:
				setUnitOfMeasurement((UnitOfMeasurement)null);
				return;
			case StaPackage.DATASTREAM__OBSERVED_AREA:
				setObservedArea(OBSERVED_AREA_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__PHENOMENON_TIME_START:
				setPhenomenonTimeStart(PHENOMENON_TIME_START_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__PHENOMENON_TIME_END:
				setPhenomenonTimeEnd(PHENOMENON_TIME_END_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__RESULT_TIME_START:
				setResultTimeStart(RESULT_TIME_START_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__RESULT_TIME_END:
				setResultTimeEnd(RESULT_TIME_END_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__PROPERTIES:
				setProperties(PROPERTIES_EDEFAULT);
				return;
			case StaPackage.DATASTREAM__THING:
				setThing((Thing)null);
				return;
			case StaPackage.DATASTREAM__SENSOR:
				setSensor((Sensor)null);
				return;
			case StaPackage.DATASTREAM__OBSERVED_PROPERTY:
				setObservedProperty((ObservedProperty)null);
				return;
			case StaPackage.DATASTREAM__OBSERVATIONS:
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
			case StaPackage.DATASTREAM__ID:
				return id != ID_EDEFAULT;
			case StaPackage.DATASTREAM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case StaPackage.DATASTREAM__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case StaPackage.DATASTREAM__OBSERVATION_TYPE:
				return OBSERVATION_TYPE_EDEFAULT == null ? observationType != null : !OBSERVATION_TYPE_EDEFAULT.equals(observationType);
			case StaPackage.DATASTREAM__UNIT_OF_MEASUREMENT:
				return unitOfMeasurement != null;
			case StaPackage.DATASTREAM__OBSERVED_AREA:
				return OBSERVED_AREA_EDEFAULT == null ? observedArea != null : !OBSERVED_AREA_EDEFAULT.equals(observedArea);
			case StaPackage.DATASTREAM__PHENOMENON_TIME_START:
				return PHENOMENON_TIME_START_EDEFAULT == null ? phenomenonTimeStart != null : !PHENOMENON_TIME_START_EDEFAULT.equals(phenomenonTimeStart);
			case StaPackage.DATASTREAM__PHENOMENON_TIME_END:
				return PHENOMENON_TIME_END_EDEFAULT == null ? phenomenonTimeEnd != null : !PHENOMENON_TIME_END_EDEFAULT.equals(phenomenonTimeEnd);
			case StaPackage.DATASTREAM__RESULT_TIME_START:
				return RESULT_TIME_START_EDEFAULT == null ? resultTimeStart != null : !RESULT_TIME_START_EDEFAULT.equals(resultTimeStart);
			case StaPackage.DATASTREAM__RESULT_TIME_END:
				return RESULT_TIME_END_EDEFAULT == null ? resultTimeEnd != null : !RESULT_TIME_END_EDEFAULT.equals(resultTimeEnd);
			case StaPackage.DATASTREAM__PROPERTIES:
				return PROPERTIES_EDEFAULT == null ? properties != null : !PROPERTIES_EDEFAULT.equals(properties);
			case StaPackage.DATASTREAM__THING:
				return thing != null;
			case StaPackage.DATASTREAM__SENSOR:
				return sensor != null;
			case StaPackage.DATASTREAM__OBSERVED_PROPERTY:
				return observedProperty != null;
			case StaPackage.DATASTREAM__OBSERVATIONS:
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
		result.append(", observationType: ");
		result.append(observationType);
		result.append(", observedArea: ");
		result.append(observedArea);
		result.append(", phenomenonTimeStart: ");
		result.append(phenomenonTimeStart);
		result.append(", phenomenonTimeEnd: ");
		result.append(phenomenonTimeEnd);
		result.append(", resultTimeStart: ");
		result.append(resultTimeStart);
		result.append(", resultTimeEnd: ");
		result.append(resultTimeEnd);
		result.append(", properties: ");
		result.append(properties);
		result.append(')');
		return result.toString();
	}

} //DatastreamImpl
