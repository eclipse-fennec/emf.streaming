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
# Starts and stops the broker of the MQTT OSGi tests (org.eclipse.fennec.stream.mqtt.tests):
# Mosquitto 2 on host port ${MQTT_PORT:-11893}. Works with podman and docker.
#
#   tools/brokers.sh up | down | status
#
# Without a broker on that port the MQTT tests skip themselves.
set -eu
DIR=$(cd "$(dirname "$0")" && pwd)
RUNTIME=${CONTAINER_RUNTIME:-$(command -v podman >/dev/null 2>&1 && echo podman || echo docker)}
MQTT_PORT=${MQTT_PORT:-11893}
MOSQUITTO_IMAGE=${MOSQUITTO_IMAGE:-docker.io/library/eclipse-mosquitto:2}
NAME=fennec-stream-mosquitto

case "${1:-}" in
up)
	$RUNTIME rm -f "$NAME" >/dev/null 2>&1 || true
	# the config is written inside the container by mosquitto itself, so no bind mount and no
	# ownership questions between machines
	$RUNTIME run -d --name "$NAME" --user mosquitto -p "$MQTT_PORT:1883" \
		-e MOSQUITTO_CONF="$(cat "$DIR/brokers/mosquitto.conf")" "$MOSQUITTO_IMAGE" \
		sh -c 'printf "%s\n" "$MOSQUITTO_CONF" > /mosquitto/config/test.conf && exec mosquitto -c /mosquitto/config/test.conf' >/dev/null
	i=0
	while ! (exec 3<>"/dev/tcp/127.0.0.1/$MQTT_PORT") 2>/dev/null; do
		i=$((i + 1))
		if [ "$i" -gt 60 ]; then
			$RUNTIME logs --tail 30 "$NAME" >&2 || true
			echo "port $MQTT_PORT did not open" >&2
			exit 1
		fi
		sleep 1
	done
	echo "mosquitto up: tcp://localhost:$MQTT_PORT"
	;;
down)
	$RUNTIME rm -f "$NAME" >/dev/null 2>&1 || true
	;;
status)
	$RUNTIME ps -a --filter "name=$NAME" --format '{{.Names}} {{.Status}}'
	;;
*)
	echo "usage: $0 up|down|status" >&2
	exit 2
	;;
esac
