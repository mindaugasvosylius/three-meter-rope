package game.event.handler.impl;

import game.event.EventType;
import game.event.handler.GameEvent;
import game.event.handler.GameEventHandler;
import game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerJoinedEventHandler implements GameEventHandler {

    @Autowired
    private GameService gameService;

    @Override
    public EventType getEventType() {
        return EventType.PLAYER_JOINED;
    }

    @Override
    public void handle(GameEvent event) {
        gameService.join(event.getGame(), event.getPlayer());
    }
}
