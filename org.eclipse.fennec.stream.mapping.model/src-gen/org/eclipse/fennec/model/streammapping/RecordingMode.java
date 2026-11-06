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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Recording Mode</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * How a value is recorded in the stream.
 * <!-- end-model-doc -->
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getRecordingMode()
 * @model
 * @generated
 */
@ProviderType
public enum RecordingMode implements Enumerator {
	/**
	 * The '<em><b>TIMESERIES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Absolute samples: no old value, never coalesced, each with its own event time. Not invertible.
	 * <!-- end-model-doc -->
	 * @see #TIMESERIES_VALUE
	 * @generated
	 * @ordered
	 */
	TIMESERIES(0, "TIMESERIES", "TIMESERIES"),

	/**
	 * The '<em><b>CHANGELOG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Deltas with the old value, coalesced per change set, invertible.
	 * <!-- end-model-doc -->
	 * @see #CHANGELOG_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGELOG(1, "CHANGELOG", "CHANGELOG");

	/**
	 * The '<em><b>TIMESERIES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Absolute samples: no old value, never coalesced, each with its own event time. Not invertible.
	 * <!-- end-model-doc -->
	 * @see #TIMESERIES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TIMESERIES_VALUE = 0;

	/**
	 * The '<em><b>CHANGELOG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Deltas with the old value, coalesced per change set, invertible.
	 * <!-- end-model-doc -->
	 * @see #CHANGELOG
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHANGELOG_VALUE = 1;

	/**
	 * An array of all the '<em><b>Recording Mode</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final RecordingMode[] VALUES_ARRAY =
		new RecordingMode[] {
			TIMESERIES,
			CHANGELOG,
		};

	/**
	 * A public read-only list of all the '<em><b>Recording Mode</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<RecordingMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Recording Mode</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RecordingMode get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RecordingMode result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Recording Mode</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RecordingMode getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RecordingMode result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Recording Mode</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RecordingMode get(int value) {
		switch (value) {
			case TIMESERIES_VALUE: return TIMESERIES;
			case CHANGELOG_VALUE: return CHANGELOG;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private RecordingMode(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //RecordingMode
