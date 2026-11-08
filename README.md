# Eclipse Fennec — EMF Streaming

Change streams for EMF models: capturing model changes as an ordered, replayable,
self-describing stream of `ChangeSet`s, and consuming, transporting and applying them.

> **Status:** incubation. The concept is being refined from use cases to requirements to an
> implementation; the first building block is the change-stream model.

## Origin

The concept and the first metamodel come from the *unified persistence* work in
[emf.persistence-jpa](https://github.com/eclipse-fennec/emf.persistence-jpa)
(`concept.md` §4–§13, §20). All streaming development continues here.

## Bundles

| Bundle | Content |
|---|---|
| `org.eclipse.fennec.stream.model` | The wire/log format (`stream.ecore`): `ChangeSet`, `ChangeEntry`, `DeltaKind`, `SlotValue`, the snapshot chain; validator and class/feature addressing |
| `org.eclipse.fennec.mapping.model` | Generic EObject-to-EObject mapping: target class, slots, identity resolution, value sources (extends `DataTransformation` of the shared atlas configuration model) |
| `org.eclipse.fennec.mapping` | Plain-Java mapping engine: value ladder (OCL > function > feature path, constant, converter), identity resolution with cache, validator, registry |
| `org.eclipse.fennec.mapping.ocl` | OCL expressions through the Fennec M2X engine |
| `org.eclipse.fennec.mapping.osgi` | Function/converter whiteboards, mapping registry on an emf.osgi EObject registry |
| `org.eclipse.fennec.stream.mapping.model` | Streaming policies: `StreamMapping`, `StreamSlot`, change rules, batching, retention |
| `org.eclipse.fennec.stream` | Plain-Java core: change rules, event core (lanes per object, batching, keyframes, backpressure, retention), store and projection SPIs, receiver, undo/redo, as-of |
| `org.eclipse.fennec.stream.osgi` | The core as OSGi service; in-memory store, no-op projection and replica targets as defaults without configuration ([docs/configuration.md](docs/configuration.md): JPA/Mongo projection, transport) |
| `org.eclipse.fennec.stream.persistence` | Store and projection on a Fennec persistence repository (JPA or Mongo): change sets as ordinary models, slot values by update commands |
| `org.eclipse.fennec.stream.tck` | The store contract as JUnit 5 tests to extend; the in-memory and the repository store run it |
| `org.eclipse.fennec.stream.sta.model` | The OGC SensorThings API 1.1 data model (Thing, Location, Datastream, Sensor, ObservedProperty, Observation, FeatureOfInterest) |
| `org.eclipse.fennec.stream.sta` | SensorThings as a projection target: a stream sink that turns the entries of TIMESERIES slots into observations in a persistence repository; MQTT extension ([docs/sta-conformance.md](docs/sta-conformance.md): conformance test) |
| `org.eclipse.fennec.stream.messaging` | Transport over Gecko Messaging: channels per object, CloudEvents, payload codec SPI, retained keyframes ([docs/transport.md](docs/transport.md)) |
| `org.eclipse.fennec.stream.codec.json` | JSON payload through the Fennec EMF codec |

## Example

`org.eclipse.fennec.stream.example.pool` runs the leisure pool demo of emf.ogc.features with
simulated temperature and pH sensors: a stream mapping feeds `waterTemperature` and `ph` of the
pools, one update command per pool and window, and the OGC Features map shows them.

```bash
./gradlew :org.eclipse.fennec.stream.example.pool:run.pool
# http://localhost:8080/ogc/collections/pools/items  -  map: http://localhost:8080/ogc/viewer/
```

## Build

bnd workspace driven by Gradle 9 (`biz.aQute.bnd.workspace`), Java 21.

```bash
./gradlew build        # build + unit tests
./gradlew testOSGi     # OSGi integration tests
tools/brokers.sh up    # Mosquitto 2 (podman or docker) for the MQTT OSGi tests; without it they skip
```

Project coordinates live in `gradle.properties`, the library selection in `cnf/ext/fennec.bnd`
(see `cnf/SETUP.MD`).

## License

[Eclipse Public License 2.0](LICENSE)
