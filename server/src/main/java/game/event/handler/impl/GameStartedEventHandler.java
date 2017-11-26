package game.event.handler.impl;

import game.event.EventType;
import game.event.handler.GameEvent;
import game.event.handler.GameEventHandler;
import org.springframework.stereotype.Component;

@Component
public class GameStartedEventHandler implements GameEventHandler {

    @Override
    public EventType getEventType() {
        return EventType.GAME_STARTED;
    }

    @Override
    public void handle(GameEvent event) {
        event.getGame().start();
    }
}
