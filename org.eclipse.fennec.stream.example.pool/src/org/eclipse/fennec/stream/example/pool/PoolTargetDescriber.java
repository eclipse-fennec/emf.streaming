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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.ogc.features.geo.GeoJsonText;
import org.eclipse.fennec.persistence.repository.api.Repository;
import org.eclipse.fennec.stream.sta.TargetDescriber;
import org.geojson.Geometry;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Names and locates a pool for SensorThings: the thing's name is the pool's {@code name}, its
 * location the pool's {@code geometry} as GeoJSON. Reads the pool from its repository once per
 * pool, taking a repository instance for the call and giving it back.
 * <p>
 * Configuration required (PID {@value #PID}): {@code repository.target} names the repository of
 * the pools. Carries {@code fennec.sta.describer=pool}.
 */
@Component(name = PoolTargetDescriber.PID, configurationPolicy = ConfigurationPolicy.REQUIRE,
		property = "fennec.sta.describer=pool")
@Designate(ocd = PoolTargetDescriber.Config.class)
public class PoolTargetDescriber implements TargetDescriber {

	/** The configuration PID and component name. */
	public static final String PID = "FennecPoolTargetDescriber";

	private static final Logger LOGGER = Logger.getLogger(PoolTargetDescriber.class.getName());

	/**
	 * Configuration.
	 */
	@ObjectClassDefinition(name = "SensorThings description of the pools")
	public @interface Config {

		/**
		 * @return the filter selecting the repository
		 */
		@AttributeDefinition(description = "Filter selecting the repository of the pools, e.g. (persistence.repository.id=bath)")
		String repository_target();
	}

	private final ComponentServiceObjects<Repository> repositories;
	private final GeoJsonText geoJson;

	/**
	 * @param repositories the repository service objects
	 * @param geoJsonFactory writes geometries as GeoJSON
	 */
	@Activate
	public PoolTargetDescriber(
			@Reference(name = "repository", scope = ReferenceScope.PROTOTYPE_REQUIRED) ComponentServiceObjects<Repository> repositories,
			@Reference(name = "geoJson", target = "(emf.configuratorName=geojson)") Resource.Factory geoJsonFactory) {
		this.repositories = repositories;
		this.geoJson = new GeoJsonText(geoJsonFactory);
	}

	@Override
	public Description describe(EClass targetClass, String targetId) {
		Repository repository = repositories.getService();
		try {
			EObject pool = repository.getEObject(targetClass, targetId);
			if (pool == null) {
				return IDS.describe(targetClass, targetId);
			}
			Object name = value(pool, "name");
			Object geometry = value(pool, "geometry");
			String location = geometry instanceof Geometry shape ? geoJson.write(EcoreUtil.copy(shape)) : null;
			return new Description(name == null ? targetId : name.toString(), null, location);
		} catch (IOException | RuntimeException e) {
			LOGGER.log(Level.WARNING, e, () -> "Pool " + targetId + " could not be described - named by its id");
			return IDS.describe(targetClass, targetId);
		} finally {
			repositories.ungetService(repository);
		}
	}

	private static Object value(EObject object, String featureName) {
		EStructuralFeature feature = object.eClass().getEStructuralFeature(featureName);
		return feature == null ? null : object.eGet(feature);
	}
}
