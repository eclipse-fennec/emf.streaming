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
package org.eclipse.fennec.stream.sta.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.StreamSink;
import org.eclipse.fennec.stream.persistence.RepositoryAccess;
import org.eclipse.fennec.stream.sta.RepositoryStaWriter;
import org.eclipse.fennec.stream.sta.StaListener;
import org.eclipse.fennec.stream.sta.StaMapper;
import org.eclipse.fennec.stream.sta.StaSink;
import org.eclipse.fennec.stream.sta.TargetDescriber;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The STA projection as a {@link StreamSink} service: the core hands it every change set it writes,
 * and it writes the observations into the repository holding the STA model.
 * <p>
 * Configuration required (PID {@value #PID}): {@code repository.target} names that repository,
 * {@code describer.target} the {@link TargetDescriber} (default: things named by their target id,
 * without location), {@code units} the units of features whose model carries none. Repository instances come from the prototype-scoped service, at most
 * {@code repositories} at once, all given back on deactivation. Every {@link StaListener} service
 * (e.g. the MQTT extension) hears about the stored batches.
 */
@Component(name = StaSinkComponent.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = StaSinkComponent.Config.class)
public class StaSinkComponent implements StreamSink {

	/** The configuration PID and component name. */
	public static final String PID = "FennecStaSink";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Fennec SensorThings projection")
	public @interface Config {

		/**
		 * @return the filter selecting the repository
		 */
		@AttributeDefinition(description = "Filter selecting the repository holding the STA model, e.g. (persistence.repository.id=sta)")
		String repository_target();

		/**
		 * @return the filter selecting the describer
		 */
		@AttributeDefinition(description = "Filter selecting the TargetDescriber; default: things named by their target id")
		String describer_target() default IdsTargetDescriberComponent.FILTER;

		/**
		 * @return the units of features by feature URI
		 */
		@AttributeDefinition(description = "Units of features whose model carries none: featureUri=name|symbol|definition")
		String[] units() default {};

		/**
		 * @return the most repository instances in use at once
		 */
		@AttributeDefinition(description = "Most repository instances in use at once")
		int repositories() default 4;
	}

	private final RepositoryAccess access;
	private final StaSink sink;

	/**
	 * @param repositories the repository service objects
	 * @param describer names and locates the target objects
	 * @param config the configuration
	 */
	@Activate
	public StaSinkComponent(
			@Reference(name = "repository", scope = ReferenceScope.PROTOTYPE_REQUIRED) ComponentServiceObjects<Repository> repositories,
			@Reference(name = "describer", target = IdsTargetDescriberComponent.FILTER) TargetDescriber describer,
			Config config) {
		this.access = new RepositoryAccess(repositories::getService, repositories::ungetService, config.repositories());
		Map<String, StaMapper.Unit> units = new HashMap<>();
		for (String unit : config.units()) {
			Map.Entry<String, StaMapper.Unit> entry = StaMapper.Unit.parse(unit);
			units.put(entry.getKey(), entry.getValue());
		}
		this.sink = new StaSink(new StaMapper(describer, units), new RepositoryStaWriter(access));
	}

	/**
	 * Whiteboard: every {@link StaListener} service hears about the batches stored while it is there.
	 *
	 * @param listener the listener
	 */
	@Reference(name = "listeners", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	void addListener(StaListener listener) {
		sink.addListener(listener);
	}

	void removeListener(StaListener listener) {
		sink.removeListener(listener);
	}

	/**
	 * Gives every repository instance back.
	 */
	@Deactivate
	void deactivate() {
		access.close();
	}

	@Override
	public void written(StreamMapping mapping, ChangeSet changeSet) {
		sink.written(mapping, changeSet);
	}
}
