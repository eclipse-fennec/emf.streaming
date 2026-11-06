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
package org.eclipse.fennec.model.streammapping.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fennec.model.mapping.impl.SlotImpl;

import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.ChangeRule;
import org.eclipse.fennec.model.streammapping.DeletionRule;
import org.eclipse.fennec.model.streammapping.Lateness;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMappingPackage;
import org.eclipse.fennec.model.streammapping.StreamSlot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stream Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl#getRecording <em>Recording</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl#getLateness <em>Lateness</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl#getBatch <em>Batch</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl#getExecutionGroup <em>Execution Group</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamSlotImpl#getDeletionRule <em>Deletion Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StreamSlotImpl extends SlotImpl implements StreamSlot {
	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected ChangeRule rule;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final ProjectionMode MODE_EDEFAULT = ProjectionMode.PROJECT;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected ProjectionMode mode = MODE_EDEFAULT;

	/**
	 * This is true if the Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean modeESet;

	/**
	 * The default value of the '{@link #getRecording() <em>Recording</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecording()
	 * @generated
	 * @ordered
	 */
	protected static final RecordingMode RECORDING_EDEFAULT = RecordingMode.TIMESERIES;

	/**
	 * The cached value of the '{@link #getRecording() <em>Recording</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecording()
	 * @generated
	 * @ordered
	 */
	protected RecordingMode recording = RECORDING_EDEFAULT;

	/**
	 * This is true if the Recording attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean recordingESet;

	/**
	 * The default value of the '{@link #getLateness() <em>Lateness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLateness()
	 * @generated
	 * @ordered
	 */
	protected static final Lateness LATENESS_EDEFAULT = Lateness.ARRIVAL;

	/**
	 * The cached value of the '{@link #getLateness() <em>Lateness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLateness()
	 * @generated
	 * @ordered
	 */
	protected Lateness lateness = LATENESS_EDEFAULT;

	/**
	 * This is true if the Lateness attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean latenessESet;

	/**
	 * The cached value of the '{@link #getBatch() <em>Batch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBatch()
	 * @generated
	 * @ordered
	 */
	protected BatchStrategy batch;

	/**
	 * The default value of the '{@link #getExecutionGroup() <em>Execution Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutionGroup()
	 * @generated
	 * @ordered
	 */
	protected static final String EXECUTION_GROUP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExecutionGroup() <em>Execution Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutionGroup()
	 * @generated
	 * @ordered
	 */
	protected String executionGroup = EXECUTION_GROUP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDeletionRule() <em>Deletion Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletionRule()
	 * @generated
	 * @ordered
	 */
	protected DeletionRule deletionRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreamSlotImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StreamMappingPackage.Literals.STREAM_SLOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ChangeRule getRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(ChangeRule newRule, NotificationChain msgs) {
		ChangeRule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__RULE, oldRule, newRule);
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
	public void setRule(ChangeRule newRule) {
		if (newRule != rule) {
			NotificationChain msgs = null;
			if (rule != null)
				msgs = ((InternalEObject)rule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_SLOT__RULE, null, msgs);
			if (newRule != null)
				msgs = ((InternalEObject)newRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_SLOT__RULE, null, msgs);
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__RULE, newRule, newRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectionMode getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMode(ProjectionMode newMode) {
		ProjectionMode oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		boolean oldModeESet = modeESet;
		modeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__MODE, oldMode, mode, !oldModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMode() {
		ProjectionMode oldMode = mode;
		boolean oldModeESet = modeESet;
		mode = MODE_EDEFAULT;
		modeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, StreamMappingPackage.STREAM_SLOT__MODE, oldMode, MODE_EDEFAULT, oldModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMode() {
		return modeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RecordingMode getRecording() {
		return recording;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRecording(RecordingMode newRecording) {
		RecordingMode oldRecording = recording;
		recording = newRecording == null ? RECORDING_EDEFAULT : newRecording;
		boolean oldRecordingESet = recordingESet;
		recordingESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__RECORDING, oldRecording, recording, !oldRecordingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetRecording() {
		RecordingMode oldRecording = recording;
		boolean oldRecordingESet = recordingESet;
		recording = RECORDING_EDEFAULT;
		recordingESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, StreamMappingPackage.STREAM_SLOT__RECORDING, oldRecording, RECORDING_EDEFAULT, oldRecordingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetRecording() {
		return recordingESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Lateness getLateness() {
		return lateness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLateness(Lateness newLateness) {
		Lateness oldLateness = lateness;
		lateness = newLateness == null ? LATENESS_EDEFAULT : newLateness;
		boolean oldLatenessESet = latenessESet;
		latenessESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__LATENESS, oldLateness, lateness, !oldLatenessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetLateness() {
		Lateness oldLateness = lateness;
		boolean oldLatenessESet = latenessESet;
		lateness = LATENESS_EDEFAULT;
		latenessESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, StreamMappingPackage.STREAM_SLOT__LATENESS, oldLateness, LATENESS_EDEFAULT, oldLatenessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetLateness() {
		return latenessESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BatchStrategy getBatch() {
		return batch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBatch(BatchStrategy newBatch, NotificationChain msgs) {
		BatchStrategy oldBatch = batch;
		batch = newBatch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__BATCH, oldBatch, newBatch);
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
	public void setBatch(BatchStrategy newBatch) {
		if (newBatch != batch) {
			NotificationChain msgs = null;
			if (batch != null)
				msgs = ((InternalEObject)batch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_SLOT__BATCH, null, msgs);
			if (newBatch != null)
				msgs = ((InternalEObject)newBatch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_SLOT__BATCH, null, msgs);
			msgs = basicSetBatch(newBatch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__BATCH, newBatch, newBatch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExecutionGroup() {
		return executionGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExecutionGroup(String newExecutionGroup) {
		String oldExecutionGroup = executionGroup;
		executionGroup = newExecutionGroup;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__EXECUTION_GROUP, oldExecutionGroup, executionGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeletionRule getDeletionRule() {
		return deletionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeletionRule(DeletionRule newDeletionRule, NotificationChain msgs) {
		DeletionRule oldDeletionRule = deletionRule;
		deletionRule = newDeletionRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__DELETION_RULE, oldDeletionRule, newDeletionRule);
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
	public void setDeletionRule(DeletionRule newDeletionRule) {
		if (newDeletionRule != deletionRule) {
			NotificationChain msgs = null;
			if (deletionRule != null)
				msgs = ((InternalEObject)deletionRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_SLOT__DELETION_RULE, null, msgs);
			if (newDeletionRule != null)
				msgs = ((InternalEObject)newDeletionRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_SLOT__DELETION_RULE, null, msgs);
			msgs = basicSetDeletionRule(newDeletionRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_SLOT__DELETION_RULE, newDeletionRule, newDeletionRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StreamMappingPackage.STREAM_SLOT__RULE:
				return basicSetRule(null, msgs);
			case StreamMappingPackage.STREAM_SLOT__BATCH:
				return basicSetBatch(null, msgs);
			case StreamMappingPackage.STREAM_SLOT__DELETION_RULE:
				return basicSetDeletionRule(null, msgs);
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
			case StreamMappingPackage.STREAM_SLOT__RULE:
				return getRule();
			case StreamMappingPackage.STREAM_SLOT__MODE:
				return getMode();
			case StreamMappingPackage.STREAM_SLOT__RECORDING:
				return getRecording();
			case StreamMappingPackage.STREAM_SLOT__LATENESS:
				return getLateness();
			case StreamMappingPackage.STREAM_SLOT__BATCH:
				return getBatch();
			case StreamMappingPackage.STREAM_SLOT__EXECUTION_GROUP:
				return getExecutionGroup();
			case StreamMappingPackage.STREAM_SLOT__DELETION_RULE:
				return getDeletionRule();
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
			case StreamMappingPackage.STREAM_SLOT__RULE:
				setRule((ChangeRule)newValue);
				return;
			case StreamMappingPackage.STREAM_SLOT__MODE:
				setMode((ProjectionMode)newValue);
				return;
			case StreamMappingPackage.STREAM_SLOT__RECORDING:
				setRecording((RecordingMode)newValue);
				return;
			case StreamMappingPackage.STREAM_SLOT__LATENESS:
				setLateness((Lateness)newValue);
				return;
			case StreamMappingPackage.STREAM_SLOT__BATCH:
				setBatch((BatchStrategy)newValue);
				return;
			case StreamMappingPackage.STREAM_SLOT__EXECUTION_GROUP:
				setExecutionGroup((String)newValue);
				return;
			case StreamMappingPackage.STREAM_SLOT__DELETION_RULE:
				setDeletionRule((DeletionRule)newValue);
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
			case StreamMappingPackage.STREAM_SLOT__RULE:
				setRule((ChangeRule)null);
				return;
			case StreamMappingPackage.STREAM_SLOT__MODE:
				unsetMode();
				return;
			case StreamMappingPackage.STREAM_SLOT__RECORDING:
				unsetRecording();
				return;
			case StreamMappingPackage.STREAM_SLOT__LATENESS:
				unsetLateness();
				return;
			case StreamMappingPackage.STREAM_SLOT__BATCH:
				setBatch((BatchStrategy)null);
				return;
			case StreamMappingPackage.STREAM_SLOT__EXECUTION_GROUP:
				setExecutionGroup(EXECUTION_GROUP_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_SLOT__DELETION_RULE:
				setDeletionRule((DeletionRule)null);
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
			case StreamMappingPackage.STREAM_SLOT__RULE:
				return rule != null;
			case StreamMappingPackage.STREAM_SLOT__MODE:
				return isSetMode();
			case StreamMappingPackage.STREAM_SLOT__RECORDING:
				return isSetRecording();
			case StreamMappingPackage.STREAM_SLOT__LATENESS:
				return isSetLateness();
			case StreamMappingPackage.STREAM_SLOT__BATCH:
				return batch != null;
			case StreamMappingPackage.STREAM_SLOT__EXECUTION_GROUP:
				return EXECUTION_GROUP_EDEFAULT == null ? executionGroup != null : !EXECUTION_GROUP_EDEFAULT.equals(executionGroup);
			case StreamMappingPackage.STREAM_SLOT__DELETION_RULE:
				return deletionRule != null;
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
		result.append(" (mode: ");
		if (modeESet) result.append(mode); else result.append("<unset>");
		result.append(", recording: ");
		if (recordingESet) result.append(recording); else result.append("<unset>");
		result.append(", lateness: ");
		if (latenessESet) result.append(lateness); else result.append("<unset>");
		result.append(", executionGroup: ");
		result.append(executionGroup);
		result.append(')');
		return result.toString();
	}

} //StreamSlotImpl
