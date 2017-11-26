package game.model;

import game.event.EventMessage;
import game.event.EventMessageProperty;
import game.event.EventType;
import game.event.publisher.GameEventMessagePublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static game.event.EventMessageBuilder.builderFor;

public class Game {

    private String id;
    private List<Player> players = new ArrayList<>();
    private Player host;
    private Player cardTzar;
    private List<Player> roundPlayers = new ArrayList<>();
    private int nextTzarIndex = 0;
    private Deck deck;
    private List<WhiteCard> playedCards = new ArrayList<>();
    private BlackCard blackCard;
    private int maxPlayerSize = 10;
    private int pointsRequired = 10;

    private final GameEventMessagePublisher eventMessagePublisher;

    public Game(Player host, GameEventMessagePublisher eventMessagePublisher) {
        this.id = UUID.randomUUID().toString();
        this.host = host;
        this.players.add(host);
        this.eventMessagePublisher = eventMessagePublisher;
    }

    public void start() {
        assignPlayerRoles();
        dealBlackCard();
        dealWhiteCardsForNewGame();
        requestInput();
    }

    private void requestInput() {
        notifyPlayers(builderFor(EventType.WHITE_CARD_REQUEST)
                .build(), roundPlayers);
    }

    public void addPlayer(Player player) {
        notifyPlayers(builderFor(EventType.PLAYER_JOINED)
                .withProperty(EventMessageProperty.ID, player.getId())
                .withProperty(EventMessageProperty.PLAYER_NAME, player.getName())
                .build(), players);
        this.players.add(player);
    }

    public String getName() {
        return host.getName() + "'s game";
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getCurrentPlayerSize() {
        return players.size();
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void newRound() {
        assignPlayerRoles();
        dealBlackCard();
        dealWhiteCardsForNewRound();
    }

    private void dealWhiteCardsForNewGame() {
        for (Player player : players) {
            List<WhiteCard> whiteCardsForNewPlayer = deck.getWhiteCardsForNewPlayer();
            player.addCards(whiteCardsForNewPlayer);
            whiteCardsForNewPlayer.forEach(whiteCard ->
                    notifyPlayer(builderFor(EventType.WHITE_CARD_NEW)
                            .withProperty(EventMessageProperty.ID, whiteCard.getId())
                            .withProperty(EventMessageProperty.CARD_TEXT, whiteCard.getText())
                            .build(), player));
        }
    }

    private void dealWhiteCardsForNewRound() {
        for (Player player : players) {
            List<WhiteCard> whiteCardsForRound = deck.getWhiteCardsForRound();
            player.addCards(whiteCardsForRound);
            whiteCardsForRound.forEach(whiteCard ->
                notifyPlayer(builderFor(EventType.WHITE_CARD_NEW)
                        .withProperty(EventMessageProperty.ID, whiteCard.getId())
                        .withProperty(EventMessageProperty.CARD_TEXT, whiteCard.getText())
                        .build(), player));
        }
    }

    private void dealBlackCard() {
        blackCard = deck.getBlackCard();

        notifyPlayers(builderFor(EventType.BLACK_CARD_NEW)
                .withProperty(EventMessageProperty.ID, blackCard.getId())
                .withProperty(EventMessageProperty.CARD_TEXT, blackCard.getText())
                .build(), players);
    }

    private void notifyPlayer(EventMessage eventMessage, Player player) {
        eventMessagePublisher.publish(eventMessage, player.getSessionId());
    }

    private void notifyPlayers(EventMessage eventMessage, List<Player> playersToNotify) {
        eventMessagePublisher.publish(eventMessage, playersToNotify.stream()
                .map(Player::getSessionId)
                .collect(Collectors.toList()));
    }

    private void assignPlayerRoles() {
        assignCardTzar();
        assignRoundPlayers();
    }

    private void assignCardTzar() {
        cardTzar = players.get(nextTzarIndex);
        nextTzarIndex++;
        if (nextTzarIndex >= players.size()) {
            nextTzarIndex = 0;
        }
        notifyPlayer(builderFor(EventType.CARD_TZAR_CHOSEN)
                .withProperty(EventMessageProperty.ID, cardTzar.getId())
                .build(), cardTzar);
    }

    private void assignRoundPlayers() {
        roundPlayers = players.stream()
                .filter(player -> !cardTzar.equals(player))
                .collect(Collectors.toList());
        roundPlayers.forEach(player -> notifyPlayer(builderFor(EventType.ROUND_PLAYER_CHOSEN)
                .withProperty(EventMessageProperty.ID, player.getId())
                .build(), player));
    }

    public BlackCard getBlackCard() {
        return blackCard;
    }

    public void setBlackCard(BlackCard blackCard) {
        this.blackCard = blackCard;
    }

    public List<WhiteCard> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<WhiteCard> playedCards) {
        this.playedCards = playedCards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getHost() {
        return host;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public int getMaxPlayerSize() {
        return maxPlayerSize;
    }

    public void setMaxPlayerSize(int maxPlayerSize) {
        this.maxPlayerSize = maxPlayerSize;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(int pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public void playCard(Player player, Long cardId) {
        WhiteCard whiteCard = player.playCard(cardId);
        playedCards.add(whiteCard);
        notifyPlayers(builderFor(EventType.WHITE_CARD_PLAYED)
                .withProperty(EventMessageProperty.ID, whiteCard.getId())
                .withProperty(EventMessageProperty.PLAYER_ID, player.getId())
                .withProperty(EventMessageProperty.CARD_TEXT, whiteCard.getText())
                .build(), players);
        if (playedCards.size() == roundPlayers.size()) {
            notifyPlayers(builderFor(EventType.REQUESTING_CHOICE).build(), players);
            notifyPlayer(builderFor(EventType.WINNER_CARD_REQUEST)
                    .build(), cardTzar);
        }
    }

    public void whiteCardChosen(Player player, Long cardId) {
    }
}
