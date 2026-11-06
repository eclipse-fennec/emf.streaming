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

import java.util.Map;

import org.eclipse.fennec.emf.osgi.eobject.registry.EObjectRegistryWriter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Puts the example's stream mapping into the EObject registry the mapping registry reads
 * ({@code fennec-mappings}), as a dynamic source writes there: the writer is published only once
 * the registry is loaded. No configuration.
 */
@Component
public class PoolMappingContribution {

	static final String SOURCE = "pool-example";

	private final EObjectRegistryWriter writer;

	/**
	 * @param writer the write face of the mapping registry
	 */
	@Activate
	public PoolMappingContribution(
			@Reference(name = "writer", target = "(emf.eobject.registry.name=fennec-mappings)") EObjectRegistryWriter writer) {
		this.writer = writer;
		writer.put(SOURCE, PoolStreamMapping.ID, PoolStreamMapping.create(), Map.of());
	}

	/**
	 * Takes the mapping out again.
	 */
	@Deactivate
	void deactivate() {
		writer.remove(SOURCE, PoolStreamMapping.ID);
	}
}
