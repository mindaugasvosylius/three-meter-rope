package game.service;

import game.model.BlackCard;
import game.model.WhiteCard;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CardService {

    public List<WhiteCard> getWhiteCards(int count) {
        List<WhiteCard> cards = new ArrayList<>(Arrays.asList(
                new WhiteCard(1L, "Being on fire."),
                new WhiteCard(2L, "Racism."),
                new WhiteCard(3L, "Old-people smell."),
                new WhiteCard(4L, "A micropenis."),
                new WhiteCard(5L, "Women in yogurt commercials."),
                new WhiteCard(6L, "Classist undertones."),
                new WhiteCard(7L, "Not giving a shit about the Third World."),
                new WhiteCard(8L, "Inserting a mason jar into my anus."),
                new WhiteCard(9L, "Court-ordered rehab."),
                new WhiteCard(10L, "A windmill full of corpses."),
                new WhiteCard(11L, "The gays."),
                new WhiteCard(12L, "An oversized lollipop."),
                new WhiteCard(13L, "African children."),
                new WhiteCard(14L, "An asymmetric boob job."),
                new WhiteCard(15L, "Bingeing and purging."),
                new WhiteCard(16L, "The hardworking Mexican.")
        ));

        if (cards.size() < count) {
            int diff = count - cards.size();
            for (int i = 0; i < diff; i++) {
                cards.add(new WhiteCard(17L + i, "The hardworking Mexican."));
            }
        }

        return cards;
    }

    public List<BlackCard> getBlackCards(int count) {
        List<BlackCard> cards = new ArrayList<>(Arrays.asList(
                new BlackCard(1L, "How did I lose my virginity?"),
                new BlackCard(2L, "Why can't I sleep at night?"),
                new BlackCard(3L, "What's that smell?"),
                new BlackCard(4L, "I got 99 problems but __________ ain't one."),
                new BlackCard(5L, "Maybe she's born with it. Maybe it's ________.")
        ));

        if (cards.size() < count) {
            int diff = count - cards.size();
            for (int i = 0; i < diff; i++) {
                cards.add(new BlackCard(6L + i, "Maybe she's born with it. Maybe it's ________."));
            }
        }

        return cards;
    }
}
