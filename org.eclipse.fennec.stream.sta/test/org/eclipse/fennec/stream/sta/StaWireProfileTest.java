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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.odata.wire.api.ServiceRoot;
import org.eclipse.fennec.odata.wire.mapping.ClassMapping;
import org.eclipse.fennec.odata.wire.mapping.WireMapping;
import org.eclipse.fennec.odata.wire.mapping.WireMappingFactory;
import org.eclipse.fennec.odata.wire.mapping.WireProperty;
import org.junit.jupiter.api.Test;

class StaWireProfileTest {

	private static final String BASE = "http://localhost:8080/sta/v1.1";

	private static final ServiceRoot ROOT = new ServiceRoot(BASE,
			List.of(new ServiceRoot.Entry("Things", BASE + "/Things"),
					new ServiceRoot.Entry("Datastreams", BASE + "/Datastreams")),
			List.of());

	@Test
	void writesTheStaControlsWithAbsoluteLinksAndTheKeyOnlyAsIotId() {
		StaWireProfile profile = new StaWireProfile();

		assertThat(profile.controls().keyValue()).isEqualTo("@iot.id");
		assertThat(profile.controls().selfLink()).isEqualTo("@iot.selfLink");
		assertThat(profile.controls().navigationLink()).isEqualTo("@iot.navigationLink");
		assertThat(profile.controls().count()).isEqualTo("@iot.count");
		assertThat(profile.controls().nextLink()).isEqualTo("@iot.nextLink");
		assertThat(profile.controls().context()).isNull();
		assertThat(profile.controls().type()).isNull();
		assertThat(profile.absoluteLinks()).isTrue();
		assertThat(profile.keyInBody(StaPackage.Literals.THING)).isFalse();
		assertThat(profile.structuredRawValue()).isTrue();
	}

	@Test
	void aPropertyValueIsNamedAfterTheProperty() {
		StaWireProfile profile = new StaWireProfile();

		assertThat(profile.propertyMember(StaPackage.Literals.THING__NAME)).isEqualTo("name");
		assertThat(profile.propertyMember(StaPackage.Literals.DATASTREAM__UNIT_OF_MEASUREMENT)).isEqualTo("unitOfMeasurement");
	}

	@Test
	void theWirePropertiesOfDatastreamAndObservationHaveAHandlerEach() {
		StaWireProfile profile = new StaWireProfile();
		WireMapping mapping = profile.mapping().orElseThrow();

		assertThat(mapping.getClasses()).extracting(ClassMapping::getEClass)
				.containsExactly(StaPackage.Literals.DATASTREAM, StaPackage.Literals.OBSERVATION);
		assertThat(mapping.getClasses().get(0).getProperties()).extracting(WireProperty::getName)
				.containsExactly("phenomenonTime", "resultTime");
		assertThat(mapping.getClasses().get(1).getProperties()).extracting(WireProperty::getName)
				.containsExactly("phenomenonTime", "validTime", "result");
		for (ClassMapping classMapping : mapping.getClasses()) {
			for (WireProperty property : classMapping.getProperties()) {
				assertThat(profile.handler(property)).as(property.getName()).get()
						.isInstanceOf("result".equals(property.getName()) ? StaResultHandler.class : StaIntervalHandler.class);
			}
		}
	}

	@Test
	void aPropertyOfAnotherShapeHasNoHandler() {
		WireProperty odd = WireMappingFactory.eINSTANCE.createWireProperty();
		odd.setName("odd");
		odd.getParts().add(StaPackage.Literals.THING__NAME);

		assertThat(new StaWireProfile().handler(odd)).isEmpty();
	}

	@Test
	void theRootDocumentListsTheSetsWithAbsoluteUrlsAndTheConformanceClasses() {
		String document = new StaWireProfile().serviceDocument(ROOT).orElseThrow();

		assertThat(document).startsWith("{\"value\":[{\"name\":\"Things\",\"url\":\"" + BASE + "/Things\"},"
				+ "{\"name\":\"Datastreams\",\"url\":\"" + BASE + "/Datastreams\"}]");
		assertThat(document).contains("\"serverSettings\":{\"conformance\":[")
				.contains("\"http://www.opengis.net/spec/iot_sensing/1.1/req/datamodel\"")
				.contains("\"http://www.opengis.net/spec/iot_sensing/1.1/req/request-data\"")
				.doesNotContain(StaWireProfile.MQTT_RECEIVE);
	}

	@Test
	void theMqttExtensionIsAnnouncedWithItsEndpoints() {
		String document = new StaWireProfile(List.of("mqtt://localhost:1883")).serviceDocument(ROOT).orElseThrow();

		assertThat(document).contains("\"" + StaWireProfile.MQTT_RECEIVE + "\"]")
				.contains("\"" + StaWireProfile.MQTT_RECEIVE + "\":{\"endpoints\":[\"mqtt://localhost:1883\"]}");
	}
}
