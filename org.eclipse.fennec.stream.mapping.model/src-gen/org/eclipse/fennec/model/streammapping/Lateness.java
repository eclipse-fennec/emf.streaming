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
 * A representation of the literals of the enumeration '<em><b>Lateness</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * What a value does whose event time is older than the last one of its slot.
 * <!-- end-model-doc -->
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getLateness()
 * @model
 * @generated
 */
@ProviderType
public enum Lateness implements Enumerator {
	/**
	 * The '<em><b>ARRIVAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Arrival order counts: the value is applied.
	 * <!-- end-model-doc -->
	 * @see #ARRIVAL_VALUE
	 * @generated
	 * @ordered
	 */
	ARRIVAL(0, "ARRIVAL", "ARRIVAL"),

	/**
	 * The '<em><b>EVENT TIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Event time counts: the value enters the stream in its place, the target keeps the newer value.
	 * <!-- end-model-doc -->
	 * @see #EVENT_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	EVENT_TIME(1, "EVENT_TIME", "EVENT_TIME");

	/**
	 * The '<em><b>ARRIVAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Arrival order counts: the value is applied.
	 * <!-- end-model-doc -->
	 * @see #ARRIVAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ARRIVAL_VALUE = 0;

	/**
	 * The '<em><b>EVENT TIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Event time counts: the value enters the stream in its place, the target keeps the newer value.
	 * <!-- end-model-doc -->
	 * @see #EVENT_TIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_TIME_VALUE = 1;

	/**
	 * An array of all the '<em><b>Lateness</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final Lateness[] VALUES_ARRAY =
		new Lateness[] {
			ARRIVAL,
			EVENT_TIME,
		};

	/**
	 * A public read-only list of all the '<em><b>Lateness</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<Lateness> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Lateness</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Lateness get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Lateness result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Lateness</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Lateness getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Lateness result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Lateness</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Lateness get(int value) {
		switch (value) {
			case ARRIVAL_VALUE: return ARRIVAL;
			case EVENT_TIME_VALUE: return EVENT_TIME;
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
	private Lateness(int value, String name, String literal) {
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
	
} //Lateness
