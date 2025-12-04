package utils.event;

/**
 * Generic game event wrapper
 * @param <T> Type of event payload
 */
public class GameEvent<T> {
    private String eventType;
    private T payload;
    private long timestamp;

    public GameEvent(String eventType, T payload) {
        this.eventType = eventType;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }

    public String getEventType() {
        return eventType;
    }

    public T getPayload() {
        return payload;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "GameEvent{type='" + eventType + "', timestamp=" + timestamp + "}";
    }
}