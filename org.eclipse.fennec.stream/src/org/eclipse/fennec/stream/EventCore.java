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

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.mapping.MappedValue;
import org.eclipse.fennec.mapping.Mapper;
import org.eclipse.fennec.mapping.MappingRegistry;
import org.eclipse.fennec.mapping.Mappings;
import org.eclipse.fennec.model.mapping.Mapping;
import org.eclipse.fennec.model.stream.ChangeEntry;
import org.eclipse.fennec.model.stream.ChangeSet;
import org.eclipse.fennec.model.stream.DeltaKind;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.Lateness;
import org.eclipse.fennec.model.streammapping.ProjectionMode;
import org.eclipse.fennec.model.streammapping.RecordingMode;
import org.eclipse.fennec.model.streammapping.StreamMapping;
import org.eclipse.fennec.model.streammapping.StreamSlot;
import org.eclipse.fennec.stream.TargetStream.Batch;
import org.eclipse.fennec.stream.TargetStream.Pending;
import org.eclipse.fennec.stream.impl.Mailbox;
import org.osgi.util.pushstream.PushStream;
import org.osgi.util.pushstream.PushStreamProvider;
import org.osgi.util.pushstream.QueuePolicyOption;
import org.osgi.util.pushstream.SimplePushEventSource;

/**
 * The event core: turns source objects into change sets of their target objects (UC-01, UC-28).
 * <p>
 * {@link #offer} runs on the caller's thread up to the hand-over: it finds the stream mappings
 * reading the source's class, extracts the values and resolves the target ids - so the values are
 * a snapshot taken at the moment of the offer. Then each target object's values go to its lane
 * (a {@link Mailbox} over the pool of its execution group), where they are handled one after the
 * other: change rules, batching, sequence, store, projection, keyframes, publication.
 * <p>
 * The stream of a target object is identified by {@code <classUri>/<targetId>}. Sequences are
 * assigned here, per stream, without gaps. A change set is final once published.
 * <p>
 * Plain Java; {@link #close()} stops the pools.
 */
public final class EventCore implements AutoCloseable {

	/**
	 * A named thread pool of the core. The order in which groups are configured is their rank: a
	 * target object whose slots name several groups runs in the first of them.
	 *
	 * @param name the name mappings refer to
	 * @param threads the pool size
	 * @param slice how many tasks a lane runs before it yields its thread
	 */
	public record ExecutionGroup(String name, int threads, int slice) {

		/**
		 * Canonical constructor.
		 */
		public ExecutionGroup {
			requireNonNull(name, "Group name is required");
			if (threads < 1 || slice < 1) {
				throw new IllegalArgumentException("A group needs at least one thread and a slice of at least 1");
			}
		}
	}

	/**
	 * Counters of the core (requirement OPS-05).
	 *
	 * @param offered values handed to lanes
	 * @param passed values that passed their rules
	 * @param rejected values the rules held back
	 * @param changeSets change sets written, keyframes included
	 * @param keyframes keyframe change sets written
	 * @param lanes lanes currently holding work
	 */
	public record Metrics(long offered, long passed, long rejected, long changeSets, long keyframes, int lanes) {
	}

	private static final Logger LOGGER = Logger.getLogger(EventCore.class.getName());
	private static final StreamFactory STREAM = StreamFactory.eINSTANCE;
	private static final int OUTPUT_BUFFER = 1024;

	private final MappingRegistry mappings;
	private final Mapper mapper;
	private final ChangeRules rules;
	private final StreamStore store;
	private final Projection projection;
	private final Function<EPackage, String> fingerprints;
	private final Clock clock;
	private final List<ExecutionGroup> groups;
	private final Map<String, ExecutorService> pools = new LinkedHashMap<>();
	private final ScheduledExecutorService timer;
	private final Map<String, Mailbox> mailboxes = new ConcurrentHashMap<>();
	private final Map<String, TargetStream> streams = new ConcurrentHashMap<>();
	private final Set<String> replicated = ConcurrentHashMap.newKeySet();
	private final PushStreamProvider pushStreams = new PushStreamProvider();
	private final SimplePushEventSource<ChangeSet> output;
	private final AtomicLong offered = new AtomicLong();
	private final AtomicLong passed = new AtomicLong();
	private final AtomicLong rejected = new AtomicLong();
	private final AtomicLong changeSets = new AtomicLong();
	private final AtomicLong keyframes = new AtomicLong();

