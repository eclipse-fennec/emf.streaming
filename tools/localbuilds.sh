#!/bin/bash
#
# Copyright (c) 2026 Contributors to the Eclipse Foundation.
#
# This program and the accompanying materials are made
# available under the terms of the Eclipse Public License 2.0
# which is available at https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Tests this workspace against local builds of other Fennec workspaces (emf.persistence-jpa,
# emf.odata, ...) before their snapshots are published. The bundles are copied into
# cnf/localbuilds/, which a LocalIndexedRepo (cnf/ext/localbuilds.bnd) offers next to Maven
# Central; their build qualifier is newer than the published snapshot's, so version=latest and the
# resolver take them. Both paths are git-ignored: nothing of this reaches CI.
#
#   tools/localbuilds.sh add <workspace-dir> [bsn ...]   copy <ws>/<bsn>/generated/<bsn>.jar (all bundles without bsn)
#   tools/localbuilds.sh list
#   tools/localbuilds.sh clear                            back to the published snapshots
#
# After add/clear re-resolve the bndruns that use the bundles (resolve tasks, or delete
# generated/test.bndrun of OSGi test projects) and stop the Gradle daemon (./gradlew --stop): it
# keeps the workspace with its repositories.
set -eu
DIR=$(cd "$(dirname "$0")/.." && pwd)
STORE="$DIR/cnf/localbuilds"
PLUGIN="$DIR/cnf/ext/localbuilds.bnd"

plugin() {
	cat > "$PLUGIN" <<'BND'
# Written by tools/localbuilds.sh - git-ignored, never commit.
-plugin.9.LocalBuilds: \
	aQute.bnd.deployer.repository.LocalIndexedRepo; \
		name = LocalBuilds; \
		pretty = true; \
		local = ${build}/localbuilds
BND
}

case "${1:-}" in
add)
	WS=$(cd "${2:?workspace directory required}" && pwd)
	shift 2
	mkdir -p "$STORE"
	if [ $# -eq 0 ]; then
		set -- $(cd "$WS" && ls -d org.eclipse.fennec.* 2>/dev/null | grep -v '\.tests\?$')
	fi
	for BSN in "$@"; do
		JAR="$WS/$BSN/generated/$BSN.jar"
		if [ -f "$JAR" ]; then
			VERSION=$(unzip -p "$JAR" META-INF/MANIFEST.MF | sed -n 's/^Bundle-Version: //p' | tr -d '\r')
			rm -rf "${STORE:?}/$BSN"
			mkdir -p "$STORE/$BSN"
			cp "$JAR" "$STORE/$BSN/$BSN-$VERSION.jar"
			echo "added $BSN ($VERSION)"
		else
			echo "skipped $BSN: no $JAR (build it first)" >&2
		fi
	done
	# the repository rebuilds its index from the jars when there is none
	rm -f "$STORE"/index.xml* "$STORE"/index.xml.sha
	plugin
	;;
list)
	(cd "$STORE" 2>/dev/null && ls */*.jar) || echo "no local builds"
	;;
clear)
	rm -rf "$STORE" "$PLUGIN"
	echo "local builds removed"
	;;
*)
	echo "usage: $0 add <workspace-dir> [bsn ...] | list | clear" >&2
	exit 2
	;;
esac
