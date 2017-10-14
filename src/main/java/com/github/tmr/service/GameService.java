package com.github.tmr.service;

import com.github.tmr.model.Game;
import com.github.tmr.model.GameState;
import com.github.tmr.model.Player;
import com.github.tmr.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Mindaugas on 2016-05-04.
 */
public class GameService {

    private List<Game> games = new ArrayList<>();

    public Game getGame(Long id) {
        Objects.requireNonNull(id);
        Optional<Game> gameOptional = games.stream().filter(game -> game.getId().equals(id)).findFirst();
        return gameOptional.isPresent() ? gameOptional.get() : null;
    }

    public Game createGame(User user) {
        Game newGame = new Game(user);
        games.add(newGame);
        return newGame;
    }

    public List<Game> getGames() {
        return games;
    }

    public Player getPlayer(String id) {
        return games.stream()
                .flatMap(game -> game.getPlayers().stream())
                .filter(player -> player.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