	private EventCore(Builder builder) {
		this.mappings = requireNonNull(builder.mappings, "MappingRegistry is required");
		this.mapper = requireNonNull(builder.mapper, "Mapper is required");
		this.rules = builder.rules != null ? builder.rules : new ChangeRules(null);
		this.store = builder.store != null ? builder.store : new InMemoryStreamStore();
		this.projection = builder.projection != null ? builder.projection : Projection.NONE;
		this.fingerprints = builder.fingerprints;
		this.clock = builder.clock;
		this.groups = List.copyOf(builder.groups());
		for (ExecutionGroup group : groups) {
			pools.put(group.name(), Executors.newFixedThreadPool(group.threads(), threads("fennec-stream-" + group.name())));
		}
		this.timer = Executors.newSingleThreadScheduledExecutor(threads("fennec-stream-timer"));
		this.output = pushStreams.createSimpleEventSource(ChangeSet.class);
	}

	/**
	 * @return a builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Offers a source object now, with the default author.
	 *
	 * @param source the source object
	 */
	public void offer(EObject source) {
		offer(source, clock.millis(), null);
	}

	/**
	 * Offers a source object: every stream mapping reading its class maps it, and each target
	 * object's values go to that object's lane.
	 *
	 * @param source the source object
	 * @param eventTime the event time of its values, epoch millis
	 * @param author who emits the change; {@code null} = {@code mapping:<id>}
	 */
	public void offer(EObject source, long eventTime, String author) {
		route(List.of(requireNonNull(source, "Source object is required")), eventTime, author, false);
	}

	/**
	 * Offers several source objects as one unit of work: slots batching by {@code COMMIT} write one
	 * change set per target object for the whole unit.
	 *
	 * @param sources the source objects
	 * @param eventTime the event time, epoch millis
	 * @param author who emits the change; {@code null} = {@code mapping:<id>}
	 */
	public void offerUnitOfWork(Collection<? extends EObject> sources, long eventTime, String author) {
		route(List.copyOf(requireNonNull(sources, "Sources are required")), eventTime, author, true);
	}

	/**
	 * A new stream of the change sets written from now on (requirement CON-01). A slow consumer
	 * loses the oldest change sets of its buffer rather than holding up the core.
	 *
	 * @return the stream; closing it ends the subscription
	 */
	public PushStream<ChangeSet> changes() {
		return pushStreams.buildStream(output).withBuffer(new ArrayBlockingQueue<>(OUTPUT_BUFFER))
				.withQueuePolicy(QueuePolicyOption.DISCARD_OLDEST).build();
	}

	/**
	 * Writes every pending batch now, whatever its strategy.
	 */
	public void flush() {
		for (TargetStream stream : streams.values()) {
			enqueue(stream.streamId, stream.group, () -> {
				for (String key : new ArrayList<>(stream.batches.keySet())) {
					flushBatch(stream, key, null);
				}
			});
		}
	}

	/**
	 * Waits until no lane holds work - for tests and orderly shutdown.
	 *
	 * @param timeout how long to wait at most
	 * @return whether the core went idle in time
	 * @throws InterruptedException if interrupted while waiting
	 */
	public boolean awaitIdle(Duration timeout) throws InterruptedException {
		long deadline = System.nanoTime() + timeout.toNanos();
		while (System.nanoTime() < deadline) {
			if (mailboxes.values().stream().allMatch(Mailbox::isIdle)) {
				return true;
			}
			TimeUnit.MILLISECONDS.sleep(2);
		}
		return false;
	}

	/**
	 * Schedules a task into the lane of a stream after a delay.
	 */
	void schedule(String streamId, String group, Runnable task, long delayMillis) {
		timer.schedule(() -> enqueue(streamId, group, task), delayMillis, TimeUnit.MILLISECONDS);
	}

	/**
	 * @return the clock of the core
	 */
	Clock clock() {
		return clock;
	}

