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

import org.eclipse.emf.ecore.EFactory;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage
 * @generated
 */
@ProviderType
public interface StreamMappingFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StreamMappingFactory eINSTANCE = org.eclipse.fennec.model.streammapping.impl.StreamMappingFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Stream Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stream Mapping</em>'.
	 * @generated
	 */
	StreamMapping createStreamMapping();

	/**
	 * Returns a new object of class '<em>Stream Slot</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stream Slot</em>'.
	 * @generated
	 */
	StreamSlot createStreamSlot();

	/**
	 * Returns a new object of class '<em>Absolute Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Absolute Change</em>'.
	 * @generated
	 */
	AbsoluteChange createAbsoluteChange();

	/**
	 * Returns a new object of class '<em>Percentage Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Percentage Change</em>'.
	 * @generated
	 */
	PercentageChange createPercentageChange();

	/**
	 * Returns a new object of class '<em>Count Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Count Change</em>'.
	 * @generated
	 */
	CountChange createCountChange();

	/**
	 * Returns a new object of class '<em>Time Throttle</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Throttle</em>'.
	 * @generated
	 */
	TimeThrottle createTimeThrottle();

	/**
	 * Returns a new object of class '<em>Composite Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composite Rule</em>'.
	 * @generated
	 */
	CompositeRule createCompositeRule();

	/**
	 * Returns a new object of class '<em>Expression Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expression Rule</em>'.
	 * @generated
	 */
	ExpressionRule createExpressionRule();

	/**
	 * Returns a new object of class '<em>Batch Strategy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Batch Strategy</em>'.
	 * @generated
	 */
	BatchStrategy createBatchStrategy();

	/**
	 * Returns a new object of class '<em>Deletion Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deletion Rule</em>'.
	 * @generated
	 */
	DeletionRule createDeletionRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	StreamMappingPackage getStreamMappingPackage();

} //StreamMappingFactory
