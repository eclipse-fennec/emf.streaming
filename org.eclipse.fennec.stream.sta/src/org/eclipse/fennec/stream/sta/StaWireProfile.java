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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fennec.odata.wire.api.ControlNames;
import org.eclipse.fennec.odata.wire.api.ServiceRoot;
import org.eclipse.fennec.odata.wire.api.WireProfile;

/**
 * The SensorThings API 1.1 wire format on an emf.odata service root (emf.odata ADR-0010):
 * {@code @iot.*} control information with absolute links, the key only as {@code @iot.id}, and
 * the STA root document with its server settings. Querying, paging and {@code $metadata} stay
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

	private final List<String> mqttEndpoints;

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

	private static String array(List<String> values) {
		return values.stream().map(StaJson::string).collect(Collectors.joining(",", "[", "]"));
	}
}
