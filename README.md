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
| `org.eclipse.fennec.stream.model` | Change-stream metamodel (`stream.ecore`): `ChangeSet`, `ChangeEntry`, `DeltaKind`, `SlotValue`, the context-snapshot chain (`ContextSnapshot`, `CompositeSnapshot`, `PackageFingerprint`). Wire/log format, evolution is additive-only. |

## Build

bnd workspace driven by Gradle 9 (`biz.aQute.bnd.workspace`), Java 21.

```bash
./gradlew build        # build + unit tests
./gradlew testOSGi     # OSGi integration tests
```

Project coordinates live in `gradle.properties`, the library selection in `cnf/ext/fennec.bnd`
(see `cnf/SETUP.MD`).

## License

[Eclipse Public License 2.0](LICENSE)
