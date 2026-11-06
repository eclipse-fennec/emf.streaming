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
 * A representation of the model object '<em><b>Percentage Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Passes when |value - last| / |last| * 100 >= percent (numeric values; a last value of 0 passes any change).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.PercentageChange#getPercent <em>Percent</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getPercentageChange()
 * @model
 * @generated
 */
@ProviderType
public interface PercentageChange extends ChangeRule {
	/**
	 * Returns the value of the '<em><b>Percent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The minimal relative change in percent.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Percent</em>' attribute.
	 * @see #setPercent(double)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getPercentageChange_Percent()
	 * @model required="true"
	 * @generated
	 */
	double getPercent();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.PercentageChange#getPercent <em>Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Percent</em>' attribute.
	 * @see #getPercent()
	 * @generated
	 */
	void setPercent(double value);

} // PercentageChange
