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
 * A representation of the model object '<em><b>Observed Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * What is observed: the target feature of a slot.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.ObservedProperty#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.ObservedProperty#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.ObservedProperty#getDefinition <em>Definition</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.ObservedProperty#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.ObservedProperty#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.ObservedProperty#getDatastreams <em>Datastreams</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.sta.StaPackage#getObservedProperty()
 * @model
 * @generated
 */
@ProviderType
public interface ObservedProperty extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entity id (@iot.id).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservedProperty_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.ObservedProperty#getId <em>Id</em>}' attribute.
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
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservedProperty_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.ObservedProperty#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A URI defining the property: the feature URI.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Definition</em>' attribute.
	 * @see #setDefinition(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservedProperty_Definition()
	 * @model required="true"
	 * @generated
	 */
	String getDefinition();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.ObservedProperty#getDefinition <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' attribute.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservedProperty_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.ObservedProperty#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Free key/value pairs (a JSON object).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' attribute.
	 * @see #setProperties(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservedProperty_Properties()
	 * @model dataType="org.eclipse.fennec.model.sta.JsonObject"
	 * @generated
	 */
	String getProperties();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.ObservedProperty#getProperties <em>Properties</em>}' attribute.
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
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Datastream#getObservedProperty <em>Observed Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Datastreams</em>' reference list.
	 * @see org.eclipse.fennec.model.sta.StaPackage#getObservedProperty_Datastreams()
	 * @see org.eclipse.fennec.model.sta.Datastream#getObservedProperty
	 * @model opposite="ObservedProperty"
	 * @generated
	 */
	EList<Datastream> getDatastreams();

} // ObservedProperty
