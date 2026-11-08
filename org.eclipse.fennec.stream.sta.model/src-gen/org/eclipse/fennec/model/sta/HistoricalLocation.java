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
 * A representation of the model object '<em><b>Historical Location</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Where a thing was, since a time.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.sta.HistoricalLocation#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.HistoricalLocation#getTime <em>Time</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.HistoricalLocation#getThing <em>Thing</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.sta.HistoricalLocation#getLocations <em>Locations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.sta.StaPackage#getHistoricalLocation()
 * @model
 * @generated
 */
@ProviderType
public interface HistoricalLocation extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entity id (@iot.id).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getHistoricalLocation_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(Date)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getHistoricalLocation_Time()
	 * @model required="true"
	 * @generated
	 */
	Date getTime();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(Date value);

	/**
	 * Returns the value of the '<em><b>Thing</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Thing#getHistoricalLocations <em>Historical Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Thing</em>' reference.
	 * @see #setThing(Thing)
	 * @see org.eclipse.fennec.model.sta.StaPackage#getHistoricalLocation_Thing()
	 * @see org.eclipse.fennec.model.sta.Thing#getHistoricalLocations
	 * @model opposite="HistoricalLocations" required="true"
	 * @generated
	 */
	Thing getThing();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getThing <em>Thing</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thing</em>' reference.
	 * @see #getThing()
	 * @generated
	 */
	void setThing(Thing value);

	/**
	 * Returns the value of the '<em><b>Locations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.sta.Location}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.sta.Location#getHistoricalLocations <em>Historical Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Locations</em>' reference list.
	 * @see org.eclipse.fennec.model.sta.StaPackage#getHistoricalLocation_Locations()
	 * @see org.eclipse.fennec.model.sta.Location#getHistoricalLocations
	 * @model opposite="HistoricalLocations"
	 * @generated
	 */
	EList<Location> getLocations();

} // HistoricalLocation
