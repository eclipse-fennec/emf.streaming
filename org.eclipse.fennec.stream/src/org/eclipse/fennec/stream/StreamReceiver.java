/********************************************************************
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Data In Motion Consulting - initial implementation
 ********************************************************************/
package org.eclipse.fennec.stream;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.support.ChangeSetValidator;
import org.eclipse.fennec.model.stream.support.StreamAddresses;
import org.eclipse.fennec.model.streammapping.GuardPolicy;

/**
 * Applies change sets that come from another node to local replica objects (UC-06, UC-29;
 * requirements APL-01..05, OPS-01..03). Received change sets run in the lane of their object, so
 * they never overtake each other.
 * <p>
 * Per stream it keeps the last applied sequence: a sequence seen before is a duplicate; a sequence
 * further ahead than the next is held for the gap buffer (default 5 s) in case the missing one still
 * arrives. A gap that stays makes the object {@link State#STALE} (no deltas applied until the next
 * keyframe), or, under {@link GapPolicy#SKIP_AND_MARK}, is counted and skipped. The first change
 * set of a stream is never a gap - a receiver may join a stream at any point.
 * <p>
 * A change set under a model version the receiver does not know is refused. An object the receiver
 * does not hold is introduced by a {@code KEYFRAME} or {@code CREATE}; under
 * {@link UnknownObjectPolicy#CREATE_FROM_DELTAS} also by plain deltas, and then stays
 * {@link State#INCOMPLETE} until its first keyframe. Changelog entries (with an old value) are
 * guarded: the old value must match the replica, else the {@link GuardPolicy} decides.
 * <p>
 * Applied change sets are stored in the core's store and published to its consumers.
 */
public final class StreamReceiver {

	/** What a receiver does with deltas of an object it does not hold. */
	public enum UnknownObjectPolicy {
		/** Drop them until a KEYFRAME (or CREATE) introduces the object. */
		EXPECT_KEYFRAME,
		/** Create the object from the deltas; it stays INCOMPLETE until its first keyframe. */
		CREATE_FROM_DELTAS
	}

	/** What a receiver does with a gap that the buffer did not close. */
	public enum GapPolicy {
		/** The object becomes STALE until the next keyframe. */
		STALE,
		/** The gap is counted and skipped; the object stays as it is. */
		SKIP_AND_MARK
	}

	/** How far a replica object can be trusted. */
	public enum State {
		/** Nothing received yet, or the object was deleted. */
		UNKNOWN,
		/** Built from deltas: features it did not receive are not known, not empty. */
		INCOMPLETE,
		/** Built from a keyframe and every delta since. */
		COMPLETE,
		/** A gap or a refused change set: no deltas are applied until the next keyframe. */
		STALE
	}

	/** What became of a received change set. */
	public enum Outcome {
		/** Applied (possibly with warnings). */
		APPLIED,
		/** Seen before; nothing done. */
		DUPLICATE,
		/** Held back until the gap before it closes or the buffer expires. */
		BUFFERED,
		/** Refused - invalid, unknown model version, or a guard under FAIL. The object is STALE. */
		REJECTED,
		/** Not applied because the object is unknown here (EXPECT_KEYFRAME). */
		DROPPED,
		/** Not applied because the object is STALE. */
		SKIPPED_STALE
	}

	/**
	 * @param outcome what became of the change set
	 * @param diagnostic why, with warnings for skipped entries; OK otherwise
	 */
	public record Result(Outcome outcome, Diagnostic diagnostic) {
	}

	private static final String SOURCE = "org.eclipse.fennec.stream.receiver";

	private static final class ReceiverStream {
		long lastApplied = -1;
		State state = State.UNKNOWN;
		final TreeMap<Long, Held> held = new TreeMap<>();
		boolean gapTimer;
		long gaps;
		final Map<String, Long> featureTimes = new HashMap<>();
	}

	private record Held(ChangeSet changeSet, CompletableFuture<Result> result) {
	}

	private final EventCore core;
	private final Function<String, EPackage.Registry> modelVersions;
	private final ReplicaTargets targets;
	private final GuardPolicy guard;
	private final UnknownObjectPolicy unknownObjects;
	private final GapPolicy gapPolicy;
	private final long gapBuffer;
	private final String group;
	private final Map<String, ReceiverStream> streams = new ConcurrentHashMap<>();

