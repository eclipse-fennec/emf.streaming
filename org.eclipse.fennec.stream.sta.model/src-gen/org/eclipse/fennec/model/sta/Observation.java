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

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Observation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * One value of a datastream: a SET entry of a TIMESERIES slot.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getResultTime <em>Result Time</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getResultNumber <em>Result Number</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getResultBoolean <em>Result Boolean</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getResultText <em>Result Text</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getResultQuality <em>Result Quality</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getValidTimeStart <em>Valid Time Start</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getValidTimeEnd <em>Valid Time End</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getDatastream <em>Datastream</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Observation#getFeatureOfInterest <em>Feature Of Interest</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation()
 * @model
 * @generated
 */
@ProviderType
public interface Observation extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entity id (@iot.id).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Phenomenon Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * When the value was observed: the entry time, else the change set time.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Phenomenon Time Start</em>' attribute.
	 * @see #setPhenomenonTimeStart(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_PhenomenonTimeStart()
	 * @model required="true"
	 *        annotation="https://eclipse.org/fennec/sta/wire interval='phenomenonTime' part='start'"
	 * @generated
	 */
	Date getPhenomenonTimeStart();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}' attribute.
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
	 * Set for an interval.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Phenomenon Time End</em>' attribute.
	 * @see #setPhenomenonTimeEnd(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_PhenomenonTimeEnd()
	 * @model annotation="https://eclipse.org/fennec/sta/wire interval='phenomenonTime' part='end'"
	 * @generated
	 */
	Date getPhenomenonTimeEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phenomenon Time End</em>' attribute.
	 * @see #getPhenomenonTimeEnd()
	 * @generated
	 */
	void setPhenomenonTimeEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Result Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * When the result was produced: the change set time.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Time</em>' attribute.
	 * @see #setResultTime(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_ResultTime()
	 * @model
	 * @generated
	 */
	Date getResultTime();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getResultTime <em>Result Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Time</em>' attribute.
	 * @see #getResultTime()
	 * @generated
	 */
	void setResultTime(Date value);

	/**
	 * Returns the value of the '<em><b>Result Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Number</em>' attribute.
	 * @see #setResultNumber(Double)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_ResultNumber()
	 * @model annotation="https://eclipse.org/fennec/sta/wire name='result'"
	 * @generated
	 */
	Double getResultNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getResultNumber <em>Result Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Number</em>' attribute.
	 * @see #getResultNumber()
	 * @generated
	 */
	void setResultNumber(Double value);

	/**
	 * Returns the value of the '<em><b>Result Boolean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Boolean</em>' attribute.
	 * @see #setResultBoolean(Boolean)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_ResultBoolean()
	 * @model annotation="https://eclipse.org/fennec/sta/wire name='result'"
	 * @generated
	 */
	Boolean getResultBoolean();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getResultBoolean <em>Result Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Boolean</em>' attribute.
	 * @see #getResultBoolean()
	 * @generated
	 */
	void setResultBoolean(Boolean value);

	/**
	 * Returns the value of the '<em><b>Result Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Text</em>' attribute.
	 * @see #setResultText(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_ResultText()
	 * @model annotation="https://eclipse.org/fennec/sta/wire name='result'"
	 * @generated
	 */
	String getResultText();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getResultText <em>Result Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Text</em>' attribute.
	 * @see #getResultText()
	 * @generated
	 */
	void setResultText(String value);

	/**
	 * Returns the value of the '<em><b>Result Quality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Quality</em>' attribute.
	 * @see #setResultQuality(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_ResultQuality()
	 * @model dataType="org.eclipse.fennec.model.sta.JsonObject"
	 * @generated
	 */
	String getResultQuality();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getResultQuality <em>Result Quality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Quality</em>' attribute.
	 * @see #getResultQuality()
	 * @generated
	 */
	void setResultQuality(String value);

	/**
	 * Returns the value of the '<em><b>Valid Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Valid Time Start</em>' attribute.
	 * @see #setValidTimeStart(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_ValidTimeStart()
	 * @model annotation="https://eclipse.org/fennec/sta/wire interval='validTime' part='start'"
	 * @generated
	 */
	Date getValidTimeStart();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getValidTimeStart <em>Valid Time Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid Time Start</em>' attribute.
	 * @see #getValidTimeStart()
	 * @generated
	 */
	void setValidTimeStart(Date value);

	/**
	 * Returns the value of the '<em><b>Valid Time End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Valid Time End</em>' attribute.
	 * @see #setValidTimeEnd(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_ValidTimeEnd()
	 * @model annotation="https://eclipse.org/fennec/sta/wire interval='validTime' part='end'"
	 * @generated
	 */
	Date getValidTimeEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getValidTimeEnd <em>Valid Time End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid Time End</em>' attribute.
	 * @see #getValidTimeEnd()
	 * @generated
	 */
	void setValidTimeEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute.
	 * @see #setParameters(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_Parameters()
	 * @model dataType="org.eclipse.fennec.model.sta.JsonObject"
	 * @generated
	 */
	String getParameters();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getParameters <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameters</em>' attribute.
	 * @see #getParameters()
	 * @generated
	 */
	void setParameters(String value);

	/**
	 * Returns the value of the '<em><b>Datastream</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Datastream#getObservations <em>Observations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Datastream</em>' reference.
	 * @see #setDatastream(Datastream)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_Datastream()
	 * @see org.eclipse.fennec.model.sta.Datastream#getObservations
	 * @model opposite="observations" required="true"
	 * @generated
	 */
	Datastream getDatastream();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getDatastream <em>Datastream</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Datastream</em>' reference.
	 * @see #getDatastream()
	 * @generated
	 */
	void setDatastream(Datastream value);

	/**
	 * Returns the value of the '<em><b>Feature Of Interest</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getObservations <em>Observations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Feature Of Interest</em>' reference.
	 * @see #setFeatureOfInterest(FeatureOfInterest)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservation_FeatureOfInterest()
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getObservations
	 * @model opposite="observations" required="true"
	 * @generated
	 */
	FeatureOfInterest getFeatureOfInterest();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Observation#getFeatureOfInterest <em>Feature Of Interest</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Of Interest</em>' reference.
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	void setFeatureOfInterest(FeatureOfInterest value);

} // Observation
