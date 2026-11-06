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
 * Transport of the Fennec change stream over Gecko Messaging: {@link org.eclipse.fennec.stream.messaging.StreamPublisher},
 * {@link org.eclipse.fennec.stream.messaging.StreamSubscriber}, the channel scheme and the payload codec SPI.
 */
@org.osgi.annotation.bundle.Export
@org.osgi.annotation.versioning.Version("0.1.0")
package org.eclipse.fennec.stream.messaging;
