# Runtime configuration

All examples use the OSGi Configurator format (`OSGI-INF/configurator/*.json` or a file handed to
the Configurator); factory configurations use the tilde notation `pid~name`.

## What runs without configuration

| Component (PID) | Service | Service property | Role |
|---|---|---|---|
| `FennecStreamCore` | `StreamCore` | — | the event core; configuration optional |
| `FennecInMemoryStreamStore` | `StreamStore` | `fennec.stream.store=memory` | default store of the core |
| `FennecNoProjection` | `Projection` | `fennec.stream.projection=none` | default projection: audit only, writes nothing |
| `FennecInMemoryReplicaTargets` | `ReplicaTargets` | `fennec.stream.targets=memory` | default targets of a subscriber |
| `FennecMappingRegistry` | `MappingRegistry` | — | mappings from the EObject registry `fennec-mappings` |

The core selects store and projection with `store.target` and `projection.target`. Without a
configuration those name the in-memory store and the no-op projection. A configuration switches to
another service; nothing has to be switched off.

## Minimal runtime: mappings only

The core needs mappings, read from an emf.osgi EObject registry named `fennec-mappings`. Change
sets go to the in-memory store, and the target objects are not written (audit only).

```json
{
  ":configurator:resource-version": 1,
  ":configurator:symbolic-name": "my.app.streams",
  ":configurator:version": "1.0.0",
  "FileEObjectProvider~mappings": {
    "emf.eobject.provider.name": "mapping-files",
    "locations": ["mappings/pool-streams.xmi"],
    "key.feature": "id"
  },
  "EObjectRegistry~mappings": {
    "name": "fennec-mappings",
    "initialProvider.target": "(emf.eobject.provider.name=mapping-files)"
  }
}
```

Optional settings of the core:

```json
"FennecStreamCore": {
  "groups": ["high:2:64", "default:4:64:10000", "low:1:64"],
  "retentionIntervalSeconds:Long": 3600,
  "blockTimeoutMillis:Long": 30000
}
```

### OCL expressions

Expressions of a mapping run on the M2X OCL engine (`DefaultOclEngine`). The engine parses an
expression once and also remembers a parse failure, in its expression cache. The default cache
(`DefaultOclExpressionCache`) keys by nsURI. With the bundle `org.eclipse.fennec.m2x.ocl.fingerprint`
the cache can key by model fingerprint, so a changed model is parsed again:

```json
"DefaultOclEngine": {
  "expressionCache.target": "(cache.name=fingerprint)"
}
```

## Projection into a persistence repository

A projection writes slot values into the stored target objects. It does not load them: each batch
becomes one `UpdateCommand` per target (selector on the id, one template entry per feature).

The projection is prototype-scoped. The core takes one instance per lane thread and releases it
when the thread ends or the core stops. Every projection instance gets a repository instance of its
own, so neither has to be thread-safe.

Store and projection on a repository come with the bundle `org.eclipse.fennec.stream.persistence`.

### JPA (EclipseLink)

Data source, mapping and persistence unit come from emf.persistence-jpa; see its JPA user guide.

```json
"daanse.jdbc.datasource.h2.DataSource~bath": {
  "identifier": "./data/bath;DB_CLOSE_DELAY=-1"
},
"fennec.jpa.EORMMappingService~bath": {
  "fennec.jpa.eorm.mappingName": "bath",
  "fennec.jpa.eorm.model.target": "(emf.nsURI=https://eclipse.org/fennec/ogc/example/bath/1.0)"
},
"fennec.jpa.EMPersistenceUnit~bath": {
  "fennec.jpa.persistenceUnitName": "bath",
  "fennec.jpa.mapping.target": "(fennec.jpa.eorm.mapping=bath)",
  "fennec.jpa.ext.eclipselink.ddl-generation": "create-tables",
  "fennec.jpa.dataSource.target": "(subprotocol=*)"
},
"fennec.repository.jpa~bath": {
  "repositoryId": "bath",
  "unit.target": "(osgi.unit.name=bath)"
},
"FennecRepositoryProjection": {
  "repository.target": "(persistence.repository.id=bath)"
},
"FennecStreamCore": {
  "projection.target": "(fennec.stream.projection=repository)"
}
```

The update becomes one `UPDATE ... SET` per target and batch.

### MongoDB

Client, database and repository come from emf.persistence-jpa; see its Mongo user guide. The
projection is the same; only the repository changes.

