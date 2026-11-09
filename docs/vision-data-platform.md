# Vision: a model-driven data platform

**Status:** vision, recorded 2026-11-09. Not an implementation plan. It describes where the
streaming work is meant to lead and which building blocks exist or are missing. Concrete work
comes in as issues.

## 1. The idea

A data platform configured entirely through models. A user picks input sources in a UI and
connects their values to the features of a domain object. They choose where the result should
go: a database, an OGC Features endpoint, an MQTT topic, a REST service. Behind the UI the
platform creates the models that do the work: mapping models, generated Ecore models and service
models. **No code is generated.** Everything is a model that can be stored, validated, versioned
and edited again.

The platform rests on the change stream of this project and on configuration. Most building
blocks exist already in the Fennec repositories. What is missing is mainly the central piece that
connects them, the **object store**, plus joins, rules and service design.

## 2. The running example: a pool

A public bath has pools. Each pool has a temperature sensor and a pH sensor. The sensors send
over LoRaWAN; ChirpStack forwards their messages over MQTT. Each pool also has a geometry,
maintained elsewhere as a GeoJSON pool.

What the user does in the UI:

1. **Pick the inputs:** the temperature and pH sensors (MQTT via ChirpStack) and the GeoJSON
   pools (a database or service).
2. **Drag values onto features:** sensor temperature → `Pool.currentTemperature`, sensor pH →
   `Pool.currentPh`. This creates a stream mapping (today `StreamMapping`).
3. **Assign sensors to pools:** sensor `A81758FFFE04…` belongs to pool `4711`.
4. **Compose an OGC feature:** `Pool` (primary) and `GeoJsonPool` (secondary) are joined into one
   OGC feature. This is a second mapping, with a join.
5. **Pick the outputs:** persist the pool, serve the OGC feature, publish changes over MQTT,
   offer a REST service.

```
 sensors ──MQTT──▶ [convert, validate, deadband] ──▶ stream ──▶ [identity: sensor → pool]
                                                     │                 │
                                          time series (every change)   ▼
                                                              ┌─────────────────┐
 GeoJSON pools ──(lazy/eager, by mapping table)─────────────▶ │  OBJECT STORE   │
                                                              │ Pool, GeoJsonPool│
                                                              │  OGC Feature …   │
                                                              └────────┬────────┘
                                         [window, batch, coalesce per source/listener]
                                                                       │ events
                    ┌───────────────┬──────────────────┬───────────────┼──────────────┐
                    ▼               ▼                  ▼               ▼              ▼
                persistence    OGC feature       MQTT output      rules         REST/DDSR
                (per EClass)   composition                      (OCL/QVT-O)    services
```

## 3. Building blocks

### 3.1 Inputs and value conversion

Sources are streams (MQTT, ChirpStack), databases and services. A stream message carries the id
of its sensor and the raw value.

**Converters and validators may run on both sides of a mapping:**

- **Source side:** the raw value of the hardware (e.g. a byte 0–255) becomes a temperature or a
  pH value. This depends on the sensor type.
- **Target side:** a value is converted or validated before it is written into the target
  feature.

The mapping model has hooks for this already: `ValueSource.converterId` (source side) and
`Slot.targetConverterId` (target side). Validators are missing as their counterpart.

### 3.2 Deadband rules on two levels

A change can be dropped when it is too small, on each level separately:

| Level | Example rule | Effect |
|---|---|---|
| Sensor / stream | send only if Δ ≥ 0.5 °C | the **stream**, and with it the time series, records every 0.5 °C step |
| Target object | write only if Δ ≥ 1 °C | `Pool.currentTemperature` changes only in whole degrees |

So the time series and the object state are different views with their own resolution. The
stream mapping has the rule types already (`ChangeRule` with `AbsoluteChange` and
`PercentageChange`). Open is on which level a rule applies and how both levels are configured.

### 3.3 Identity, join and composition

The same mechanism serves two purposes:

- **Sensor assignment (input side):** the stream message carries the sensor id. The mapping
  extracts the value and resolves the sensor id to the primary object: sensor `A81758FFFE04…`
  → pool `4711`.
- **Join (composition):** several source objects feed one target object. For the OGC feature:

  | Source class | Identity | Target |
  |---|---|---|
  | `Pool` (primary) | `Pool.id` | `Feature.id` |
  | `GeoJsonPool` (secondary) | `GeoJsonPool.poolId` | `Feature.id` |

  Equal target ids mean the same object: the slots of both sources are merged into one feature.

**When the ids do not match**, a mapping table translates them. This needs a **primary** source
(the leading system, whose events drive the join) and **secondary** sources. When a primary
object arrives, the platform looks up the secondary id in the mapping table, fetches the
secondary object from its database or service (**lazy** on first access or **eager** on arrival)
and puts it into the object store.

