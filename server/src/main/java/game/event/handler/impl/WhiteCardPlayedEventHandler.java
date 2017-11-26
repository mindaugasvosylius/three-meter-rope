package game.event.handler.impl;

import game.event.EventMessageProperty;
import game.event.EventType;
import game.event.handler.GameEvent;
import game.event.handler.GameEventHandler;
import org.springframework.stereotype.Component;

@Component
public class WhiteCardPlayedEventHandler implements GameEventHandler {

    @Override
    public EventType getEventType() {
        return EventType.WHITE_CARD_PLAYED;
    }

    @Override
    public void handle(GameEvent event) {
        Double cardId = (Double) event.getEventMessage().getProperties().get(EventMessageProperty.ID.toString());

        event.getGame().playCard(event.getPlayer(), cardId.longValue());
    }
}
