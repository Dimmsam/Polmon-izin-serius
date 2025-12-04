package utils.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic event manager for publish-subscribe pattern
 * @param <T> Type of event data
 */
public class EventManager<T> {
    private List<EventListener<T>> listeners;

    public EventManager() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Subscribe to events
     * @param listener Event listener to add
     */
    public void subscribe(EventListener<T> listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unsubscribe from events
     * @param listener Event listener to remove
     */
    public void unsubscribe(EventListener<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all listeners of an event
     * @param data Event data to broadcast
     */
    public void notifyListeners(T data) {
        for (EventListener<T> listener : listeners) {
            listener.onEvent(data);
        }
    }

    /**
     * Clear all listeners
     */
    public void clear() {
        listeners.clear();
    }

    /**
     * Get number of subscribed listeners
     * @return Number of listeners
     */
    public int getListenerCount() {
        return listeners.size();
    }
}