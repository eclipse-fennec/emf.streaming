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

import org.eclipse.fennec.emf.osgi.ResourceSetFactory;
import org.eclipse.fennec.emf.osgi.constants.EMFNamespaces;
import org.eclipse.fennec.model.stream.StreamPackage;
import org.eclipse.fennec.stream.StreamCore;
import org.eclipse.fennec.stream.messaging.ChangeSetCodec;
import org.eclipse.fennec.stream.messaging.StreamChannels;
import org.eclipse.fennec.stream.messaging.StreamPublisher;
import org.gecko.messaging.api.MessagePublisher;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Publishes everything the {@link StreamCore} writes on one Gecko Messaging instance. Factory
 * configuration required (PID {@value #PID}): there is no sensible default instance, so
 * {@code publisher.target} has to name one, e.g. {@code (osgi.messaging.name=broker)}. All references
 * are mandatory and static. The resource set factory must have the stream model and the models of
 * the target classes registered ({@code resourceSets.target}).
 */
@Component(name = StreamPublisherComponent.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = StreamPublisherComponent.Config.class, factory = true)
public class StreamPublisherComponent {

	/** The factory PID and component name. */
	public static final String PID = "FennecStreamPublisher";

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "Fennec stream publisher")
	public @interface Config {

		/**
		 * @return the filter selecting the messaging instance
		 */
		@AttributeDefinition(description = "Filter selecting the Gecko Messaging instance, e.g. (osgi.messaging.name=broker)")
		String publisher_target();

		/**
		 * @return the filter selecting the payload codec
		 */
		@AttributeDefinition(description = "Filter selecting the payload codec")
		String codec_target() default "(" + ChangeSetCodec.CONTENT_TYPE_PROPERTY + "=application/json)";

		/**
		 * @return the first channel segment
		 */
		@AttributeDefinition(description = "First segment of every channel")
		String root() default StreamChannels.DEFAULT_ROOT;

		/**
		 * @return whether keyframes are retained
		 */
		@AttributeDefinition(description = "Publish change sets holding a keyframe retained (only for instances with the retain feature)")
		boolean retainKeyframes() default false;

		/**
		 * @return the filter selecting the resource set factory
		 */
		@AttributeDefinition(description = "Filter selecting the ResourceSetFactory whose package registry resolves the stream classes; "
				+ "name the target models too, e.g. (&(emf.name=stream)(emf.name=bath))")
		String resourceSets_target() default RESOURCE_SETS_TARGET;
	}

	/** The default resource set factory: one that has the stream model registered. */
	static final String RESOURCE_SETS_TARGET = "(" + EMFNamespaces.EMF_NAME + "=" + StreamPackage.eNAME + ")";

	private final AutoCloseable attachment;

	/**
	 * @param core the core whose change sets are published
	 * @param publisher the messaging instance
	 * @param codec the payload codec
	 * @param resourceSets resolves the class of a stream through the registered packages
	 * @param config the configuration
	 */
	@Activate
	public StreamPublisherComponent(@Reference(name = "core") StreamCore core,
			@Reference(name = "publisher") MessagePublisher publisher, @Reference(name = "codec") ChangeSetCodec codec,
			@Reference(name = "resourceSets", target = RESOURCE_SETS_TARGET) ResourceSetFactory resourceSets, Config config) {
		StreamPublisher streamPublisher = new StreamPublisher(publisher, codec, new StreamChannels(config.root()),
				resourceSets.createResourceSet().getPackageRegistry(), config.retainKeyframes());
		this.attachment = streamPublisher.attach(core);
	}

	/**
	 * Stops publishing.
	 *
	 * @throws Exception if the subscription to the core cannot be closed
	 */
	@Deactivate
	void deactivate() throws Exception {
		attachment.close();
	}
}
