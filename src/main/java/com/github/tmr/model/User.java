package com.github.tmr.model;

/**
 * Created by Mindaugas on 2016-04-24.
 */
public class User {

    private String id;
    private String name;

    public User() {
    }

    public User(String id) {
        this.id = id;
        this.name = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
