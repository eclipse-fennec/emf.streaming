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
package org.eclipse.fennec.model.sta;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Datastream</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Observations of one observed property of one thing by one sensor: a TIMESERIES slot of a target object.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getObservationType <em>Observation Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getUnitOfMeasurement <em>Unit Of Measurement</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getObservedArea <em>Observed Area</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getResultTimeStart <em>Result Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getResultTimeEnd <em>Result Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getThing <em>Thing</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getSensor <em>Sensor</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getObservedProperty <em>Observed Property</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Datastream#getObservations <em>Observations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream()
 * @model
 * @generated
 */
@ProviderType
public interface Datastream extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entity id (@iot.id).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(long value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Observation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An OGC observation type URI, e.g. OM_Measurement.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Observation Type</em>' attribute.
	 * @see #setObservationType(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_ObservationType()
	 * @model required="true"
	 * @generated
	 */
	String getObservationType();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getObservationType <em>Observation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Observation Type</em>' attribute.
	 * @see #getObservationType()
	 * @generated
	 */
	void setObservationType(String value);

	/**
	 * Returns the value of the '<em><b>Unit Of Measurement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unit Of Measurement</em>' containment reference.
	 * @see #setUnitOfMeasurement(UnitOfMeasurement)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_UnitOfMeasurement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	UnitOfMeasurement getUnitOfMeasurement();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getUnitOfMeasurement <em>Unit Of Measurement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit Of Measurement</em>' containment reference.
	 * @see #getUnitOfMeasurement()
	 * @generated
	 */
	void setUnitOfMeasurement(UnitOfMeasurement value);

	/**
	 * Returns the value of the '<em><b>Observed Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The area of its observations (GeoJSON).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Observed Area</em>' attribute.
	 * @see #setObservedArea(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_ObservedArea()
	 * @model dataType="org.eclipse.fennec.model.sta.GeoJson"
	 * @generated
	 */
	String getObservedArea();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getObservedArea <em>Observed Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Observed Area</em>' attribute.
	 * @see #getObservedArea()
	 * @generated
	 */
	void setObservedArea(String value);

	/**
	 * Returns the value of the '<em><b>Phenomenon Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Phenomenon Time Start</em>' attribute.
	 * @see #setPhenomenonTimeStart(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_PhenomenonTimeStart()
	 * @model
	 * @generated
	 */
	Date getPhenomenonTimeStart();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phenomenon Time Start</em>' attribute.
	 * @see #getPhenomenonTimeStart()
	 * @generated
	 */
	void setPhenomenonTimeStart(Date value);

	/**
	 * Returns the value of the '<em><b>Phenomenon Time End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Phenomenon Time End</em>' attribute.
	 * @see #setPhenomenonTimeEnd(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_PhenomenonTimeEnd()
	 * @model
	 * @generated
	 */
	Date getPhenomenonTimeEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phenomenon Time End</em>' attribute.
	 * @see #getPhenomenonTimeEnd()
	 * @generated
	 */
	void setPhenomenonTimeEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Result Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Time Start</em>' attribute.
	 * @see #setResultTimeStart(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_ResultTimeStart()
	 * @model
	 * @generated
	 */
	Date getResultTimeStart();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getResultTimeStart <em>Result Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Time Start</em>' attribute.
	 * @see #getResultTimeStart()
	 * @generated
	 */
	void setResultTimeStart(Date value);

	/**
	 * Returns the value of the '<em><b>Result Time End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Time End</em>' attribute.
	 * @see #setResultTimeEnd(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_ResultTimeEnd()
	 * @model
	 * @generated
	 */
	Date getResultTimeEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getResultTimeEnd <em>Result Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Time End</em>' attribute.
	 * @see #getResultTimeEnd()
	 * @generated
	 */
	void setResultTimeEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Free key/value pairs (a JSON object).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' attribute.
	 * @see #setProperties(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_Properties()
	 * @model dataType="org.eclipse.fennec.model.sta.JsonObject"
	 * @generated
	 */
	String getProperties();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getProperties <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' attribute.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(String value);

	/**
	 * Returns the value of the '<em><b>Thing</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Thing#getDatastreams <em>Datastreams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Thing</em>' reference.
	 * @see #setThing(Thing)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_Thing()
	 * @see org.eclipse.fennec.model.sta.Thing#getDatastreams
	 * @model opposite="Datastreams" required="true"
	 * @generated
	 */
	Thing getThing();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getThing <em>Thing</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thing</em>' reference.
	 * @see #getThing()
	 * @generated
	 */
	void setThing(Thing value);

	/**
	 * Returns the value of the '<em><b>Sensor</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Sensor#getDatastreams <em>Datastreams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Sensor</em>' reference.
	 * @see #setSensor(Sensor)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_Sensor()
	 * @see org.eclipse.fennec.model.sta.Sensor#getDatastreams
	 * @model opposite="Datastreams" required="true"
	 * @generated
	 */
	Sensor getSensor();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getSensor <em>Sensor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sensor</em>' reference.
	 * @see #getSensor()
	 * @generated
	 */
	void setSensor(Sensor value);

	/**
	 * Returns the value of the '<em><b>Observed Property</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.ObservedProperty#getDatastreams <em>Datastreams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Observed Property</em>' reference.
	 * @see #setObservedProperty(ObservedProperty)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_ObservedProperty()
	 * @see org.eclipse.fennec.model.sta.ObservedProperty#getDatastreams
	 * @model opposite="Datastreams" required="true"
	 * @generated
	 */
	ObservedProperty getObservedProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Datastream#getObservedProperty <em>Observed Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Observed Property</em>' reference.
	 * @see #getObservedProperty()
	 * @generated
	 */
	void setObservedProperty(ObservedProperty value);

	/**
	 * Returns the value of the '<em><b>Observations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.sta.Observation}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Observation#getDatastream <em>Datastream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Observations</em>' reference list.
	 * @see org.eclipse.fennec.model.sta.StaPackage#getDatastream_Observations()
	 * @see org.eclipse.fennec.model.sta.Observation#getDatastream
	 * @model opposite="Datastream"
	 * @generated
	 */
	EList<Observation> getObservations();

} // Datastream
