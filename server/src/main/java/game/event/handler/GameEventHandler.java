package game.event.handler;

import game.event.EventType;

public interface GameEventHandler {

    EventType getEventType();

    void handle(GameEvent event);
}
