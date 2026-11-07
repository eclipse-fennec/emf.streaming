# Change streams on the wire

How change sets of the Fennec change stream travel over a message broker, so that clients
without Fennec (an MQTT client, a dashboard, another platform) can subscribe to them.

Status: draft, implemented by `org.eclipse.fennec.stream.messaging` on Gecko Messaging 2.0
(MQTT 5 / 3.1.1, AMQP 0-9-1, WebSocket, in-memory).

## Channels

One channel per object:

```
<root>/<nsPrefix>/<EClass>/<objectId>
```

| Segment | Meaning | Example |
|---|---|---|
| `root` | first segment, configurable per deployment (tenant, environment) | `stream` |
| `nsPrefix` | `nsPrefix` of the object's EPackage | `pool` |
| `EClass` | name of the object's class | `Pool` |
| `objectId` | id of the object, URL-encoded (UTF-8), so ids containing `/`, `+` or `#` stay one segment | `pool-1`, `a%2Fb` |

Subscribe with the wildcards of the protocol: `stream/pool/#` (every object of the model),
`stream/pool/Pool/+` (every pool), `stream/pool/Pool/pool-1` (one pool).

All change sets of one object travel on its channel, so a broker that keeps the order per channel
(MQTT, AMQP queues) keeps the order of the object's stream.

## Message

Where the messaging instance speaks CloudEvents (binary or structured mode), a change set is a
CloudEvent:

| Attribute | Value |
|---|---|
| `id` | id of the change set (UUID) |
| `source` | stream id: `<nsURI>#//<EClass>/<objectId>` |
| `type` | `org.eclipse.fennec.stream.changeset` |
| `subject` | object id |
| `dataschema` | fingerprint of the model version the change set was written under |
| `time` | commit time of the change set |
| `datacontenttype` | the payload codec, e.g. `application/xmi+xml` |
| extension `sequence` | sequence of the change set within its stream (carried as a message header) |

Without CloudEvents, the message carries the channel, the content type and the payload only.
Everything a receiver needs is in the payload; the envelope serves routing and filtering.

## Payload

The change set itself (`stream.ecore`, nsURI `https://org.eclipse/fennec/stream/1.0.0`), encoded
by a pluggable codec: JSON through emf.codec (`org.eclipse.fennec.stream.codec.json`, the default,
`application/json`), XMI (`application/xmi+xml`), Protobuf through emf.util as a later option. Values inside the change set are strings in the EMF literal form of their
type; references are the id of the referenced object. The typing resolves from
(`dataschema`, feature) on the receiving side.

## Publishing

The publisher publishes the change sets of a core one after the other; one the instance refuses
is logged and skipped. By default a Gecko Messaging instance refuses publishing while it is not
connected. Configure it with `publishAwaitConnection` (milliseconds; bounded by
`publishAwaitMessages`/`publishAwaitBytes`) so that a publish waits for the connect or reconnect
instead (gecko#20). During a long outage the core's output buffer drops its oldest change sets;
receivers notice the gap, go stale and come back with the next keyframe.

## Receiving

- A sequence seen before is a duplicate and ignored.
- A sequence further ahead than the next one is held for a gap buffer (default 5 s). If the missing
  change set does not arrive, the object is stale until its next keyframe (or the gap is counted
  and skipped, by configuration).
- An object the receiver does not hold enters with its next `KEYFRAME`; senders write one on a
  stream's first change set and then every 100 change sets or 15 minutes (configurable).
  Publishers may retain change sets holding a keyframe (option of `StreamPublisher`, only for
  instances with the `retain` feature such as MQTT): a new subscriber of an object's channel then
  gets its latest keyframe at once.
- Messages are untrusted input: payloads above a size limit (default 1 MiB), payloads the codec
  cannot read, structurally invalid change sets and change sets of an unknown model version are
  dropped, never applied.
