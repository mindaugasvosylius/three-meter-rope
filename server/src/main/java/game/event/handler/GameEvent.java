package game.event.handler;

import game.event.EventMessage;
import game.model.Game;
import game.model.Player;
import org.springframework.web.socket.WebSocketSession;

public class GameEvent {

    private final Game game;
    private final Player player;
    private final EventMessage eventMessage;
    private final WebSocketSession session;

    public GameEvent(final Game game, final Player player, final EventMessage eventMessage, final WebSocketSession session) {
        this.game = game;
        this.player = player;
        this.eventMessage = eventMessage;
        this.session = session;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public EventMessage getEventMessage() {
        return eventMessage;
    }

    public WebSocketSession getSession() {
        return session;
    }
}
