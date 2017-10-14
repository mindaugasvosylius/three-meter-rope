package com.github.tmr.web.response;

import com.github.tmr.event.Event;
import org.springframework.http.HttpEntity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mindaugas on 2016-05-12.
 */
public class EventsEntity {

    private List<EventEntity> events;

    public EventsEntity(List<EventEntity> events) {
        this.events = events;
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }

    public static Builder events(List<Event> events) {
        return new Builder().events(events);
    }

    public static class Builder {

        private List<EventEntity> events;

        public Builder events(List<Event> events) {
            this.events = events.stream().map(e -> EventEntity.event(e).build()).collect(Collectors.toList());
            return this;
        }

        public EventsEntity build() {
            EventsEntity entity = new EventsEntity(events);
            return entity;
        }
    }
}
