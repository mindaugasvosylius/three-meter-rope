package io.github.mindaugasvosylius.threemeterrope.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import io.github.mindaugasvosylius.threemeterrope.utils.Messenger;
import lombok.Builder;

@Builder
public class Game {
    private List<Player> players;
    private List<BlackCard> blackCards;
    private List<WhiteCard> whiteCards;
    private int initialCardAmount;
    private int roundCardAmount;
    private int pointLimit;
    private Player tzar;
    private List<Player> roundPlayers;
    private Player winner;

    public void play() {
        initialize();

        do {
            playRound();
        } while (getWinner() == null);

        Messenger.send("%s wins!", getWinner().getName());
    }

    private void initialize() {
        Collections.shuffle(players);
        Collections.shuffle(blackCards);
        Collections.shuffle(whiteCards);

        drawCards(players, initialCardAmount);
    }

    private void playRound() {
        assignRoles();
        Messenger.send("%s is the card tzar.", tzar.getName());

        final BlackCard blackCard = drawBlackCard();
        Messenger.send("Black card: %s", blackCard.getText());

        drawCards(roundPlayers, roundCardAmount);

        final List<PlayerMove> playerMoves = roundPlayers.stream()
                .map(player -> PlayerMove.builder()
                        .cards(player.playCards(blackCard))
                        .player(player)
                        .build())
                .toList();
        displayPlayerMoves(playerMoves);

        final PlayerMove winningMove = tzar.selectWinner(playerMoves);
        winningMove.getPlayer().receivePoints();
        displayRoundResults(blackCard, winningMove);
    }

    private void drawCards(final List<Player> drawPlayers, final int cardAmount) {
        drawPlayers.forEach(player -> player.receiveCards(drawWhiteCards(cardAmount)));
    }

    private void displayRoundResults(final BlackCard blackCard, final PlayerMove winningMove) {
        final Player roundWinner = winningMove.getPlayer();
        final String resultText = injectPlaceholders(blackCard.getText(), winningMove.getCards().stream()
                .map(WhiteCard::getText)
                .toList());

        Messenger.send(resultText);
        Messenger.send("%s wins the round and now has %s points!", roundWinner.getName(), roundWinner.getPoints());
    }

    private String injectPlaceholders(final String text, final List<String> values) {
        StringBuilder result;
        if (text.contains("____")) {
            final String[] tokens = text.split("____");
            result = new StringBuilder();
            for (int i = 0; i < tokens.length - 1; i++) {
                result.append(tokens[i]).append("[").append(values.get(i)).append("]");
            }
            result.append(tokens[tokens.length - 1]);
        } else {
            result = new StringBuilder(text + " " + values);
        }
        return result.toString();
    }

    private void displayPlayerMoves(final List<PlayerMove> playerMoves) {
        for (int i = 0; i < playerMoves.size(); i++) {
            final PlayerMove playerMove = playerMoves.get(i);
            Messenger.send("%s => %s played: %s", i, playerMove.getPlayer().getName(), playerMove.getCards().stream()
                    .map(WhiteCard::getText)
                    .toList());
        }
    }

    private List<WhiteCard> drawWhiteCards(int number) {
        return IntStream.range(0, number)
                .mapToObj(counter -> whiteCards.remove(0))
                .toList();
    }

    private BlackCard drawBlackCard() {
        return blackCards.remove(0);
    }

    private Player getWinner() {
        if (winner == null) {
            winner = players.stream()
                    .filter(player -> player.getPoints() == pointLimit)
                    .findFirst().orElse(null);
        }
        return winner;
    }

    private void assignRoles() {
        int currentTzarIndex = (tzar == null) ? -1 : players.indexOf(tzar);
        int nextTzarIndex = (currentTzarIndex == players.size() - 1) ? 0 : currentTzarIndex + 1;
        tzar = players.get(nextTzarIndex);
        roundPlayers = players.stream()
                .filter(player -> !player.equals(tzar))
                .toList();
    }
}