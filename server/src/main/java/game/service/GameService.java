package game.service;

import game.event.publisher.GameEventMessagePublisher;
import game.model.Deck;
import game.model.Game;
import game.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameService {

    private Map<String, Game> games = new ConcurrentHashMap<>();

    @Autowired
    private DeckService deckService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameEventMessagePublisher eventMessagePublisher;

    public Game create(Player host) {
        Game game = new Game(host, eventMessagePublisher);
        Deck deck = deckService.getDeck();
        game.setDeck(deck);

        games.put(game.getId(), game);

        return game;
    }

    public Game join(Game game, Player player) {
        game.addPlayer(player);
        return game;
    }

    public Game find(String id) {
        return games.get(id);
    }

    public Collection<Game> getGames() {
        return games.values();
    }

    public void removePlayer(String id, String playerId) {
        Game game = find(id);
        Player player = playerService.find(playerId);
        game.removePlayer(player);
        if (game.getCurrentPlayerSize() == 0) {
            games.remove(id);
        }
    }
}
