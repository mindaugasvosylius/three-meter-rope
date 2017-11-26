package game.service;

import game.model.Player;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PlayerService {

    private Map<String, Player> players = new ConcurrentHashMap<>();

    public Player addOrUpdate(String id, String name) {
        Player player = players.get(id);
        if (player == null) {
            player = new Player(id, name);
            players.put(id, player);
        }
        return player;
    }

    public Player find(String id) {
        return players.get(id);
    }

    public void remove(String id) {
        players.remove(id);
    }

    public Player updatePlayerSessionId(String id, String sessionId) {
        Player player = find(id);
        player.setSessionId(sessionId);
        return player;
    }
}