	private StreamReceiver(Builder builder) {
		this.core = requireNonNull(builder.core, "EventCore is required");
		this.modelVersions = requireNonNull(builder.modelVersions, "Model versions are required");
		this.targets = builder.targets != null ? builder.targets : ReplicaTargets.inMemory();
		this.guard = builder.guard;
		this.unknownObjects = builder.unknownObjects;
		this.gapPolicy = builder.gapPolicy;
		this.gapBuffer = builder.gapBuffer.toMillis();
		this.group = builder.group;
	}

	/**
	 * @return a builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Receives a change set. Invalid ones are refused at once; the others run in their object's lane.
	 *
	 * @param changeSet the change set; not changed
	 * @return what became of it, once decided
	 */
	public CompletableFuture<Result> receive(ChangeSet changeSet) {
		requireNonNull(changeSet, "ChangeSet is required");
		Diagnostic structure = ChangeSetValidator.validate(changeSet);
		if (structure.getSeverity() >= Diagnostic.ERROR) {
			return CompletableFuture.completedFuture(new Result(Outcome.REJECTED, structure));
		}
		String streamId = changeSet.getStreamId();
		core.markReplicated(streamId);
		CompletableFuture<Result> result = new CompletableFuture<>();
		core.enqueue(streamId, group, () -> handle(changeSet, result));
		return result;
	}

	/**
	 * @param streamId a stream
	 * @return how far its replica object can be trusted
	 */
	public State state(String streamId) {
		ReceiverStream stream = streams.get(streamId);
		return stream == null ? State.UNKNOWN : stream.state;
	}

	/**
	 * @param streamId a stream
	 * @return how many gaps were skipped under {@link GapPolicy#SKIP_AND_MARK}
	 */
	public long gaps(String streamId) {
		ReceiverStream stream = streams.get(streamId);
		return stream == null ? 0 : stream.gaps;
	}

	/** Lane task. */
	private void handle(ChangeSet changeSet, CompletableFuture<Result> result) {
		ReceiverStream stream = streams.computeIfAbsent(changeSet.getStreamId(), id -> new ReceiverStream());
		long sequence = changeSet.getSequence();
		if (sequence <= stream.lastApplied || stream.held.containsKey(sequence)) {
			result.complete(new Result(Outcome.DUPLICATE, ok("Sequence " + sequence + " was received before")));
			return;
		}
		if (stream.lastApplied >= 0 && sequence > stream.lastApplied + 1) {
			stream.held.put(sequence, new Held(changeSet, result));
			if (!stream.gapTimer) {
				stream.gapTimer = true;
				core.schedule(changeSet.getStreamId(), group, () -> gapExpired(stream), gapBuffer);
			}
			return;
		}
		result.complete(apply(stream, changeSet));
		drain(stream);
	}

	/** Applies held change sets that follow on without a gap. */
	private void drain(ReceiverStream stream) {
		while (!stream.held.isEmpty() && stream.held.firstKey() == stream.lastApplied + 1) {
			Held next = stream.held.pollFirstEntry().getValue();
			next.result().complete(apply(stream, next.changeSet()));
		}
	}

	/** Lane task: the buffer expired, the gap before the held change sets is real. */
	private void gapExpired(ReceiverStream stream) {
		stream.gapTimer = false;
		if (stream.held.isEmpty()) {
			return;
		}
		if (gapPolicy == GapPolicy.STALE) {
			stream.state = State.STALE;
		} else {
			stream.gaps++;
		}
		stream.lastApplied = stream.held.firstKey() - 1;
		drain(stream);
		if (!stream.held.isEmpty()) {
			// a further gap among the held ones
			stream.gapTimer = true;
			String streamId = stream.held.firstEntry().getValue().changeSet().getStreamId();
			core.schedule(streamId, group, () -> gapExpired(stream), gapBuffer);
		}
	}

