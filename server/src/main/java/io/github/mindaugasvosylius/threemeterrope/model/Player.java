package io.github.mindaugasvosylius.threemeterrope.model;

import java.util.ArrayList;
import java.util.List;

import io.github.mindaugasvosylius.threemeterrope.utils.FileUtils;
import io.github.mindaugasvosylius.threemeterrope.utils.Messenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private String name;
    private int points;
    private List<WhiteCard> cards;

    public List<WhiteCard> playCards(final BlackCard blackCard) {
        final List<WhiteCard> playedCards = new ArrayList<>();
        for (int i = 0; i < blackCard.getPlaceholderCount(); i++) {
            playedCards.add(playCard());
        }
        return playedCards;
    }

    public WhiteCard playCard() {
        Messenger.send("Your cards: ");
        for (int i = 0; i < cards.size(); i++) {
            Messenger.send(i + " => " + cards.get(i).getText());
        }
        try {
            String cardIndex = FileUtils.readLine();
            int index = Integer.parseInt(cardIndex);
            return cards.remove(index);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerMove selectWinner(List<PlayerMove> playerMoves) {
        final int index = Integer.parseInt(FileUtils.readLine("Pick winner:"));
        return playerMoves.get(index);
    }

    public void receivePoints() {
        points++;
    }

    public void receiveCards(List<WhiteCard> drawCards) {
        cards.addAll(drawCards);
    }
}