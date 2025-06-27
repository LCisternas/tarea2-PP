package com.miempresa.bloom.events;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventBus {
    private static final Map<Class<? extends Event>, Set<EventListener<? extends Event>>> listeners =
        new ConcurrentHashMap<>();

    public static <E extends Event> void subscribe(Class<E> eventType, EventListener<E> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArraySet<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Event> void publish(E event) {
        Set<EventListener<? extends Event>> set = listeners.get(event.getClass());
        if (set != null) {
            for (EventListener<? extends Event> l : set) {
                ((EventListener<E>) l).onEvent(event);
            }
        }
    }

    public interface EventListener<E extends Event> {
        void onEvent(E event);
    }
}