The mapping model is the place for this. `IdentityResolution` exists already: per
`(sourceClass, targetClass)`, an optional `resolver` (a `ValueSource`, so OCL, a function or a
feature path) yields the target id, and `cacheable` marks a stable result. Missing:

- the **role** of a source (primary or secondary);
- **mapping tables** as a resolver kind;
- **fetching** secondary objects, lazy or eager.

#### Loading secondary objects through proxy URIs

EMF already has the mechanism to load an object on demand: a **proxy** with a URI, resolved when
it is first accessed. The platform uses it for secondary objects:

- **Every secondary source has a base URI.** Where the GeoJSON pools come from has to be
  configured anyway: a REST or OpenAPI interface, a database, a file. That configuration yields
  the base URI of all potential GeoJSON pools, e.g. `https://gis.example.org/pools/` or a
  persistence URI of a repository.
- **The resolved id completes the URI.** Once the identity map (below) gives the secondary id
  of a primary object, the URI of the secondary object is the base URI plus that id:
  `https://gis.example.org/pools/GP-0815`.
- **The object is a proxy until needed.** The composed object references the secondary object as
  a proxy with this URI. Resolving it loads the instance from its source through the matching
  resource factory (REST, persistence, CSV) and puts it into the object store. **Lazy** means
  resolving on first access; **eager** means resolving when the primary object arrives.

So a join needs no special loader per source: the source is a resource behind a URI, and EMF's
proxy resolution does the fetching.

#### The identity map

Before any of this, the platform has to know **which ids belong together**: which temperature and
pH sensors belong to which pool, and which GeoJSON pool belongs to which pool. These assignments
are made somewhere, and that somewhere is a model: an **identity map** for a whole set of
EClasses, saying where the data comes from and which ids correspond.

- **Static assignments** for small, stable sets: sensors `5` and `12` belong to pool `8`.
- **Mapping tables** for larger or external sets: the GeoJSON pools come from a CSV file. The file
  is loaded and becomes EMF objects; each row says that pool `8` (feature `poolId`) corresponds to
  GeoJSON pool `GP-0815` (feature `geoJsonPool`). The map records the file's name and location.
- **Rules** where an assignment is computed rather than listed (OCL, a function).
- **Sources:** for each EClass, the base URI its objects are loaded from (see above).

With that, the identity map is a complete picture: which ids belong together and where each
object comes from. Stream mapping (sensor → pool) and join (pool → GeoJSON pool → OGC feature)
use the same map.

### 3.4 The object store

The central piece, and the main missing building block. It starts in emf.streaming and can move
into a project of its own later.

- **A standalone cache of EObjects**, the central instance of all live objects (pools, GeoJSON
  pools, composed features). It is not a front of the database; persistence is one of its
  consumers.
- **Every change raises an event** on its instance, whether the change comes from a stream or
  elsewhere. Listeners subscribe to events.
- **Lifecycle:** on shutdown every object is serialized, on startup deserialized, so the store
  comes back with all its objects.
- **Eviction:** an object without events for a configurable interval is serialized to free
  memory. When an event for it arrives, it is loaded again first and then the event is
  dispatched.
- **Heartbeat:** optionally an object raises an event at regular intervals, configurable per
  object or EClass.
- **Persistence, two ways:**
  - per EClass or object: write through to the database on every change;
  - or as a listener on the event mechanism.
- **Time series** need their own answer. The stream keeps every change that passed the source
  deadband (§3.2). The object keeps only its current state. Persisting the time series belongs
  to the stream level (`Recording`, see `timeseries-access`); how this connects to the store is
  open.

### 3.5 Windowing, batching, coalescing

Many changes become fewer events. This is configurable **per source** and **per listener**:

- **By time:** e.g. every 10 s. All pH and temperature values of the window are collected, the
  last value is written into the object, and the change is detected then. Many changes within a
  second become one.
- **Every change, in order:** each change is kept and sorted by its timestamp.
- **By count:** e.g. every 10 events.
- **On one slot or across slots:** 10 events on the slots `ph` and `temperature` become one event
  that carries both values (a cascade over several slots).
- **Coalescing by operation or method:** changes are summarized by what they do, e.g. the last
  value wins, or the minimum, maximum or mean of the window.

OCL expresses most of the conditions and aggregations.

### 3.6 Listeners and outputs

Listeners hang on the store and are configured, not coded:

- **persistence** of the whole object (e.g. the pool);
- **composition** of derived objects (the OGC feature from pool and GeoJSON pool);
- **MQTT output** of the whole object or its changes (today the STA MQTT publisher, to be made
  generic);
- **rules** (§3.7);
- **services** (§3.8).

### 3.7 Rules

Some reactions are rules over the live objects: derived values (virtual sensors), conditions,
actions, timing. A rule language exists as a draft in emf.matter
(`workspace/docs/rule-language.md`):

