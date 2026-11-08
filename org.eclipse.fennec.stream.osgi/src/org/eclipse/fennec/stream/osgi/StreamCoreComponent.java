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
package org.eclipse.fennec.stream.osgi;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.emf.osgi.metadata.MetadataService;
import org.eclipse.fennec.emf.osgi.model.metadata.PackageMetadata;
import org.eclipse.fennec.mapping.ConverterRegistry;
import org.eclipse.fennec.mapping.ExpressionEvaluator;
import org.eclipse.fennec.mapping.FunctionRegistry;
import org.eclipse.fennec.mapping.IdentityResolver;
import org.eclipse.fennec.mapping.Mapper;
import org.eclipse.fennec.mapping.MappingRegistry;
import org.eclipse.fennec.mapping.ValueExtractor;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.stream.ChangeRules;
import org.eclipse.fennec.stream.EventCore;
import org.eclipse.fennec.stream.Projection;
import org.eclipse.fennec.stream.ProjectionSource;
import org.eclipse.fennec.stream.StreamCore;
import org.eclipse.fennec.stream.StreamReceiver;
import org.eclipse.fennec.stream.StreamStore;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.util.pushstream.PushStream;

/**
 * The {@link StreamCore} service of a framework: an {@link EventCore} wired from services (#9).
 * <p>
 * All references are mandatory and static: the mappings and the function/converter whiteboards, the
 * OCL evaluator, the store, the projection and the metadata service, which names the fingerprint
 * stamped on every change set. A prototype-scoped projection gives every lane thread an instance of
 * its own, given back when the thread ends or the core stops.
 * <p>
 * The configuration (PID {@value #PID}) is optional. Without one the core runs with the in-memory
 * store ({@code fennec.stream.store=memory}), the projection that writes nothing
 * ({@code fennec.stream.projection=none}), both also without configuration, the groups
 * high/default/low, an hourly retention sweep and a 30 s block timeout. {@code store.target} and
 * {@code projection.target} select other services, e.g. a repository projection.
 */
@Component(name = StreamCoreComponent.PID, service = StreamCore.class)
@Designate(ocd = StreamCoreComponent.Config.class)
public class StreamCoreComponent implements StreamCore {

	/** The configuration PID and component name. */
	public static final String PID = "FennecStreamCore";

	/** Service property naming a {@link StreamStore}. */
	public static final String STORE_PROPERTY = "fennec.stream.store";

	/** Service property naming a {@link Projection}. */
	public static final String PROJECTION_PROPERTY = "fennec.stream.projection";

	/** Service property naming the replica targets of a subscriber. */
	public static final String TARGETS_PROPERTY = "fennec.stream.targets";

	/** The name of the in-memory store and replica targets. */
	public static final String MEMORY = "memory";

	/** The name of the projection that writes nothing. */
	public static final String NONE = "none";

	static final String DEFAULT_STORE = "(" + STORE_PROPERTY + "=" + MEMORY + ")";
	static final String DEFAULT_PROJECTION = "(" + PROJECTION_PROPERTY + "=" + NONE + ")";

	/**
	 * Optional configuration.
	 */
	@ObjectClassDefinition(name = "Fennec stream core")
	public @interface Config {

		/**
		 * @return the execution groups in rank order, each {@code name:threads:slice:maxQueued}
		 */
		@AttributeDefinition(description = "Execution groups in rank order: name:threads:slice:maxQueued (maxQueued 0 = unbounded). Empty: high/default/low.")
		String[] groups() default {};

		/**
		 * @return seconds between retention sweeps, 0 disables them
		 */
		@AttributeDefinition(description = "Seconds between two retention sweeps; 0 disables them")
		long retentionIntervalSeconds() default 3600;

		/**
		 * @return milliseconds a producer waits for space in a full lane
		 */
		@AttributeDefinition(description = "Milliseconds a producer waits for space in a full lane before enqueuing anyway")
		long blockTimeoutMillis() default 30_000;

		/**
		 * @return the filter selecting the store
		 */
		@AttributeDefinition(description = "Filter selecting the StreamStore; default the in-memory store")
		String store_target() default DEFAULT_STORE;

		/**
		 * @return the filter selecting the projection
		 */
		@AttributeDefinition(description = "Filter selecting the Projection, e.g. (fennec.stream.projection=repository); default none (audit only)")
		String projection_target() default DEFAULT_PROJECTION;
	}

