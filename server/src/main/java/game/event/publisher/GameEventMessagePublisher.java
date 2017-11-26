package game.event.publisher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.event.EventMessage;
import game.event.WebSocketSessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

import static java.lang.String.format;

@Component
public class GameEventMessagePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameEventMessagePublisher.class);

    @Autowired
    private WebSocketSessionRegistry sessionRegistry;

    private Gson gson = new GsonBuilder().create();

    public void publish(EventMessage eventMessage, List<String> sessionIds) {
        sessionIds.forEach(id -> publish(eventMessage, id));
    }

    public void publish(EventMessage eventMessage, String sessionId) {
        WebSocketSession session = sessionRegistry.find(sessionId);
        publish(eventMessage, session);
    }

    private void publish(EventMessage eventMessage, WebSocketSession session) {
        if (session == null || !session.isOpen()) {
            return;
        }
        try {
            String messageString = gson.toJson(eventMessage);
            LOGGER.debug(format("Now sending message: [%s]", messageString));
            session.sendMessage(new TextMessage(messageString));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