```json
"persistence.mongo.client~main": {
  "ident": "main",
  "connectionString": "mongodb://localhost:27017"
},
"persistence.mongo.database~bath": {
  "alias": "bath",
  "database": "bath",
  "client.target": "(mongo.client.ident=main)"
},
"fennec.repository.mongo~bath": {
  "repositoryId": "bath",
  "database.target": "(mongo.database.alias=bath)"
},
"FennecRepositoryProjection": {
  "repository.target": "(persistence.repository.id=bath)"
},
"FennecStreamCore": {
  "projection.target": "(fennec.stream.projection=repository)"
}
```

The update becomes one `$set` per target and batch.

## Store

The change sets of a stream go to the in-memory store by default. The repository store
(`FennecRepositoryStreamStore`) saves change sets as ordinary models of the stream model through a
repository (JPA or Mongo). It uses up to `repositories` repository instances at once (default 4) and
releases them when it stops. A store is selected the same way as a projection:

```json
"fennec.jpa.EORMMappingService~streams": {
  "fennec.jpa.eorm.mappingName": "streams",
  "fennec.jpa.eorm.model.target": "(emf.nsURI=https://org.eclipse/fennec/stream/1.0.0)"
},
"fennec.jpa.EMPersistenceUnit~streams": {
  "fennec.jpa.persistenceUnitName": "streams",
  "fennec.jpa.mapping.target": "(fennec.jpa.eorm.mapping=streams)",
  "fennec.jpa.ext.eclipselink.ddl-generation": "create-tables",
  "fennec.jpa.dataSource.target": "(subprotocol=*)"
},
"fennec.repository.jpa~streams": {
  "repositoryId": "streams",
  "unit.target": "(osgi.unit.name=streams)"
},
"FennecRepositoryStreamStore": {
  "repository.target": "(persistence.repository.id=streams)"
},
"FennecStreamCore": {
  "store.target": "(fennec.stream.store=repository)"
}
```

> Retention (the deletion rules of a mapping) removes entries and change sets through the repository.

## SensorThings (STA 1.1)

