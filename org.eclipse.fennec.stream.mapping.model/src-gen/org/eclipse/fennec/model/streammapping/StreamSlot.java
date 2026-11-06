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

import org.eclipse.fennec.model.mapping.Slot;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stream Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A slot whose values enter the change stream. Every attribute here is unsettable: unset means the StreamMapping's value.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamSlot#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRecording <em>Recording</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamSlot#getLateness <em>Lateness</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamSlot#getBatch <em>Batch</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamSlot#getExecutionGroup <em>Execution Group</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.streammapping.StreamSlot#getDeletionRule <em>Deletion Rule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot()
 * @model
 * @generated
 */
@ProviderType
public interface StreamSlot extends Slot {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Decides whether a value enters the stream; compared against the last value that passed. Unset = every change passes.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(ChangeRule)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot_Rule()
	 * @model containment="true"
	 * @generated
	 */
	ChangeRule getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(ChangeRule value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.ProjectionMode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides the mapping's mode.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.ProjectionMode
	 * @see #isSetMode()
	 * @see #unsetMode()
	 * @see #setMode(ProjectionMode)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot_Mode()
	 * @model unsettable="true"
	 * @generated
	 */
	ProjectionMode getMode();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.ProjectionMode
	 * @see #isSetMode()
	 * @see #unsetMode()
	 * @see #getMode()
	 * @generated
	 */
	void setMode(ProjectionMode value);

	/**
	 * Unsets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMode()
	 * @see #getMode()
	 * @see #setMode(ProjectionMode)
	 * @generated
	 */
	void unsetMode();

	/**
	 * Returns whether the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getMode <em>Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Mode</em>' attribute is set.
	 * @see #unsetMode()
	 * @see #getMode()
	 * @see #setMode(ProjectionMode)
	 * @generated
	 */
	boolean isSetMode();

	/**
	 * Returns the value of the '<em><b>Recording</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.RecordingMode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides the mapping's recording.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Recording</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.RecordingMode
	 * @see #isSetRecording()
	 * @see #unsetRecording()
	 * @see #setRecording(RecordingMode)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot_Recording()
	 * @model unsettable="true"
	 * @generated
	 */
	RecordingMode getRecording();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRecording <em>Recording</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recording</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.RecordingMode
	 * @see #isSetRecording()
	 * @see #unsetRecording()
	 * @see #getRecording()
	 * @generated
	 */
	void setRecording(RecordingMode value);

	/**
	 * Unsets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRecording <em>Recording</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRecording()
	 * @see #getRecording()
	 * @see #setRecording(RecordingMode)
	 * @generated
	 */
	void unsetRecording();

	/**
	 * Returns whether the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getRecording <em>Recording</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Recording</em>' attribute is set.
	 * @see #unsetRecording()
	 * @see #getRecording()
	 * @see #setRecording(RecordingMode)
	 * @generated
	 */
	boolean isSetRecording();

	/**
	 * Returns the value of the '<em><b>Lateness</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.streammapping.Lateness}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides the mapping's lateness.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Lateness</em>' attribute.
	 * @see org.eclipse.fennec.model.streammapping.Lateness
	 * @see #isSetLateness()
	 * @see #unsetLateness()
	 * @see #setLateness(Lateness)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot_Lateness()
	 * @model unsettable="true"
	 * @generated
	 */
	Lateness getLateness();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getLateness <em>Lateness</em>}' attribute.
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
	 * Unsets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getLateness <em>Lateness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLateness()
	 * @see #getLateness()
	 * @see #setLateness(Lateness)
	 * @generated
	 */
	void unsetLateness();

	/**
	 * Returns whether the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getLateness <em>Lateness</em>}' attribute is set.
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
	 * Overrides the mapping's batching.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Batch</em>' containment reference.
	 * @see #setBatch(BatchStrategy)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot_Batch()
	 * @model containment="true"
	 * @generated
	 */
	BatchStrategy getBatch();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getBatch <em>Batch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Batch</em>' containment reference.
	 * @see #getBatch()
	 * @generated
	 */
	void setBatch(BatchStrategy value);

	/**
	 * Returns the value of the '<em><b>Execution Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides the mapping's execution group. A target object with slots of several groups runs in the highest-ranked one.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Execution Group</em>' attribute.
	 * @see #setExecutionGroup(String)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot_ExecutionGroup()
	 * @model
	 * @generated
	 */
	String getExecutionGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getExecutionGroup <em>Execution Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Group</em>' attribute.
	 * @see #getExecutionGroup()
	 * @generated
	 */
	void setExecutionGroup(String value);

	/**
	 * Returns the value of the '<em><b>Deletion Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How long the stream of this slot is kept. Carried only; the store applies it or reports that it cannot.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Deletion Rule</em>' containment reference.
	 * @see #setDeletionRule(DeletionRule)
	 * @see org.eclipse.fennec.model.streammapping.StreamMappingPackage#getStreamSlot_DeletionRule()
	 * @model containment="true"
	 * @generated
	 */
	DeletionRule getDeletionRule();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.streammapping.StreamSlot#getDeletionRule <em>Deletion Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deletion Rule</em>' containment reference.
	 * @see #getDeletionRule()
	 * @generated
	 */
	void setDeletionRule(DeletionRule value);

} // StreamSlot
