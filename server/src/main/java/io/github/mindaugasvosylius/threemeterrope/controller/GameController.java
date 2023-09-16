package io.github.mindaugasvosylius.threemeterrope.controller;

import io.github.mindaugasvosylius.threemeterrope.model.Game;
import io.github.mindaugasvosylius.threemeterrope.model.Player;
import io.github.mindaugasvosylius.threemeterrope.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {

    private GameService gameService;

    @PostMapping("/create")
    public Game create(@RequestBody Player player) {
        return gameService.create(player);
    }
}
