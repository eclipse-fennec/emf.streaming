/********************************************************************
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
 ********************************************************************/
package org.eclipse.fennec.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.fennec.model.mapping.Slot;
import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.Lateness;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamMappingFactory;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.junit.jupiter.api.Test;

/**
 * Defaults and overrides of stream slots (requirements STR-02, STR-09).
 */
class StreamSlotsTest {

	private static final StreamMappingFactory F = StreamMappingFactory.eINSTANCE;

	@Test
	void aSlotWithoutSettingsGetsTheBuiltInDefaults() {
		StreamSlot slot = F.createStreamSlot();
		F.createStreamMapping().getSlots().add(slot);

		assertThat(StreamSlots.mode(slot)).isEqualTo(ProjectionMode.PROJECT);
		assertThat(StreamSlots.recording(slot)).isEqualTo(RecordingMode.TIMESERIES);
		assertThat(StreamSlots.lateness(slot)).isEqualTo(Lateness.EVENT_TIME);
		assertThat(StreamSlots.batch(slot).getKind()).isEqualTo(BatchKind.IMMEDIATE);
		assertThat(StreamSlots.executionGroup(slot)).isEqualTo("default");
	}

	@Test
	void theMappingSetsTheDefaultsOfItsSlots() {
		StreamMapping mapping = F.createStreamMapping();
		mapping.setMode(ProjectionMode.AUDIT_ONLY);
		mapping.setRecording(RecordingMode.CHANGELOG);
		mapping.setExecutionGroup("high");
		BatchStrategy window = F.createBatchStrategy();
		window.setKind(BatchKind.WINDOW);
		mapping.setBatch(window);
		StreamSlot slot = F.createStreamSlot();
		mapping.getSlots().add(slot);

		assertThat(StreamSlots.mode(slot)).isEqualTo(ProjectionMode.AUDIT_ONLY);
		assertThat(StreamSlots.recording(slot)).isEqualTo(RecordingMode.CHANGELOG);
		assertThat(StreamSlots.lateness(slot)).isEqualTo(Lateness.ARRIVAL);
		assertThat(StreamSlots.batch(slot)).isSameAs(window);
		assertThat(StreamSlots.executionGroup(slot)).isEqualTo("high");
	}

	@Test
	void aSlotOverridesItsMapping() {
		StreamMapping mapping = F.createStreamMapping();
		mapping.setRecording(RecordingMode.CHANGELOG);
		mapping.setLateness(Lateness.ARRIVAL);
		StreamSlot slot = F.createStreamSlot();
		slot.setMode(ProjectionMode.AUDIT_ONLY);
		slot.setRecording(RecordingMode.TIMESERIES);
		slot.setLateness(Lateness.EVENT_TIME);
		slot.setExecutionGroup("low");
		mapping.getSlots().add(slot);

		assertThat(StreamSlots.mode(slot)).isEqualTo(ProjectionMode.AUDIT_ONLY);
		assertThat(StreamSlots.recording(slot)).isEqualTo(RecordingMode.TIMESERIES);
		assertThat(StreamSlots.lateness(slot)).isEqualTo(Lateness.EVENT_TIME);
		assertThat(StreamSlots.executionGroup(slot)).isEqualTo("low");
	}

	@Test
	void anExplicitlySetDefaultValueStillOverrides() {
		// unsettable: setting PROJECT on the slot overrides an AUDIT_ONLY mapping
		StreamMapping mapping = F.createStreamMapping();
		mapping.setMode(ProjectionMode.AUDIT_ONLY);
		StreamSlot slot = F.createStreamSlot();
		slot.setMode(ProjectionMode.PROJECT);
		mapping.getSlots().add(slot);

		assertThat(StreamSlots.mode(slot)).isEqualTo(ProjectionMode.PROJECT);
	}

	@Test
	void theExecutionGroupsOfAMappingAreCollected() {
		StreamMapping mapping = F.createStreamMapping();
		StreamSlot plain = F.createStreamSlot();
		StreamSlot hot = F.createStreamSlot();
		hot.setExecutionGroup("high");
		mapping.getSlots().add(plain);
		mapping.getSlots().add(hot);

		assertThat(StreamSlots.executionGroups(mapping)).containsExactly("default", "high");
		assertThat(StreamSlots.executionGroups(F.createStreamMapping())).containsExactly("default");
	}

	@Test
	void theDefaultsOfTheModelAreTheDocumentedOnes() {
		StreamMapping mapping = F.createStreamMapping();
		assertThat(mapping.getKeyframeEverySets()).isEqualTo(100);
		assertThat(mapping.getKeyframeEveryMinutes()).isEqualTo(15);
		assertThat(mapping.isUndoable()).isFalse();
		assertThat(mapping.getGuard().getName()).isEqualTo("FAIL");
	}

	@Test
	void includingUnmappedStreamsEveryOtherAttributeOfTheTargetAsIs() {
		PoolStreams fx = new PoolStreams();
		StreamMapping mapping = fx.mapping();
		mapping.setRecording(RecordingMode.CHANGELOG);
		mapping.setIncludeUnmapped(true);
		mapping.getExcludedFeatures().add(fx.poolId);
		fx.depth.setTransient(true);

		StreamMapping expanded = StreamSlots.expand(mapping);

		assertThat(mapping.getSlots()).as("the registered mapping stays as it is").hasSize(2);
		assertThat(expanded.getSlots()).extracting(Slot::getTargetFeature)
				.containsExactly(fx.currentTemperature, fx.currentPh, fx.poolName);
		StreamSlot name = (StreamSlot) expanded.getSlots().get(2);
		assertThat(name.getSourceClass()).as("read from the target itself").isNull();
		assertThat(name.getValue().getFeaturePath()).containsExactly(fx.poolName);
		assertThat(StreamSlots.recording(name)).as("the mapping's defaults").isEqualTo(RecordingMode.CHANGELOG);
	}

	@Test
	void withoutIncludingUnmappedTheMappingIsStreamedAsDeclared() {
		PoolStreams fx = new PoolStreams();
		StreamMapping mapping = fx.mapping();
		assertThat(StreamSlots.expand(mapping)).isSameAs(mapping);
		mapping.setIncludeUnmapped(false);
		assertThat(StreamSlots.expand(mapping)).isSameAs(mapping);
	}

	@Test
	void aPathFollowingAReferenceOutOfTheAggregateIsReported() {
		PoolStreams fx = new PoolStreams();
		EReference probe = reference("probe", fx.tempSensor, true);
		EReference station = reference("station", fx.tempSensor, false);
		fx.pool.getEStructuralFeatures().addAll(List.of(probe, station));
		StreamMapping mapping = F.createStreamMapping();
		mapping.setId("pool-mirror");
		mapping.setTarget(fx.pool);
		mapping.getSlots().add(PoolStreams.slot(fx.currentTemperature, fx.pool, PoolStreams.path(probe, fx.tempSensorValue)));
		mapping.getSlots().add(PoolStreams.slot(fx.currentPh, fx.pool, PoolStreams.path(station, fx.tempSensorValue)));

		assertThat(StreamSlots.pathsLeavingTheAggregate(mapping)).singleElement().asString()
				.contains("slot currentPh").contains("'station'");
	}

	private static EReference reference(String name, EClass type, boolean containment) {
		EReference reference = EcoreFactory.eINSTANCE.createEReference();
		reference.setName(name);
		reference.setEType(type);
		reference.setContainment(containment);
		return reference;
	}
}
