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
 * A representation of the model object '<em><b>Absolute Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Passes when |value - last| >= delta (numeric values).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.AbsoluteChange#getDelta <em>Delta</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getAbsoluteChange()
 * @model
 * @generated
 */
@ProviderType
public interface AbsoluteChange extends ChangeRule {
	/**
	 * Returns the value of the '<em><b>Delta</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The minimal absolute change.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delta</em>' attribute.
	 * @see #setDelta(double)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getAbsoluteChange_Delta()
	 * @model required="true"
	 * @generated
	 */
	double getDelta();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.AbsoluteChange#getDelta <em>Delta</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delta</em>' attribute.
	 * @see #getDelta()
	 * @generated
	 */
	void setDelta(double value);

} // AbsoluteChange
