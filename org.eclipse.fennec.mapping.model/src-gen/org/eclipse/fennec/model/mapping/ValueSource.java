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
package org.eclipse.fennec.model.mapping;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * How one value is produced on a source object - the ladder shared by every consumer:
 * 1. expression (OCL), evaluated with self = the source object; wins over everything else.
 * 2. functionId: with a featurePath the function gets what the path found, without one the whole source object.
 * 3. featurePath: navigated feature by feature; where it crosses a collection, collectionFilter, else collectionIndex picks the element.
 * The first declared producer answers - it is no fallback chain. When it yields nothing, the constant answers (parsed into the target type, not converted). converterId then post-processes a produced value.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.mapping.ValueSource#getFeaturePath <em>Feature Path</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.ValueSource#getCollectionIndex <em>Collection Index</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.ValueSource#getCollectionFilter <em>Collection Filter</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.ValueSource#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.ValueSource#getFunctionId <em>Function Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.ValueSource#getConverterId <em>Converter Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.ValueSource#getConstant <em>Constant</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource()
 * @model
 * @generated
 */
@ProviderType
public interface ValueSource extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature Path</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EStructuralFeature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The path from the source object to the value. Checked against the metamodel when the mapping is loaded.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Feature Path</em>' reference list.
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource_FeaturePath()
	 * @model
	 * @generated
	 */
	EList<EStructuralFeature> getFeaturePath();

	/**
	 * Returns the value of the '<em><b>Collection Index</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element to carry on from wherever the path crosses a collection, when no collectionFilter is set. Default 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Collection Index</em>' attribute.
	 * @see #setCollectionIndex(int)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource_CollectionIndex()
	 * @model default="0"
	 * @generated
	 */
	int getCollectionIndex();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.ValueSource#getCollectionIndex <em>Collection Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Collection Index</em>' attribute.
	 * @see #getCollectionIndex()
	 * @generated
	 */
	void setCollectionIndex(int value);

	/**
	 * Returns the value of the '<em><b>Collection Filter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Selects the element of a crossed collection by value: 'path.to.feature=value' (value optionally quoted), the first match wins; wins over collectionIndex.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Collection Filter</em>' attribute.
	 * @see #setCollectionFilter(String)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource_CollectionFilter()
	 * @model
	 * @generated
	 */
	String getCollectionFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.ValueSource#getCollectionFilter <em>Collection Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Collection Filter</em>' attribute.
	 * @see #getCollectionFilter()
	 * @generated
	 */
	void setCollectionFilter(String value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * OCL expression with self = the source object. The first producer.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expression</em>' attribute.
	 * @see #setExpression(String)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource_Expression()
	 * @model
	 * @generated
	 */
	String getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.ValueSource#getExpression <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' attribute.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(String value);

	/**
	 * Returns the value of the '<em><b>Function Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Id of a registered function. An unknown id produces nothing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Function Id</em>' attribute.
	 * @see #setFunctionId(String)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource_FunctionId()
	 * @model
	 * @generated
	 */
	String getFunctionId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.ValueSource#getFunctionId <em>Function Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Id</em>' attribute.
	 * @see #getFunctionId()
	 * @generated
	 */
	void setFunctionId(String value);

	/**
	 * Returns the value of the '<em><b>Converter Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Id of a registered converter applied to the produced value. An unknown id leaves the value unconverted; a failing converter drops it.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Converter Id</em>' attribute.
	 * @see #setConverterId(String)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource_ConverterId()
	 * @model
	 * @generated
	 */
	String getConverterId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.ValueSource#getConverterId <em>Converter Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Converter Id</em>' attribute.
	 * @see #getConverterId()
	 * @generated
	 */
	void setConverterId(String value);

	/**
	 * Returns the value of the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Literal that answers when the producer yields nothing or none is declared.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constant</em>' attribute.
	 * @see #setConstant(String)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getValueSource_Constant()
	 * @model
	 * @generated
	 */
	String getConstant();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.ValueSource#getConstant <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constant</em>' attribute.
	 * @see #getConstant()
	 * @generated
	 */
	void setConstant(String value);

} // ValueSource