- QVT-O syntax with `rule … when { OCL }`;
- triggers derived from the OCL AST, so a rule runs only when a feature it reads changes;
- rules are models, typed against the metamodel when loaded.

Mapping stays with QVT; expressions stay with OCL. The rule engine is the missing third piece.

### 3.8 Generated models and service design

The output of a mapping need not be an existing model. It can be **a new Ecore generated from
the mapping**: the target class with exactly the slots the user connected. That model is
registered in the **model atlas** (fennec-model.atlas), so other tools find it like any other
model.

For such a model, **services are designed, not coded**:

1. **REST model:** an EMF model describes the operations (e.g. `GET /pools/{id}`,
   `GET /pools?minTemperature=…`), their parameters and result types, all over the generated
   Ecore.
2. **Mapping to DDSR:** the REST model maps onto the service model of DDSR (emf.services, the
   Dynamic Distributed Service Registry), and back. The operations become registered services,
   usable inside the infrastructure (Java and TypeScript SDKs, REST/SSE, MQTT) and by third
   parties.
3. **OpenAPI** is generated from the REST model.

Nothing is generated as code. A new service is a model change.

## 4. Building blocks: status

| Building block | Status | Where |
|---|---|---|
| Change stream from sources into a domain model | exists | emf.streaming |
| Generic mapping (slots, value sources, identities, OCL) | exists | fennec.models (`mapping.model`), emf.util (engine) |
| Converter hooks on both sides | exist; validators missing | `ValueSource.converterId`, `Slot.targetConverterId` |
| Deadband rules (absolute, percentage), time units, retention | exist; level not settled | `streammapping.ecore` (`ChangeRule`) |
| Time series of the stream | concept | `timeseries-access`, `Recording` |
| Persistence (JPA, Mongo) | exists | emf.persistence-jpa |
| OGC Features | exists | emf.ogc.features |
| SensorThings output, MQTT publisher | exists (STA-specific) | emf.streaming (`stream.sta`) |
| Units of measurement | exists | fennec.models (`units.model`) |
| OCL, QVT-O | exist | m2x |
| Model atlas | exists | fennec-model.atlas |
| Service registry (DDSR), SDKs, REST/SSE, MQTT | exists | emf.services |
| **Object store** (cache, events, eviction, lifecycle) | **missing** | starts in emf.streaming |
| **Windowing / batching / coalescing** per source and listener | **missing** (window types exist in the stream mapping) | emf.streaming |
| **Join / composition** (primary, secondary, mapping tables, lazy/eager) | **missing** | mapping model + store |
| **Identity map** (static assignments, CSV mapping tables, base URIs per EClass) | **missing** | mapping model |
| Loading secondary objects on demand | mechanism exists (EMF proxies); resource factories per source kind partly missing | EMF, emf.persistence-jpa |
| **Generating an Ecore from a mapping** | **missing** | |
| **REST model ⇄ DDSR mapping, OpenAPI** | **missing** | emf.services |
| **Rule engine** | **draft** | emf.matter (`rule-language.md`) |
| **UI** | **missing** | |

## 5. Open questions

- **Object store API:** how a listener subscribes (EClass, object, feature); whether events are
  EMF notifications, stream change sets, or both.
- **Eviction and consistency:** what happens to a pending window when its object is evicted.
- **Time series:** how the stream's time series and the store's current state are stored and
  queried together (§3.4).
- **Deadband levels:** where a source-level rule and a target-level rule are configured, and
  whether both live in the mapping (§3.2).
- **Join model:** primary/secondary roles and mapping tables as an extension of
  `IdentityResolution`, or a composition model of its own.
- **Identity map:** whether it is part of the mapping model or a model of its own that several
  mappings share; how a mapping table (CSV, database) is described and kept up to date.
- **Proxy resolution:** which resource factories exist per source kind (REST/OpenAPI, persistence,
  CSV), and how a failed resolution is handled (retry, leave the proxy, event).
- **Generated Ecore:** how a generated model evolves when the mapping changes (versions,
  fingerprints, the metadata of `emf.osgi`).
- **Service design:** what the REST model looks like and where it lives (emf.services or
  fennec.models).

## 6. A first slice through the stack

The pool example end to end, as the first proof:

1. Sensors (MQTT) → stream with source deadband → `Pool.currentTemperature`, `Pool.currentPh`
   (largely exists today; see the pool example).
2. Object store holding the pools; persistence as a listener.
3. An identity map: sensors → pools (static), pools → GeoJSON pools (a CSV mapping table).
4. `GeoJsonPool` as a secondary source behind a base URI, loaded as a proxy and joined into an
   OGC feature, served through emf.ogc.features.
5. A generated Ecore of the OGC feature, registered in the model atlas.
6. A REST model with `GET /pools/{id}`, mapped to DDSR, with a generated OpenAPI.
