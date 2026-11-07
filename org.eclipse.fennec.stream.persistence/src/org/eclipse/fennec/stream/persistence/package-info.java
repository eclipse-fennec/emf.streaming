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
/**
 * The change stream on a Fennec persistence repository: {@link org.eclipse.fennec.stream.persistence.RepositoryStreamStore}
 * saves change sets as ordinary models, {@link org.eclipse.fennec.stream.persistence.RepositoryProjection}
 * writes slot values by update commands. Plain Java; the OSGi components are in the impl package.
 */
@org.osgi.annotation.bundle.Export
@org.osgi.annotation.versioning.Version("0.1.0")
package org.eclipse.fennec.stream.persistence;
