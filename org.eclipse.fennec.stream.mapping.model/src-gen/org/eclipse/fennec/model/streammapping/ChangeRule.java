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
 * A representation of the model object '<em><b>Change Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Decides whether a value enters the stream. Rules are evaluated without side effects; their state (last passed value, count, time) is updated only when the slot as a whole lets the value pass. The first value of a slot always passes.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getChangeRule()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface ChangeRule extends EObject {
} // ChangeRule
