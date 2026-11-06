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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.eclipse.fennec.stream.EventCore;
import org.junit.jupiter.api.Test;

/**
 * The configuration format of execution groups.
 */
class StreamCoreComponentTest {

	@Test
	void groupsAreNameThreadsSliceMaxQueued() {
		assertThat(StreamCoreComponent.parseGroup("high:4:16:1000")).isEqualTo(new EventCore.ExecutionGroup("high", 4, 16, 1000));
		assertThat(StreamCoreComponent.parseGroup(" low : 1 ")).isEqualTo(new EventCore.ExecutionGroup("low", 1, 64, 0));
		assertThatIllegalArgumentException().isThrownBy(() -> StreamCoreComponent.parseGroup("high"));
		assertThatIllegalArgumentException().isThrownBy(() -> StreamCoreComponent.parseGroup(":2"));
	}
}
