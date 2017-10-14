package com.github.tmr.event;

public class BlackCardDrawEvent extends Event {

    private static final String PARAM_CARD_TEXT = "cardText";

    public BlackCardDrawEvent(String cardText) {
        setParam(PARAM_CARD_TEXT, cardText);
    }
    
}
