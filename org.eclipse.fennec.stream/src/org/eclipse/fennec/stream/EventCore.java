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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.logging.Level;
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
import org.eclipse.fennec.model.stream.Recording;
import org.eclipse.fennec.model.stream.SlotValue;
import org.eclipse.fennec.model.stream.StreamFactory;
import org.eclipse.fennec.model.streammapping.BatchKind;
import org.eclipse.fennec.model.streammapping.BatchStrategy;
import org.eclipse.fennec.model.streammapping.DeletionRule;
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
public final class EventCore implements StreamCore, AutoCloseable {

	/**
	 * A named thread pool of the core. The order in which groups are configured is their rank: a
	 * target object whose slots name several groups runs in the first of them.
	 *
	 * @param name the name mappings refer to
	 * @param threads the pool size
	 * @param slice how many tasks a lane runs before it yields its thread
	 * @param maxQueued how many offers may wait in one lane (0 = unbounded): beyond it, an offer of
	 *        time-series values evicts the oldest waiting one of its kind, any other offer makes the
	 *        producer wait (requirement COR-06)
	 */
	public record ExecutionGroup(String name, int threads, int slice, int maxQueued) {

		/**
		 * Canonical constructor.
		 */
		public ExecutionGroup {
			requireNonNull(name, "Group name is required");
			if (threads < 1 || slice < 1 || maxQueued < 0) {
				throw new IllegalArgumentException("A group needs at least one thread, a slice of at least 1 and maxQueued >= 0");
			}
		}