	/**
	 * Publishes a change set to the consumers of {@link #changes()} - for change sets the receiver applied.
	 */
	void publish(ChangeSet changeSet) {
		output.publish(changeSet);
	}

	/**
	 * @return the store the core writes to
	 */
	public StreamStore store() {
		return store;
	}

	/**
	 * @param targetClass the class of a target object
	 * @param targetId its id
	 * @return the id of its stream
	 */
	public static String streamId(EClass targetClass, String targetId) {
		return Mappings.uriOf(targetClass) + "/" + targetId;
	}

	/**
	 * @return the counters
	 */
	public Metrics metrics() {
		return new Metrics(offered.get(), passed.get(), rejected.get(), changeSets.get(), keyframes.get(), mailboxes.size());
	}

	@Override
	public void close() {
		timer.shutdownNow();
		pools.values().forEach(ExecutorService::shutdown);
		output.close();
	}

	private void route(List<EObject> sources, long eventTime, String author, boolean unitOfWork) {
		Map<String, List<Runnable>> work = new LinkedHashMap<>();
		Map<String, TargetKey> keys = new LinkedHashMap<>();
		for (EObject source : sources) {
			for (Mapping mapping : mappings.forSource(source.eClass())) {
				if (!(mapping instanceof StreamMapping streamMapping) || streamMapping.getTarget() == null) {
					continue;
				}
				Map<String, List<MappedValue>> byTarget = new LinkedHashMap<>();
				for (MappedValue value : mapper.map(streamMapping, source)) {
					byTarget.computeIfAbsent(value.targetId(), id -> new ArrayList<>()).add(value);
				}
				for (Map.Entry<String, List<MappedValue>> target : byTarget.entrySet()) {
					String streamId = streamId(streamMapping.getTarget(), target.getKey());
					if (replicated.contains(streamId)) {
						// single master: a replicated object is written by its origin only
						LOGGER.warning(() -> "Stream " + streamId + " is replicated here - local values are refused");
						continue;
					}
					keys.putIfAbsent(streamId, new TargetKey(streamMapping, target.getKey()));
					// created here, so flush() sees the stream as soon as offer() returned
					TargetStream stream = stream(streamId, streamMapping, target.getKey());
					List<MappedValue> values = target.getValue();
					offered.addAndGet(values.size());
					work.computeIfAbsent(streamId, id -> new ArrayList<>())
							.add(() -> handle(stream, values, eventTime, author, unitOfWork));
				}
			}
		}
		for (Map.Entry<String, List<Runnable>> entry : work.entrySet()) {
			TargetKey key = keys.get(entry.getKey());
			String group = groupOf(key.mapping());
			for (Runnable task : entry.getValue()) {
				enqueue(entry.getKey(), group, task);
			}
			if (unitOfWork) {
				enqueue(entry.getKey(), group, () -> commit(stream(entry.getKey(), key.mapping(), key.targetId()), author));
			}
		}
	}

	private record TargetKey(StreamMapping mapping, String targetId) {
	}

	/**
	 * Marks a stream as replicated: its change sets come from another node, local capture is refused
	 * (requirement OPS-03).
	 */
	void markReplicated(String streamId) {
		replicated.add(streamId);
	}

	/**
	 * Runs a task in the lane of a stream - for the receiver, so received and captured change sets of
	 * one object never overtake each other.
	 */
	void enqueue(String streamId, String group, Runnable task) {
		ExecutorService pool = pools.getOrDefault(group, pools.get(StreamSlots.DEFAULT_GROUP));
		int slice = sliceOf(group);
		mailboxes.compute(streamId, (id, mailbox) -> {
			Mailbox lane = mailbox != null ? mailbox
					: new Mailbox(pool, slice, () -> mailboxes.computeIfPresent(id, (k, m) -> m.isIdle() ? null : m));
			lane.enqueue(task);
			return lane;
		});
	}

	private TargetStream stream(String streamId, StreamMapping mapping, String targetId) {
		return streams.computeIfAbsent(streamId,
				id -> new TargetStream(id, targetId, mapping, groupOf(mapping), store.lastSequence(id)));
	}

