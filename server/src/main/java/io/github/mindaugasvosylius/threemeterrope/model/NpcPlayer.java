package io.github.mindaugasvosylius.threemeterrope.model;

import java.util.List;
import java.util.Random;

import lombok.Builder;

public class NpcPlayer extends Player {

    @Builder(builderMethodName = "npcBuilder")
    public NpcPlayer(final String name, final int points, final List<WhiteCard> cards) {
        super(name, points, cards);
    }

    public WhiteCard playCard() {
        return getCards().remove(0);
    }

    public PlayerMove selectWinner(List<PlayerMove> playerMoves) {
        final int index = new Random().nextInt(getCards().size());
        return playerMoves.get(index);
    }
}