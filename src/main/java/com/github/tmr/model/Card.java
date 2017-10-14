package com.github.tmr.model;

public class Card {

    private Long id;
    private String text;

    public Card() {
    }

    public Card(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
