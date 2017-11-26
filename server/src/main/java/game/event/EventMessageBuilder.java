package game.event;

import java.util.Map;

public class EventMessageBuilder {

    private EventMessage eventMessage = new EventMessage();

    public EventMessageBuilder withEventType(EventType eventType) {
        eventMessage.setEventType(eventType);
        return this;
    }

    public EventMessageBuilder withProperties(Map<String, Object> properties) {
        eventMessage.setProperties(properties);
        return this;
    }

    public EventMessageBuilder withProperty(String key, Object value) {
        eventMessage.getProperties().put(key, value);
        return this;
    }

    public EventMessageBuilder withProperty(EventMessageProperty property, Object value) {
        eventMessage.getProperties().put(property.name(), value);
        return this;
    }

    public EventMessage build() {
        return eventMessage;
    }

    public static EventMessageBuilder builder() {
        return new EventMessageBuilder();
    }

    public static EventMessageBuilder builderFor(EventType eventType) {
        return new EventMessageBuilder().withEventType(eventType);
    }
}
