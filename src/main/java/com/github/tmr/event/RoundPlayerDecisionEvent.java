package com.github.tmr.event;

/**
 * Created by Mindaugas on 2016-05-14.
 */
public class RoundPlayerDecisionEvent extends Event {

    private static final String PARAM_CARD_TEXT = "cardText";

    public RoundPlayerDecisionEvent(String cardText) {
        setParam(PARAM_CARD_TEXT, cardText);
    }
}
