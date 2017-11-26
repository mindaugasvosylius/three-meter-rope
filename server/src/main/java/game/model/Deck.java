package game.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<WhiteCard> whiteCards;
    private List<BlackCard> blackCards;

    public Deck(List<WhiteCard> whiteCards, List<BlackCard> blackCards) {
        this.whiteCards = new ArrayList<>(whiteCards);
        this.blackCards = new ArrayList<>(blackCards);
    }

    public List<WhiteCard> getWhiteCardsForNewPlayer() {
        return getWhiteCards(5);
    }

    public List<WhiteCard> getWhiteCardsForRound() {
        return getWhiteCards(1);
    }

    private List<WhiteCard> getWhiteCards(int count) {
        List<WhiteCard> cards = new ArrayList<>(this.whiteCards.subList(0, count));
        whiteCards.removeIf(cards::contains);
        return cards;
    }

    public BlackCard getBlackCard() {
        return blackCards.remove(0);
    }
}
