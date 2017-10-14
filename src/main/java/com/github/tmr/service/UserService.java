package com.github.tmr.service;

import com.github.tmr.model.User;

/**
 * Created by Mindaugas on 2016-05-07.
 */
public class UserService {

    public User getUser(String id) {
        return new User(id);
    }
}
