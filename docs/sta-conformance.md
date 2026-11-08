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

Until the wire profile of emf.odata#110 exists and the STA profile is implemented here (#55), the
endpoint answers in OData form (`@odata.*`, `phenomenonTimeStart` …): the baseline is red. The
report shows what each stage of ADR-0010 achieves.
