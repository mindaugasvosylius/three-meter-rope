package game.event.handler.impl;

import game.event.EventMessage;
import game.event.EventMessageProperty;
import game.event.EventType;
import game.event.handler.GameEvent;
import game.event.handler.GameEventHandler;
import game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WhiteCardChosenEventHandler implements GameEventHandler {

    @Autowired
    private GameService gameService;

    @Override
    public EventType getEventType() {
        return EventType.WHITE_CARD_CHOSEN;
    }

    @Override
    public void handle(GameEvent event) {
        EventMessage eventMessage = event.getEventMessage();
        Double o = (Double) eventMessage.getProperties().get(EventMessageProperty.ID.toString());

        event.getGame().whiteCardChosen(event.getPlayer(), o.longValue());
    }
}
