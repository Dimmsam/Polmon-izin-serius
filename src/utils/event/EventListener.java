package utils.event;

/**
 * Generic event listener interface
 * @param <T> Type of event data
 */
public interface EventListener<T> {
    /**
     * Called when event occurs
     * @param data Event data
     */
    void onEvent(T data);
}