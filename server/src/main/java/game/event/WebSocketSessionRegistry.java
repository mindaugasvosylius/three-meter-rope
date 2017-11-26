package game.event;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketSessionRegistry {

    private Map<String, WebSocketSession> sessionsById = new HashMap<>();

    public void add(WebSocketSession session) {
        sessionsById.put(session.getId(), session);
    }

    public WebSocketSession find(String id) {
        return sessionsById.get(id);
    }

    public void remove(String id) {
        sessionsById.remove(id);
    }
}