	/** Lane task: rules and batching for the values of one offer. */
	private void handle(TargetStream stream, List<MappedValue> values, long eventTime, String author, boolean unitOfWork) {
		for (MappedValue value : values) {
			if (!(value.slot() instanceof StreamSlot slot)) {
				LOGGER.fine(() -> "Slot without streaming policies in stream mapping " + stream.mapping.getId() + " - skipped");
				continue;
			}
			String address = StreamSlots.address(slot);
			stream.slots.putIfAbsent(address, slot);
			SlotState state = stream.states.computeIfAbsent(address, a -> restore(stream, slot, a));
			boolean hadLast = state.hasLast();
			Object previous = state.lastValue();
			if (!rules.offer(slot.getRule(), state, value.value(), eventTime, value.source())) {
				rejected.incrementAndGet();
				continue;
			}
			passed.incrementAndGet();
			RecordingMode recording = StreamSlots.recording(slot);
			String valueOld = recording == RecordingMode.CHANGELOG && hadLast ? encode(previous) : null;
			BatchStrategy strategy = StreamSlots.batch(slot);
			String key = batchKey(strategy, unitOfWork);
			Batch batch = stream.batches.computeIfAbsent(key, k -> new Batch());
			add(batch, new Pending(slot, address, value.value(), encode(value.value()), valueOld, eventTime, recording,
					value.source()));
			decideFlush(stream, key, batch, strategy, unitOfWork, author);
		}
	}

	private static void add(Batch batch, Pending pending) {
		if (pending.recording == RecordingMode.CHANGELOG) {
			for (Pending held : batch.pending) {
				if (held.recording == RecordingMode.CHANGELOG && held.address.equals(pending.address)) {
					// coalesce: keep the old value from before the batch, take the newest value
					held.value = pending.value;
					held.valueNew = pending.valueNew;
					held.eventTime = pending.eventTime;
					return;
				}
			}
		}
		batch.pending.add(pending);
	}

	private void decideFlush(TargetStream stream, String key, Batch batch, BatchStrategy strategy, boolean unitOfWork,
			String author) {
		BatchKind kind = strategy.getKind();
		if (kind == BatchKind.COMMIT && unitOfWork) {
			return; // written by the commit task at the end of the unit
		}
		if (kind == BatchKind.COUNT && batch.pending.size() < Math.max(1, strategy.getCount())) {
			return;
		}
		if (kind == BatchKind.WINDOW && strategy.getWindow() > 0) {
			if (!batch.flushScheduled) {
				batch.flushScheduled = true;
				long delay = ChangeRules.millis(strategy.getWindow(), strategy.getUnit());
				timer.schedule(() -> enqueue(stream.streamId, stream.group, () -> flushBatch(stream, key, author)), delay,
						TimeUnit.MILLISECONDS);
			}
			return;
		}
		flushBatch(stream, key, author);
	}

	/** Lane task at the end of a unit of work. */
	private void commit(TargetStream stream, String author) {
		for (String key : new ArrayList<>(stream.batches.keySet())) {
			if (key.startsWith(BatchKind.COMMIT.getName())) {
				flushBatch(stream, key, author);
			}
		}
	}

	/** Lane task: writes one batch as a change set, then projects and keyframes. */
	private void flushBatch(TargetStream stream, String key, String author) {
		Batch batch = stream.batches.remove(key);
		if (batch == null || batch.pending.isEmpty()) {
			return;
		}
		EClass target = stream.mapping.getTarget();
		long now = clock.millis();
		ChangeSet changeSet = header(stream, author, batch.pending.size() == 1 ? batch.pending.get(0).eventTime : now, now);
		for (Pending pending : batch.pending) {
			ChangeEntry entry = STREAM.createChangeEntry();
			entry.setObjectId(stream.targetId);
			entry.setKind(DeltaKind.SET);
			EStructuralFeature feature = pending.slot.getTargetFeature();
			if (feature != null) {
				entry.setFeatureId(target.getFeatureID(feature));
			}
			entry.setFeatureUri(pending.address);
			entry.setValueOld(pending.valueOld);
			entry.setValueNew(pending.valueNew);
			entry.setTimestamp(pending.eventTime);
			changeSet.getEntries().add(entry);
		}
		write(changeSet);
		for (Pending pending : batch.pending) {
			project(stream, pending);
		}
		stream.setsSinceKeyframe++;
		if (keyframeDue(stream, now)) {
			writeKeyframe(stream, author, now);
		}
	}

