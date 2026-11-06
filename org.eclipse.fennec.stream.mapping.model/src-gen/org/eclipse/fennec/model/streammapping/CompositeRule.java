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

import org.eclipse.emf.common.util.EList;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Combines rules: AND (all pass), OR (any passes), NOT (the single rule does not pass). Nestable.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.CompositeRule#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.CompositeRule#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getCompositeRule()
 * @model
 * @generated
 */
@ProviderType
public interface CompositeRule extends ChangeRule {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The default value is <code>"AND"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.RuleOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How the rules combine.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.RuleOperator
	 * @see #setOperator(RuleOperator)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getCompositeRule_Operator()
	 * @model default="AND"
	 * @generated
	 */
	RuleOperator getOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.CompositeRule#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.RuleOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(RuleOperator value);

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.streammapping.ChangeRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The combined rules; exactly one for NOT.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getCompositeRule_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<ChangeRule> getRules();

} // CompositeRule
