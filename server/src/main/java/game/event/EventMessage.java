package game.event;

import java.util.HashMap;
import java.util.Map;

public class EventMessage {

    private EventType eventType;

    private Map<String, Object> properties = new HashMap<>();

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
