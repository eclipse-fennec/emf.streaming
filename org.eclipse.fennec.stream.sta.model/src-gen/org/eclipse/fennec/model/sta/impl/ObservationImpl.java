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

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.FeatureOfInterest;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.StaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Observation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getResultTime <em>Result Time</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getResultNumber <em>Result Number</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getResultBoolean <em>Result Boolean</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getResultText <em>Result Text</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getResultQuality <em>Result Quality</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getValidTimeStart <em>Valid Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getValidTimeEnd <em>Valid Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getDatastream <em>Datastream</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.impl.ObservationImpl#getFeatureOfInterest <em>Feature Of Interest</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObservationImpl extends MinimalEObjectImpl.Container implements Observation {
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
	 * The default value of the '{@link #getResultTime() <em>Result Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultTime()
	 * @generated
	 * @ordered
	 */
	protected static final Date RESULT_TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultTime() <em>Result Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultTime()
	 * @generated
	 * @ordered
	 */
	protected Date resultTime = RESULT_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultNumber() <em>Result Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultNumber()
	 * @generated
	 * @ordered
	 */
	protected static final Double RESULT_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultNumber() <em>Result Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultNumber()
	 * @generated
	 * @ordered
	 */
	protected Double resultNumber = RESULT_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultBoolean() <em>Result Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultBoolean()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean RESULT_BOOLEAN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultBoolean() <em>Result Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultBoolean()
	 * @generated
	 * @ordered
	 */
	protected Boolean resultBoolean = RESULT_BOOLEAN_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultText() <em>Result Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultText()
	 * @generated
	 * @ordered
	 */
	protected static final String RESULT_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultText() <em>Result Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultText()
	 * @generated
	 * @ordered
	 */
	protected String resultText = RESULT_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultQuality() <em>Result Quality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultQuality()
	 * @generated
	 * @ordered
	 */
	protected static final String RESULT_QUALITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultQuality() <em>Result Quality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultQuality()
	 * @generated
	 * @ordered
	 */
	protected String resultQuality = RESULT_QUALITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getValidTimeStart() <em>Valid Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidTimeStart()
	 * @generated
	 * @ordered
	 */
	protected static final Date VALID_TIME_START_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidTimeStart() <em>Valid Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidTimeStart()
	 * @generated
	 * @ordered
	 */
	protected Date validTimeStart = VALID_TIME_START_EDEFAULT;

	/**
	 * The default value of the '{@link #getValidTimeEnd() <em>Valid Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidTimeEnd()
	 * @generated
	 * @ordered
	 */
	protected static final Date VALID_TIME_END_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidTimeEnd() <em>Valid Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidTimeEnd()
	 * @generated
	 * @ordered
	 */
	protected Date validTimeEnd = VALID_TIME_END_EDEFAULT;

	/**
	 * The default value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected static final String PARAMETERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected String parameters = PARAMETERS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDatastream() <em>Datastream</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDatastream()
	 * @generated
	 * @ordered
	 */
	protected Datastream datastream;

	/**
	 * The cached value of the '{@link #getFeatureOfInterest() <em>Feature Of Interest</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureOfInterest()
	 * @generated
	 * @ordered
	 */
	protected FeatureOfInterest featureOfInterest;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObservationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StaPackage.Literals.OBSERVATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__PHENOMENON_TIME_START, oldPhenomenonTimeStart, phenomenonTimeStart));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__PHENOMENON_TIME_END, oldPhenomenonTimeEnd, phenomenonTimeEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getResultTime() {
		return resultTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultTime(Date newResultTime) {
		Date oldResultTime = resultTime;
		resultTime = newResultTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__RESULT_TIME, oldResultTime, resultTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getResultNumber() {
		return resultNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultNumber(Double newResultNumber) {
		Double oldResultNumber = resultNumber;
		resultNumber = newResultNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__RESULT_NUMBER, oldResultNumber, resultNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getResultBoolean() {
		return resultBoolean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultBoolean(Boolean newResultBoolean) {
		Boolean oldResultBoolean = resultBoolean;
		resultBoolean = newResultBoolean;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__RESULT_BOOLEAN, oldResultBoolean, resultBoolean));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getResultText() {
		return resultText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultText(String newResultText) {
		String oldResultText = resultText;
		resultText = newResultText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__RESULT_TEXT, oldResultText, resultText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getResultQuality() {
		return resultQuality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultQuality(String newResultQuality) {
		String oldResultQuality = resultQuality;
		resultQuality = newResultQuality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__RESULT_QUALITY, oldResultQuality, resultQuality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getValidTimeStart() {
		return validTimeStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValidTimeStart(Date newValidTimeStart) {
		Date oldValidTimeStart = validTimeStart;
		validTimeStart = newValidTimeStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__VALID_TIME_START, oldValidTimeStart, validTimeStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getValidTimeEnd() {
		return validTimeEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValidTimeEnd(Date newValidTimeEnd) {
		Date oldValidTimeEnd = validTimeEnd;
		validTimeEnd = newValidTimeEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__VALID_TIME_END, oldValidTimeEnd, validTimeEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getParameters() {
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParameters(String newParameters) {
		String oldParameters = parameters;
		parameters = newParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__PARAMETERS, oldParameters, parameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Datastream getDatastream() {
		if (datastream != null && datastream.eIsProxy()) {
			InternalEObject oldDatastream = (InternalEObject)datastream;
			datastream = (Datastream)eResolveProxy(oldDatastream);
			if (datastream != oldDatastream) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StaPackage.OBSERVATION__DATASTREAM, oldDatastream, datastream));
			}
		}
		return datastream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Datastream basicGetDatastream() {
		return datastream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDatastream(Datastream newDatastream, NotificationChain msgs) {
		Datastream oldDatastream = datastream;
		datastream = newDatastream;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__DATASTREAM, oldDatastream, newDatastream);
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
	public void setDatastream(Datastream newDatastream) {
		if (newDatastream != datastream) {
			NotificationChain msgs = null;
			if (datastream != null)
				msgs = ((InternalEObject)datastream).eInverseRemove(this, StaPackage.DATASTREAM__OBSERVATIONS, Datastream.class, msgs);
			if (newDatastream != null)
				msgs = ((InternalEObject)newDatastream).eInverseAdd(this, StaPackage.DATASTREAM__OBSERVATIONS, Datastream.class, msgs);
			msgs = basicSetDatastream(newDatastream, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__DATASTREAM, newDatastream, newDatastream));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureOfInterest getFeatureOfInterest() {
		if (featureOfInterest != null && featureOfInterest.eIsProxy()) {
			InternalEObject oldFeatureOfInterest = (InternalEObject)featureOfInterest;
			featureOfInterest = (FeatureOfInterest)eResolveProxy(oldFeatureOfInterest);
			if (featureOfInterest != oldFeatureOfInterest) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StaPackage.OBSERVATION__FEATURE_OF_INTEREST, oldFeatureOfInterest, featureOfInterest));
			}
		}
		return featureOfInterest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureOfInterest basicGetFeatureOfInterest() {
		return featureOfInterest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatureOfInterest(FeatureOfInterest newFeatureOfInterest, NotificationChain msgs) {
		FeatureOfInterest oldFeatureOfInterest = featureOfInterest;
		featureOfInterest = newFeatureOfInterest;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__FEATURE_OF_INTEREST, oldFeatureOfInterest, newFeatureOfInterest);
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
	public void setFeatureOfInterest(FeatureOfInterest newFeatureOfInterest) {
		if (newFeatureOfInterest != featureOfInterest) {
			NotificationChain msgs = null;
			if (featureOfInterest != null)
				msgs = ((InternalEObject)featureOfInterest).eInverseRemove(this, StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS, FeatureOfInterest.class, msgs);
			if (newFeatureOfInterest != null)
				msgs = ((InternalEObject)newFeatureOfInterest).eInverseAdd(this, StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS, FeatureOfInterest.class, msgs);
			msgs = basicSetFeatureOfInterest(newFeatureOfInterest, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StaPackage.OBSERVATION__FEATURE_OF_INTEREST, newFeatureOfInterest, newFeatureOfInterest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StaPackage.OBSERVATION__DATASTREAM:
				if (datastream != null)
					msgs = ((InternalEObject)datastream).eInverseRemove(this, StaPackage.DATASTREAM__OBSERVATIONS, Datastream.class, msgs);
				return basicSetDatastream((Datastream)otherEnd, msgs);
			case StaPackage.OBSERVATION__FEATURE_OF_INTEREST:
				if (featureOfInterest != null)
					msgs = ((InternalEObject)featureOfInterest).eInverseRemove(this, StaPackage.FEATURE_OF_INTEREST__OBSERVATIONS, FeatureOfInterest.class, msgs);
				return basicSetFeatureOfInterest((FeatureOfInterest)otherEnd, msgs);
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
			case StaPackage.OBSERVATION__DATASTREAM:
				return basicSetDatastream(null, msgs);
			case StaPackage.OBSERVATION__FEATURE_OF_INTEREST:
				return basicSetFeatureOfInterest(null, msgs);
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
			case StaPackage.OBSERVATION__ID:
				return getId();
			case StaPackage.OBSERVATION__PHENOMENON_TIME_START:
				return getPhenomenonTimeStart();
			case StaPackage.OBSERVATION__PHENOMENON_TIME_END:
				return getPhenomenonTimeEnd();
			case StaPackage.OBSERVATION__RESULT_TIME:
				return getResultTime();
			case StaPackage.OBSERVATION__RESULT_NUMBER:
				return getResultNumber();
			case StaPackage.OBSERVATION__RESULT_BOOLEAN:
				return getResultBoolean();
			case StaPackage.OBSERVATION__RESULT_TEXT:
				return getResultText();
			case StaPackage.OBSERVATION__RESULT_QUALITY:
				return getResultQuality();
			case StaPackage.OBSERVATION__VALID_TIME_START:
				return getValidTimeStart();
			case StaPackage.OBSERVATION__VALID_TIME_END:
				return getValidTimeEnd();
			case StaPackage.OBSERVATION__PARAMETERS:
				return getParameters();
			case StaPackage.OBSERVATION__DATASTREAM:
				if (resolve) return getDatastream();
				return basicGetDatastream();
			case StaPackage.OBSERVATION__FEATURE_OF_INTEREST:
				if (resolve) return getFeatureOfInterest();
				return basicGetFeatureOfInterest();
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
			case StaPackage.OBSERVATION__ID:
				setId((String)newValue);
				return;
			case StaPackage.OBSERVATION__PHENOMENON_TIME_START:
				setPhenomenonTimeStart((Date)newValue);
				return;
			case StaPackage.OBSERVATION__PHENOMENON_TIME_END:
				setPhenomenonTimeEnd((Date)newValue);
				return;
			case StaPackage.OBSERVATION__RESULT_TIME:
				setResultTime((Date)newValue);
				return;
			case StaPackage.OBSERVATION__RESULT_NUMBER:
				setResultNumber((Double)newValue);
				return;
			case StaPackage.OBSERVATION__RESULT_BOOLEAN:
				setResultBoolean((Boolean)newValue);
				return;
			case StaPackage.OBSERVATION__RESULT_TEXT:
				setResultText((String)newValue);
				return;
			case StaPackage.OBSERVATION__RESULT_QUALITY:
				setResultQuality((String)newValue);
				return;
			case StaPackage.OBSERVATION__VALID_TIME_START:
				setValidTimeStart((Date)newValue);
				return;
			case StaPackage.OBSERVATION__VALID_TIME_END:
				setValidTimeEnd((Date)newValue);
				return;
			case StaPackage.OBSERVATION__PARAMETERS:
				setParameters((String)newValue);
				return;
			case StaPackage.OBSERVATION__DATASTREAM:
				setDatastream((Datastream)newValue);
				return;
			case StaPackage.OBSERVATION__FEATURE_OF_INTEREST:
				setFeatureOfInterest((FeatureOfInterest)newValue);
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
			case StaPackage.OBSERVATION__ID:
				setId(ID_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__PHENOMENON_TIME_START:
				setPhenomenonTimeStart(PHENOMENON_TIME_START_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__PHENOMENON_TIME_END:
				setPhenomenonTimeEnd(PHENOMENON_TIME_END_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__RESULT_TIME:
				setResultTime(RESULT_TIME_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__RESULT_NUMBER:
				setResultNumber(RESULT_NUMBER_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__RESULT_BOOLEAN:
				setResultBoolean(RESULT_BOOLEAN_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__RESULT_TEXT:
				setResultText(RESULT_TEXT_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__RESULT_QUALITY:
				setResultQuality(RESULT_QUALITY_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__VALID_TIME_START:
				setValidTimeStart(VALID_TIME_START_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__VALID_TIME_END:
				setValidTimeEnd(VALID_TIME_END_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__PARAMETERS:
				setParameters(PARAMETERS_EDEFAULT);
				return;
			case StaPackage.OBSERVATION__DATASTREAM:
				setDatastream((Datastream)null);
				return;
			case StaPackage.OBSERVATION__FEATURE_OF_INTEREST:
				setFeatureOfInterest((FeatureOfInterest)null);
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
			case StaPackage.OBSERVATION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case StaPackage.OBSERVATION__PHENOMENON_TIME_START:
				return PHENOMENON_TIME_START_EDEFAULT == null ? phenomenonTimeStart != null : !PHENOMENON_TIME_START_EDEFAULT.equals(phenomenonTimeStart);
			case StaPackage.OBSERVATION__PHENOMENON_TIME_END:
				return PHENOMENON_TIME_END_EDEFAULT == null ? phenomenonTimeEnd != null : !PHENOMENON_TIME_END_EDEFAULT.equals(phenomenonTimeEnd);
			case StaPackage.OBSERVATION__RESULT_TIME:
				return RESULT_TIME_EDEFAULT == null ? resultTime != null : !RESULT_TIME_EDEFAULT.equals(resultTime);
			case StaPackage.OBSERVATION__RESULT_NUMBER:
				return RESULT_NUMBER_EDEFAULT == null ? resultNumber != null : !RESULT_NUMBER_EDEFAULT.equals(resultNumber);
			case StaPackage.OBSERVATION__RESULT_BOOLEAN:
				return RESULT_BOOLEAN_EDEFAULT == null ? resultBoolean != null : !RESULT_BOOLEAN_EDEFAULT.equals(resultBoolean);
			case StaPackage.OBSERVATION__RESULT_TEXT:
				return RESULT_TEXT_EDEFAULT == null ? resultText != null : !RESULT_TEXT_EDEFAULT.equals(resultText);
			case StaPackage.OBSERVATION__RESULT_QUALITY:
				return RESULT_QUALITY_EDEFAULT == null ? resultQuality != null : !RESULT_QUALITY_EDEFAULT.equals(resultQuality);
			case StaPackage.OBSERVATION__VALID_TIME_START:
				return VALID_TIME_START_EDEFAULT == null ? validTimeStart != null : !VALID_TIME_START_EDEFAULT.equals(validTimeStart);
			case StaPackage.OBSERVATION__VALID_TIME_END:
				return VALID_TIME_END_EDEFAULT == null ? validTimeEnd != null : !VALID_TIME_END_EDEFAULT.equals(validTimeEnd);
			case StaPackage.OBSERVATION__PARAMETERS:
				return PARAMETERS_EDEFAULT == null ? parameters != null : !PARAMETERS_EDEFAULT.equals(parameters);
			case StaPackage.OBSERVATION__DATASTREAM:
				return datastream != null;
			case StaPackage.OBSERVATION__FEATURE_OF_INTEREST:
				return featureOfInterest != null;
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
		result.append(", phenomenonTimeStart: ");
		result.append(phenomenonTimeStart);
		result.append(", phenomenonTimeEnd: ");
		result.append(phenomenonTimeEnd);
		result.append(", resultTime: ");
		result.append(resultTime);
		result.append(", resultNumber: ");
		result.append(resultNumber);
		result.append(", resultBoolean: ");
		result.append(resultBoolean);
		result.append(", resultText: ");
		result.append(resultText);
		result.append(", resultQuality: ");
		result.append(resultQuality);
		result.append(", validTimeStart: ");
		result.append(validTimeStart);
		result.append(", validTimeEnd: ");
		result.append(validTimeEnd);
		result.append(", parameters: ");
		result.append(parameters);
		result.append(')');
		return result.toString();
	}

} //ObservationImpl
