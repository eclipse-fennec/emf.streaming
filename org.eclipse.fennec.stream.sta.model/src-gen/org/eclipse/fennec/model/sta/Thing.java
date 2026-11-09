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
 * A representation of the model object '<em><b>Thing</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An object of the physical or information world: the target object of a stream mapping.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.Thing#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Thing#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Thing#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Thing#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Thing#getLocations <em>Locations</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Thing#getHistoricalLocations <em>Historical Locations</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.Thing#getDatastreams <em>Datastreams</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.sta.StaPackage#getThing()
 * @model
 * @generated
 */
@ProviderType
public interface Thing extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entity id (@iot.id).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getThing_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Thing#getId <em>Id</em>}' attribute.
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
	 * @see org.eclipse.fennec.model.sta.StaPackage#getThing_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Thing#getName <em>Name</em>}' attribute.
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
	 * @see org.eclipse.fennec.model.sta.StaPackage#getThing_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Thing#getDescription <em>Description</em>}' attribute.
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
	 * @see org.eclipse.fennec.model.sta.StaPackage#getThing_Properties()
	 * @model dataType="org.eclipse.fennec.model.sta.JsonObject"
	 * @generated
	 */
	String getProperties();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.Thing#getProperties <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' attribute.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(String value);

	/**
	 * Returns the value of the '<em><b>Locations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.sta.Location}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Location#getThings <em>Things</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Its current locations.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Locations</em>' reference list.
	 * @see org.eclipse.fennec.model.sta.StaPackage#getThing_Locations()
	 * @see org.eclipse.fennec.model.sta.Location#getThings
	 * @model opposite="Things"
	 * @generated
	 */
	EList<Location> getLocations();

	/**
	 * Returns the value of the '<em><b>Historical Locations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.sta.HistoricalLocation}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getThing <em>Thing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Historical Locations</em>' reference list.
	 * @see org.eclipse.fennec.model.sta.StaPackage#getThing_HistoricalLocations()
	 * @see org.eclipse.fennec.model.sta.HistoricalLocation#getThing
	 * @model opposite="Thing"
	 * @generated
	 */
	EList<HistoricalLocation> getHistoricalLocations();

	/**
	 * Returns the value of the '<em><b>Datastreams</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.sta.Datastream}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Datastream#getThing <em>Thing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Datastreams</em>' reference list.
	 * @see org.eclipse.fennec.model.sta.StaPackage#getThing_Datastreams()
	 * @see org.eclipse.fennec.model.sta.Datastream#getThing
	 * @model opposite="Thing"
	 * @generated
	 */
	EList<Datastream> getDatastreams();

} // Thing
