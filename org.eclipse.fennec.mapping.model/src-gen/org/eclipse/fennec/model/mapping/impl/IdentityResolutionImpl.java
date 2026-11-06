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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.model.mapping.IdentityResolution;
import org.eclipse.fennec.model.mapping.MappingPackage;
import org.eclipse.fennec.model.mapping.ValueSource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Identity Resolution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl#getSourceClass <em>Source Class</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl#getTargetClass <em>Target Class</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl#getResolver <em>Resolver</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl#isCacheable <em>Cacheable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IdentityResolutionImpl extends MinimalEObjectImpl.Container implements IdentityResolution {
	/**
	 * The cached value of the '{@link #getSourceClass() <em>Source Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceClass()
	 * @generated
	 * @ordered
	 */
	protected EClass sourceClass;

	/**
	 * The cached value of the '{@link #getTargetClass() <em>Target Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetClass()
	 * @generated
	 * @ordered
	 */
	protected EClass targetClass;

	/**
	 * The cached value of the '{@link #getResolver() <em>Resolver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResolver()
	 * @generated
	 * @ordered
	 */
	protected ValueSource resolver;

	/**
	 * The default value of the '{@link #isCacheable() <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCacheable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CACHEABLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isCacheable() <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCacheable()
	 * @generated
	 * @ordered
	 */
	protected boolean cacheable = CACHEABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IdentityResolutionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MappingPackage.Literals.IDENTITY_RESOLUTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSourceClass() {
		if (sourceClass != null && sourceClass.eIsProxy()) {
			InternalEObject oldSourceClass = (InternalEObject)sourceClass;
			sourceClass = (EClass)eResolveProxy(oldSourceClass);
			if (sourceClass != oldSourceClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MappingPackage.IDENTITY_RESOLUTION__SOURCE_CLASS, oldSourceClass, sourceClass));
			}
		}
		return sourceClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetSourceClass() {
		return sourceClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceClass(EClass newSourceClass) {
		EClass oldSourceClass = sourceClass;
		sourceClass = newSourceClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.IDENTITY_RESOLUTION__SOURCE_CLASS, oldSourceClass, sourceClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTargetClass() {
		if (targetClass != null && targetClass.eIsProxy()) {
			InternalEObject oldTargetClass = (InternalEObject)targetClass;
			targetClass = (EClass)eResolveProxy(oldTargetClass);
			if (targetClass != oldTargetClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MappingPackage.IDENTITY_RESOLUTION__TARGET_CLASS, oldTargetClass, targetClass));
			}
		}
		return targetClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetTargetClass() {
		return targetClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetClass(EClass newTargetClass) {
		EClass oldTargetClass = targetClass;
		targetClass = newTargetClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.IDENTITY_RESOLUTION__TARGET_CLASS, oldTargetClass, targetClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueSource getResolver() {
		return resolver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResolver(ValueSource newResolver, NotificationChain msgs) {
		ValueSource oldResolver = resolver;
		resolver = newResolver;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingPackage.IDENTITY_RESOLUTION__RESOLVER, oldResolver, newResolver);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResolver(ValueSource newResolver) {
		if (newResolver != resolver) {
			NotificationChain msgs = null;
			if (resolver != null)
				msgs = ((InternalEObject)resolver).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingPackage.IDENTITY_RESOLUTION__RESOLVER, null, msgs);
			if (newResolver != null)
				msgs = ((InternalEObject)newResolver).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingPackage.IDENTITY_RESOLUTION__RESOLVER, null, msgs);
			msgs = basicSetResolver(newResolver, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.IDENTITY_RESOLUTION__RESOLVER, newResolver, newResolver));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCacheable(boolean newCacheable) {
		boolean oldCacheable = cacheable;
		cacheable = newCacheable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.IDENTITY_RESOLUTION__CACHEABLE, oldCacheable, cacheable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MappingPackage.IDENTITY_RESOLUTION__RESOLVER:
				return basicSetResolver(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MappingPackage.IDENTITY_RESOLUTION__SOURCE_CLASS:
				if (resolve) return getSourceClass();
				return basicGetSourceClass();
			case MappingPackage.IDENTITY_RESOLUTION__TARGET_CLASS:
				if (resolve) return getTargetClass();
				return basicGetTargetClass();
			case MappingPackage.IDENTITY_RESOLUTION__RESOLVER:
				return getResolver();
			case MappingPackage.IDENTITY_RESOLUTION__CACHEABLE:
				return isCacheable();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MappingPackage.IDENTITY_RESOLUTION__SOURCE_CLASS:
				setSourceClass((EClass)newValue);
				return;
			case MappingPackage.IDENTITY_RESOLUTION__TARGET_CLASS:
				setTargetClass((EClass)newValue);
				return;
			case MappingPackage.IDENTITY_RESOLUTION__RESOLVER:
				setResolver((ValueSource)newValue);
				return;
			case MappingPackage.IDENTITY_RESOLUTION__CACHEABLE:
				setCacheable((Boolean)newValue);
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
			case MappingPackage.IDENTITY_RESOLUTION__SOURCE_CLASS:
				setSourceClass((EClass)null);
				return;
			case MappingPackage.IDENTITY_RESOLUTION__TARGET_CLASS:
				setTargetClass((EClass)null);
				return;
			case MappingPackage.IDENTITY_RESOLUTION__RESOLVER:
				setResolver((ValueSource)null);
				return;
			case MappingPackage.IDENTITY_RESOLUTION__CACHEABLE:
				setCacheable(CACHEABLE_EDEFAULT);
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
			case MappingPackage.IDENTITY_RESOLUTION__SOURCE_CLASS:
				return sourceClass != null;
			case MappingPackage.IDENTITY_RESOLUTION__TARGET_CLASS:
				return targetClass != null;
			case MappingPackage.IDENTITY_RESOLUTION__RESOLVER:
				return resolver != null;
			case MappingPackage.IDENTITY_RESOLUTION__CACHEABLE:
				return cacheable != CACHEABLE_EDEFAULT;
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
		result.append(" (cacheable: ");
		result.append(cacheable);
		result.append(')');
		return result.toString();
	}

} //IdentityResolutionImpl
