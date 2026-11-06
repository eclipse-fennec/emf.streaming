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
package org.eclipse.fennec.stream.codec.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.config.ConfigurationResolver;
import org.eclipse.fennec.codec.resource.CodecResourceFactory;
import org.eclipse.fennec.codec.util.MetadataServiceFactory;
import org.eclipse.fennec.emf.osgi.metadata.MetadataWhiteboard;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.junit.jupiter.api.Test;

/**
 * The JSON payload (decision D-6, requirement TRN-04).
 */
class JsonChangeSetCodecTest {

	private final JsonChangeSetCodec codec = new JsonChangeSetCodec();

	@Test
	void aChangeSetSurvivesTheRoundTrip() throws IOException {
		ChangeSet original = changeSet();

		ByteBuffer payload = codec.encode(original);
		ChangeSet copy = codec.decode(payload);

		assertThat(EcoreUtil.equals(original, copy)).as(text(codec.encode(original))).isTrue();
		assertThat(original.eResource()).isNull();
		assertThat(codec.contentType()).isEqualTo("application/json");
	}

	@Test
	void thePayloadIsJson() throws IOException {
		String json = text(codec.encode(changeSet()));
		assertThat(json).startsWith("{").contains("\"streamId\"").contains("pool-1").contains("24.5");
	}

	@Test
	void aClassWithKeyAndValueIsNoMapEntry() throws IOException {
		// emf.codec#291: classes with features key and value used to be written as map entries
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("kv");
		ePackage.setNsPrefix("kv");
		ePackage.setNsURI("https://example.org/kv");
		EClass holder = eClass(ePackage, "Holder");
		EClass slot = eClass(ePackage, "Slot");
		for (String name : new String[] { "key", "value", "address" }) {
			EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
			attribute.setName(name);
			attribute.setEType(EcorePackage.Literals.ESTRING);
			slot.getEStructuralFeatures().add(attribute);
		}
		EReference slots = EcoreFactory.eINSTANCE.createEReference();
		slots.setName("slots");
		slots.setEType(slot);
		slots.setContainment(true);
		slots.setUpperBound(-1);
		holder.getEStructuralFeatures().add(slots);
		// as for a registered model: the package sits in a resource under its nsURI, so types are absolute
		new ResourceImpl(URI.createURI(ePackage.getNsURI())).getContents().add(ePackage);
		MetadataWhiteboard metadata = MetadataServiceFactory.create();
		metadata.registerPackage(ePackage);
		// the codec reads the _type of a document through the global package registry
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
		try {
			roundTrip(ePackage, holder, slot, slots, metadata);
		} finally {
			EPackage.Registry.INSTANCE.remove(ePackage.getNsURI());
		}
	}

	private static void roundTrip(EPackage ePackage, EClass holder, EClass slot, EReference slots,
			MetadataWhiteboard metadata) throws IOException {

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json",
				new CodecResourceFactory(metadata, ConfigurationResolver.defaults()));
		EObject original = EcoreUtil.create(holder);
		EObject element = EcoreUtil.create(slot);
		element.eSet(slot.getEStructuralFeature("key"), "k");
		element.eSet(slot.getEStructuralFeature("value"), "v");
		element.eSet(slot.getEStructuralFeature("address"), "a");
		@SuppressWarnings("unchecked")
		List<EObject> list = (List<EObject>) original.eGet(slots);
		list.add(element);

		Resource out = resourceSet.createResource(URI.createURI("out.json"));
		out.getContents().add(original);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		out.save(bytes, Map.of());
		Resource in = resourceSet.createResource(URI.createURI("in.json"));
		in.load(new ByteArrayInputStream(bytes.toByteArray()), Map.of());

		String json = bytes.toString(StandardCharsets.UTF_8);
		assertThat(in.getContents()).as(json).hasSize(1);
		assertThat(EcoreUtil.equals(original, in.getContents().get(0))).as(json).isTrue();
	}

	private static EClass eClass(EPackage ePackage, String name) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		ePackage.getEClassifiers().add(eClass);
		return eClass;
	}

	@Test
	void whatIsNoChangeSetIsRefused() {
		assertThatExceptionOfType(IOException.class)
				.isThrownBy(() -> codec.decode(ByteBuffer.wrap("not json".getBytes(StandardCharsets.UTF_8))));
	}

	private static ChangeSet changeSet() {
		ChangeSet set = StreamFactory.eINSTANCE.createChangeSet();
		set.setId("cs-1");
		set.setStreamId("https://example.org/pool#//Pool/pool-1");
		set.setSequence(7);
		set.setTimestamp(1_000);
		set.setCommitTime(1_005);
		set.setContextFingerprint("fp-1");
		set.setAuthor("device:t1");
		set.setReason("ingest");
		ChangeEntry value = StreamFactory.eINSTANCE.createChangeEntry();
		value.setObjectId("pool-1");
		value.setKind(DeltaKind.SET);
		value.setFeatureId(2);
		value.setFeatureUri("https://example.org/pool#//Pool/currentTemperature");
		value.setValueOld("24.0");
		value.setValueNew("24.5");
		value.setTimestamp(999L);
		set.getEntries().add(value);
		ChangeEntry keyframe = StreamFactory.eINSTANCE.createChangeEntry();
		keyframe.setObjectId("pool-1");
		keyframe.setKind(DeltaKind.KEYFRAME);
		keyframe.setClassUri("https://example.org/pool#//Pool");
		SlotValue slot = StreamFactory.eINSTANCE.createSlotValue();
		slot.setFeatureUri("https://example.org/pool#//Pool/currentTemperature");
		slot.setValue("24.5");
		keyframe.getState().add(slot);
		set.getEntries().add(keyframe);
		return set;
	}

	private static String text(ByteBuffer payload) {
		byte[] bytes = new byte[payload.remaining()];
		payload.duplicate().get(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}
}
