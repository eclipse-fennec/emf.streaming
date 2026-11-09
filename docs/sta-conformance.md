# SensorThings conformance (ets-sta)

The STA endpoint of the pool example (`/sta/v1.1`) is checked with the OGC conformance test suite in
TEAM Engine (#63). The only public STA suite is **ets-sta10** (STA 1.0, image `ogccite/ets-sta10`);
STA 1.1 is largely backwards compatible, so it serves until a 1.1 suite exists.

```bash
./gradlew :org.eclipse.fennec.stream.example.pool:run.pool   # the endpoint on :8080
tools/ets-sta.sh up                                           # TEAM Engine on :8081
tools/ets-sta.sh run                                          # report in generated/ets-sta/
tools/ets-sta.sh down
```

`run` takes another service root as argument. Inside the container the host is
`host.containers.internal` (podman) or `host.docker.internal` (docker); `IUT_HOST` overrides it. The
TEAM Engine web UI is at `http://localhost:8081/teamengine/` (`ogctest`/`ogctest`).

## What the suite covers

| Conformance class | ets-sta10 |
|---|---|
| A.1 Sensing Core | yes |
| A.2 Filtering Extension | yes |
| A.3 Create-Update-Delete | yes (not offered here yet: POST comes after D-25's first stage) |
| A.4 - A.8 (batch, MultiDatastream, data array, MQTT) | not implemented by the suite |

## Preconditions of the suite

- At least one entity of each type: Things, Locations, HistoricalLocations, Datastreams, Sensors,
  ObservedProperties, Observations, FeaturesOfInterest. The STA projection records a
  HistoricalLocation when it gives a thing its location.
- Each set fits on one page (no `@iot.nextLink` needed). The pool example adds observations every
  two seconds - run the suite early after the start, or raise the page size of the root.

## Expected state

The endpoint writes the SensorThings wire format through the wire profile of emf.odata (#55);
the stages of emf.odata ADR-0010 still missing keep some tests red.

| Date | Result | Open |
|---|---|---|
| 2026-11-08 | refused before the first test: the root document was the OData one | wire profile (emf.odata#110) |
| 2026-11-09 | 2 passed (`checkServiceRootUri`, `readNonexistentEntity`), 4 failed | `phenomenonTime`/`result` (emf.odata#119), property as `{"name":…}` (emf.odata#125), `$ref` on sets and `@iot.selfLink` in references (emf.odata#126) |
| 2026-11-09 (#125, #126 in) | 2 passed, 4 failed — the property and reference reads pass; missing `description`s fixed | `phenomenonTime`/`result` (emf.odata#119), `$value` on `unitOfMeasurement` (emf.odata#130), `Things(…)/Datastreams(…)/Sensor/Datastreams` 400 ("no duplicates", emf.persistence-jpa#413) |
| 2026-11-09 (wire mapping, #69–#71) | **5 passed, 1 failed** — `phenomenonTime`/`result` as wire properties, `unitOfMeasurement/$value` | `Things(…)/HistoricalLocations(…)/Locations(…)/HistoricalLocations` 400 ("no duplicates" over a many-to-many opposite, emf.persistence-jpa#415) |

The suite reads `@iot.id` as a number; the STA projection derives numeric ids from its keys for
that reason.
