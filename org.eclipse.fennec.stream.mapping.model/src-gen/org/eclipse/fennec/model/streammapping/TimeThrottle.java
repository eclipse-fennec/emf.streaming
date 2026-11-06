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
package org.eclipse.fennec.model.streammapping;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Throttle</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Passes at most once per interval, measured on the event time of the values.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.TimeThrottle#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.TimeThrottle#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getTimeThrottle()
 * @model
 * @generated
 */
@ProviderType
public interface TimeThrottle extends ChangeRule {
	/**
	 * Returns the value of the '<em><b>Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The interval.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Interval</em>' attribute.
	 * @see #setInterval(long)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getTimeThrottle_Interval()
	 * @model required="true"
	 * @generated
	 */
	long getInterval();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.TimeThrottle#getInterval <em>Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interval</em>' attribute.
	 * @see #getInterval()
	 * @generated
	 */
	void setInterval(long value);

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * The default value is <code>"SECONDS"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.DurationUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Its unit.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #setUnit(DurationUnit)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getTimeThrottle_Unit()
	 * @model default="SECONDS"
	 * @generated
	 */
	DurationUnit getUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.TimeThrottle#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(DurationUnit value);

} // TimeThrottle