`org.eclipse.fennec.stream.sta` turns the entries of TIMESERIES slots into SensorThings
observations (D-25, #56):
- the target object becomes a Thing
- its TIMESERIES slot a Datastream
- the target feature an ObservedProperty
- the source class a Sensor
- the entry an Observation, with `phenomenonTime` = entry time and `resultTime` = change set time

The component `FennecStaSink` is a `StreamSink`: the core hands it every change set right after
the store, without dropping any. It writes into a repository whose unit maps the STA model
(`https://eclipse.org/fennec/sta/1.1`).

```json
"fennec.jpa.EORMMappingService~sta": {
  "fennec.jpa.eorm.mappingName": "sta",
  "fennec.jpa.eorm.model.target": "(emf.nsURI=https://eclipse.org/fennec/sta/1.1)"
},
"fennec.jpa.EMPersistenceUnit~sta": {
  "fennec.jpa.persistenceUnitName": "sta",
  "fennec.jpa.mapping.target": "(fennec.jpa.eorm.mapping=sta)",
  "fennec.jpa.ext.eclipselink.ddl-generation": "create-tables",
  "fennec.jpa.dataSource.target": "(subprotocol=*)"
},
"fennec.repository.jpa~sta": {
  "repositoryId": "sta",
  "unit.target": "(osgi.unit.name=sta)"
},
"FennecStaSink": {
  "repository.target": "(persistence.repository.id=sta)",
  "describer.target": "(fennec.sta.describer=pool)",
  "units": ["https://eclipse.org/fennec/ogc/example/bath/1.0#//Pool/waterTemperature=degree Celsius|°C|http://unitsofmeasure.org/ucum.html#para-30"]
}
```

- **`describer.target`** selects a `TargetDescriber` that names and locates a thing. The default
  names a thing by its target id and gives it no location. The pool example reads name and
  geometry of the pool (`FennecPoolTargetDescriber`).
- **Units.** The unit of a datastream is resolved in this order; the first match wins:
  1. `units` of the sink, by feature URI (`name|symbol|definition`). Use it for models you
     cannot change.
  2. The neutral annotation `https://eclipse.org/fennec/unit` on the feature (`name`, `symbol`,
     `definition`). This is the preferred place in your own models. A unit is part of what a
     value means, like its type, so it belongs to the domain model. It is not a SensorThings
     concept. Later it will be read as the unit aspect of model.metadata
     (eclipse-fennec/model.metadata#33).
  3. *Deprecated:* the annotation `https://eclipse.org/fennec/sta` (`unit.name`, `unit.symbol`,
     `unit.definition`). Do not use it in new models. It puts STA-specific metadata into the
     domain model.

  The rule of thumb for metadata on a feature: information that changes what stored values
  mean (type, multiplicity, unit) belongs to the model. Information that only one target or
  protocol needs (STA names, `@iot` controls, OData sets, topics) belongs to that target's
  mapping or configuration.

emf.odata serves the repository, with the STA set names, in the SensorThings wire format of
`FennecStaWireProfile` (`@iot.*` control information, absolute links, the STA root document):

```json
"org.eclipse.fennec.odata.persistence.repository~sta": {
  "repository.target": "(persistence.repository.id=sta)",
  "emf.nsURIs": ["https://eclipse.org/fennec/sta/1.1"]
},
"org.eclipse.fennec.odata.servlet~sta": {
  "osgi.http.whiteboard.servlet.pattern": "/sta/v1.1/*",
  "wireProfile.target": "(odata.wire.profile=sta)",
  "odata.model.entitysets": ["Things=https://eclipse.org/fennec/sta/1.1#Thing", "Datastreams=https://eclipse.org/fennec/sta/1.1#Datastream", "..."]
}
```

`odata.base.url` sets the absolute URL of the links when the request does not tell it (behind a
proxy). `FennecStaWireProfile` runs without configuration; `mqtt.endpoints` announces the MQTT
extension in the root document.

The `@iot.id` of an entity is a number derived from its key (`Pool:kinderland`,
`Pool:kinderland.waterTemperature`, ...); the key is in `properties` (`parameters` of an
observation) as `fennec.key`.

The profile also brings the STA wire properties over several attributes (`mapping()`, emf.odata#119):
`phenomenonTime`, `resultTime` and `validTime` are ISO 8601 instants or intervals (`s/e`) over
their start/end attributes, and `result` is the one set of `resultNumber`, `resultBoolean` and
`resultText`. `$filter`, `$orderby` and `$select` work on these names and are pushed down to the
attributes. A property reads as `{"name":…}`, `$value` also works for structured values
(`unitOfMeasurement/$value`), and references read as `{"@iot.selfLink":…}`.

> Not in STA form yet: JSON-valued properties (`properties`, `parameters`, `feature`) are written
> as JSON text, not as objects (emf.odata#118).

**MQTT extension**: `FennecStaMqttPublisher` publishes each stored observation as STA JSON on
`v1.1/Observations`, `v1.1/Datastreams(<id>)/Observations` and
`v1.1/FeaturesOfInterest(<id>)/Observations`. It needs a Gecko Messaging instance on an MQTT broker:

```json
"FennecStaMqttPublisher": {
  "publisher.target": "(osgi.messaging.name=mqtt)",
  "serviceRoot": "http://localhost:8080/sta/v1.1"
}
```

The pool example runs with it as `pool-mqtt.bndrun` against the broker of `tools/brokers.sh`:

```bash
tools/brokers.sh up
./gradlew :org.eclipse.fennec.stream.example.pool:run.pool-mqtt
mosquitto_sub -p 11893 -v -t 'v1.1/#'
```

## Transport

Publisher and subscriber are factory configurations; each names a Gecko Messaging instance. See
[transport.md](transport.md) for channels and CloudEvent attributes.

```json
"org.gecko.messaging.mqtt~origin": {
  "osgi.messaging.name": "origin",
  "uri": "tcp://broker:1883",
  "cloudEvents": "binary",
  "publishAwaitConnection:Long": 10000
},
"FennecStreamPublisher~origin": {
  "publisher.target": "(osgi.messaging.name=origin)",
  "resourceSets.target": "(&(emf.name=stream)(emf.name=bath))",
  "retainKeyframes": true
},
"FennecStreamSubscriber~replica": {
  "subscription.target": "(osgi.messaging.name=replica)",
  "channel": "stream/#"
}
```

- `resourceSets.target` must name the stream model and the models of the streamed classes.
- `publishAwaitConnection` lets a publish wait for the (re)connect instead of failing.
- The subscriber writes into the in-memory replica targets unless `targets.target` names others.
