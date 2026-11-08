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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.fennec.model.mapping.Mapping;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stream Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A mapping whose slots feed the change stream of their target objects. Its attributes are the defaults of its slots; a slot overrides by setting its own value.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getRecording <em>Recording</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getLateness <em>Lateness</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getBatch <em>Batch</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getExecutionGroup <em>Execution Group</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getGuard <em>Guard</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEverySets <em>Keyframe Every Sets</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEveryMinutes <em>Keyframe Every Minutes</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#isUndoable <em>Undoable</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamMapping#getExcludedFeatures <em>Excluded Features</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping()
 * @model
 * @generated
 */
@ProviderType
public interface StreamMapping extends Mapping {
	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The default value is <code>"PROJECT"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.ProjectionMode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default for the slots: write the value into the target object (PROJECT) or only into the stream (AUDIT_ONLY).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.ProjectionMode
	 * @see #setMode(ProjectionMode)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_Mode()
	 * @model default="PROJECT"
	 * @generated
	 */
	ProjectionMode getMode();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.ProjectionMode
	 * @see #getMode()
	 * @generated
	 */
	void setMode(ProjectionMode value);

	/**
	 * Returns the value of the '<em><b>Recording</b></em>' attribute.
	 * The default value is <code>"TIMESERIES"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.RecordingMode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default for the slots: absolute samples (TIMESERIES) or deltas with old value (CHANGELOG). CHANGELOG is required for undo and replication.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Recording</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.RecordingMode
	 * @see #setRecording(RecordingMode)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_Recording()
	 * @model default="TIMESERIES"
	 * @generated
	 */
	RecordingMode getRecording();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getRecording <em>Recording</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recording</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.RecordingMode
	 * @see #getRecording()
	 * @generated
	 */
	void setRecording(RecordingMode value);

	/**
	 * Returns the value of the '<em><b>Lateness</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.Lateness}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default for the slots: what a value with an older event time than the last one does. Unset = by recording (TIMESERIES: EVENT_TIME, CHANGELOG: ARRIVAL).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Lateness</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.Lateness
	 * @see #isSetLateness()
	 * @see #unsetLateness()
	 * @see #setLateness(Lateness)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_Lateness()
	 * @model unsettable="true"
	 * @generated
	 */
	Lateness getLateness();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getLateness <em>Lateness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lateness</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.Lateness
	 * @see #isSetLateness()
	 * @see #unsetLateness()
	 * @see #getLateness()
	 * @generated
	 */
	void setLateness(Lateness value);

	/**
	 * Unsets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getLateness <em>Lateness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLateness()
	 * @see #getLateness()
	 * @see #setLateness(Lateness)
	 * @generated
	 */
	void unsetLateness();

	/**
	 * Returns whether the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getLateness <em>Lateness</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Lateness</em>' attribute is set.
	 * @see #unsetLateness()
	 * @see #getLateness()
	 * @see #setLateness(Lateness)
	 * @generated
	 */
	boolean isSetLateness();

	/**
	 * Returns the value of the '<em><b>Batch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default batching of the slots. Unset = IMMEDIATE (every value its own change set).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Batch</em>' containment reference.
	 * @see #setBatch(BatchStrategy)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_Batch()
	 * @model containment="true"
	 * @generated
	 */
	BatchStrategy getBatch();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getBatch <em>Batch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Batch</em>' containment reference.
	 * @see #getBatch()
	 * @generated
	 */
	void setBatch(BatchStrategy value);

	/**
	 * Returns the value of the '<em><b>Execution Group</b></em>' attribute.
	 * The default value is <code>"default"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default execution group: the named thread pool of the event core the target objects of this mapping run in.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Execution Group</em>' attribute.
	 * @see #setExecutionGroup(String)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_ExecutionGroup()
	 * @model default="default"
	 * @generated
	 */
	String getExecutionGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getExecutionGroup <em>Execution Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Group</em>' attribute.
	 * @see #getExecutionGroup()
	 * @generated
	 */
	void setExecutionGroup(String value);

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' attribute.
	 * The default value is <code>"FAIL"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.GuardPolicy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * What applying a received CHANGELOG change does when its old value does not match the target.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Guard</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.GuardPolicy
	 * @see #setGuard(GuardPolicy)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_Guard()
	 * @model default="FAIL"
	 * @generated
	 */
	GuardPolicy getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getGuard <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.GuardPolicy
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(GuardPolicy value);

	/**
	 * Returns the value of the '<em><b>Keyframe Every Sets</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A KEYFRAME of a target object is written after this many change sets of it (0 = never by count).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Keyframe Every Sets</em>' attribute.
	 * @see #setKeyframeEverySets(int)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_KeyframeEverySets()
	 * @model default="100"
	 * @generated
	 */
	int getKeyframeEverySets();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEverySets <em>Keyframe Every Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Keyframe Every Sets</em>' attribute.
	 * @see #getKeyframeEverySets()
	 * @generated
	 */
	void setKeyframeEverySets(int value);

	/**
	 * Returns the value of the '<em><b>Keyframe Every Minutes</b></em>' attribute.
	 * The default value is <code>"15"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * ... or after this many minutes, whichever comes first (0 = never by time).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Keyframe Every Minutes</em>' attribute.
	 * @see #setKeyframeEveryMinutes(int)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_KeyframeEveryMinutes()
	 * @model default="15"
	 * @generated
	 */
	int getKeyframeEveryMinutes();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#getKeyframeEveryMinutes <em>Keyframe Every Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Keyframe Every Minutes</em>' attribute.
	 * @see #getKeyframeEveryMinutes()
	 * @generated
	 */
	void setKeyframeEveryMinutes(int value);

	/**
	 * Returns the value of the '<em><b>Undoable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the change stream of the target objects offers undo/redo (CHANGELOG slots only).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Undoable</em>' attribute.
	 * @see #setUndoable(boolean)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_Undoable()
	 * @model default="false"
	 * @generated
	 */
	boolean isUndoable();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamMapping#isUndoable <em>Undoable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Undoable</em>' attribute.
	 * @see #isUndoable()
	 * @generated
	 */
	void setUndoable(boolean value);

	/**
	 * Returns the value of the '<em><b>Excluded Features</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EStructuralFeature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * With includeUnmapped = true: the attributes of the target that are not streamed. Without it, ignored.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Excluded Features</em>' reference list.
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamMapping_ExcludedFeatures()
	 * @model
	 * @generated
	 */
	EList<EStructuralFeature> getExcludedFeatures();

} // StreamMapping
