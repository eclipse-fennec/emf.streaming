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
package org.eclipse.fennec.stream.messaging.impl;

import java.time.Duration;
import java.util.function.Function;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.fennec.emf.osgi.metadata.MetadataService;
import org.eclipse.fennec.model.streammapping.GuardPolicy;
import org.eclipse.fennec.stream.ReplicaTargets;
import org.eclipse.fennec.stream.StreamCore;
import org.eclipse.fennec.stream.StreamReceiver;
import org.eclipse.fennec.stream.messaging.ChangeSetCodec;
import org.eclipse.fennec.stream.messaging.StreamSubscriber;
import org.gecko.messaging.api.MessageSubscription;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Receives change sets from one Gecko Messaging instance into the {@link StreamCore}'s lanes and
 * applies them to the replica objects. Factory configuration required (PID {@value #PID}):
 * {@code subscription.target} names the messaging instance, {@code channel} what to subscribe to. A
 * change set's model version is resolved through the metadata service (fingerprint → package). All
 * references are mandatory and static; the replica targets default to the in-memory ones
 * ({@code targets.target}).
 */
@Component(name = StreamSubscriberComponent.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = StreamSubscriberComponent.Config.class, factory = true)
public class StreamSubscriberComponent {

	/** The factory PID and component name. */
	public static final String PID = "FennecStreamSubscriber";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Fennec stream subscriber")
	public @interface Config {

		/**
		 * @return the filter selecting the messaging instance
		 */
		@AttributeDefinition(description = "Filter selecting the Gecko Messaging instance, e.g. (osgi.messaging.name=broker)")
		String subscription_target();

		/**
		 * @return the channel or pattern
		 */
		@AttributeDefinition(description = "Channel or pattern to subscribe to, e.g. stream/pool/#")
		String channel();

		/**
		 * @return the filter selecting the payload codec
		 */
		@AttributeDefinition(description = "Filter selecting the payload codec")
		String codec_target() default "(" + ChangeSetCodec.CONTENT_TYPE_PROPERTY + "=application/json)";

		/**
		 * @return the guard policy
		 */
		GuardPolicy guard() default GuardPolicy.FAIL;

		/**
		 * @return the unknown-object policy
		 */
		StreamReceiver.UnknownObjectPolicy unknownObjects() default StreamReceiver.UnknownObjectPolicy.EXPECT_KEYFRAME;

		/**
		 * @return the gap policy
		 */
		StreamReceiver.GapPolicy gapPolicy() default StreamReceiver.GapPolicy.STALE;

		/**
		 * @return the gap buffer in milliseconds
		 */
		long gapBufferMillis() default 5000;

		/**
		 * @return the largest payload accepted in bytes
		 */
		int maxPayload() default StreamSubscriber.DEFAULT_MAX_PAYLOAD;

		/**
		 * @return the filter selecting the replica targets
		 */
		@AttributeDefinition(description = "Filter selecting the ReplicaTargets; default the in-memory targets")
		String targets_target() default DEFAULT_TARGETS;
	}

	/** The in-memory replica targets of org.eclipse.fennec.stream.osgi, which run without configuration. */
	static final String DEFAULT_TARGETS = "(fennec.stream.targets=memory)";

	private final AutoCloseable subscription;

	/**
	 * @param core the core whose lanes apply the change sets
	 * @param messaging the messaging instance
	 * @param codec the payload codec
	 * @param targets where replica objects live
	 * @param metadata resolves model versions by fingerprint
	 * @param config the configuration
	 */
	@Activate
	public StreamSubscriberComponent(@Reference(name = "core") StreamCore core,
			@Reference(name = "subscription") MessageSubscription messaging, @Reference(name = "codec") ChangeSetCodec codec,
			@Reference(name = "targets", target = DEFAULT_TARGETS) ReplicaTargets targets, @Reference(name = "metadata") MetadataService metadata,
			Config config) {
		StreamReceiver receiver = core.receiver().targets(targets).modelVersions(modelVersions(metadata))
				.guard(config.guard()).unknownObjects(config.unknownObjects()).gapPolicy(config.gapPolicy())
				.gapBuffer(Duration.ofMillis(config.gapBufferMillis())).build();
		this.subscription = new StreamSubscriber(messaging, codec, receiver, config.maxPayload()).subscribe(config.channel());
	}

	/**
	 * Ends the subscription.
	 *
	 * @throws Exception if it cannot be closed
	 */
	@Deactivate
	void deactivate() throws Exception {
		subscription.close();
	}

	static Function<String, EPackage.Registry> modelVersions(MetadataService metadata) {
		return fingerprint -> metadata.getPackageMetadataByFingerprint(fingerprint).map(packageMetadata -> {
			EPackage.Registry registry = new EPackageRegistryImpl();
			registry.put(packageMetadata.getEPackage().getNsURI(), packageMetadata.getEPackage());
			return registry;
		}).orElse(null);
	}
}