	private Result apply(ReceiverStream stream, ChangeSet changeSet) {
		stream.lastApplied = changeSet.getSequence();
		EPackage.Registry registry = modelVersions.apply(changeSet.getContextFingerprint());
		if (registry == null) {
			stream.state = State.STALE;
			return new Result(Outcome.REJECTED,
					error("Unknown model version " + changeSet.getContextFingerprint() + " - change set refused"));
		}
		String[] classAndId = splitStreamId(changeSet.getStreamId());
		Optional<EClass> resolved = classAndId == null ? Optional.empty() : StreamAddresses.resolveClass(registry, classAndId[0]);
		if (resolved.isEmpty()) {
			stream.state = State.STALE;
			return new Result(Outcome.REJECTED, error("Stream " + changeSet.getStreamId() + " names no known class"));
		}
		EClass eClass = resolved.get();
		String id = classAndId[1];
		boolean introduces = changeSet.getEntries().stream()
				.anyMatch(e -> e.getKind() == DeltaKind.KEYFRAME || e.getKind() == DeltaKind.CREATE);
		boolean keyframe = changeSet.getEntries().stream().anyMatch(e -> e.getKind() == DeltaKind.KEYFRAME);
		if (stream.state == State.STALE && !keyframe) {
			return new Result(Outcome.SKIPPED_STALE, ok("Object is stale until its next keyframe"));
		}
		EObject object = targets.find(eClass, id);
		boolean created = false;
		if (object == null) {
			if (!introduces && unknownObjects == UnknownObjectPolicy.EXPECT_KEYFRAME) {
				return new Result(Outcome.DROPPED, ok("Object " + id + " is unknown here; waiting for its keyframe"));
			}
			object = targets.create(eClass, id);
			setId(object, id);
			created = true;
		}

		BasicDiagnostic diagnostic = new BasicDiagnostic(SOURCE, 0, "ChangeSet " + changeSet.getId(), null);
		List<ChangeEntry> deltas = new ArrayList<>();
		for (ChangeEntry entry : changeSet.getEntries()) {
			if (entry.getKind() != DeltaKind.CREATE && entry.getKind() != DeltaKind.KEYFRAME) {
				deltas.add(entry);
			}
		}
		// guard before anything is changed, so FAIL leaves the object untouched
		List<ChangeEntry> mismatched = new ArrayList<>();
		for (ChangeEntry entry : deltas) {
			if (entry.getKind() == DeltaKind.SET && entry.getValueOld() != null && !created) {
				EStructuralFeature feature = StreamAddresses.resolveFeature(eClass, entry).orElse(null);
				if (feature != null && !Objects.equals(entry.getValueOld(), encode(object.eGet(feature)))) {
					mismatched.add(entry);
				}
			}
		}
		if (!mismatched.isEmpty() && guard == GuardPolicy.FAIL) {
			stream.state = State.STALE;
			return new Result(Outcome.REJECTED, error(mismatched.size() + " entries do not match the replica (guard FAIL)"));
		}
		EObject target = object;
		// phase 1: keyframes
		for (ChangeEntry entry : changeSet.getEntries()) {
			if (entry.getKind() == DeltaKind.KEYFRAME) {
				for (SlotValue slot : entry.getState()) {
					StreamAddresses.resolveFeature(eClass, slot)
							.ifPresentOrElse(f -> set(target, f, slot.getValue(), diagnostic),
									() -> warn(diagnostic, "Keyframe slot " + slot.getFeatureUri() + " is no feature of " + eClass.getName()));
				}
				stream.state = State.COMPLETE;
			}
		}
		if (created && !keyframe) {
			stream.state = State.INCOMPLETE;
		}
		// phase 2: deltas
		long setTime = changeSet.getTimestamp();
		for (ChangeEntry entry : deltas) {
			if (guard == GuardPolicy.MERGE && mismatched.contains(entry)) {
				warn(diagnostic, "Entry for " + entry.getFeatureUri() + " skipped: old value does not match (MERGE)");
				continue;
			}
			switch (entry.getKind()) {
			case SET, UNSET -> applyFeature(stream, object, eClass, entry, setTime, diagnostic);
			case DELETE -> {
				targets.delete(eClass, id);
				stream.state = State.UNKNOWN;
				stream.featureTimes.clear();
			}
			default -> warn(diagnostic, entry.getKind() + " is not applied by this receiver yet");
			}
		}
		if (stream.state != State.UNKNOWN) {
			targets.changed(object);
		}
		core.store().append(changeSet);
		core.publish(changeSet);
		return new Result(Outcome.APPLIED, diagnostic);
	}

	private void applyFeature(ReceiverStream stream, EObject object, EClass eClass, ChangeEntry entry, long setTime,
			BasicDiagnostic diagnostic) {
		Optional<EStructuralFeature> feature = StreamAddresses.resolveFeature(eClass, entry);
		if (feature.isEmpty()) {
			warn(diagnostic, "Entry addresses no feature of " + eClass.getName() + ": " + entry.getFeatureUri());
			return;
		}
		long time = entry.getTimestamp() != null ? entry.getTimestamp() : setTime;
		String key = feature.get().getName();
		Long newest = stream.featureTimes.get(key);
		if (entry.getTimestamp() != null && newest != null && time < newest) {
			return; // a late sample: the replica already holds a newer value
		}
		stream.featureTimes.put(key, time);
		if (entry.getKind() == DeltaKind.UNSET) {
			object.eUnset(feature.get());
		} else {
			set(object, feature.get(), entry.getValueNew(), diagnostic);
		}
	}

