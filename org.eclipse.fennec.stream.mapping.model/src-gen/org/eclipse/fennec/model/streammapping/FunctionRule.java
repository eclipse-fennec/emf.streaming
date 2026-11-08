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
 * A representation of the model object '<em><b>Function Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A named function decides, for what an OCL guard cannot express or is too costly for. The function (a ValueFunction registered under the id) gets a ChangeCandidate (the source object, the new value, the last passed value) and answers a Boolean; an unknown id, a failure or a non-Boolean answer lets the value pass.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.FunctionRule#getFunctionId <em>Function Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getFunctionRule()
 * @model
 * @generated
 */
@ProviderType
public interface FunctionRule extends ChangeRule {
	/**
	 * Returns the value of the '<em><b>Function Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The id of the function (ValueFunction.FUNCTION_ID).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Function Id</em>' attribute.
	 * @see #setFunctionId(String)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getFunctionRule_FunctionId()
	 * @model required="true"
	 * @generated
	 */
	String getFunctionId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.FunctionRule#getFunctionId <em>Function Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Id</em>' attribute.
	 * @see #getFunctionId()
	 * @generated
	 */
	void setFunctionId(String value);

} // FunctionRule
