package game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player {

    private String id;
    private String sessionId;
    private String name;
    private List<WhiteCard> cards = new ArrayList<>();
    private int awesomePoints;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCards(List<WhiteCard> whiteCards) {
        cards.addAll(whiteCards);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public WhiteCard playCard(Long cardId) {
        Optional<WhiteCard> cardOptional = cards.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst();

        return cardOptional.get();
    }
}
