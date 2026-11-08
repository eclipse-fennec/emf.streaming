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


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.fennec.emf.osgi.annotation.provide.EPackage;

import org.eclipse.fennec.model.mapping.MappingPackage;

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
 * Streaming policies on top of the generic Fennec mapping: a StreamMapping fills the slots of a target class from source objects and records what passes its change rules in the change stream (stream.ecore).
 * 
 * Each StreamSlot may override what its StreamMapping sets as default (an unset slot attribute means: the mapping's value). Defaults: mode PROJECT, recording TIMESERIES, lateness by recording (TIMESERIES: EVENT_TIME, CHANGELOG: ARRIVAL), batch IMMEDIATE, execution group 'default'. Without a rule every change passes.
 * <!-- end-model-doc -->
 * @see org.eclipse.fennec.model.streammapping.StreamMappingFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = StreamMappingPackage.eNS_URI, fingerprint = "fp1:b3fc797499786d0282d54eaa07bb3b01162e27b15e114bc447087d3509006642", genModel = "/model/streammapping.genmodel", genModelSourceLocations = {"model/streammapping.genmodel","org.eclipse.fennec.stream.mapping.model/model/streammapping.genmodel"}, ecore = "/model/streammapping.ecore", ecoreSourceLocations = "/model/streammapping.ecore")
