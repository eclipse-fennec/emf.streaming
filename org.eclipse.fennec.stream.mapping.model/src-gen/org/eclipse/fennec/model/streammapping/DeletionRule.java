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

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deletion Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Retention of a stream, carried to the store. Not every store can delete on its own; one that cannot reports it.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.DeletionRule#getRetention <em>Retention</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.DeletionRule#getRetentionUnit <em>Retention Unit</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.DeletionRule#getMaxCount <em>Max Count</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupInterval <em>Cleanup Interval</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupIntervalUnit <em>Cleanup Interval Unit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getDeletionRule()
 * @model
 * @generated
 */
@ProviderType
public interface DeletionRule extends EObject {
	/**
	 * Returns the value of the '<em><b>Retention</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Keep values for this long (0 = unlimited).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Retention</em>' attribute.
	 * @see #setRetention(long)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getDeletionRule_Retention()
	 * @model default="0"
	 * @generated
	 */
	long getRetention();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getRetention <em>Retention</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retention</em>' attribute.
	 * @see #getRetention()
	 * @generated
	 */
	void setRetention(long value);

	/**
	 * Returns the value of the '<em><b>Retention Unit</b></em>' attribute.
	 * The default value is <code>"DAYS"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.DurationUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit of the retention.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Retention Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #setRetentionUnit(DurationUnit)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getDeletionRule_RetentionUnit()
	 * @model default="DAYS"
	 * @generated
	 */
	DurationUnit getRetentionUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getRetentionUnit <em>Retention Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retention Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #getRetentionUnit()
	 * @generated
	 */
	void setRetentionUnit(DurationUnit value);

	/**
	 * Returns the value of the '<em><b>Max Count</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Keep at most this many values (0 = unlimited).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Count</em>' attribute.
	 * @see #setMaxCount(int)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getDeletionRule_MaxCount()
	 * @model default="0"
	 * @generated
	 */
	int getMaxCount();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getMaxCount <em>Max Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Count</em>' attribute.
	 * @see #getMaxCount()
	 * @generated
	 */
	void setMaxCount(int value);

	/**
	 * Returns the value of the '<em><b>Cleanup Interval</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How often the store cleans up (0 = the store decides).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cleanup Interval</em>' attribute.
	 * @see #setCleanupInterval(long)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getDeletionRule_CleanupInterval()
	 * @model default="0"
	 * @generated
	 */
	long getCleanupInterval();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupInterval <em>Cleanup Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cleanup Interval</em>' attribute.
	 * @see #getCleanupInterval()
	 * @generated
	 */
	void setCleanupInterval(long value);

	/**
	 * Returns the value of the '<em><b>Cleanup Interval Unit</b></em>' attribute.
	 * The default value is <code>"HOURS"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.DurationUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit of the cleanup interval.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cleanup Interval Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #setCleanupIntervalUnit(DurationUnit)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getDeletionRule_CleanupIntervalUnit()
	 * @model default="HOURS"
	 * @generated
	 */
	DurationUnit getCleanupIntervalUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupIntervalUnit <em>Cleanup Interval Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cleanup Interval Unit</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see #getCleanupIntervalUnit()
	 * @generated
	 */
	void setCleanupIntervalUnit(DurationUnit value);

} // DeletionRule
