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
package org.eclipse.fennec.stream.example.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.fennec.stream.StreamCore;
import org.eclipse.fennec.stream.example.pool.sensor.PhSensor;
import org.eclipse.fennec.stream.example.pool.sensor.SensorFactory;
import org.eclipse.fennec.stream.example.pool.sensor.TempSensor;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Simulates one temperature sensor ({@code temp-<pool>}) and one pH probe ({@code ph-<pool>}) per
 * pool: every interval each takes a small random step and is offered to the stream core.
 * Configuration required (PID {@value #PID}).
 */
@Component(name = SensorSimulator.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = SensorSimulator.Config.class)
public class SensorSimulator {

	/** The configuration PID and component name. */
	public static final String PID = "FennecPoolSensorSimulator";

	private static final Logger LOGGER = Logger.getLogger(SensorSimulator.class.getName());

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Pool sensor simulator")
	public @interface Config {

		/**
		 * @return the pool ids
		 */
		@AttributeDefinition(description = "Ids of the pools to simulate sensors for")
		String[] pools();

		/**
		 * @return the interval
		 */
		@AttributeDefinition(description = "Milliseconds between two readings")
		long intervalMillis() default 1000;
	}

	private final StreamCore core;
	private final List<TempSensor> temperatures = new ArrayList<>();
	private final List<PhSensor> probes = new ArrayList<>();
	private final Random random = new Random();
	private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor(r -> {
		Thread thread = new Thread(r, "pool-sensor-simulator");
		thread.setDaemon(true);
		return thread;
	});

	/**
	 * @param core where the readings go
	 * @param config the pools and the interval
	 */
	@Activate
	public SensorSimulator(@Reference(name = "core") StreamCore core, Config config) {
		this.core = core;
		for (String pool : config.pools()) {
			TempSensor temperature = SensorFactory.eINSTANCE.createTempSensor();
			temperature.setId("temp-" + pool);
			temperature.setPoolId(pool);
			temperature.setValue(26 + random.nextDouble() * 6);
			temperatures.add(temperature);
			PhSensor probe = SensorFactory.eINSTANCE.createPhSensor();
			probe.setId("ph-" + pool);
			probe.setReading(7.0 + random.nextDouble() * 0.4);
			probes.add(probe);
		}
		timer.scheduleAtFixedRate(this::tick, 0, config.intervalMillis(), TimeUnit.MILLISECONDS);
	}

	/**
	 * Stops the simulation.
	 */
	@Deactivate
	void deactivate() {
		timer.shutdownNow();
	}

	/** One reading per sensor. */
	void tick() {
		try {
			long now = System.currentTimeMillis();
			for (TempSensor temperature : temperatures) {
				temperature.setValue(round(temperature.getValue() + random.nextGaussian() * 0.15, 2));
				core.offer(temperature, now, "simulator:" + temperature.getId());
			}
			for (PhSensor probe : probes) {
				probe.setReading(round(probe.getReading() + random.nextGaussian() * 0.03, 3));
				core.offer(probe, now, "simulator:" + probe.getId());
			}
		} catch (RuntimeException e) {
			LOGGER.log(Level.WARNING, "Simulated reading failed", e);
		}
	}

	private static double round(double value, int digits) {
		double scale = Math.pow(10, digits);
		return Math.round(value * scale) / scale;
	}
}
