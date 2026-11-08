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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.fennec.model.mapping.impl.MappingImpl;

import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.GuardPolicy;
import org.eclipse.fennec.model.streammapping.Lateness;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stream Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getRecording <em>Recording</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getLateness <em>Lateness</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getBatch <em>Batch</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getExecutionGroup <em>Execution Group</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getKeyframeEverySets <em>Keyframe Every Sets</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getKeyframeEveryMinutes <em>Keyframe Every Minutes</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#isUndoable <em>Undoable</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.impl.StreamMappingImpl#getExcludedFeatures <em>Excluded Features</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StreamMappingImpl extends MappingImpl implements StreamMapping {
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
	protected static final String EXECUTION_GROUP_EDEFAULT = "default";

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
	 * The default value of the '{@link #getGuard() <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected static final GuardPolicy GUARD_EDEFAULT = GuardPolicy.FAIL;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected GuardPolicy guard = GUARD_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeyframeEverySets() <em>Keyframe Every Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyframeEverySets()
	 * @generated
	 * @ordered
	 */
	protected static final int KEYFRAME_EVERY_SETS_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getKeyframeEverySets() <em>Keyframe Every Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyframeEverySets()
	 * @generated
	 * @ordered
	 */
	protected int keyframeEverySets = KEYFRAME_EVERY_SETS_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeyframeEveryMinutes() <em>Keyframe Every Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyframeEveryMinutes()
	 * @generated
	 * @ordered
	 */
	protected static final int KEYFRAME_EVERY_MINUTES_EDEFAULT = 15;

	/**
	 * The cached value of the '{@link #getKeyframeEveryMinutes() <em>Keyframe Every Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyframeEveryMinutes()
	 * @generated
	 * @ordered
	 */
	protected int keyframeEveryMinutes = KEYFRAME_EVERY_MINUTES_EDEFAULT;

	/**
	 * The default value of the '{@link #isUndoable() <em>Undoable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUndoable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UNDOABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUndoable() <em>Undoable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUndoable()
	 * @generated
	 * @ordered
	 */
	protected boolean undoable = UNDOABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExcludedFeatures() <em>Excluded Features</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludedFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<EStructuralFeature> excludedFeatures;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreamMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StreamMappingPackage.Literals.STREAM_MAPPING;
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
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__MODE, oldMode, mode));
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
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__RECORDING, oldRecording, recording));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__LATENESS, oldLateness, lateness, !oldLatenessESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, StreamMappingPackage.STREAM_MAPPING__LATENESS, oldLateness, LATENESS_EDEFAULT, oldLatenessESet));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__BATCH, oldBatch, newBatch);
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
				msgs = ((InternalEObject)batch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_MAPPING__BATCH, null, msgs);
			if (newBatch != null)
				msgs = ((InternalEObject)newBatch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StreamMappingPackage.STREAM_MAPPING__BATCH, null, msgs);
			msgs = basicSetBatch(newBatch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__BATCH, newBatch, newBatch));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__EXECUTION_GROUP, oldExecutionGroup, executionGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GuardPolicy getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGuard(GuardPolicy newGuard) {
		GuardPolicy oldGuard = guard;
		guard = newGuard == null ? GUARD_EDEFAULT : newGuard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getKeyframeEverySets() {
		return keyframeEverySets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKeyframeEverySets(int newKeyframeEverySets) {
		int oldKeyframeEverySets = keyframeEverySets;
		keyframeEverySets = newKeyframeEverySets;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_SETS, oldKeyframeEverySets, keyframeEverySets));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getKeyframeEveryMinutes() {
		return keyframeEveryMinutes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKeyframeEveryMinutes(int newKeyframeEveryMinutes) {
		int oldKeyframeEveryMinutes = keyframeEveryMinutes;
		keyframeEveryMinutes = newKeyframeEveryMinutes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_MINUTES, oldKeyframeEveryMinutes, keyframeEveryMinutes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUndoable() {
		return undoable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUndoable(boolean newUndoable) {
		boolean oldUndoable = undoable;
		undoable = newUndoable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StreamMappingPackage.STREAM_MAPPING__UNDOABLE, oldUndoable, undoable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EStructuralFeature> getExcludedFeatures() {
		if (excludedFeatures == null) {
			excludedFeatures = new EObjectResolvingEList<EStructuralFeature>(EStructuralFeature.class, this, StreamMappingPackage.STREAM_MAPPING__EXCLUDED_FEATURES);
		}
		return excludedFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StreamMappingPackage.STREAM_MAPPING__BATCH:
				return basicSetBatch(null, msgs);
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
			case StreamMappingPackage.STREAM_MAPPING__MODE:
				return getMode();
			case StreamMappingPackage.STREAM_MAPPING__RECORDING:
				return getRecording();
			case StreamMappingPackage.STREAM_MAPPING__LATENESS:
				return getLateness();
			case StreamMappingPackage.STREAM_MAPPING__BATCH:
				return getBatch();
			case StreamMappingPackage.STREAM_MAPPING__EXECUTION_GROUP:
				return getExecutionGroup();
			case StreamMappingPackage.STREAM_MAPPING__GUARD:
				return getGuard();
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_SETS:
				return getKeyframeEverySets();
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_MINUTES:
				return getKeyframeEveryMinutes();
			case StreamMappingPackage.STREAM_MAPPING__UNDOABLE:
				return isUndoable();
			case StreamMappingPackage.STREAM_MAPPING__EXCLUDED_FEATURES:
				return getExcludedFeatures();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StreamMappingPackage.STREAM_MAPPING__MODE:
				setMode((ProjectionMode)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__RECORDING:
				setRecording((RecordingMode)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__LATENESS:
				setLateness((Lateness)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__BATCH:
				setBatch((BatchStrategy)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__EXECUTION_GROUP:
				setExecutionGroup((String)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__GUARD:
				setGuard((GuardPolicy)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_SETS:
				setKeyframeEverySets((Integer)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_MINUTES:
				setKeyframeEveryMinutes((Integer)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__UNDOABLE:
				setUndoable((Boolean)newValue);
				return;
			case StreamMappingPackage.STREAM_MAPPING__EXCLUDED_FEATURES:
				getExcludedFeatures().clear();
				getExcludedFeatures().addAll((Collection<? extends EStructuralFeature>)newValue);
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
			case StreamMappingPackage.STREAM_MAPPING__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_MAPPING__RECORDING:
				setRecording(RECORDING_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_MAPPING__LATENESS:
				unsetLateness();
				return;
			case StreamMappingPackage.STREAM_MAPPING__BATCH:
				setBatch((BatchStrategy)null);
				return;
			case StreamMappingPackage.STREAM_MAPPING__EXECUTION_GROUP:
				setExecutionGroup(EXECUTION_GROUP_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_MAPPING__GUARD:
				setGuard(GUARD_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_SETS:
				setKeyframeEverySets(KEYFRAME_EVERY_SETS_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_MINUTES:
				setKeyframeEveryMinutes(KEYFRAME_EVERY_MINUTES_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_MAPPING__UNDOABLE:
				setUndoable(UNDOABLE_EDEFAULT);
				return;
			case StreamMappingPackage.STREAM_MAPPING__EXCLUDED_FEATURES:
				getExcludedFeatures().clear();
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
			case StreamMappingPackage.STREAM_MAPPING__MODE:
				return mode != MODE_EDEFAULT;
			case StreamMappingPackage.STREAM_MAPPING__RECORDING:
				return recording != RECORDING_EDEFAULT;
			case StreamMappingPackage.STREAM_MAPPING__LATENESS:
				return isSetLateness();
			case StreamMappingPackage.STREAM_MAPPING__BATCH:
				return batch != null;
			case StreamMappingPackage.STREAM_MAPPING__EXECUTION_GROUP:
				return EXECUTION_GROUP_EDEFAULT == null ? executionGroup != null : !EXECUTION_GROUP_EDEFAULT.equals(executionGroup);
			case StreamMappingPackage.STREAM_MAPPING__GUARD:
				return guard != GUARD_EDEFAULT;
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_SETS:
				return keyframeEverySets != KEYFRAME_EVERY_SETS_EDEFAULT;
			case StreamMappingPackage.STREAM_MAPPING__KEYFRAME_EVERY_MINUTES:
				return keyframeEveryMinutes != KEYFRAME_EVERY_MINUTES_EDEFAULT;
			case StreamMappingPackage.STREAM_MAPPING__UNDOABLE:
				return undoable != UNDOABLE_EDEFAULT;
			case StreamMappingPackage.STREAM_MAPPING__EXCLUDED_FEATURES:
				return excludedFeatures != null && !excludedFeatures.isEmpty();
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
		result.append(mode);
		result.append(", recording: ");
		result.append(recording);
		result.append(", lateness: ");
		if (latenessESet) result.append(lateness); else result.append("<unset>");
		result.append(", executionGroup: ");
		result.append(executionGroup);
		result.append(", guard: ");
		result.append(guard);
		result.append(", keyframeEverySets: ");
		result.append(keyframeEverySets);
		result.append(", keyframeEveryMinutes: ");
		result.append(keyframeEveryMinutes);
		result.append(", undoable: ");
		result.append(undoable);
		result.append(')');
		return result.toString();
	}

} //StreamMappingImpl
