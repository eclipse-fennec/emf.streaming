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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fennec.model.streammapping.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StreamMappingFactoryImpl extends EFactoryImpl implements StreamMappingFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StreamMappingFactory init() {
		try {
			StreamMappingFactory theStreamMappingFactory = (StreamMappingFactory)EPackage.Registry.INSTANCE.getEFactory(StreamMappingPackage.eNS_URI);
			if (theStreamMappingFactory != null) {
				return theStreamMappingFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StreamMappingFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamMappingFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case StreamMappingPackage.STREAM_MAPPING: return createStreamMapping();
			case StreamMappingPackage.STREAM_SLOT: return createStreamSlot();
			case StreamMappingPackage.ABSOLUTE_CHANGE: return createAbsoluteChange();
			case StreamMappingPackage.PERCENTAGE_CHANGE: return createPercentageChange();
			case StreamMappingPackage.COUNT_CHANGE: return createCountChange();
			case StreamMappingPackage.TIME_THROTTLE: return createTimeThrottle();
			case StreamMappingPackage.COMPOSITE_RULE: return createCompositeRule();
			case StreamMappingPackage.EXPRESSION_RULE: return createExpressionRule();
			case StreamMappingPackage.BATCH_STRATEGY: return createBatchStrategy();
			case StreamMappingPackage.DELETION_RULE: return createDeletionRule();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case StreamMappingPackage.PROJECTION_MODE:
				return createProjectionModeFromString(eDataType, initialValue);
			case StreamMappingPackage.RECORDING_MODE:
				return createRecordingModeFromString(eDataType, initialValue);
			case StreamMappingPackage.LATENESS:
				return createLatenessFromString(eDataType, initialValue);
			case StreamMappingPackage.GUARD_POLICY:
				return createGuardPolicyFromString(eDataType, initialValue);
			case StreamMappingPackage.BATCH_KIND:
				return createBatchKindFromString(eDataType, initialValue);
			case StreamMappingPackage.RULE_OPERATOR:
				return createRuleOperatorFromString(eDataType, initialValue);
			case StreamMappingPackage.DURATION_UNIT:
				return createDurationUnitFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case StreamMappingPackage.PROJECTION_MODE:
				return convertProjectionModeToString(eDataType, instanceValue);
			case StreamMappingPackage.RECORDING_MODE:
				return convertRecordingModeToString(eDataType, instanceValue);
			case StreamMappingPackage.LATENESS:
				return convertLatenessToString(eDataType, instanceValue);
			case StreamMappingPackage.GUARD_POLICY:
				return convertGuardPolicyToString(eDataType, instanceValue);
			case StreamMappingPackage.BATCH_KIND:
				return convertBatchKindToString(eDataType, instanceValue);
			case StreamMappingPackage.RULE_OPERATOR:
				return convertRuleOperatorToString(eDataType, instanceValue);
			case StreamMappingPackage.DURATION_UNIT:
				return convertDurationUnitToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StreamMapping createStreamMapping() {
		StreamMappingImpl streamMapping = new StreamMappingImpl();
		return streamMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StreamSlot createStreamSlot() {
		StreamSlotImpl streamSlot = new StreamSlotImpl();
		return streamSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbsoluteChange createAbsoluteChange() {
		AbsoluteChangeImpl absoluteChange = new AbsoluteChangeImpl();
		return absoluteChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PercentageChange createPercentageChange() {
		PercentageChangeImpl percentageChange = new PercentageChangeImpl();
		return percentageChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CountChange createCountChange() {
		CountChangeImpl countChange = new CountChangeImpl();
		return countChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TimeThrottle createTimeThrottle() {
		TimeThrottleImpl timeThrottle = new TimeThrottleImpl();
		return timeThrottle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeRule createCompositeRule() {
		CompositeRuleImpl compositeRule = new CompositeRuleImpl();
		return compositeRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExpressionRule createExpressionRule() {
		ExpressionRuleImpl expressionRule = new ExpressionRuleImpl();
		return expressionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BatchStrategy createBatchStrategy() {
		BatchStrategyImpl batchStrategy = new BatchStrategyImpl();
		return batchStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeletionRule createDeletionRule() {
		DeletionRuleImpl deletionRule = new DeletionRuleImpl();
		return deletionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectionMode createProjectionModeFromString(EDataType eDataType, String initialValue) {
		ProjectionMode result = ProjectionMode.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProjectionModeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RecordingMode createRecordingModeFromString(EDataType eDataType, String initialValue) {
		RecordingMode result = RecordingMode.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRecordingModeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Lateness createLatenessFromString(EDataType eDataType, String initialValue) {
		Lateness result = Lateness.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLatenessToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GuardPolicy createGuardPolicyFromString(EDataType eDataType, String initialValue) {
		GuardPolicy result = GuardPolicy.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGuardPolicyToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BatchKind createBatchKindFromString(EDataType eDataType, String initialValue) {
		BatchKind result = BatchKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBatchKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleOperator createRuleOperatorFromString(EDataType eDataType, String initialValue) {
		RuleOperator result = RuleOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRuleOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DurationUnit createDurationUnitFromString(EDataType eDataType, String initialValue) {
		DurationUnit result = DurationUnit.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDurationUnitToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StreamMappingPackage getStreamMappingPackage() {
		return (StreamMappingPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StreamMappingPackage getPackage() {
		return StreamMappingPackage.eINSTANCE;
	}

} //StreamMappingFactoryImpl
