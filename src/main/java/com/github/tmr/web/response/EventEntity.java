package com.github.tmr.web.response;

import com.github.tmr.event.Event;
import org.springframework.http.HttpEntity;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Mindaugas on 2016-05-12.
 */
public class EventEntity {

    private Long id;
    private String type;
    private Map<String, String> params = new HashMap<>();

    public EventEntity(Long id, String type, Map<String, String> params) {
        this.id = id;
        this.type = type;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public static Builder event(Event event) {
        return new Builder().event(event);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class Builder {

        private Long id;
        private String type;
        private Map<String, String> params;

        public Builder event(Event event) {
            this.id = event.getId();
            this.type = event.getType();
            this.params = event.getParams().entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toString()));
            return this;
        }

        public EventEntity build() {
            EventEntity entity = new EventEntity(id, type, params);
            return entity;
        }
    }
}
