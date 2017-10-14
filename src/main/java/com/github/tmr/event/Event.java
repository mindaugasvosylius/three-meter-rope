package com.github.tmr.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mindaugas on 2016-05-04.
 */
public abstract class Event {

    private static long ID_COUNTER = 0;

    private Long id;
    private Map<String, Object> params = new HashMap<>();

    public Event() {
        id = ID_COUNTER++;
    }

    public String getType() {
        return getClass().getSimpleName();
    }

    protected final void setParam(String key, Object value) {
        params.put(key, value);
    }

    public Map<String, Object> getParams() {
        return Collections.unmodifiableMap(params);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