public interface StreamMappingPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "streammapping";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://eclipse.org/fennec/stream/mapping/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "streammapping";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StreamMappingPackage eINSTANCE = org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl <em>Stream Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getStreamMapping()
	 * @generated
	 */
	int STREAM_MAPPING = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__ID = MappingPackage.MAPPING__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__NAME = MappingPackage.MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__DESCRIPTION = MappingPackage.MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Supported EClasses</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__SUPPORTED_ECLASSES = MappingPackage.MAPPING__SUPPORTED_ECLASSES;

	/**
	 * The feature id for the '<em><b>Result EClasses</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__RESULT_ECLASSES = MappingPackage.MAPPING__RESULT_ECLASSES;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__TARGET = MappingPackage.MAPPING__TARGET;

	/**
	 * The feature id for the '<em><b>Identities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__IDENTITIES = MappingPackage.MAPPING__IDENTITIES;

	/**
	 * The feature id for the '<em><b>Slots</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__SLOTS = MappingPackage.MAPPING__SLOTS;

	/**
	 * The feature id for the '<em><b>Include Unmapped</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__INCLUDE_UNMAPPED = MappingPackage.MAPPING__INCLUDE_UNMAPPED;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__MODE = MappingPackage.MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Recording</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__RECORDING = MappingPackage.MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lateness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__LATENESS = MappingPackage.MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Batch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__BATCH = MappingPackage.MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Execution Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__EXECUTION_GROUP = MappingPackage.MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__GUARD = MappingPackage.MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Keyframe Every Sets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__KEYFRAME_EVERY_SETS = MappingPackage.MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Keyframe Every Minutes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__KEYFRAME_EVERY_MINUTES = MappingPackage.MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Undoable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__UNDOABLE = MappingPackage.MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Excluded Features</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING__EXCLUDED_FEATURES = MappingPackage.MAPPING_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Stream Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING_FEATURE_COUNT = MappingPackage.MAPPING_FEATURE_COUNT + 10;

	/**
	 * The number of operations of the '<em>Stream Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_MAPPING_OPERATION_COUNT = MappingPackage.MAPPING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl <em>Stream Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getStreamSlot()
	 * @generated
	 */
	int STREAM_SLOT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__NAME = MappingPackage.SLOT__NAME;

	/**
	 * The feature id for the '<em><b>Target Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__TARGET_FEATURE = MappingPackage.SLOT__TARGET_FEATURE;

	/**
	 * The feature id for the '<em><b>Source Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__SOURCE_CLASS = MappingPackage.SLOT__SOURCE_CLASS;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__VALUE = MappingPackage.SLOT__VALUE;

	/**
	 * The feature id for the '<em><b>Target Converter Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__TARGET_CONVERTER_ID = MappingPackage.SLOT__TARGET_CONVERTER_ID;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__RULE = MappingPackage.SLOT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__MODE = MappingPackage.SLOT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Recording</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__RECORDING = MappingPackage.SLOT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Lateness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__LATENESS = MappingPackage.SLOT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Batch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__BATCH = MappingPackage.SLOT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Execution Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__EXECUTION_GROUP = MappingPackage.SLOT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Deletion Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT__DELETION_RULE = MappingPackage.SLOT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Stream Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT_FEATURE_COUNT = MappingPackage.SLOT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Stream Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SLOT_OPERATION_COUNT = MappingPackage.SLOT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.ChangeRuleImpl <em>Change Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.ChangeRuleImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getChangeRule()
	 * @generated
	 */
	int CHANGE_RULE = 2;

	/**
	 * The number of structural features of the '<em>Change Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_RULE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Change Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.AbsoluteChangeImpl <em>Absolute Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.AbsoluteChangeImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getAbsoluteChange()
	 * @generated
	 */
	int ABSOLUTE_CHANGE = 3;

	/**
	 * The feature id for the '<em><b>Delta</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSOLUTE_CHANGE__DELTA = CHANGE_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Absolute Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSOLUTE_CHANGE_FEATURE_COUNT = CHANGE_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Absolute Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSOLUTE_CHANGE_OPERATION_COUNT = CHANGE_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.PercentageChangeImpl <em>Percentage Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.PercentageChangeImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getPercentageChange()
	 * @generated
	 */
	int PERCENTAGE_CHANGE = 4;

	/**
	 * The feature id for the '<em><b>Percent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTAGE_CHANGE__PERCENT = CHANGE_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Percentage Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTAGE_CHANGE_FEATURE_COUNT = CHANGE_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Percentage Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTAGE_CHANGE_OPERATION_COUNT = CHANGE_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.CountChangeImpl <em>Count Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.CountChangeImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getCountChange()
	 * @generated
	 */
	int COUNT_CHANGE = 5;

	/**
	 * The feature id for the '<em><b>N</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNT_CHANGE__N = CHANGE_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Count Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNT_CHANGE_FEATURE_COUNT = CHANGE_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Count Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNT_CHANGE_OPERATION_COUNT = CHANGE_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.TimeThrottleImpl <em>Time Throttle</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.TimeThrottleImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getTimeThrottle()
	 * @generated
	 */
	int TIME_THROTTLE = 6;

	/**
	 * The feature id for the '<em><b>Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_THROTTLE__INTERVAL = CHANGE_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_THROTTLE__UNIT = CHANGE_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Time Throttle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_THROTTLE_FEATURE_COUNT = CHANGE_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Time Throttle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_THROTTLE_OPERATION_COUNT = CHANGE_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.CompositeRuleImpl <em>Composite Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.CompositeRuleImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getCompositeRule()
	 * @generated
	 */
	int COMPOSITE_RULE = 7;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_RULE__OPERATOR = CHANGE_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_RULE__RULES = CHANGE_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Composite Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_RULE_FEATURE_COUNT = CHANGE_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Composite Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_RULE_OPERATION_COUNT = CHANGE_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.ExpressionRuleImpl <em>Expression Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.ExpressionRuleImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getExpressionRule()
	 * @generated
	 */
	int EXPRESSION_RULE = 8;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_RULE__EXPRESSION = CHANGE_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_RULE_FEATURE_COUNT = CHANGE_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Expression Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_RULE_OPERATION_COUNT = CHANGE_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.FunctionRuleImpl <em>Function Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.FunctionRuleImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getFunctionRule()
	 * @generated
	 */
	int FUNCTION_RULE = 9;

	/**
	 * The feature id for the '<em><b>Function Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_RULE__FUNCTION_ID = CHANGE_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Function Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_RULE_FEATURE_COUNT = CHANGE_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Function Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_RULE_OPERATION_COUNT = CHANGE_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.BatchStrategyImpl <em>Batch Strategy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.BatchStrategyImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getBatchStrategy()
	 * @generated
	 */
	int BATCH_STRATEGY = 10;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_STRATEGY__KIND = 0;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_STRATEGY__COUNT = 1;

	/**
	 * The feature id for the '<em><b>Window</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_STRATEGY__WINDOW = 2;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_STRATEGY__UNIT = 3;

	/**
	 * The number of structural features of the '<em>Batch Strategy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_STRATEGY_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Batch Strategy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_STRATEGY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl <em>Deletion Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getDeletionRule()
	 * @generated
	 */
	int DELETION_RULE = 11;

	/**
	 * The feature id for the '<em><b>Retention</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETION_RULE__RETENTION = 0;

	/**
	 * The feature id for the '<em><b>Retention Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETION_RULE__RETENTION_UNIT = 1;

	/**
	 * The feature id for the '<em><b>Max Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETION_RULE__MAX_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Cleanup Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETION_RULE__CLEANUP_INTERVAL = 3;

	/**
	 * The feature id for the '<em><b>Cleanup Interval Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETION_RULE__CLEANUP_INTERVAL_UNIT = 4;

	/**
	 * The number of structural features of the '<em>Deletion Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETION_RULE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Deletion Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETION_RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.ProjectionMode <em>Projection Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.ProjectionMode
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getProjectionMode()
	 * @generated
	 */
	int PROJECTION_MODE = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.RecordingMode <em>Recording Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.RecordingMode
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getRecordingMode()
	 * @generated
	 */
	int RECORDING_MODE = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.Lateness <em>Lateness</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.Lateness
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getLateness()
	 * @generated
	 */
	int LATENESS = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.GuardPolicy <em>Guard Policy</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.GuardPolicy
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getGuardPolicy()
	 * @generated
	 */
	int GUARD_POLICY = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.BatchKind <em>Batch Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.BatchKind
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getBatchKind()
	 * @generated
	 */
	int BATCH_KIND = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.RuleOperator <em>Rule Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.RuleOperator
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getRuleOperator()
	 * @generated
	 */
	int RULE_OPERATOR = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.streammapping.DurationUnit <em>Duration Unit</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getDurationUnit()
	 * @generated
	 */
	int DURATION_UNIT = 18;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.StreamMapping <em>Stream Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Mapping</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping
	 * @generated
	 */
	EClass getStreamMapping();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getMode()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_Mode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getRecording <em>Recording</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recording</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getRecording()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_Recording();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getLateness <em>Lateness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lateness</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getLateness()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_Lateness();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getBatch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Batch</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getBatch()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EReference getStreamMapping_Batch();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getExecutionGroup <em>Execution Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Execution Group</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getExecutionGroup()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_ExecutionGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guard</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getGuard()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_Guard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEverySets <em>Keyframe Every Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Keyframe Every Sets</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEverySets()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_KeyframeEverySets();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEveryMinutes <em>Keyframe Every Minutes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Keyframe Every Minutes</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEveryMinutes()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_KeyframeEveryMinutes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamMapping#isUndoable <em>Undoable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Undoable</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#isUndoable()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EAttribute getStreamMapping_Undoable();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getExcludedFeatures <em>Excluded Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Excluded Features</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamMapping#getExcludedFeatures()
	 * @see #getStreamMapping()
	 * @generated
	 */
	EReference getStreamMapping_ExcludedFeatures();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.StreamSlot <em>Stream Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Slot</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot
	 * @generated
	 */
	EClass getStreamSlot();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot#getRule()
	 * @see #getStreamSlot()
	 * @generated
	 */
	EReference getStreamSlot_Rule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot#getMode()
	 * @see #getStreamSlot()
	 * @generated
	 */
	EAttribute getStreamSlot_Mode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRecording <em>Recording</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recording</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot#getRecording()
	 * @see #getStreamSlot()
	 * @generated
	 */
	EAttribute getStreamSlot_Recording();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getLateness <em>Lateness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lateness</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot#getLateness()
	 * @see #getStreamSlot()
	 * @generated
	 */
	EAttribute getStreamSlot_Lateness();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getBatch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Batch</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot#getBatch()
	 * @see #getStreamSlot()
	 * @generated
	 */
	EReference getStreamSlot_Batch();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getExecutionGroup <em>Execution Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Execution Group</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot#getExecutionGroup()
	 * @see #getStreamSlot()
	 * @generated
	 */
	EAttribute getStreamSlot_ExecutionGroup();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getDeletionRule <em>Deletion Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deletion Rule</em>'.
	 * @see org.eclipse.fennec.model.streammapping.StreamSlot#getDeletionRule()
	 * @see #getStreamSlot()
	 * @generated
	 */
	EReference getStreamSlot_DeletionRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.ChangeRule <em>Change Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Rule</em>'.
	 * @see org.eclipse.fennec.model.streammapping.ChangeRule
	 * @generated
	 */
	EClass getChangeRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.AbsoluteChange <em>Absolute Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Absolute Change</em>'.
	 * @see org.eclipse.fennec.model.streammapping.AbsoluteChange
	 * @generated
	 */
	EClass getAbsoluteChange();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.AbsoluteChange#getDelta <em>Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delta</em>'.
	 * @see org.eclipse.fennec.model.streammapping.AbsoluteChange#getDelta()
	 * @see #getAbsoluteChange()
	 * @generated
	 */
	EAttribute getAbsoluteChange_Delta();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.PercentageChange <em>Percentage Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Percentage Change</em>'.
	 * @see org.eclipse.fennec.model.streammapping.PercentageChange
	 * @generated
	 */
	EClass getPercentageChange();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.PercentageChange#getPercent <em>Percent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Percent</em>'.
	 * @see org.eclipse.fennec.model.streammapping.PercentageChange#getPercent()
	 * @see #getPercentageChange()
	 * @generated
	 */
	EAttribute getPercentageChange_Percent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.CountChange <em>Count Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Count Change</em>'.
	 * @see org.eclipse.fennec.model.streammapping.CountChange
	 * @generated
	 */
	EClass getCountChange();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.CountChange#getN <em>N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>N</em>'.
	 * @see org.eclipse.fennec.model.streammapping.CountChange#getN()
	 * @see #getCountChange()
	 * @generated
	 */
	EAttribute getCountChange_N();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.TimeThrottle <em>Time Throttle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Throttle</em>'.
	 * @see org.eclipse.fennec.model.streammapping.TimeThrottle
	 * @generated
	 */
	EClass getTimeThrottle();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.TimeThrottle#getInterval <em>Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interval</em>'.
	 * @see org.eclipse.fennec.model.streammapping.TimeThrottle#getInterval()
	 * @see #getTimeThrottle()
	 * @generated
	 */
	EAttribute getTimeThrottle_Interval();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.TimeThrottle#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see org.eclipse.fennec.model.streammapping.TimeThrottle#getUnit()
	 * @see #getTimeThrottle()
	 * @generated
	 */
	EAttribute getTimeThrottle_Unit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.CompositeRule <em>Composite Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Rule</em>'.
	 * @see org.eclipse.fennec.model.streammapping.CompositeRule
	 * @generated
	 */
	EClass getCompositeRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.CompositeRule#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.eclipse.fennec.model.streammapping.CompositeRule#getOperator()
	 * @see #getCompositeRule()
	 * @generated
	 */
	EAttribute getCompositeRule_Operator();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.model.streammapping.CompositeRule#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.eclipse.fennec.model.streammapping.CompositeRule#getRules()
	 * @see #getCompositeRule()
	 * @generated
	 */
	EReference getCompositeRule_Rules();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.ExpressionRule <em>Expression Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Rule</em>'.
	 * @see org.eclipse.fennec.model.streammapping.ExpressionRule
	 * @generated
	 */
	EClass getExpressionRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.ExpressionRule#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see org.eclipse.fennec.model.streammapping.ExpressionRule#getExpression()
	 * @see #getExpressionRule()
	 * @generated
	 */
	EAttribute getExpressionRule_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.FunctionRule <em>Function Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Rule</em>'.
	 * @see org.eclipse.fennec.model.streammapping.FunctionRule
	 * @generated
	 */
	EClass getFunctionRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.FunctionRule#getFunctionId <em>Function Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function Id</em>'.
	 * @see org.eclipse.fennec.model.streammapping.FunctionRule#getFunctionId()
	 * @see #getFunctionRule()
	 * @generated
	 */
	EAttribute getFunctionRule_FunctionId();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.BatchStrategy <em>Batch Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Batch Strategy</em>'.
	 * @see org.eclipse.fennec.model.streammapping.BatchStrategy
	 * @generated
	 */
	EClass getBatchStrategy();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.fennec.model.streammapping.BatchStrategy#getKind()
	 * @see #getBatchStrategy()
	 * @generated
	 */
	EAttribute getBatchStrategy_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see org.eclipse.fennec.model.streammapping.BatchStrategy#getCount()
	 * @see #getBatchStrategy()
	 * @generated
	 */
	EAttribute getBatchStrategy_Count();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getWindow <em>Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window</em>'.
	 * @see org.eclipse.fennec.model.streammapping.BatchStrategy#getWindow()
	 * @see #getBatchStrategy()
	 * @generated
	 */
	EAttribute getBatchStrategy_Window();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.BatchStrategy#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see org.eclipse.fennec.model.streammapping.BatchStrategy#getUnit()
	 * @see #getBatchStrategy()
	 * @generated
	 */
	EAttribute getBatchStrategy_Unit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.streammapping.DeletionRule <em>Deletion Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deletion Rule</em>'.
	 * @see org.eclipse.fennec.model.streammapping.DeletionRule
	 * @generated
	 */
	EClass getDeletionRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getRetention <em>Retention</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Retention</em>'.
	 * @see org.eclipse.fennec.model.streammapping.DeletionRule#getRetention()
	 * @see #getDeletionRule()
	 * @generated
	 */
	EAttribute getDeletionRule_Retention();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getRetentionUnit <em>Retention Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Retention Unit</em>'.
	 * @see org.eclipse.fennec.model.streammapping.DeletionRule#getRetentionUnit()
	 * @see #getDeletionRule()
	 * @generated
	 */
	EAttribute getDeletionRule_RetentionUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getMaxCount <em>Max Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Count</em>'.
	 * @see org.eclipse.fennec.model.streammapping.DeletionRule#getMaxCount()
	 * @see #getDeletionRule()
	 * @generated
	 */
	EAttribute getDeletionRule_MaxCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupInterval <em>Cleanup Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cleanup Interval</em>'.
	 * @see org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupInterval()
	 * @see #getDeletionRule()
	 * @generated
	 */
	EAttribute getDeletionRule_CleanupInterval();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupIntervalUnit <em>Cleanup Interval Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cleanup Interval Unit</em>'.
	 * @see org.eclipse.fennec.model.streammapping.DeletionRule#getCleanupIntervalUnit()
	 * @see #getDeletionRule()
	 * @generated
	 */
	EAttribute getDeletionRule_CleanupIntervalUnit();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.model.streammapping.ProjectionMode <em>Projection Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Projection Mode</em>'.
	 * @see org.eclipse.fennec.model.streammapping.ProjectionMode
	 * @generated
	 */
	EEnum getProjectionMode();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.model.streammapping.RecordingMode <em>Recording Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Recording Mode</em>'.
	 * @see org.eclipse.fennec.model.streammapping.RecordingMode
	 * @generated
	 */
	EEnum getRecordingMode();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.model.streammapping.Lateness <em>Lateness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Lateness</em>'.
	 * @see org.eclipse.fennec.model.streammapping.Lateness
	 * @generated
	 */
	EEnum getLateness();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.model.streammapping.GuardPolicy <em>Guard Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Guard Policy</em>'.
	 * @see org.eclipse.fennec.model.streammapping.GuardPolicy
	 * @generated
	 */
	EEnum getGuardPolicy();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.model.streammapping.BatchKind <em>Batch Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Batch Kind</em>'.
	 * @see org.eclipse.fennec.model.streammapping.BatchKind
	 * @generated
	 */
	EEnum getBatchKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.model.streammapping.RuleOperator <em>Rule Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Rule Operator</em>'.
	 * @see org.eclipse.fennec.model.streammapping.RuleOperator
	 * @generated
	 */
	EEnum getRuleOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.model.streammapping.DurationUnit <em>Duration Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Duration Unit</em>'.
	 * @see org.eclipse.fennec.model.streammapping.DurationUnit
	 * @generated
	 */
	EEnum getDurationUnit();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StreamMappingFactory getStreamMappingFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl <em>Stream Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getStreamMapping()
		 * @generated
		 */
		EClass STREAM_MAPPING = eINSTANCE.getStreamMapping();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__MODE = eINSTANCE.getStreamMapping_Mode();

		/**
		 * The meta object literal for the '<em><b>Recording</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__RECORDING = eINSTANCE.getStreamMapping_Recording();

		/**
		 * The meta object literal for the '<em><b>Lateness</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__LATENESS = eINSTANCE.getStreamMapping_Lateness();

		/**
		 * The meta object literal for the '<em><b>Batch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_MAPPING__BATCH = eINSTANCE.getStreamMapping_Batch();

		/**
		 * The meta object literal for the '<em><b>Execution Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__EXECUTION_GROUP = eINSTANCE.getStreamMapping_ExecutionGroup();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__GUARD = eINSTANCE.getStreamMapping_Guard();

		/**
		 * The meta object literal for the '<em><b>Keyframe Every Sets</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__KEYFRAME_EVERY_SETS = eINSTANCE.getStreamMapping_KeyframeEverySets();

		/**
		 * The meta object literal for the '<em><b>Keyframe Every Minutes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__KEYFRAME_EVERY_MINUTES = eINSTANCE.getStreamMapping_KeyframeEveryMinutes();

		/**
		 * The meta object literal for the '<em><b>Undoable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_MAPPING__UNDOABLE = eINSTANCE.getStreamMapping_Undoable();

		/**
		 * The meta object literal for the '<em><b>Excluded Features</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_MAPPING__EXCLUDED_FEATURES = eINSTANCE.getStreamMapping_ExcludedFeatures();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl <em>Stream Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getStreamSlot()
		 * @generated
		 */
		EClass STREAM_SLOT = eINSTANCE.getStreamSlot();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_SLOT__RULE = eINSTANCE.getStreamSlot_Rule();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_SLOT__MODE = eINSTANCE.getStreamSlot_Mode();

		/**
		 * The meta object literal for the '<em><b>Recording</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_SLOT__RECORDING = eINSTANCE.getStreamSlot_Recording();

		/**
		 * The meta object literal for the '<em><b>Lateness</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_SLOT__LATENESS = eINSTANCE.getStreamSlot_Lateness();

		/**
		 * The meta object literal for the '<em><b>Batch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_SLOT__BATCH = eINSTANCE.getStreamSlot_Batch();

		/**
		 * The meta object literal for the '<em><b>Execution Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_SLOT__EXECUTION_GROUP = eINSTANCE.getStreamSlot_ExecutionGroup();

		/**
		 * The meta object literal for the '<em><b>Deletion Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_SLOT__DELETION_RULE = eINSTANCE.getStreamSlot_DeletionRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.ChangeRuleImpl <em>Change Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.ChangeRuleImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getChangeRule()
		 * @generated
		 */
		EClass CHANGE_RULE = eINSTANCE.getChangeRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.AbsoluteChangeImpl <em>Absolute Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.AbsoluteChangeImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getAbsoluteChange()
		 * @generated
		 */
		EClass ABSOLUTE_CHANGE = eINSTANCE.getAbsoluteChange();

		/**
		 * The meta object literal for the '<em><b>Delta</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSOLUTE_CHANGE__DELTA = eINSTANCE.getAbsoluteChange_Delta();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.PercentageChangeImpl <em>Percentage Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.PercentageChangeImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getPercentageChange()
		 * @generated
		 */
		EClass PERCENTAGE_CHANGE = eINSTANCE.getPercentageChange();

		/**
		 * The meta object literal for the '<em><b>Percent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERCENTAGE_CHANGE__PERCENT = eINSTANCE.getPercentageChange_Percent();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.CountChangeImpl <em>Count Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.CountChangeImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getCountChange()
		 * @generated
		 */
		EClass COUNT_CHANGE = eINSTANCE.getCountChange();

		/**
		 * The meta object literal for the '<em><b>N</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNT_CHANGE__N = eINSTANCE.getCountChange_N();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.TimeThrottleImpl <em>Time Throttle</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.TimeThrottleImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getTimeThrottle()
		 * @generated
		 */
		EClass TIME_THROTTLE = eINSTANCE.getTimeThrottle();

		/**
		 * The meta object literal for the '<em><b>Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_THROTTLE__INTERVAL = eINSTANCE.getTimeThrottle_Interval();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_THROTTLE__UNIT = eINSTANCE.getTimeThrottle_Unit();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.CompositeRuleImpl <em>Composite Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.CompositeRuleImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getCompositeRule()
		 * @generated
		 */
		EClass COMPOSITE_RULE = eINSTANCE.getCompositeRule();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOSITE_RULE__OPERATOR = eINSTANCE.getCompositeRule_Operator();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_RULE__RULES = eINSTANCE.getCompositeRule_Rules();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.ExpressionRuleImpl <em>Expression Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.ExpressionRuleImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getExpressionRule()
		 * @generated
		 */
		EClass EXPRESSION_RULE = eINSTANCE.getExpressionRule();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION_RULE__EXPRESSION = eINSTANCE.getExpressionRule_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.FunctionRuleImpl <em>Function Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.FunctionRuleImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getFunctionRule()
		 * @generated
		 */
		EClass FUNCTION_RULE = eINSTANCE.getFunctionRule();

		/**
		 * The meta object literal for the '<em><b>Function Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_RULE__FUNCTION_ID = eINSTANCE.getFunctionRule_FunctionId();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.BatchStrategyImpl <em>Batch Strategy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.BatchStrategyImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getBatchStrategy()
		 * @generated
		 */
		EClass BATCH_STRATEGY = eINSTANCE.getBatchStrategy();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BATCH_STRATEGY__KIND = eINSTANCE.getBatchStrategy_Kind();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BATCH_STRATEGY__COUNT = eINSTANCE.getBatchStrategy_Count();

		/**
		 * The meta object literal for the '<em><b>Window</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BATCH_STRATEGY__WINDOW = eINSTANCE.getBatchStrategy_Window();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BATCH_STRATEGY__UNIT = eINSTANCE.getBatchStrategy_Unit();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl <em>Deletion Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.impl.DeletionRuleImpl
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getDeletionRule()
		 * @generated
		 */
		EClass DELETION_RULE = eINSTANCE.getDeletionRule();

		/**
		 * The meta object literal for the '<em><b>Retention</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELETION_RULE__RETENTION = eINSTANCE.getDeletionRule_Retention();

		/**
		 * The meta object literal for the '<em><b>Retention Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELETION_RULE__RETENTION_UNIT = eINSTANCE.getDeletionRule_RetentionUnit();

		/**
		 * The meta object literal for the '<em><b>Max Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELETION_RULE__MAX_COUNT = eINSTANCE.getDeletionRule_MaxCount();

		/**
		 * The meta object literal for the '<em><b>Cleanup Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELETION_RULE__CLEANUP_INTERVAL = eINSTANCE.getDeletionRule_CleanupInterval();

		/**
		 * The meta object literal for the '<em><b>Cleanup Interval Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELETION_RULE__CLEANUP_INTERVAL_UNIT = eINSTANCE.getDeletionRule_CleanupIntervalUnit();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.ProjectionMode <em>Projection Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.ProjectionMode
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getProjectionMode()
		 * @generated
		 */
		EEnum PROJECTION_MODE = eINSTANCE.getProjectionMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.RecordingMode <em>Recording Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.RecordingMode
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getRecordingMode()
		 * @generated
		 */
		EEnum RECORDING_MODE = eINSTANCE.getRecordingMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.Lateness <em>Lateness</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.Lateness
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getLateness()
		 * @generated
		 */
		EEnum LATENESS = eINSTANCE.getLateness();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.GuardPolicy <em>Guard Policy</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.GuardPolicy
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getGuardPolicy()
		 * @generated
		 */
		EEnum GUARD_POLICY = eINSTANCE.getGuardPolicy();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.BatchKind <em>Batch Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.BatchKind
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getBatchKind()
		 * @generated
		 */
		EEnum BATCH_KIND = eINSTANCE.getBatchKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.RuleOperator <em>Rule Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.RuleOperator
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getRuleOperator()
		 * @generated
		 */
		EEnum RULE_OPERATOR = eINSTANCE.getRuleOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.streammapping.DurationUnit <em>Duration Unit</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.streammapping.DurationUnit
		 * @see org.eclipse.fennec.model.streammapping.impl.StreamMappingPackageImpl#getDurationUnit()
		 * @generated
		 */
		EEnum DURATION_UNIT = eINSTANCE.getDurationUnit();

	}

} //StreamMappingPackage
