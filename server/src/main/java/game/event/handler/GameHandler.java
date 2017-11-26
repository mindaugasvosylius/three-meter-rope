package game.event.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.event.EventMessage;
import game.event.EventType;
import game.event.WebSocketSessionRegistry;
import game.model.Game;
import game.model.Player;
import game.service.GameService;
import game.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GameHandler extends TextWebSocketHandler implements InitializingBean, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameHandler.class);

    @Autowired
    private WebSocketSessionRegistry sessionRegistry;

    private Gson gson = new GsonBuilder().create();

    private ApplicationContext applicationContext;

    private Map<EventType, GameEventHandler> eventMessageHandlerMap;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LOGGER.debug("Connection established for session: " + session.getId() + " and http session: " + session.getAttributes().get("sessionId"));
        Optional<String> playerSession = getPlayerSession(session);

        if (!playerSession.isPresent()) {
            return;
        }

        sessionRegistry.add(session);
        playerService.updatePlayerSessionId(playerSession.get(), session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionRegistry.remove(session.getId());

        Optional<String> playerSession = getPlayerSession(session);

        if (!playerSession.isPresent()) {
            return;
        }
        Player player = playerService.updatePlayerSessionId(playerSession.get(), null);

        String gameId = (String) session.getAttributes().get("gameId");
        gameService.removePlayer(gameId, player.getId());
    }

    private Optional<String> getPlayerSession(WebSocketSession session) {
        return Optional.ofNullable(session.getAttributes().get("sessionId")).map(Object::toString);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        LOGGER.debug("got message: " + message.getPayload());

        Optional<String> playerSession = getPlayerSession(session);

        if (!playerSession.isPresent()) {
            return;
        }

        String playerSessionId = playerSession.get();
        Player player = playerService.find(playerSessionId);

        EventMessage eventMessage = gson.fromJson(message.getPayload(), EventMessage.class);

        GameEventHandler messageHandler = eventMessageHandlerMap.get(eventMessage.getEventType());
        if (messageHandler == null) {
            LOGGER.debug("Unrecognized message: " + eventMessage);
            return;
        }
        String gameId = (String) session.getAttributes().get("gameId");
        Game game = gameService.find(gameId);
        messageHandler.handle(new GameEvent(game, player, eventMessage, session));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eventMessageHandlerMap = applicationContext.getBeansOfType(GameEventHandler.class).values().stream()
            .collect(Collectors.toMap(GameEventHandler::getEventType, Function.identity()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
