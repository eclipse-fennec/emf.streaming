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


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.fennec.atlas.configuration.AtlasConfigPackage;

import org.eclipse.fennec.emf.osgi.annotation.provide.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Generic EObject-to-EObject mapping of Eclipse Fennec: which value of which source object ends up in which slot of which target.
 * 
 * One mapping has one target class and, per slot, one source. The way a value is obtained (ValueSource) is a ladder shared by every consumer: expression (OCL) > functionId (with or without a feature path) > feature path; the constant answers when the producer yields nothing; converterId post-processes a produced value. IdentityResolution says which target object a source object feeds, once per source class.
 * 
 * Consumers (streaming, search, OGC features, the event atlas) extend Mapping and Slot with their own policies; trigger and direction (push or pull) are theirs, not the mapping's. Mapping extends the engine-neutral DataTransformation of the shared atlas configuration model, so an atlas references a mapping like any other transformation.
 * <!-- end-model-doc -->
 * @see org.eclipse.fennec.model.mapping.MappingFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = MappingPackage.eNS_URI, fingerprint = "fp1:b38053fbf7e1dded385f85d806b17777553928df26378b3777081737f9e37c1a", genModel = "/model/mapping.genmodel", genModelSourceLocations = {"model/mapping.genmodel","org.eclipse.fennec.mapping.model/model/mapping.genmodel"}, ecore = "/model/mapping.ecore", ecoreSourceLocations = "/model/mapping.ecore")
