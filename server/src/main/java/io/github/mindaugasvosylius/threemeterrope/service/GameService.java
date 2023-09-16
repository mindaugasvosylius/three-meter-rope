package io.github.mindaugasvosylius.threemeterrope.service;

import java.util.ArrayList;
import java.util.List;

import io.github.mindaugasvosylius.threemeterrope.model.BlackCard;
import io.github.mindaugasvosylius.threemeterrope.model.Game;
import io.github.mindaugasvosylius.threemeterrope.model.Player;
import io.github.mindaugasvosylius.threemeterrope.model.WhiteCard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {

    private BlackCardService blackCardService;
    private WhiteCardService whiteCardService;
    private NpcPlayerService npcPlayerService;

    public Game create(Player player) {
        final List<BlackCard> blackCards = blackCardService.list();
        final List<WhiteCard> whiteCards = whiteCardService.list();

        List<Player> players = new ArrayList<>();
        players.add(player);
        players.addAll(npcPlayerService.randomizedList(3));

        return Game.builder()
                .pointLimit(3)
                .initialCardAmount(3)
                .roundCardAmount(1)
                .blackCards(blackCards)
                .whiteCards(whiteCards)
                .players(players)
                .build();
    }
}