	private void project(TargetStream stream, Pending pending) {
		EStructuralFeature feature = pending.slot.getTargetFeature();
		if (feature == null || StreamSlots.mode(pending.slot) != ProjectionMode.PROJECT) {
			return;
		}
		Long projected = stream.projectedTimes.get(pending.address);
		if (StreamSlots.lateness(pending.slot) == Lateness.EVENT_TIME && projected != null && pending.eventTime < projected) {
			return; // the target already holds a newer value
		}
		Optional<Object> value = mapper.extractor().toTarget(pending.slot, pending.value);
		if (value.isPresent() && projection.project(stream.mapping.getTarget(), stream.targetId, feature, value.get())) {
			stream.projectedTimes.put(pending.address, pending.eventTime);
		}
	}

	private boolean keyframeDue(TargetStream stream, long now) {
		if (stream.lastKeyframeTime < 0) {
			return true;
		}
		int everySets = stream.mapping.getKeyframeEverySets();
		int everyMinutes = stream.mapping.getKeyframeEveryMinutes();
		return everySets > 0 && stream.setsSinceKeyframe >= everySets
				|| everyMinutes > 0 && now - stream.lastKeyframeTime >= TimeUnit.MINUTES.toMillis(everyMinutes);
	}

	private void writeKeyframe(TargetStream stream, String author, long now) {
		EClass target = stream.mapping.getTarget();
		ChangeSet changeSet = header(stream, author, now, now);
		ChangeEntry keyframe = STREAM.createChangeEntry();
		keyframe.setObjectId(stream.targetId);
		keyframe.setKind(DeltaKind.KEYFRAME);
		keyframe.setClassUri(Mappings.uriOf(target));
		for (Map.Entry<String, SlotState> state : stream.states.entrySet()) {
			if (!state.getValue().hasLast()) {
				continue;
			}
			SlotValue slot = STREAM.createSlotValue();
			StreamSlot streamSlot = stream.slots.get(state.getKey());
			if (streamSlot != null && streamSlot.getTargetFeature() != null) {
				slot.setFeatureId(target.getFeatureID(streamSlot.getTargetFeature()));
			}
			slot.setFeatureUri(state.getKey());
			slot.setValue(encode(state.getValue().lastValue()));
			keyframe.getState().add(slot);
		}
		changeSet.getEntries().add(keyframe);
		changeSet.setReason("keyframe");
		write(changeSet);
		keyframes.incrementAndGet();
		stream.lastKeyframeTime = now;
		stream.setsSinceKeyframe = 0;
	}

	private ChangeSet header(TargetStream stream, String author, long batchTime, long now) {
		ChangeSet changeSet = STREAM.createChangeSet();
		changeSet.setId(UUID.randomUUID().toString());
		changeSet.setStreamId(stream.streamId);
		changeSet.setSequence(++stream.sequence);
		changeSet.setTimestamp(batchTime);
		changeSet.setCommitTime(now);
		changeSet.setContextFingerprint(fingerprints.apply(stream.mapping.getTarget().getEPackage()));
		changeSet.setAuthor(author != null ? author : "mapping:" + stream.mapping.getId());
		changeSet.setReason("ingest");
		return changeSet;
	}

	private void write(ChangeSet changeSet) {
		store.append(changeSet);
		changeSets.incrementAndGet();
		output.publish(changeSet);
	}

	private SlotState restore(TargetStream stream, StreamSlot slot, String address) {
		EStructuralFeature feature = slot.getTargetFeature();
		return store.lastValue(stream.streamId, address)
				.map(stored -> SlotState.of(StreamValues.decode(stored.value(), feature == null ? null : feature.getEType()),
						stored.eventTime()))
				.orElseGet(SlotState::empty);
	}

	private String encode(Object value) {
		return StreamValues.encode(value, mapper.identities()::idOf);
	}

	private String groupOf(StreamMapping mapping) {
		Set<String> named = StreamSlots.executionGroups(mapping);
		for (ExecutionGroup group : groups) {
			if (named.contains(group.name())) {
				return group.name();
			}
		}
		return StreamSlots.DEFAULT_GROUP;
	}

