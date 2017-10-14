package com.github.tmr.model;

import com.github.tmr.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private Long id;
    private String name;
    private List<Player> roundPlayers = new ArrayList<>();
    private Player czar;
    private BlackCard blackCard;
    private List<BlackCard> blackCards = new ArrayList<>();
    private List<WhiteCard> whiteCards = new ArrayList<>();
    private Player creator;
    private List<Player> players = new ArrayList<>();
    private GameState state;
    private GameEventBroker eventBroker;

    {
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));
        blackCards.add(new BlackCard("Black card."));

        whiteCards.add(new WhiteCard("An Oedipus complex."));
        whiteCards.add(new WhiteCard("A tiny horse."));
        whiteCards.add(new WhiteCard("Boogers."));
        whiteCards.add(new WhiteCard("Advice from a wise, old black man."));
        whiteCards.add(new WhiteCard("Famine."));
        whiteCards.add(new WhiteCard("An Oedipus complex."));
        whiteCards.add(new WhiteCard("A tiny horse."));
        whiteCards.add(new WhiteCard("Boogers."));
        whiteCards.add(new WhiteCard("Advice from a wise, old black man."));
        whiteCards.add(new WhiteCard("Famine."));
        whiteCards.add(new WhiteCard("An Oedipus complex."));
        whiteCards.add(new WhiteCard("A tiny horse."));
        whiteCards.add(new WhiteCard("Boogers."));
        whiteCards.add(new WhiteCard("Advice from a wise, old black man."));
        whiteCards.add(new WhiteCard("Famine."));
        whiteCards.add(new WhiteCard("An Oedipus complex."));
        whiteCards.add(new WhiteCard("A tiny horse."));
        whiteCards.add(new WhiteCard("Boogers."));
        whiteCards.add(new WhiteCard("Advice from a wise, old black man."));
        whiteCards.add(new WhiteCard("Famine."));
        whiteCards.add(new WhiteCard("An Oedipus complex."));
        whiteCards.add(new WhiteCard("A tiny horse."));
        whiteCards.add(new WhiteCard("Boogers."));
        whiteCards.add(new WhiteCard("Advice from a wise, old black man."));
        whiteCards.add(new WhiteCard("Famine."));
    }

    public Game(User user) {
        this.id = 1l;
        this.name = user.getName() + "'s game";
        this.state = GameState.LOBBY;
        this.creator = addPlayer(user);
        this.eventBroker = new GameEventBroker(this);
    }

    private Player addPlayer(User user) {
        Player player = new Player(user);
        getPlayers().add(player);
        return player;
    }

    public Long getId() {
        return id;
    }

    public Player joinGame(User user) {
        return addPlayer(user);
    }

    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void startGame() {
        czar = players.get(0);
        notifyCardCzar();
        roundPlayers = players.subList(1, players.size());
        notifyRoundPlayers();

        state = GameState.DEALING;
        blackCard = blackCards.remove(0);
        notifyPlayers(players, new BlackCardDrawEvent(blackCard.getText()));
        for (Player player : players) {
            for (int i = 0; i < 2; i++) {
                WhiteCard whiteCard = whiteCards.remove(0);
                player.getCards().add(whiteCard);
                notifyPlayer(player, new WhiteCardDrawEvent(whiteCard.getText()));
            }
        }
        state = GameState.PLAYING;
    }

    private void notifyRoundPlayers() {
        for (Player player : roundPlayers) {
            notifyPlayer(player, new PlayerRoundEvent());
        }
    }

    private void notifyCardCzar() {
        notifyPlayer(czar, new PlayerCardCzarEvent());
    }

    private void notifyPlayer(Player player, Event event) {
        player.getEvents().add(event);
    }

    private void notifyPlayers(List<Player> players, Event event) {
        for (Player player : players) {
            notifyPlayer(player, event);
        }
    }

    public Player getPlayer(String id) {
        return players.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    private List<Player> getPlayersExcept(Player excluded) {
        return players.stream().filter(p -> !p.equals(excluded)).collect(Collectors.toList());
    }

    public void selectWhiteCard(Player player, WhiteCard card) {
        // notify other players
        notifyPlayers(getPlayersExcept(player), new RoundPlayerDecisionEvent(card.getText()));
    }
}