	private static void set(EObject object, EStructuralFeature feature, String literal, BasicDiagnostic diagnostic) {
		if (feature instanceof EReference || feature.isMany()) {
			warn(diagnostic, "Feature " + feature.getName() + " is a reference or many-valued - not applied by this receiver yet");
			return;
		}
		if (literal == null) {
			object.eUnset(feature);
			return;
		}
		try {
			object.eSet(feature, EcoreUtil.createFromString((EDataType) feature.getEType(), literal));
		} catch (RuntimeException e) {
			warn(diagnostic, "Value '" + literal + "' does not fit " + feature.getName());
		}
	}

	private static void setId(EObject object, String id) {
		EAttribute idAttribute = object.eClass().getEIDAttribute();
		if (idAttribute != null) {
			EcoreUtil.setID(object, id);
		}
	}

	/** {@code nsURI#//Class/id} → {nsURI#//Class, id}. */
	static String[] splitStreamId(String streamId) {
		int fragment = streamId.indexOf(StreamAddresses.FRAGMENT_SEPARATOR);
		if (fragment < 0) {
			return null;
		}
		int slash = streamId.indexOf('/', fragment + StreamAddresses.FRAGMENT_SEPARATOR.length());
		if (slash < 0 || slash == streamId.length() - 1) {
			return null;
		}
		return new String[] { streamId.substring(0, slash), streamId.substring(slash + 1) };
	}

	private static String encode(Object value) {
		return StreamValues.encode(value, EcoreUtil::getID);
	}

	private static Diagnostic ok(String message) {
		return new BasicDiagnostic(Diagnostic.OK, SOURCE, 0, message, null);
	}

	private static Diagnostic error(String message) {
		return new BasicDiagnostic(Diagnostic.ERROR, SOURCE, 0, message, null);
	}

	private static void warn(BasicDiagnostic diagnostic, String message) {
		diagnostic.add(new BasicDiagnostic(Diagnostic.WARNING, SOURCE, 0, message, null));
	}

	/**
	 * Builds a {@link StreamReceiver}.
	 */
	public static final class Builder {

		private EventCore core;
		private Function<String, EPackage.Registry> modelVersions;
		private ReplicaTargets targets;
		private GuardPolicy guard = GuardPolicy.FAIL;
		private UnknownObjectPolicy unknownObjects = UnknownObjectPolicy.EXPECT_KEYFRAME;
		private GapPolicy gapPolicy = GapPolicy.STALE;
		private Duration gapBuffer = Duration.ofSeconds(5);
		private String group = StreamSlots.DEFAULT_GROUP;

		private Builder() {
		}

		/**
		 * @param core whose lanes, store and consumers the receiver uses (required)
		 * @return this
		 */
		public Builder core(EventCore core) {
			this.core = core;
			return this;
		}

		/**
		 * @param modelVersions the packages of a model version by its fingerprint; {@code null} for an
		 *        unknown version (required)
		 * @return this
		 */
		public Builder modelVersions(Function<String, EPackage.Registry> modelVersions) {
			this.modelVersions = modelVersions;
			return this;
		}

		/**
		 * @param targets where replica objects live; default in memory
		 * @return this
		 */
		public Builder targets(ReplicaTargets targets) {
			this.targets = targets;
			return this;
		}

		/**
		 * @param guard what a mismatching old value does; default FAIL
		 * @return this
		 */
		public Builder guard(GuardPolicy guard) {
			this.guard = requireNonNull(guard);
			return this;
		}

		/**
		 * @param unknownObjects what deltas of an unknown object do; default EXPECT_KEYFRAME
		 * @return this
		 */
		public Builder unknownObjects(UnknownObjectPolicy unknownObjects) {
			this.unknownObjects = requireNonNull(unknownObjects);
			return this;
		}

		/**
		 * @param gapPolicy what a gap that stays does; default STALE
		 * @return this
		 */
		public Builder gapPolicy(GapPolicy gapPolicy) {
			this.gapPolicy = requireNonNull(gapPolicy);
			return this;
		}

		/**
		 * @param gapBuffer how long a missing sequence may still arrive; default 5 s
		 * @return this
		 */
		public Builder gapBuffer(Duration gapBuffer) {
			this.gapBuffer = requireNonNull(gapBuffer);
			return this;
		}

		/**
		 * @param group the execution group received change sets run in; default {@code default}
		 * @return this
		 */
		public Builder group(String group) {
			this.group = requireNonNull(group);
			return this;
		}

		/**
		 * @return the receiver
		 */
		public StreamReceiver build() {
			return new StreamReceiver(this);
		}
	}
}