	private final EventCore core;

	/**
	 * @param mappings the mappings
	 * @param functions the function whiteboard
	 * @param converters the converter whiteboard
	 * @param expressions the expression language
	 * @param store the store
	 * @param projections the projection service objects, one instance per lane thread
	 * @param metadata names model fingerprints
	 * @param config the configuration
	 */
	@Activate
	public StreamCoreComponent(@Reference(name = "mappings") MappingRegistry mappings,
			@Reference(name = "functions") FunctionRegistry functions,
			@Reference(name = "converters") ConverterRegistry converters,
			@Reference(name = "expressions") ExpressionEvaluator expressions,
			@Reference(name = "store", target = DEFAULT_STORE) StreamStore store, @Reference(name = "projection", target = DEFAULT_PROJECTION, scope = ReferenceScope.PROTOTYPE) ComponentServiceObjects<Projection> projections,
			@Reference(name = "metadata") MetadataService metadata, Config config) {
		ValueExtractor extractor = new ValueExtractor(functions, converters, expressions);
		EventCore.Builder builder = EventCore.builder().mappings(mappings)
				.mapper(new Mapper(extractor, new IdentityResolver(extractor))).rules(new ChangeRules(expressions, functions))
				.store(store).projections(projections(projections)).fingerprints(ePackage -> fingerprint(metadata, ePackage))
				.retentionInterval(Duration.ofSeconds(config.retentionIntervalSeconds()))
				.blockTimeout(Duration.ofMillis(config.blockTimeoutMillis()));
		for (String group : config.groups()) {
			builder.group(parseGroup(group));
		}
		this.core = builder.build();
	}

	/** Pairs every service object taken with exactly one release. */
	static ProjectionSource projections(ComponentServiceObjects<Projection> projections) {
		return new ProjectionSource() {

			@Override
			public Projection acquire() {
				return projections.getService();
			}

			@Override
			public void release(Projection projection) {
				projections.ungetService(projection);
			}
		};
	}

	/**
	 * Stops the lanes; they give their projections back.
	 */
	@Deactivate
	void deactivate() {
		core.close();
	}

	/**
	 * @param text {@code name:threads:slice:maxQueued}; slice and maxQueued may be left out (64, 0)
	 * @return the group
	 */
	static EventCore.ExecutionGroup parseGroup(String text) {
		String[] parts = text.trim().split(":");
		if (parts.length < 2 || parts.length > 4 || parts[0].isBlank()) {
			throw new IllegalArgumentException("Execution group '" + text + "' is not name:threads[:slice[:maxQueued]]");
		}
		int threads = Integer.parseInt(parts[1].trim());
		int slice = parts.length > 2 ? Integer.parseInt(parts[2].trim()) : 64;
		int maxQueued = parts.length > 3 ? Integer.parseInt(parts[3].trim()) : 0;
		return new EventCore.ExecutionGroup(parts[0].trim(), threads, slice, maxQueued);
	}

	private static String fingerprint(MetadataService metadata, EPackage ePackage) {
		return metadata.getPackageMetadata(ePackage).map(PackageMetadata::getModelFingerprint)
				.filter(f -> !f.isBlank()).orElseGet(() -> String.valueOf(ePackage.getNsURI()));
	}

	@Override
	public void offer(EObject source) {
		core.offer(source);
	}

	@Override
	public void offer(EObject source, long eventTime, String author) {
		core.offer(source, eventTime, author);
	}

	@Override
	public void offerUnitOfWork(Collection<? extends EObject> sources, long eventTime, String author) {
		core.offerUnitOfWork(sources, eventTime, author);
	}

	@Override
	public PushStream<ChangeSet> changes() {
		return core.changes();
	}

	@Override
	public void flush() {
		core.flush();
	}

	@Override
	public CompletableFuture<Optional<ChangeSet>> undo(EClass targetClass, String targetId) {
		return core.undo(targetClass, targetId);
	}

	@Override
	public CompletableFuture<Optional<ChangeSet>> redo(EClass targetClass, String targetId) {
		return core.redo(targetClass, targetId);
	}

	@Override
	public StreamReceiver.Builder receiver() {
		return core.receiver();
	}

	@Override
	public StreamStore store() {
		return core.store();
	}

	@Override
	public EventCore.Metrics metrics() {
		return core.metrics();
	}
}
