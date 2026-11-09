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
package org.eclipse.fennec.stream.sta;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.odata.wire.api.ControlNames;
import org.eclipse.fennec.odata.wire.api.ServiceRoot;
import org.eclipse.fennec.odata.wire.api.WireProfile;
import org.eclipse.fennec.odata.wire.api.WirePropertyHandler;
import org.eclipse.fennec.odata.wire.mapping.ClassMapping;
import org.eclipse.fennec.odata.wire.mapping.WireMapping;
import org.eclipse.fennec.odata.wire.mapping.WireMappingFactory;
import org.eclipse.fennec.odata.wire.mapping.WireProperty;

/**
 * The SensorThings API 1.1 wire format on an emf.odata service root (emf.odata ADR-0010):
 * {@code @iot.*} control information with absolute links, the key only as {@code @iot.id}, a
 * property value under the property's own name ({@code {"name":"…"}}, 9.2.4), the STA root
 * document with its server settings, and the wire properties over several attributes (#69): the
 * time intervals {@code phenomenonTime}, {@code resultTime}, {@code validTime}
 * ({@link StaIntervalHandler}) and {@code result} ({@link StaResultHandler}). Querying, paging and {@code $metadata} stay
 * those of the OData runtime.
 */
public class StaWireProfile implements WireProfile {

	/** The {@value WireProfile#PROFILE_PROPERTY} of this profile. */
	public static final String NAME = "sta";

	/** The control names of SensorThings: no context and type, the key as {@code @iot.id}. */
	public static final ControlNames CONTROLS = new ControlNames(null, null, "@iot.id", "@iot.selfLink",
			"@iot.navigationLink", "@iot.count", "@iot.nextLink");

	private static final String SPEC = "http://www.opengis.net/spec/iot_sensing/1.1/req/";

	/** The conformance classes of a read-only service. */
	public static final List<String> CONFORMANCE = List.of(SPEC + "datamodel",
			SPEC + "resource-path/resource-path-to-entities", SPEC + "request-data");

	/** The conformance class of the MQTT extension, announced with its endpoints. */
	public static final String MQTT_RECEIVE = SPEC + "receive-updates-via-mqtt/receive-updates";

	/** The wire property over {@code resultNumber}, {@code resultBoolean} and {@code resultText}. */
	public static final String RESULT = "result";

	private final List<String> mqttEndpoints;

	private final WireMapping mapping = createMapping();

	/** A profile without the MQTT extension. */
	public StaWireProfile() {
		this(List.of());
	}

	/**
	 * @param mqttEndpoints the broker URLs of the MQTT extension, e.g. {@code mqtt://host:1883};
	 *                      empty when the service does not publish over MQTT
	 */
	public StaWireProfile(List<String> mqttEndpoints) {
		this.mqttEndpoints = List.copyOf(requireNonNull(mqttEndpoints, "MQTT endpoints are required"));
	}

	@Override
	public ControlNames controls() {
		return CONTROLS;
	}

	@Override
	public boolean keyInBody(EClass type) {
		return false;
	}

	@Override
	public boolean absoluteLinks() {
		return true;
	}

	@Override
	public String propertyMember(EStructuralFeature property) {
		return requireNonNull(property, "Property is required").getName();
	}

	@Override
	public Optional<WireMapping> mapping() {
		return Optional.of(mapping);
	}

	@Override
	public Optional<WirePropertyHandler> handler(WireProperty property) {
		List<EAttribute> parts = requireNonNull(property, "Wire property is required").getParts();
		if (RESULT.equals(property.getName()) && parts.size() == 3) {
			return Optional.of(new StaResultHandler(parts.get(0), parts.get(1), parts.get(2)));
		}
		if (!RESULT.equals(property.getName()) && parts.size() == 2) {
			return Optional.of(new StaIntervalHandler(parts.get(0), parts.get(1)));
		}
		return Optional.empty();
	}

	@Override
	public Optional<String> serviceDocument(ServiceRoot root) {
		requireNonNull(root, "ServiceRoot is required");
		String value = root.entitySets().stream()
				.map(entry -> "{\"name\":" + StaJson.string(entry.name()) + ",\"url\":" + StaJson.string(entry.url()) + "}")
				.collect(Collectors.joining(",", "[", "]"));
		List<String> conformance = new ArrayList<>(CONFORMANCE);
		if (!mqttEndpoints.isEmpty()) {
			conformance.add(MQTT_RECEIVE);
		}
		StringBuilder settings = new StringBuilder("{\"conformance\":").append(array(conformance));
		if (!mqttEndpoints.isEmpty()) {
			settings.append(',').append(StaJson.string(MQTT_RECEIVE)).append(":{\"endpoints\":")
					.append(array(mqttEndpoints)).append('}');
		}
		settings.append('}');
		return Optional.of("{\"value\":" + value + ",\"serverSettings\":" + settings + "}");
	}

	/**
	 * The wire properties of STA 1.1 over the attributes of {@code sta.ecore}: intervals with the
	 * parts start, end; {@code result} with the parts number, boolean, text.
	 */
	private static WireMapping createMapping() {
		WireMapping created = WireMappingFactory.eINSTANCE.createWireMapping();
		created.getClasses().add(classMapping(StaPackage.Literals.DATASTREAM,
				property("phenomenonTime", StaPackage.Literals.DATASTREAM__PHENOMENON_TIME_START,
						StaPackage.Literals.DATASTREAM__PHENOMENON_TIME_END),
				property("resultTime", StaPackage.Literals.DATASTREAM__RESULT_TIME_START,
						StaPackage.Literals.DATASTREAM__RESULT_TIME_END)));
		created.getClasses().add(classMapping(StaPackage.Literals.OBSERVATION,
				property("phenomenonTime", StaPackage.Literals.OBSERVATION__PHENOMENON_TIME_START,
						StaPackage.Literals.OBSERVATION__PHENOMENON_TIME_END),
				property("validTime", StaPackage.Literals.OBSERVATION__VALID_TIME_START,
						StaPackage.Literals.OBSERVATION__VALID_TIME_END),
				property(RESULT, StaPackage.Literals.OBSERVATION__RESULT_NUMBER,
						StaPackage.Literals.OBSERVATION__RESULT_BOOLEAN, StaPackage.Literals.OBSERVATION__RESULT_TEXT)));
		return created;
	}

	private static ClassMapping classMapping(EClass eClass, WireProperty... properties) {
		ClassMapping created = WireMappingFactory.eINSTANCE.createClassMapping();
		created.setEClass(eClass);
		created.getProperties().addAll(List.of(properties));
		return created;
	}

	private static WireProperty property(String name, EAttribute... parts) {
		WireProperty created = WireMappingFactory.eINSTANCE.createWireProperty();
		created.setName(name);
		created.getParts().addAll(List.of(parts));
		return created;
	}

	private static String array(List<String> values) {
		return values.stream().map(StaJson::string).collect(Collectors.joining(",", "[", "]"));
	}
}
