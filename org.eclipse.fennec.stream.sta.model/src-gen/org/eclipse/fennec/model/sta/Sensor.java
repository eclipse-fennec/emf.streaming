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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sensor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * What observes: the source class of a slot.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.Sensor#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Sensor#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Sensor#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Sensor#getEncodingType <em>Encoding Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Sensor#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Sensor#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Sensor#getDatastreams <em>Datastreams</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor()
 * @model
 * @generated
 */
@ProviderType
public interface Sensor extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entity id (@iot.id).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Sensor#getId <em>Id</em>}' attribute.
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
	 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Sensor#getName <em>Name</em>}' attribute.
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
	 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Sensor#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Encoding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Encoding of metadata.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Encoding Type</em>' attribute.
	 * @see #setEncodingType(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor_EncodingType()
	 * @model required="true"
	 * @generated
	 */
	String getEncodingType();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Sensor#getEncodingType <em>Encoding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encoding Type</em>' attribute.
	 * @see #getEncodingType()
	 * @generated
	 */
	void setEncodingType(String value);

	/**
	 * Returns the value of the '<em><b>Metadata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A description of the sensor, or where to find one.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Metadata</em>' attribute.
	 * @see #setMetadata(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor_Metadata()
	 * @model required="true"
	 * @generated
	 */
	String getMetadata();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Sensor#getMetadata <em>Metadata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metadata</em>' attribute.
	 * @see #getMetadata()
	 * @generated
	 */
	void setMetadata(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Free key/value pairs (a JSON object).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' attribute.
	 * @see #setProperties(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor_Properties()
	 * @model dataType="org.eclipse.fennec.model.sta.JsonObject"
	 * @generated
	 */
	String getProperties();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Sensor#getProperties <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' attribute.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(String value);

	/**
	 * Returns the value of the '<em><b>Datastreams</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.sta.Datastream}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Datastream#getSensor <em>Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Datastreams</em>' reference list.
	 * @see org.eclipse.fennec.model.sta.StaPackage#getSensor_Datastreams()
	 * @see org.eclipse.fennec.model.sta.Datastream#getSensor
	 * @model opposite="Sensor"
	 * @generated
	 */
	EList<Datastream> getDatastreams();

} // Sensor