public interface MappingPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "mapping";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://eclipse.org/fennec/mapping/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "mapping";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MappingPackage eINSTANCE = org.eclipse.fennec.model.mapping.impl.MappingPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.mapping.impl.MappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.mapping.impl.MappingImpl
	 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__ID = AtlasConfigPackage.DATA_TRANSFORMATION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__NAME = AtlasConfigPackage.DATA_TRANSFORMATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__DESCRIPTION = AtlasConfigPackage.DATA_TRANSFORMATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Supported EClasses</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__SUPPORTED_ECLASSES = AtlasConfigPackage.DATA_TRANSFORMATION__SUPPORTED_ECLASSES;

	/**
	 * The feature id for the '<em><b>Result EClasses</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__RESULT_ECLASSES = AtlasConfigPackage.DATA_TRANSFORMATION__RESULT_ECLASSES;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__TARGET = AtlasConfigPackage.DATA_TRANSFORMATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Identities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__IDENTITIES = AtlasConfigPackage.DATA_TRANSFORMATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Slots</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__SLOTS = AtlasConfigPackage.DATA_TRANSFORMATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Include Unmapped</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__INCLUDE_UNMAPPED = AtlasConfigPackage.DATA_TRANSFORMATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_FEATURE_COUNT = AtlasConfigPackage.DATA_TRANSFORMATION_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OPERATION_COUNT = AtlasConfigPackage.DATA_TRANSFORMATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl <em>Identity Resolution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl
	 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getIdentityResolution()
	 * @generated
	 */
	int IDENTITY_RESOLUTION = 1;

	/**
	 * The feature id for the '<em><b>Source Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_RESOLUTION__SOURCE_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Target Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_RESOLUTION__TARGET_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Resolver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_RESOLUTION__RESOLVER = 2;

	/**
	 * The feature id for the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_RESOLUTION__CACHEABLE = 3;

	/**
	 * The number of structural features of the '<em>Identity Resolution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_RESOLUTION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Identity Resolution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_RESOLUTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.mapping.impl.SlotImpl <em>Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.mapping.impl.SlotImpl
	 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getSlot()
	 * @generated
	 */
	int SLOT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Target Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__TARGET_FEATURE = 1;

	/**
	 * The feature id for the '<em><b>Source Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__SOURCE_CLASS = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__VALUE = 3;

	/**
	 * The feature id for the '<em><b>Target Converter Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__TARGET_CONVERTER_ID = 4;

	/**
	 * The number of structural features of the '<em>Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl <em>Value Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.mapping.impl.ValueSourceImpl
	 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getValueSource()
	 * @generated
	 */
	int VALUE_SOURCE = 3;

	/**
	 * The feature id for the '<em><b>Feature Path</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE__FEATURE_PATH = 0;

	/**
	 * The feature id for the '<em><b>Collection Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE__COLLECTION_INDEX = 1;

	/**
	 * The feature id for the '<em><b>Collection Filter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE__COLLECTION_FILTER = 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE__EXPRESSION = 3;

	/**
	 * The feature id for the '<em><b>Function Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE__FUNCTION_ID = 4;

	/**
	 * The feature id for the '<em><b>Converter Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE__CONVERTER_ID = 5;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE__CONSTANT = 6;

	/**
	 * The number of structural features of the '<em>Value Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Value Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SOURCE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.mapping.Mapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see org.eclipse.fennec.model.mapping.Mapping
	 * @generated
	 */
	EClass getMapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.mapping.Mapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.fennec.model.mapping.Mapping#getTarget()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_Target();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.model.mapping.Mapping#getIdentities <em>Identities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Identities</em>'.
	 * @see org.eclipse.fennec.model.mapping.Mapping#getIdentities()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_Identities();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.model.mapping.Mapping#getSlots <em>Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Slots</em>'.
	 * @see org.eclipse.fennec.model.mapping.Mapping#getSlots()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_Slots();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.Mapping#getIncludeUnmapped <em>Include Unmapped</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include Unmapped</em>'.
	 * @see org.eclipse.fennec.model.mapping.Mapping#getIncludeUnmapped()
	 * @see #getMapping()
	 * @generated
	 */
	EAttribute getMapping_IncludeUnmapped();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.mapping.IdentityResolution <em>Identity Resolution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identity Resolution</em>'.
	 * @see org.eclipse.fennec.model.mapping.IdentityResolution
	 * @generated
	 */
	EClass getIdentityResolution();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.mapping.IdentityResolution#getSourceClass <em>Source Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Class</em>'.
	 * @see org.eclipse.fennec.model.mapping.IdentityResolution#getSourceClass()
	 * @see #getIdentityResolution()
	 * @generated
	 */
	EReference getIdentityResolution_SourceClass();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.mapping.IdentityResolution#getTargetClass <em>Target Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Class</em>'.
	 * @see org.eclipse.fennec.model.mapping.IdentityResolution#getTargetClass()
	 * @see #getIdentityResolution()
	 * @generated
	 */
	EReference getIdentityResolution_TargetClass();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.model.mapping.IdentityResolution#getResolver <em>Resolver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Resolver</em>'.
	 * @see org.eclipse.fennec.model.mapping.IdentityResolution#getResolver()
	 * @see #getIdentityResolution()
	 * @generated
	 */
	EReference getIdentityResolution_Resolver();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.IdentityResolution#isCacheable <em>Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cacheable</em>'.
	 * @see org.eclipse.fennec.model.mapping.IdentityResolution#isCacheable()
	 * @see #getIdentityResolution()
	 * @generated
	 */
	EAttribute getIdentityResolution_Cacheable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.mapping.Slot <em>Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slot</em>'.
	 * @see org.eclipse.fennec.model.mapping.Slot
	 * @generated
	 */
	EClass getSlot();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.Slot#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.mapping.Slot#getName()
	 * @see #getSlot()
	 * @generated
	 */
	EAttribute getSlot_Name();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.mapping.Slot#getTargetFeature <em>Target Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Feature</em>'.
	 * @see org.eclipse.fennec.model.mapping.Slot#getTargetFeature()
	 * @see #getSlot()
	 * @generated
	 */
	EReference getSlot_TargetFeature();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.mapping.Slot#getSourceClass <em>Source Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Class</em>'.
	 * @see org.eclipse.fennec.model.mapping.Slot#getSourceClass()
	 * @see #getSlot()
	 * @generated
	 */
	EReference getSlot_SourceClass();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.model.mapping.Slot#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fennec.model.mapping.Slot#getValue()
	 * @see #getSlot()
	 * @generated
	 */
	EReference getSlot_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.Slot#getTargetConverterId <em>Target Converter Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Converter Id</em>'.
	 * @see org.eclipse.fennec.model.mapping.Slot#getTargetConverterId()
	 * @see #getSlot()
	 * @generated
	 */
	EAttribute getSlot_TargetConverterId();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.mapping.ValueSource <em>Value Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Source</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource
	 * @generated
	 */
	EClass getValueSource();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.mapping.ValueSource#getFeaturePath <em>Feature Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Feature Path</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource#getFeaturePath()
	 * @see #getValueSource()
	 * @generated
	 */
	EReference getValueSource_FeaturePath();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.ValueSource#getCollectionIndex <em>Collection Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Collection Index</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource#getCollectionIndex()
	 * @see #getValueSource()
	 * @generated
	 */
	EAttribute getValueSource_CollectionIndex();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.ValueSource#getCollectionFilter <em>Collection Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Collection Filter</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource#getCollectionFilter()
	 * @see #getValueSource()
	 * @generated
	 */
	EAttribute getValueSource_CollectionFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.ValueSource#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource#getExpression()
	 * @see #getValueSource()
	 * @generated
	 */
	EAttribute getValueSource_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.ValueSource#getFunctionId <em>Function Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function Id</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource#getFunctionId()
	 * @see #getValueSource()
	 * @generated
	 */
	EAttribute getValueSource_FunctionId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.ValueSource#getConverterId <em>Converter Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Converter Id</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource#getConverterId()
	 * @see #getValueSource()
	 * @generated
	 */
	EAttribute getValueSource_ConverterId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.mapping.ValueSource#getConstant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constant</em>'.
	 * @see org.eclipse.fennec.model.mapping.ValueSource#getConstant()
	 * @see #getValueSource()
	 * @generated
	 */
	EAttribute getValueSource_Constant();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MappingFactory getMappingFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.mapping.impl.MappingImpl <em>Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.mapping.impl.MappingImpl
		 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getMapping()
		 * @generated
		 */
		EClass MAPPING = eINSTANCE.getMapping();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__TARGET = eINSTANCE.getMapping_Target();

		/**
		 * The meta object literal for the '<em><b>Identities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__IDENTITIES = eINSTANCE.getMapping_Identities();

		/**
		 * The meta object literal for the '<em><b>Slots</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__SLOTS = eINSTANCE.getMapping_Slots();

		/**
		 * The meta object literal for the '<em><b>Include Unmapped</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING__INCLUDE_UNMAPPED = eINSTANCE.getMapping_IncludeUnmapped();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl <em>Identity Resolution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.mapping.impl.IdentityResolutionImpl
		 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getIdentityResolution()
		 * @generated
		 */
		EClass IDENTITY_RESOLUTION = eINSTANCE.getIdentityResolution();

		/**
		 * The meta object literal for the '<em><b>Source Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDENTITY_RESOLUTION__SOURCE_CLASS = eINSTANCE.getIdentityResolution_SourceClass();

		/**
		 * The meta object literal for the '<em><b>Target Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDENTITY_RESOLUTION__TARGET_CLASS = eINSTANCE.getIdentityResolution_TargetClass();

		/**
		 * The meta object literal for the '<em><b>Resolver</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDENTITY_RESOLUTION__RESOLVER = eINSTANCE.getIdentityResolution_Resolver();

		/**
		 * The meta object literal for the '<em><b>Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY_RESOLUTION__CACHEABLE = eINSTANCE.getIdentityResolution_Cacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.mapping.impl.SlotImpl <em>Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.mapping.impl.SlotImpl
		 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getSlot()
		 * @generated
		 */
		EClass SLOT = eINSTANCE.getSlot();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLOT__NAME = eINSTANCE.getSlot_Name();

		/**
		 * The meta object literal for the '<em><b>Target Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLOT__TARGET_FEATURE = eINSTANCE.getSlot_TargetFeature();

		/**
		 * The meta object literal for the '<em><b>Source Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLOT__SOURCE_CLASS = eINSTANCE.getSlot_SourceClass();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLOT__VALUE = eINSTANCE.getSlot_Value();

		/**
		 * The meta object literal for the '<em><b>Target Converter Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLOT__TARGET_CONVERTER_ID = eINSTANCE.getSlot_TargetConverterId();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.mapping.impl.ValueSourceImpl <em>Value Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.mapping.impl.ValueSourceImpl
		 * @see org.eclipse.fennec.model.mapping.impl.MappingPackageImpl#getValueSource()
		 * @generated
		 */
		EClass VALUE_SOURCE = eINSTANCE.getValueSource();

		/**
		 * The meta object literal for the '<em><b>Feature Path</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALUE_SOURCE__FEATURE_PATH = eINSTANCE.getValueSource_FeaturePath();

		/**
		 * The meta object literal for the '<em><b>Collection Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_SOURCE__COLLECTION_INDEX = eINSTANCE.getValueSource_CollectionIndex();

		/**
		 * The meta object literal for the '<em><b>Collection Filter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_SOURCE__COLLECTION_FILTER = eINSTANCE.getValueSource_CollectionFilter();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_SOURCE__EXPRESSION = eINSTANCE.getValueSource_Expression();

		/**
		 * The meta object literal for the '<em><b>Function Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_SOURCE__FUNCTION_ID = eINSTANCE.getValueSource_FunctionId();

		/**
		 * The meta object literal for the '<em><b>Converter Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_SOURCE__CONVERTER_ID = eINSTANCE.getValueSource_ConverterId();

		/**
		 * The meta object literal for the '<em><b>Constant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_SOURCE__CONSTANT = eINSTANCE.getValueSource_Constant();

	}

} //MappingPackage
