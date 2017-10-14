package com.github.tmr.model;

import com.github.tmr.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindaugas on 2016-04-23.
 */
public class Player {

    private User user;
    private List<WhiteCard> cards = new ArrayList<>();
    private int awesomePoints;
    private List<Event> events = new ArrayList<>();
    public Player(User user) {
        this.user = user;
    }

    public String getId() {
        return user.getId();
    }

    public List<WhiteCard> getCards() {
        return cards;
    }

    public void setCards(List<WhiteCard> cards) {
        this.cards = cards;
    }

    public int getAwesomePoints() {
        return awesomePoints;
    }

    public void setAwesomePoints(int awesomePoints) {
        this.awesomePoints = awesomePoints;
    }

    public List<Event> getEvents() {
        return events;
    }
//
//    public void setEvents(List<Event> events) {
//        this.events = events;
//    }

    public WhiteCard getCard(Long id) {
        return cards.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }
}
