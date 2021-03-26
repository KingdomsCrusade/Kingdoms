package net.kingdomscrusade.kingdoms.modules;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class will listen to incoming events and awaits until a given predicate in combination
 * with the given event occurs.
 * <br>
 * Requirements: Java 14, Spigot dependency
 * <br>
 * Usage example:
 * Await a chat message for 60 seconds
 * <pre>
 * {@code
 *
 *   new EventAwaiter.Builder<>(this, AsyncPlayerChatEvent.class)
 *       .awaitIf(e -> e.getPlayer().getName().equals(sender.getName()))
 *       .thenExecuteSync(e -> {
 *          sender.sendMessage("You typed in: " + e.getMessage());
 *        })
 *        .orTimeOutAfter(60, TimeUnit.SECONDS, () -> {
 *           sender.sendMessage("You didn't send a message.. :(");
 *        })
 *        .build()
 *        .await();
 *
 * }</pre>
 *
 * @param <E> event class to listen to
 * @author Paul2708
 */
public final class EventAwaiter<E extends Event> {

    private final JavaPlugin plugin;
    private final Class<E> clazz;

    private Predicate<E> predicate = e -> true;
    private RunType runType;
    private Consumer<E> eventConsumer;

    private long time;
    private TimeUnit timeUnit;
    private Runnable timeoutRunnable;

    // Internal state
    private final ScheduledExecutorService executor;
    private RegisteredListener listener;

    private EventAwaiter(JavaPlugin plugin, Class<E> clazz) {
        this.plugin = plugin;
        this.clazz = clazz;

        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Start to listen for the incoming event.
     */
    public void await() {
        Consumer<Event> onEvent = event -> {
            if (event.getClass().isAssignableFrom(clazz)) {
                E awaitEvent = (E) event;
                if (predicate.test(awaitEvent)) {
                    switch (runType) {
                        case SYNC:
                            Bukkit.getScheduler().runTask(plugin, () -> eventConsumer.accept(awaitEvent));
                            break;
                        case ASYNC:
                            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> eventConsumer.accept(awaitEvent));
                            break;
                        default:
                            throw new RuntimeException("Invalid run type.");
                    }

                    unregister();
                    executor.shutdownNow();
                }
            }
        };

        register(onEvent);
        executor.schedule(() -> {
            unregister();
            timeoutRunnable.run();
        }, time, timeUnit);
    }

    private void register(Consumer<Event> onEvent) {
        this.listener = new RegisteredListener(new Listener() {
        }, (listener, event) -> onEvent.accept(event),
                EventPriority.NORMAL, plugin, false);
        for (HandlerList handler : HandlerList.getHandlerLists()) {
            handler.register(listener);
        }
    }

    private void unregister() {
        for (HandlerList handler : HandlerList.getHandlerLists()) {
            handler.unregister(listener);
        }
    }

    /**
     * Builder class for an event awaiter.
     *
     * @param <E> event type
     * @author Paul2708
     */
    public static class Builder<E extends Event> {

        private final EventAwaiter<E> awaiter;

        /**
         * Create a new event awaiter builder.
         *
         * @param plugin plugin class to register and unregister internal listener
         * @param clazz  event class to listen for
         */
        public Builder(JavaPlugin plugin, Class<E> clazz) {
            this.awaiter = new EventAwaiter<>(plugin, clazz);
        }

        /**
         * If the event, that is listen to, got called, the predicate will be tested.
         * If the test is successfully, the {@link #thenExecuteSync(Consumer)} or {@link #thenExecuteAsync(Consumer)}
         * will be called.
         * Otherwise it will wait for the next incoming event or {@link #orTimeOutAfter(long, TimeUnit, Runnable)}
         * will be called.
         *
         * @param predicate predicate to test on called event
         * @return this builder
         */
        public Builder<E> awaitIf(Predicate<E> predicate) {
            Objects.requireNonNull(predicate, "Predicate cannot be null.");

            awaiter.predicate = predicate;
            return this;
        }

        /**
         * The consumer will consume the event, that is listen to.
         * The consumer will accept the event synchronous, in the main spigot thread.
         *
         * @param consumer consumes event
         * @return this builder
         */
        public Builder<E> thenExecuteSync(Consumer<E> consumer) {
            Objects.requireNonNull(consumer, "Consumer cannot be null.");

            awaiter.runType = RunType.SYNC;
            awaiter.eventConsumer = consumer;
            return this;
        }

        /**
         * The consumer will consume the event, that is listen to.
         * The consumer will accept the event asynchronous, in the async spigot thread.
         *
         * @param consumer consumes event
         * @return this builder
         */
        public Builder<E> thenExecuteAsync(Consumer<E> consumer) {
            Objects.requireNonNull(consumer, "Consumer cannot be null.");

            awaiter.runType = RunType.ASYNC;
            awaiter.eventConsumer = consumer;
            return this;
        }

        /**
         * If the event wasn't triggered or the predicate was false, this method will be called
         * after the given timeout.
         *
         * @param timeout  time out value
         * @param unit     time out unit
         * @param runnable that runs in a separate thread after the time out expires
         * @return this builder
         */
        public Builder<E> orTimeOutAfter(long timeout, TimeUnit unit, Runnable runnable) {
            awaiter.time = timeout;
            awaiter.timeUnit = unit;
            awaiter.timeoutRunnable = runnable;

            return this;
        }

        /**
         * Build the event awaiter.
         * Await the event by calling {@link #await()}.
         *
         * @return built event awaiter
         */
        public EventAwaiter<E> build() {
            return awaiter;
        }
    }

    private enum RunType {
        SYNC,
        ASYNC
    }
}