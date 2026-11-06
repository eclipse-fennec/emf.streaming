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
package org.eclipse.fennec.model.mapping.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.fennec.model.mapping.MappingPackage;
import org.eclipse.fennec.model.mapping.ValueSource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Value Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl#getFeaturePath <em>Feature Path</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl#getCollectionIndex <em>Collection Index</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl#getCollectionFilter <em>Collection Filter</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl#getFunctionId <em>Function Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl#getConverterId <em>Converter Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl#getConstant <em>Constant</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ValueSourceImpl extends MinimalEObjectImpl.Container implements ValueSource {
	/**
	 * The cached value of the '{@link #getFeaturePath() <em>Feature Path</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeaturePath()
	 * @generated
	 * @ordered
	 */
	protected EList<EStructuralFeature> featurePath;

	/**
	 * The default value of the '{@link #getCollectionIndex() <em>Collection Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollectionIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int COLLECTION_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCollectionIndex() <em>Collection Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollectionIndex()
	 * @generated
	 * @ordered
	 */
	protected int collectionIndex = COLLECTION_INDEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getCollectionFilter() <em>Collection Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollectionFilter()
	 * @generated
	 * @ordered
	 */
	protected static final String COLLECTION_FILTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCollectionFilter() <em>Collection Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollectionFilter()
	 * @generated
	 * @ordered
	 */
	protected String collectionFilter = COLLECTION_FILTER_EDEFAULT;

	/**
	 * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected String expression = EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getFunctionId() <em>Function Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionId()
	 * @generated
	 * @ordered
	 */
	protected static final String FUNCTION_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFunctionId() <em>Function Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionId()
	 * @generated
	 * @ordered
	 */
	protected String functionId = FUNCTION_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getConverterId() <em>Converter Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverterId()
	 * @generated
	 * @ordered
	 */
	protected static final String CONVERTER_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConverterId() <em>Converter Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverterId()
	 * @generated
	 * @ordered
	 */
	protected String converterId = CONVERTER_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstant()
	 * @generated
	 * @ordered
	 */
	protected static final String CONSTANT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstant()
	 * @generated
	 * @ordered
	 */
	protected String constant = CONSTANT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValueSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MappingPackage.Literals.VALUE_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EStructuralFeature> getFeaturePath() {
		if (featurePath == null) {
			featurePath = new EObjectResolvingEList<EStructuralFeature>(EStructuralFeature.class, this, MappingPackage.VALUE_SOURCE__FEATURE_PATH);
		}
		return featurePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getCollectionIndex() {
		return collectionIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCollectionIndex(int newCollectionIndex) {
		int oldCollectionIndex = collectionIndex;
		collectionIndex = newCollectionIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.VALUE_SOURCE__COLLECTION_INDEX, oldCollectionIndex, collectionIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCollectionFilter() {
		return collectionFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCollectionFilter(String newCollectionFilter) {
		String oldCollectionFilter = collectionFilter;
		collectionFilter = newCollectionFilter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.VALUE_SOURCE__COLLECTION_FILTER, oldCollectionFilter, collectionFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExpression(String newExpression) {
		String oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.VALUE_SOURCE__EXPRESSION, oldExpression, expression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFunctionId() {
		return functionId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFunctionId(String newFunctionId) {
		String oldFunctionId = functionId;
		functionId = newFunctionId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.VALUE_SOURCE__FUNCTION_ID, oldFunctionId, functionId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getConverterId() {
		return converterId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConverterId(String newConverterId) {
		String oldConverterId = converterId;
		converterId = newConverterId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.VALUE_SOURCE__CONVERTER_ID, oldConverterId, converterId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getConstant() {
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConstant(String newConstant) {
		String oldConstant = constant;
		constant = newConstant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.VALUE_SOURCE__CONSTANT, oldConstant, constant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MappingPackage.VALUE_SOURCE__FEATURE_PATH:
				return getFeaturePath();
			case MappingPackage.VALUE_SOURCE__COLLECTION_INDEX:
				return getCollectionIndex();
			case MappingPackage.VALUE_SOURCE__COLLECTION_FILTER:
				return getCollectionFilter();
			case MappingPackage.VALUE_SOURCE__EXPRESSION:
				return getExpression();
			case MappingPackage.VALUE_SOURCE__FUNCTION_ID:
				return getFunctionId();
			case MappingPackage.VALUE_SOURCE__CONVERTER_ID:
				return getConverterId();
			case MappingPackage.VALUE_SOURCE__CONSTANT:
				return getConstant();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MappingPackage.VALUE_SOURCE__FEATURE_PATH:
				getFeaturePath().clear();
				getFeaturePath().addAll((Collection<? extends EStructuralFeature>)newValue);
				return;
			case MappingPackage.VALUE_SOURCE__COLLECTION_INDEX:
				setCollectionIndex((Integer)newValue);
				return;
			case MappingPackage.VALUE_SOURCE__COLLECTION_FILTER:
				setCollectionFilter((String)newValue);
				return;
			case MappingPackage.VALUE_SOURCE__EXPRESSION:
				setExpression((String)newValue);
				return;
			case MappingPackage.VALUE_SOURCE__FUNCTION_ID:
				setFunctionId((String)newValue);
				return;
			case MappingPackage.VALUE_SOURCE__CONVERTER_ID:
				setConverterId((String)newValue);
				return;
			case MappingPackage.VALUE_SOURCE__CONSTANT:
				setConstant((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MappingPackage.VALUE_SOURCE__FEATURE_PATH:
				getFeaturePath().clear();
				return;
			case MappingPackage.VALUE_SOURCE__COLLECTION_INDEX:
				setCollectionIndex(COLLECTION_INDEX_EDEFAULT);
				return;
			case MappingPackage.VALUE_SOURCE__COLLECTION_FILTER:
				setCollectionFilter(COLLECTION_FILTER_EDEFAULT);
				return;
			case MappingPackage.VALUE_SOURCE__EXPRESSION:
				setExpression(EXPRESSION_EDEFAULT);
				return;
			case MappingPackage.VALUE_SOURCE__FUNCTION_ID:
				setFunctionId(FUNCTION_ID_EDEFAULT);
				return;
			case MappingPackage.VALUE_SOURCE__CONVERTER_ID:
				setConverterId(CONVERTER_ID_EDEFAULT);
				return;
			case MappingPackage.VALUE_SOURCE__CONSTANT:
				setConstant(CONSTANT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MappingPackage.VALUE_SOURCE__FEATURE_PATH:
				return featurePath != null && !featurePath.isEmpty();
			case MappingPackage.VALUE_SOURCE__COLLECTION_INDEX:
				return collectionIndex != COLLECTION_INDEX_EDEFAULT;
			case MappingPackage.VALUE_SOURCE__COLLECTION_FILTER:
				return COLLECTION_FILTER_EDEFAULT == null ? collectionFilter != null : !COLLECTION_FILTER_EDEFAULT.equals(collectionFilter);
			case MappingPackage.VALUE_SOURCE__EXPRESSION:
				return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
			case MappingPackage.VALUE_SOURCE__FUNCTION_ID:
				return FUNCTION_ID_EDEFAULT == null ? functionId != null : !FUNCTION_ID_EDEFAULT.equals(functionId);
			case MappingPackage.VALUE_SOURCE__CONVERTER_ID:
				return CONVERTER_ID_EDEFAULT == null ? converterId != null : !CONVERTER_ID_EDEFAULT.equals(converterId);
			case MappingPackage.VALUE_SOURCE__CONSTANT:
				return CONSTANT_EDEFAULT == null ? constant != null : !CONSTANT_EDEFAULT.equals(constant);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (collectionIndex: ");
		result.append(collectionIndex);
		result.append(", collectionFilter: ");
		result.append(collectionFilter);
		result.append(", expression: ");
		result.append(expression);
		result.append(", functionId: ");
		result.append(functionId);
		result.append(", converterId: ");
		result.append(converterId);
		result.append(", constant: ");
		result.append(constant);
		result.append(')');
		return result.toString();
	}

} //ValueSourceImpl
