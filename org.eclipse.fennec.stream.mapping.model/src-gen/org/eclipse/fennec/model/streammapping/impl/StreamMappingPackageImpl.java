/**
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
package org.eclipse.fennec.model.streammapping.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fennec.atlas.configuration.AtlasConfigPackage;

import org.eclipse.fennec.model.mapping.MappingPackage;

import org.eclipse.fennec.model.streammapping.AbsoluteChange;
import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.ChangeRule;
import org.eclipse.fennec.model.streammapping.CompositeRule;
import org.eclipse.fennec.model.streammapping.CountChange;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.model.streammapping.DurationUnit;
import org.eclipse.fennec.model.streammapping.ExpressionRule;
import org.eclipse.fennec.model.streammapping.GuardPolicy;
import org.eclipse.fennec.model.streammapping.Lateness;
import org.eclipse.fennec.model.streammapping.PercentageChange;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.RuleOperator;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamMappingPackage;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.model.streammapping.TimeThrottle;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StreamMappingPackageImpl extends EPackageImpl implements StreamMappingPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass streamMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass streamSlotEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass absoluteChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass percentageChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass countChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeThrottleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass batchStrategyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deletionRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum projectionModeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum recordingModeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum latenessEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum guardPolicyEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum batchKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum ruleOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum durationUnitEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StreamMappingPackageImpl() {
		super(eNS_URI, StreamMappingFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link StreamMappingPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StreamMappingPackage init() {
		if (isInited) return (StreamMappingPackage)EPackage.Registry.INSTANCE.getEPackage(StreamMappingPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredStreamMappingPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		StreamMappingPackageImpl theStreamMappingPackage = registeredStreamMappingPackage instanceof StreamMappingPackageImpl ? (StreamMappingPackageImpl)registeredStreamMappingPackage : new StreamMappingPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		MappingPackage.eINSTANCE.eClass();
		AtlasConfigPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theStreamMappingPackage.createPackageContents();

		// Initialize created meta-data
		theStreamMappingPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStreamMappingPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StreamMappingPackage.eNS_URI, theStreamMappingPackage);
		return theStreamMappingPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStreamMapping() {
		return streamMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_Mode() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_Recording() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_Lateness() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStreamMapping_Batch() {
		return (EReference)streamMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_ExecutionGroup() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_Guard() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_KeyframeEverySets() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_KeyframeEveryMinutes() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamMapping_Undoable() {
		return (EAttribute)streamMappingEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStreamSlot() {
		return streamSlotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStreamSlot_Rule() {
		return (EReference)streamSlotEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamSlot_Mode() {
		return (EAttribute)streamSlotEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamSlot_Recording() {
		return (EAttribute)streamSlotEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamSlot_Lateness() {
		return (EAttribute)streamSlotEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStreamSlot_Batch() {
		return (EReference)streamSlotEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreamSlot_ExecutionGroup() {
		return (EAttribute)streamSlotEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStreamSlot_DeletionRule() {
		return (EReference)streamSlotEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getChangeRule() {
		return changeRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbsoluteChange() {
		return absoluteChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbsoluteChange_Delta() {
		return (EAttribute)absoluteChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPercentageChange() {
		return percentageChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPercentageChange_Percent() {
		return (EAttribute)percentageChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCountChange() {
		return countChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCountChange_N() {
		return (EAttribute)countChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTimeThrottle() {
		return timeThrottleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTimeThrottle_Interval() {
		return (EAttribute)timeThrottleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTimeThrottle_Unit() {
		return (EAttribute)timeThrottleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompositeRule() {
		return compositeRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompositeRule_Operator() {
		return (EAttribute)compositeRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompositeRule_Rules() {
		return (EReference)compositeRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExpressionRule() {
		return expressionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExpressionRule_Expression() {
		return (EAttribute)expressionRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBatchStrategy() {
		return batchStrategyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBatchStrategy_Kind() {
		return (EAttribute)batchStrategyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBatchStrategy_Count() {
		return (EAttribute)batchStrategyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBatchStrategy_Window() {
		return (EAttribute)batchStrategyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBatchStrategy_Unit() {
		return (EAttribute)batchStrategyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDeletionRule() {
		return deletionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDeletionRule_Retention() {
		return (EAttribute)deletionRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDeletionRule_RetentionUnit() {
		return (EAttribute)deletionRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDeletionRule_MaxCount() {
		return (EAttribute)deletionRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDeletionRule_CleanupInterval() {
		return (EAttribute)deletionRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDeletionRule_CleanupIntervalUnit() {
		return (EAttribute)deletionRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getProjectionMode() {
		return projectionModeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getRecordingMode() {
		return recordingModeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getLateness() {
		return latenessEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getGuardPolicy() {
		return guardPolicyEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getBatchKind() {
		return batchKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getRuleOperator() {
		return ruleOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDurationUnit() {
		return durationUnitEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StreamMappingFactory getStreamMappingFactory() {
		return (StreamMappingFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		streamMappingEClass = createEClass(STREAM_MAPPING);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__MODE);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__RECORDING);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__LATENESS);
		createEReference(streamMappingEClass, STREAM_MAPPING__BATCH);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__EXECUTION_GROUP);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__GUARD);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__KEYFRAME_EVERY_SETS);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__KEYFRAME_EVERY_MINUTES);
		createEAttribute(streamMappingEClass, STREAM_MAPPING__UNDOABLE);

		streamSlotEClass = createEClass(STREAM_SLOT);
		createEReference(streamSlotEClass, STREAM_SLOT__RULE);
		createEAttribute(streamSlotEClass, STREAM_SLOT__MODE);
		createEAttribute(streamSlotEClass, STREAM_SLOT__RECORDING);
		createEAttribute(streamSlotEClass, STREAM_SLOT__LATENESS);
		createEReference(streamSlotEClass, STREAM_SLOT__BATCH);
		createEAttribute(streamSlotEClass, STREAM_SLOT__EXECUTION_GROUP);
		createEReference(streamSlotEClass, STREAM_SLOT__DELETION_RULE);

		changeRuleEClass = createEClass(CHANGE_RULE);

		absoluteChangeEClass = createEClass(ABSOLUTE_CHANGE);
		createEAttribute(absoluteChangeEClass, ABSOLUTE_CHANGE__DELTA);

		percentageChangeEClass = createEClass(PERCENTAGE_CHANGE);
		createEAttribute(percentageChangeEClass, PERCENTAGE_CHANGE__PERCENT);

		countChangeEClass = createEClass(COUNT_CHANGE);
		createEAttribute(countChangeEClass, COUNT_CHANGE__N);

		timeThrottleEClass = createEClass(TIME_THROTTLE);
		createEAttribute(timeThrottleEClass, TIME_THROTTLE__INTERVAL);
		createEAttribute(timeThrottleEClass, TIME_THROTTLE__UNIT);

		compositeRuleEClass = createEClass(COMPOSITE_RULE);
		createEAttribute(compositeRuleEClass, COMPOSITE_RULE__OPERATOR);
		createEReference(compositeRuleEClass, COMPOSITE_RULE__RULES);

		expressionRuleEClass = createEClass(EXPRESSION_RULE);
		createEAttribute(expressionRuleEClass, EXPRESSION_RULE__EXPRESSION);

		batchStrategyEClass = createEClass(BATCH_STRATEGY);
		createEAttribute(batchStrategyEClass, BATCH_STRATEGY__KIND);
		createEAttribute(batchStrategyEClass, BATCH_STRATEGY__COUNT);
		createEAttribute(batchStrategyEClass, BATCH_STRATEGY__WINDOW);
		createEAttribute(batchStrategyEClass, BATCH_STRATEGY__UNIT);

		deletionRuleEClass = createEClass(DELETION_RULE);
		createEAttribute(deletionRuleEClass, DELETION_RULE__RETENTION);
		createEAttribute(deletionRuleEClass, DELETION_RULE__RETENTION_UNIT);
		createEAttribute(deletionRuleEClass, DELETION_RULE__MAX_COUNT);
		createEAttribute(deletionRuleEClass, DELETION_RULE__CLEANUP_INTERVAL);
		createEAttribute(deletionRuleEClass, DELETION_RULE__CLEANUP_INTERVAL_UNIT);

		// Create enums
		projectionModeEEnum = createEEnum(PROJECTION_MODE);
		recordingModeEEnum = createEEnum(RECORDING_MODE);
		latenessEEnum = createEEnum(LATENESS);
		guardPolicyEEnum = createEEnum(GUARD_POLICY);
		batchKindEEnum = createEEnum(BATCH_KIND);
		ruleOperatorEEnum = createEEnum(RULE_OPERATOR);
		durationUnitEEnum = createEEnum(DURATION_UNIT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		MappingPackage theMappingPackage = (MappingPackage)EPackage.Registry.INSTANCE.getEPackage(MappingPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		streamMappingEClass.getESuperTypes().add(theMappingPackage.getMapping());
		streamSlotEClass.getESuperTypes().add(theMappingPackage.getSlot());
		absoluteChangeEClass.getESuperTypes().add(this.getChangeRule());
		percentageChangeEClass.getESuperTypes().add(this.getChangeRule());
		countChangeEClass.getESuperTypes().add(this.getChangeRule());
		timeThrottleEClass.getESuperTypes().add(this.getChangeRule());
		compositeRuleEClass.getESuperTypes().add(this.getChangeRule());
		expressionRuleEClass.getESuperTypes().add(this.getChangeRule());

		// Initialize classes, features, and operations; add parameters
		initEClass(streamMappingEClass, StreamMapping.class, "StreamMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStreamMapping_Mode(), this.getProjectionMode(), "mode", "PROJECT", 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamMapping_Recording(), this.getRecordingMode(), "recording", "TIMESERIES", 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamMapping_Lateness(), this.getLateness(), "lateness", null, 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStreamMapping_Batch(), this.getBatchStrategy(), null, "batch", null, 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamMapping_ExecutionGroup(), ecorePackage.getEString(), "executionGroup", "default", 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamMapping_Guard(), this.getGuardPolicy(), "guard", "FAIL", 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamMapping_KeyframeEverySets(), ecorePackage.getEInt(), "keyframeEverySets", "100", 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamMapping_KeyframeEveryMinutes(), ecorePackage.getEInt(), "keyframeEveryMinutes", "15", 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamMapping_Undoable(), ecorePackage.getEBoolean(), "undoable", "false", 0, 1, StreamMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(streamSlotEClass, StreamSlot.class, "StreamSlot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStreamSlot_Rule(), this.getChangeRule(), null, "rule", null, 0, 1, StreamSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamSlot_Mode(), this.getProjectionMode(), "mode", null, 0, 1, StreamSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamSlot_Recording(), this.getRecordingMode(), "recording", null, 0, 1, StreamSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamSlot_Lateness(), this.getLateness(), "lateness", null, 0, 1, StreamSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStreamSlot_Batch(), this.getBatchStrategy(), null, "batch", null, 0, 1, StreamSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreamSlot_ExecutionGroup(), ecorePackage.getEString(), "executionGroup", null, 0, 1, StreamSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStreamSlot_DeletionRule(), this.getDeletionRule(), null, "deletionRule", null, 0, 1, StreamSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changeRuleEClass, ChangeRule.class, "ChangeRule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(absoluteChangeEClass, AbsoluteChange.class, "AbsoluteChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbsoluteChange_Delta(), ecorePackage.getEDouble(), "delta", null, 1, 1, AbsoluteChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(percentageChangeEClass, PercentageChange.class, "PercentageChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPercentageChange_Percent(), ecorePackage.getEDouble(), "percent", null, 1, 1, PercentageChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(countChangeEClass, CountChange.class, "CountChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCountChange_N(), ecorePackage.getEInt(), "n", "1", 1, 1, CountChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(timeThrottleEClass, TimeThrottle.class, "TimeThrottle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTimeThrottle_Interval(), ecorePackage.getELong(), "interval", null, 1, 1, TimeThrottle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimeThrottle_Unit(), this.getDurationUnit(), "unit", "SECONDS", 0, 1, TimeThrottle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositeRuleEClass, CompositeRule.class, "CompositeRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompositeRule_Operator(), this.getRuleOperator(), "operator", "AND", 0, 1, CompositeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositeRule_Rules(), this.getChangeRule(), null, "rules", null, 0, -1, CompositeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionRuleEClass, ExpressionRule.class, "ExpressionRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExpressionRule_Expression(), ecorePackage.getEString(), "expression", null, 1, 1, ExpressionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(batchStrategyEClass, BatchStrategy.class, "BatchStrategy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBatchStrategy_Kind(), this.getBatchKind(), "kind", "IMMEDIATE", 0, 1, BatchStrategy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBatchStrategy_Count(), ecorePackage.getEInt(), "count", "1", 0, 1, BatchStrategy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBatchStrategy_Window(), ecorePackage.getELong(), "window", "0", 0, 1, BatchStrategy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBatchStrategy_Unit(), this.getDurationUnit(), "unit", "SECONDS", 0, 1, BatchStrategy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deletionRuleEClass, DeletionRule.class, "DeletionRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeletionRule_Retention(), ecorePackage.getELong(), "retention", "0", 0, 1, DeletionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeletionRule_RetentionUnit(), this.getDurationUnit(), "retentionUnit", "DAYS", 0, 1, DeletionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeletionRule_MaxCount(), ecorePackage.getEInt(), "maxCount", "0", 0, 1, DeletionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeletionRule_CleanupInterval(), ecorePackage.getELong(), "cleanupInterval", "0", 0, 1, DeletionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeletionRule_CleanupIntervalUnit(), this.getDurationUnit(), "cleanupIntervalUnit", "HOURS", 0, 1, DeletionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(projectionModeEEnum, ProjectionMode.class, "ProjectionMode");
		addEEnumLiteral(projectionModeEEnum, ProjectionMode.PROJECT);
		addEEnumLiteral(projectionModeEEnum, ProjectionMode.AUDIT_ONLY);

		initEEnum(recordingModeEEnum, RecordingMode.class, "RecordingMode");
		addEEnumLiteral(recordingModeEEnum, RecordingMode.TIMESERIES);
		addEEnumLiteral(recordingModeEEnum, RecordingMode.CHANGELOG);

		initEEnum(latenessEEnum, Lateness.class, "Lateness");
		addEEnumLiteral(latenessEEnum, Lateness.ARRIVAL);
		addEEnumLiteral(latenessEEnum, Lateness.EVENT_TIME);

		initEEnum(guardPolicyEEnum, GuardPolicy.class, "GuardPolicy");
		addEEnumLiteral(guardPolicyEEnum, GuardPolicy.FAIL);
		addEEnumLiteral(guardPolicyEEnum, GuardPolicy.LAST_WRITER_WINS);
		addEEnumLiteral(guardPolicyEEnum, GuardPolicy.MERGE);

		initEEnum(batchKindEEnum, BatchKind.class, "BatchKind");
		addEEnumLiteral(batchKindEEnum, BatchKind.IMMEDIATE);
		addEEnumLiteral(batchKindEEnum, BatchKind.COMMIT);
		addEEnumLiteral(batchKindEEnum, BatchKind.COUNT);
		addEEnumLiteral(batchKindEEnum, BatchKind.WINDOW);

		initEEnum(ruleOperatorEEnum, RuleOperator.class, "RuleOperator");
		addEEnumLiteral(ruleOperatorEEnum, RuleOperator.AND);
		addEEnumLiteral(ruleOperatorEEnum, RuleOperator.OR);
		addEEnumLiteral(ruleOperatorEEnum, RuleOperator.NOT);

		initEEnum(durationUnitEEnum, DurationUnit.class, "DurationUnit");
		addEEnumLiteral(durationUnitEEnum, DurationUnit.MILLISECONDS);
		addEEnumLiteral(durationUnitEEnum, DurationUnit.SECONDS);
		addEEnumLiteral(durationUnitEEnum, DurationUnit.MINUTES);
		addEEnumLiteral(durationUnitEEnum, DurationUnit.HOURS);
		addEEnumLiteral(durationUnitEEnum, DurationUnit.DAYS);

		// Create resource
		createResource(eNS_URI);
	}

} //StreamMappingPackageImpl
