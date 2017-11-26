package game.service;

import game.model.BlackCard;
import game.model.Deck;
import game.model.WhiteCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeckService {

    @Autowired
    private CardService cardService;

    public Deck getDeck() {
        List<WhiteCard> whiteCards = cardService.getWhiteCards(50);
        List<BlackCard> blackCards = cardService.getBlackCards(10);

        Deck deck = new Deck(whiteCards, blackCards);

        return deck;
    }
}
