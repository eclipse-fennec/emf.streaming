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

import java.util.Date;

import org.eclipse.fennec.model.sta.Observation;

/**
 * Writes STA 1.1 JSON for the MQTT extension: an observation with {@code @iot.id},
 * {@code @iot.selfLink} and its navigation links. The HTTP endpoint gets its JSON from emf.odata;
 * this is only what a message carries.
 */
public final class StaJson {

	private StaJson() {
	}

	/**
	 * @param observation the observation
	 * @param serviceRoot the STA service root, e.g. {@code https://host/sta/v1.1}
	 * @return the observation as STA JSON
	 */
	public static String observation(Observation observation, String serviceRoot) {
		requireNonNull(observation, "Observation is required");
		String self = serviceRoot + "/Observations(" + key(observation.getId()) + ")";
		StringBuilder json = new StringBuilder("{");
		member(json, "@iot.id", string(observation.getId()));
		member(json, "@iot.selfLink", string(self));
		member(json, "phenomenonTime", string(time(observation.getPhenomenonTimeStart(), observation.getPhenomenonTimeEnd())));
		member(json, "resultTime", observation.getResultTime() == null ? "null" : string(time(observation.getResultTime(), null)));
		member(json, "result", result(observation));
		member(json, "Datastream@iot.navigationLink", string(self + "/Datastream"));
		member(json, "FeatureOfInterest@iot.navigationLink", string(self + "/FeatureOfInterest"));
		return json.append('}').toString();
	}

	/**
	 * @param id an entity id
	 * @return the id as an STA key literal: a quoted string, single quotes doubled
	 */
	public static String key(String id) {
		return "'" + id.replace("'", "''") + "'";
	}

	private static String result(Observation observation) {
		if (observation.getResultNumber() != null) {
			double number = observation.getResultNumber();
			return Double.isFinite(number) ? Double.toString(number) : "null";
		}
		if (observation.getResultBoolean() != null) {
			return observation.getResultBoolean().toString();
		}
		return observation.getResultText() == null ? "null" : string(observation.getResultText());
	}

	private static String time(Date start, Date end) {
		String from = start == null ? "" : start.toInstant().toString();
		return end == null ? from : from + "/" + end.toInstant().toString();
	}

	private static void member(StringBuilder json, String name, String value) {
		if (json.length() > 1) {
			json.append(',');
		}
		json.append(string(name)).append(':').append(value);
	}

	private static String string(String text) {
		StringBuilder quoted = new StringBuilder("\"");
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
			case '"' -> quoted.append("\\\"");
			case '\\' -> quoted.append("\\\\");
			case '\n' -> quoted.append("\\n");
			case '\r' -> quoted.append("\\r");
			case '\t' -> quoted.append("\\t");
			default -> {
				if (c < 0x20) {
					quoted.append(String.format("\\u%04x", (int) c));
				} else {
					quoted.append(c);
				}
			}
			}
		}
		return quoted.append('"').toString();
	}
}
