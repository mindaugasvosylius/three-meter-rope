package io.github.mindaugasvosylius.threemeterrope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
//    public static void main(String[] args) {
//        final BlackCardService blackCardService = new BlackCardService();
//        final WhiteCardService whiteCardService = new WhiteCardService();
//        final NpcPlayerService npcPlayerService = new NpcPlayerService();
//
//        final List<BlackCard> blackCards = blackCardService.list();
//        final List<WhiteCard> whiteCards = whiteCardService.list();
//
//        String name = FileUtils.readLine("Player name: ");
//        List<Player> players = new ArrayList<>();
//        players.add(Player.builder()
//                .cards(new ArrayList<>())
//                .name(name)
//                .points(0)
//                .build());
//        players.addAll(npcPlayerService.randomizedList(3));
//
//        Game.builder()
//                .pointLimit(3)
//                .initialCardAmount(3)
//                .roundCardAmount(1)
//                .blackCards(blackCards)
//                .whiteCards(whiteCards)
//                .players(players)
//                .build()
//                .play();
//    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}