	private int sliceOf(String name) {
		for (ExecutionGroup group : groups) {
			if (group.name().equals(name)) {
				return group.slice();
			}
		}
		return 64;
	}

	private static String batchKey(BatchStrategy strategy, boolean unitOfWork) {
		BatchKind kind = strategy.getKind();
		if (kind == BatchKind.COMMIT && !unitOfWork) {
			kind = BatchKind.IMMEDIATE; // a single offer is its own unit
		}
		return kind.getName() + ':' + strategy.getCount() + ':' + strategy.getWindow() + ':' + strategy.getUnit();
	}

	private static ThreadFactory threads(String prefix) {
		AtomicInteger counter = new AtomicInteger();
		return runnable -> {
			Thread thread = new Thread(runnable, prefix + "-" + counter.incrementAndGet());
			thread.setDaemon(true);
			return thread;
		};
	}

	/**
	 * Builds an {@link EventCore}.
	 */
	public static final class Builder {

		private MappingRegistry mappings;
		private Mapper mapper;
		private ChangeRules rules;
		private StreamStore store;
		private Projection projection;
		private Function<EPackage, String> fingerprints = ePackage -> String.valueOf(ePackage.getNsURI());
		private Clock clock = Clock.systemUTC();
		private final List<ExecutionGroup> groups = new ArrayList<>();

		private Builder() {
		}

		/**
		 * @param mappings where the stream mappings are found (required)
		 * @return this
		 */
		public Builder mappings(MappingRegistry mappings) {
			this.mappings = mappings;
			return this;
		}

		/**
		 * @param mapper extracts values and resolves target ids (required)
		 * @return this
		 */
		public Builder mapper(Mapper mapper) {
			this.mapper = mapper;
			return this;
		}

		/**
		 * @param rules evaluates change rules; default without expression language
		 * @return this
		 */
		public Builder rules(ChangeRules rules) {
			this.rules = rules;
			return this;
		}

		/**
		 * @param store where change sets go; default {@link InMemoryStreamStore}
		 * @return this
		 */
		public Builder store(StreamStore store) {
			this.store = store;
			return this;
		}

		/**
		 * @param projection writes values into target objects; default {@link Projection#NONE}
		 * @return this
		 */
		public Builder projection(Projection projection) {
			this.projection = projection;
			return this;
		}

		/**
		 * @param fingerprints the fingerprint of a model version; default its nsURI
		 * @return this
		 */
		public Builder fingerprints(Function<EPackage, String> fingerprints) {
			this.fingerprints = requireNonNull(fingerprints);
			return this;
		}

		/**
		 * @param clock the time of the core; default UTC system clock
		 * @return this
		 */
		public Builder clock(Clock clock) {
			this.clock = requireNonNull(clock);
			return this;
		}

		/**
		 * Adds an execution group; the order of the calls is the rank. Without any, the core has
		 * {@code high}, {@code default} and {@code low}; a {@code default} group is always added.
		 *
		 * @param group the group
		 * @return this
		 */
		public Builder group(ExecutionGroup group) {
			groups.add(requireNonNull(group));
			return this;
		}

		/**
		 * @return the core
		 */
		public EventCore build() {
			return new EventCore(this);
		}

		private List<ExecutionGroup> groups() {
			int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
			List<ExecutionGroup> result = new ArrayList<>(groups);
			if (result.isEmpty()) {
				result.add(new ExecutionGroup("high", Math.max(2, cores / 2), 64));
				result.add(new ExecutionGroup(StreamSlots.DEFAULT_GROUP, cores, 64));
				result.add(new ExecutionGroup("low", 1, 64));
			}
			Set<String> names = new LinkedHashSet<>();
			for (ExecutionGroup group : result) {
				if (!names.add(group.name())) {
					throw new IllegalArgumentException("Execution group '" + group.name() + "' is configured twice");
				}
			}
			if (!names.contains(StreamSlots.DEFAULT_GROUP)) {
				result.add(new ExecutionGroup(StreamSlots.DEFAULT_GROUP, cores, 64));
			}
			return result;
		}
	}
}
