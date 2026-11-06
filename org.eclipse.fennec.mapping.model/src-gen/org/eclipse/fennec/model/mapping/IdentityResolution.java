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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Identity Resolution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Which target object a source object feeds. Without a resolver the source object's id IS the target id. A resolver is a ValueSource evaluated on the source object; it yields the target id, or a collection of ids to feed several targets (fan-out).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.mapping.IdentityResolution#getSourceClass <em>Source Class</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.IdentityResolution#getTargetClass <em>Target Class</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.IdentityResolution#getResolver <em>Resolver</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.IdentityResolution#isCacheable <em>Cacheable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.mapping.MappingPackage#getIdentityResolution()
 * @model
 * @generated
 */
@ProviderType
public interface IdentityResolution extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The source class (and its subclasses).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Class</em>' reference.
	 * @see #setSourceClass(EClass)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getIdentityResolution_SourceClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getSourceClass();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.IdentityResolution#getSourceClass <em>Source Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Class</em>' reference.
	 * @see #getSourceClass()
	 * @generated
	 */
	void setSourceClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Target Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The target class; unset = the mapping's target.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target Class</em>' reference.
	 * @see #setTargetClass(EClass)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getIdentityResolution_TargetClass()
	 * @model
	 * @generated
	 */
	EClass getTargetClass();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.IdentityResolution#getTargetClass <em>Target Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Class</em>' reference.
	 * @see #getTargetClass()
	 * @generated
	 */
	void setTargetClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Resolver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Produces the target id(s) from the source object. Unset = same id.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resolver</em>' containment reference.
	 * @see #setResolver(ValueSource)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getIdentityResolution_Resolver()
	 * @model containment="true"
	 * @generated
	 */
	ValueSource getResolver();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.IdentityResolution#getResolver <em>Resolver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resolver</em>' containment reference.
	 * @see #getResolver()
	 * @generated
	 */
	void setResolver(ValueSource value);

	/**
	 * Returns the value of the '<em><b>Cacheable</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the runtime may cache (sourceClass, sourceId) -> target ids. False for resolvers whose answer changes without the source changing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cacheable</em>' attribute.
	 * @see #setCacheable(boolean)
	 * @see org.eclipse.fennec.model.mapping.MappingPackage#getIdentityResolution_Cacheable()
	 * @model default="true"
	 * @generated
	 */
	boolean isCacheable();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.mapping.IdentityResolution#isCacheable <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cacheable</em>' attribute.
	 * @see #isCacheable()
	 * @generated
	 */
	void setCacheable(boolean value);

} // IdentityResolution
