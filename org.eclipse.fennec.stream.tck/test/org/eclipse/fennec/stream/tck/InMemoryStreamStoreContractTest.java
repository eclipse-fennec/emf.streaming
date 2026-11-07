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
package org.eclipse.fennec.stream.tck;

import org.eclipse.fennec.stream.InMemoryStreamStore;
import org.eclipse.fennec.stream.StreamStore;

/**
 * The in-memory reference store meets the contract.
 */
class InMemoryStreamStoreContractTest extends StreamStoreContract {

	@Override
	protected StreamStore store() {
		return new InMemoryStreamStore();
	}
}