		/**
		 * A group with unbounded lanes.
		 *
		 * @param name the name
		 * @param threads the pool size
		 * @param slice the slice
		 */
		public ExecutionGroup(String name, int threads, int slice) {
			this(name, threads, slice, 0);
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
	 * @param retentionRuns deletion rules handed to the store
	 * @param droppedOffers offers of time-series values evicted from a full lane
	 */
	public record Metrics(long offered, long passed, long rejected, long changeSets, long keyframes, int lanes,
			long retentionRuns, long droppedOffers) {
	}

	private static final Logger LOGGER = Logger.getLogger(EventCore.class.getName());
	private static final StreamFactory STREAM = StreamFactory.eINSTANCE;
	private static final int OUTPUT_BUFFER = 1024;
	private static final long CLOSE_TIMEOUT_SECONDS = 5;

	private final MappingRegistry mappings;
	private final Mapper mapper;
	private final ChangeRules rules;
	private final StreamStore store;
	private final ProjectionSource projections;
	private final Map<Thread, Projection> leased = new ConcurrentHashMap<>();
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
	private final AtomicLong retentionRuns = new AtomicLong();
	private final AtomicLong droppedOffers = new AtomicLong();
	private final long blockTimeout;
	private final Set<String> retentionUnsupported = ConcurrentHashMap.newKeySet();

	private EventCore(Builder builder) {
		this.mappings = requireNonNull(builder.mappings, "MappingRegistry is required");
		this.mapper = requireNonNull(builder.mapper, "Mapper is required");
		this.rules = builder.rules != null ? builder.rules : new ChangeRules(null);
		this.store = builder.store != null ? builder.store : new InMemoryStreamStore();
		this.projections = builder.projections != null ? builder.projections : ProjectionSource.shared(Projection.NONE);
		this.fingerprints = builder.fingerprints;
		this.clock = builder.clock;
		this.groups = List.copyOf(builder.groups());
		this.blockTimeout = builder.blockTimeout.toMillis();
		for (ExecutionGroup group : groups) {
			pools.put(group.name(), Executors.newFixedThreadPool(group.threads(), laneThreads("fennec-stream-" + group.name())));
		}
		this.timer = Executors.newSingleThreadScheduledExecutor(threads("fennec-stream-timer"));
		this.output = pushStreams.createSimpleEventSource(ChangeSet.class);
		long retention = builder.retentionInterval.toMillis();
		if (retention > 0) {
			timer.scheduleWithFixedDelay(this::sweepRetention, retention, retention, TimeUnit.MILLISECONDS);
		}
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
	@Override
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
	@Override
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
	@Override
	public void offerUnitOfWork(Collection<? extends EObject> sources, long eventTime, String author) {
		route(List.copyOf(requireNonNull(sources, "Sources are required")), eventTime, author, true);
	}

	/**
	 * A new stream of the change sets written from now on (requirement CON-01). A slow consumer
	 * loses the oldest change sets of its buffer rather than holding up the core.
	 *
	 * @return the stream; closing it ends the subscription
	 */
	@Override
	public PushStream<ChangeSet> changes() {
		return pushStreams.buildStream(output).withBuffer(new ArrayBlockingQueue<>(OUTPUT_BUFFER))
				.withQueuePolicy(QueuePolicyOption.DISCARD_OLDEST).build();
	}

	/**
	 * Writes every pending batch now, whatever its strategy.
	 */
	@Override
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
	@Override
	public StreamReceiver.Builder receiver() {
		return StreamReceiver.builder().core(this);
	}

	@Override
	public StreamStore store() {
		return store;
	}

	/**
	 * @param targetClass the class of a target object
	 * @param targetId its id
	 * @return the id of its stream
	 */
	public static String streamId(EClass targetClass, String targetId) {
		return StreamId.of(targetClass, targetId).toString();
	}

	/**
	 * Takes back the newest change set of a target object that was not taken back yet (requirements
	 * APL-06, APL-07): its changelog entries are inverted and written as a new change set
	 * ({@code reason = undo:<sequence>}), the target is projected back. The history is never shortened.
	 * Only for mappings marked {@code undoable}, and only change sets of changelog slots.
	 *
	 * @param targetClass the class of the target object
	 * @param targetId its id
	 * @return the undo change set, or empty when there is nothing to undo; fails with
	 *         {@link UnsupportedOperationException} when the mapping is not undoable and with
	 *         {@link IllegalStateException} when the change set holds time-series values
	 */
	@Override
	public CompletableFuture<Optional<ChangeSet>> undo(EClass targetClass, String targetId) {
		return revert(targetClass, targetId, true);
	}

	/**
	 * Takes back the newest undo of a target object, as long as no new change set was written since.
	 *
	 * @param targetClass the class of the target object
	 * @param targetId its id
	 * @return the redo change set ({@code reason = redo:<sequence>}), or empty when there is nothing to redo
	 */
	@Override
	public CompletableFuture<Optional<ChangeSet>> redo(EClass targetClass, String targetId) {
		return revert(targetClass, targetId, false);
	}

	private CompletableFuture<Optional<ChangeSet>> revert(EClass targetClass, String targetId, boolean undo) {
		String streamId = streamId(targetClass, targetId);
		TargetStream stream = streams.get(streamId);
		if (stream == null) {
			return CompletableFuture.completedFuture(Optional.empty());
		}
		if (!stream.mapping.isUndoable()) {
			return CompletableFuture.failedFuture(
					new UnsupportedOperationException("Mapping " + stream.mapping.getId() + " is not undoable"));
		}
		CompletableFuture<Optional<ChangeSet>> result = new CompletableFuture<>();
		enqueue(streamId, stream.group, () -> {
			try {
				result.complete(revertInLane(stream, undo));
			} catch (RuntimeException e) {
				result.completeExceptionally(e);
			}
		});
		return result;
	}

	/** Lane task. */
	private Optional<ChangeSet> revertInLane(TargetStream stream, boolean undo) {
		Long sequence = (undo ? stream.undoable : stream.redoable).peek();
		if (sequence == null) {
			return Optional.empty();
		}
		List<ChangeSet> stored = store.read(stream.streamId, sequence, sequence);
		if (stored.isEmpty()) {
			throw new IllegalStateException("Change set " + sequence + " of " + stream.streamId + " is no longer stored");
		}
		long now = clock.millis();
		ChangeSet inverse = header(stream, null, now, now);
		inverse.setReason((undo ? "undo:" : "redo:") + sequence);
		List<ChangeEntry> entries = stored.get(0).getEntries();
		for (int i = entries.size() - 1; i >= 0; i--) {
			inverse.getEntries().add(invert(stream, entries.get(i)));
		}
		(undo ? stream.undoable : stream.redoable).pop();
		write(inverse);
		(undo ? stream.redoable : stream.undoable).push(inverse.getSequence());
		for (ChangeEntry entry : inverse.getEntries()) {
			projectReverted(stream, entry, now);
		}
		return Optional.of(inverse);
	}

	private ChangeEntry invert(TargetStream stream, ChangeEntry entry) {
		StreamSlot slot = stream.slots.get(entry.getFeatureUri());
		if (slot == null || StreamSlots.recording(slot) != RecordingMode.CHANGELOG
				|| entry.getKind() != DeltaKind.SET && entry.getKind() != DeltaKind.UNSET) {
			throw new IllegalStateException("Entry for " + entry.getFeatureUri() + " cannot be undone: only changelog SET/UNSET entries can");
		}
		ChangeEntry inverse = STREAM.createChangeEntry();
		inverse.setObjectId(entry.getObjectId());
		inverse.setFeatureId(entry.getFeatureId());
		inverse.setFeatureUri(entry.getFeatureUri());
		inverse.setRecording(Recording.CHANGELOG);
		inverse.setTimestamp(clock.millis());
		if (entry.getKind() == DeltaKind.UNSET || entry.getValueOld() != null) {
			inverse.setKind(DeltaKind.SET);
			inverse.setValueOld(entry.getKind() == DeltaKind.UNSET ? null : entry.getValueNew());
			inverse.setValueNew(entry.getValueOld());
		} else {
			// the feature had no value before: taking the change back unsets it
			inverse.setKind(DeltaKind.UNSET);
			inverse.setValueOld(entry.getValueNew());
		}
		return inverse;
	}

	private void projectReverted(TargetStream stream, ChangeEntry entry, long now) {
		StreamSlot slot = stream.slots.get(entry.getFeatureUri());
		EStructuralFeature feature = slot.getTargetFeature();
		if (entry.getKind() == DeltaKind.UNSET) {
			stream.states.put(entry.getFeatureUri(), SlotState.empty());
			if (feature != null && StreamSlots.mode(slot) == ProjectionMode.PROJECT) {
				projection().project(stream.mapping.getTarget(), stream.targetId, feature, null);
			}
			return;
		}
		Object value = StreamValues.decode(entry.getValueNew(), null);
		stream.states.put(entry.getFeatureUri(), SlotState.of(value, now));
		if (feature != null && StreamSlots.mode(slot) == ProjectionMode.PROJECT) {
			mapper.extractor().toTarget(slot, value)
					.ifPresent(v -> projection().project(stream.mapping.getTarget(), stream.targetId, feature, v));
		}
		stream.projectedTimes.put(entry.getFeatureUri(), now);
	}

	/**
	 * Hands the deletion rules of every slot to the store now, each stream in its own lane
	 * (requirement STO-05). Runs on its own every retention interval of the builder.
	 */
	public void sweepRetention() {
		for (TargetStream stream : streams.values()) {
			enqueue(stream.streamId, stream.group, () -> retain(stream));
		}
	}

	/** Lane task. */
	private void retain(TargetStream stream) {
		long now = clock.millis();
		for (Map.Entry<String, StreamSlot> slot : stream.slots.entrySet()) {
			DeletionRule rule = slot.getValue().getDeletionRule();
			if (rule == null || rule.getRetention() <= 0 && rule.getMaxCount() <= 0) {
				continue;
			}
			if (store.retain(stream.streamId, slot.getKey(), rule, now) == StreamStore.RetentionOutcome.UNSUPPORTED
					&& retentionUnsupported.add(slot.getKey())) {
				LOGGER.warning(() -> "The store cannot apply the deletion rule of " + slot.getKey() + " - values are kept");
			}
			retentionRuns.incrementAndGet();
		}
	}

	/**
	 * @return the counters
	 */
	@Override
	public Metrics metrics() {
		return new Metrics(offered.get(), passed.get(), rejected.get(), changeSets.get(), keyframes.get(), mailboxes.size(),
				retentionRuns.get(), droppedOffers.get());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Lets the lanes finish their queued work (at most {@value #CLOSE_TIMEOUT_SECONDS} s) and
	 * releases every projection the lane threads acquired.
	 */
	@Override
	public void close() {
		timer.shutdownNow();
		pools.values().forEach(ExecutorService::shutdown);
		try {
			for (ExecutorService pool : pools.values()) {
				if (!pool.awaitTermination(CLOSE_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
					pool.shutdownNow();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			pools.values().forEach(ExecutorService::shutdownNow);
		}
		// threads still running after the timeout lose their projection here
		leased.keySet().forEach(this::releaseProjection);
		output.close();
	}

	/** The projection of the calling lane thread, acquired on first use. */
	private Projection projection() {
		return leased.computeIfAbsent(Thread.currentThread(), thread -> projections.acquire());
	}

	private void releaseProjection(Thread thread) {
		Projection projection = leased.remove(thread);
		if (projection != null) {
			try {
				projections.release(projection);
			} catch (RuntimeException e) {
				LOGGER.log(Level.WARNING, "Releasing the projection of " + thread.getName() + " failed", e);
			}
		}
	}

	/** Lane threads give their projection back when they end. */
	private ThreadFactory laneThreads(String prefix) {
		ThreadFactory threads = threads(prefix);
		return runnable -> threads.newThread(() -> {
			try {
				runnable.run();
			} finally {
				releaseProjection(Thread.currentThread());
			}
		});
	}

	private void route(List<EObject> sources, long eventTime, String author, boolean unitOfWork) {
		Map<String, List<Offer>> work = new LinkedHashMap<>();
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
					boolean droppable = !unitOfWork && values.stream().allMatch(v -> v.slot() instanceof StreamSlot slot
							&& StreamSlots.recording(slot) == RecordingMode.TIMESERIES);
					work.computeIfAbsent(streamId, id -> new ArrayList<>())
							.add(new Offer(() -> handle(stream, values, eventTime, author, unitOfWork), droppable));
				}
			}
		}
		for (Map.Entry<String, List<Offer>> entry : work.entrySet()) {
			TargetKey key = keys.get(entry.getKey());
			String group = groupOf(key.mapping());
			for (Offer offer : entry.getValue()) {
				enqueueOffer(entry.getKey(), group, offer);
			}
			if (unitOfWork) {
				enqueue(entry.getKey(), group, () -> commit(stream(entry.getKey(), key.mapping(), key.targetId()), author));
			}
		}
	}

	private record TargetKey(StreamMapping mapping, String targetId) {
	}

	/** Values of one offer for one target, and whether a full lane may drop them. */
	private record Offer(Runnable task, boolean droppable) {
	}

	/** Enqueues a producer's offer under the bound of its group: evict (time series) or wait (changelog). */
	private void enqueueOffer(String streamId, String group, Offer offer) {
		int max = maxQueuedOf(group);
		if (max > 0 && !offer.droppable()) {
			Mailbox lane = mailboxes.get(streamId);
			try {
				if (lane != null && !lane.awaitSpace(max, blockTimeout)) {
					LOGGER.warning(() -> "Lane " + streamId + " stayed full for " + blockTimeout + " ms - enqueued anyway");
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		ExecutorService pool = pools.getOrDefault(group, pools.get(StreamSlots.DEFAULT_GROUP));
		int slice = sliceOf(group);
		mailboxes.compute(streamId, (id, mailbox) -> {
			Mailbox lane = mailbox != null ? mailbox
					: new Mailbox(pool, slice, () -> mailboxes.computeIfPresent(id, (k, m) -> m.isIdle() ? null : m));
			if (lane.enqueue(offer.task(), offer.droppable(), max)) {
				droppedOffers.incrementAndGet();
			}
			return lane;
		});
	}

	private int maxQueuedOf(String name) {
		for (ExecutionGroup group : groups) {
			if (group.name().equals(name)) {
				return group.maxQueued();
			}
		}
		return 0;
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
			entry.setRecording(pending.recording == RecordingMode.CHANGELOG ? Recording.CHANGELOG : Recording.TIMESERIES);
			entry.setValueOld(pending.valueOld);
			entry.setValueNew(pending.valueNew);
			entry.setTimestamp(pending.eventTime);
			changeSet.getEntries().add(entry);
		}
		write(changeSet);
		project(stream, batch.pending);
		if (stream.mapping.isUndoable()) {
			stream.undoable.push(changeSet.getSequence());
			stream.redoable.clear();
		}
		stream.setsSinceKeyframe++;
		if (keyframeDue(stream, now)) {
			writeKeyframe(stream, author, now);
		}
	}

	/** Projects the values of one batch with one call per target: the store may turn it into one update. */
	private void project(TargetStream stream, List<Pending> batch) {
		Map<EStructuralFeature, Object> values = new LinkedHashMap<>();
		Map<String, Long> times = new LinkedHashMap<>();
		for (Pending pending : batch) {
			EStructuralFeature feature = pending.slot.getTargetFeature();
			if (feature == null || StreamSlots.mode(pending.slot) != ProjectionMode.PROJECT) {
				continue;
			}
			Long projected = stream.projectedTimes.get(pending.address);
			if (StreamSlots.lateness(pending.slot) == Lateness.EVENT_TIME && projected != null
					&& pending.eventTime < projected) {
				continue; // the target already holds a newer value
			}
			Long inBatch = times.get(pending.address);
			if (StreamSlots.lateness(pending.slot) == Lateness.EVENT_TIME && inBatch != null && pending.eventTime < inBatch) {
				continue; // a newer value of the same feature is already in this batch
			}
			Optional<Object> value = mapper.extractor().toTarget(pending.slot, pending.value);
			if (value.isPresent()) {
				values.put(feature, value.get());
				times.put(pending.address, pending.eventTime);
			}
		}
		if (!values.isEmpty() && projection().project(stream.mapping.getTarget(), stream.targetId, values)) {
			stream.projectedTimes.putAll(times);
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
		// a keyframe describes the state as of the newest event time of its slots, not as of the wall clock
		long stateTime = Long.MIN_VALUE;
		for (SlotState state : stream.states.values()) {
			if (state.hasLast()) {
				stateTime = Math.max(stateTime, state.lastTime());
			}
		}
		ChangeSet changeSet = header(stream, author, stateTime == Long.MIN_VALUE ? now : stateTime, now);
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
		private ProjectionSource projections;
		private Function<EPackage, String> fingerprints = ePackage -> String.valueOf(ePackage.getNsURI());
		private Clock clock = Clock.systemUTC();
		private Duration retentionInterval = Duration.ofHours(1);
		private Duration blockTimeout = Duration.ofSeconds(30);
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
		 * @param projection writes values into target objects, shared by all lanes - it must be
		 *        thread-safe; default {@link Projection#NONE}
		 * @return this
		 */
		public Builder projection(Projection projection) {
			this.projections = ProjectionSource.shared(projection);
			return this;
		}

		/**
		 * @param projections hands every lane thread a projection of its own, released when the
		 *        thread ends or the core closes
		 * @return this
		 */
		public Builder projections(ProjectionSource projections) {
			this.projections = projections;
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
		 * @param retentionInterval how often the deletion rules go to the store; zero or negative
		 *        disables the sweep (call {@link EventCore#sweepRetention()} yourself); default 1 h
		 * @return this
		 */
		public Builder retentionInterval(Duration retentionInterval) {
			this.retentionInterval = requireNonNull(retentionInterval);
			return this;
		}

		/**
		 * @param blockTimeout how long a producer waits for space in a full lane before it enqueues
		 *        anyway (changelog values are never dropped); default 30 s
		 * @return this
		 */
		public Builder blockTimeout(Duration blockTimeout) {
			this.blockTimeout = requireNonNull(blockTimeout);
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
