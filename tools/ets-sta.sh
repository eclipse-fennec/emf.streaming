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
# Runs the OGC SensorThings conformance test suite (TEAM Engine with ets-sta10, the only public
# STA suite; STA 1.1 is largely backwards compatible) against an STA endpoint, by default the one of
# the pool example. Works with podman and docker. See docs/sta-conformance.md.
#
#   tools/ets-sta.sh up | run [service-root] | down
#
# The report (EARL/RDF) lands in generated/ets-sta/; run prints the number of passed and failed tests.
set -eu
DIR=$(cd "$(dirname "$0")/.." && pwd)
RUNTIME=${CONTAINER_RUNTIME:-$(command -v podman >/dev/null 2>&1 && echo podman || echo docker)}
TE_PORT=${TE_PORT:-8081}
IMAGE=${ETS_STA_IMAGE:-docker.io/ogccite/ets-sta10:latest}
NAME=fennec-stream-ets-sta
# the host as seen from inside the container
HOST=${IUT_HOST:-$([ "$RUNTIME" = podman ] && echo host.containers.internal || echo host.docker.internal)}
CREDENTIALS=${TE_CREDENTIALS:-ogctest:ogctest}

case "${1:-}" in
up)
	$RUNTIME rm -f "$NAME" >/dev/null 2>&1 || true
	$RUNTIME run -d --name "$NAME" --add-host "$HOST:host-gateway" -p "$TE_PORT:8080" "$IMAGE" >/dev/null
	i=0
	until [ "$(curl -s -o /dev/null -w '%{http_code}' "http://localhost:$TE_PORT/teamengine/")" = 200 ]; do
		i=$((i + 1))
		if [ "$i" -gt 90 ]; then
			$RUNTIME logs --tail 30 "$NAME" >&2 || true
			echo "TEAM Engine did not come up on port $TE_PORT" >&2
			exit 1
		fi
		sleep 2
	done
	echo "TEAM Engine up: http://localhost:$TE_PORT/teamengine/ ($CREDENTIALS)"
	;;
run)
	IUT=${2:-http://$HOST:8080/sta/v1.1}
	OUT="$DIR/generated/ets-sta"
	mkdir -p "$OUT"
	REPORT="$OUT/report-$(date +%Y%m%d-%H%M%S).rdf"
	curl -s -u "$CREDENTIALS" -H 'Accept: application/rdf+xml' -G \
		--data-urlencode "iut=$IUT" "http://localhost:$TE_PORT/teamengine/rest/suites/sta10/run" -o "$REPORT"
	PASSED=$(grep -c 'earl:passed' "$REPORT" || true)
	FAILED=$(grep -c 'earl:failed' "$REPORT" || true)
	echo "ets-sta10 against $IUT: $PASSED passed, $FAILED failed - $REPORT"
	;;
down)
	$RUNTIME rm -f "$NAME" >/dev/null 2>&1 || true
	echo "TEAM Engine down"
	;;
*)
	echo "usage: $0 up | run [service-root] | down" >&2
	exit 2
	;;
esac
