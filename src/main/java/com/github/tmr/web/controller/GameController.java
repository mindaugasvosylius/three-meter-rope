package com.github.tmr.web.controller;

import com.github.tmr.model.Game;
import com.github.tmr.model.Player;
import com.github.tmr.model.WhiteCard;
import com.github.tmr.service.GameService;
import com.github.tmr.service.UserService;
import com.github.tmr.web.response.EventEntity;
import com.github.tmr.web.response.EventsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mindaugas on 2016-05-02.
 */
@Controller
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @RequestMapping("game.mvc")
    public ModelAndView viewGame(@RequestParam("gameId") String gameId) {
        return new ModelAndView("game").addObject("gameId", gameId);
    }

    @RequestMapping(value = "startGame.mvc", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Void> startGame(@RequestParam("gameId") Long gameId) {
        final Game game = gameService.getGame(gameId);
        game.startGame();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "selectWhiteCard.mvc", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Void> selectWhiteCard(@RequestParam("gameId") Long gameId,
                                         @RequestParam("whiteCardId") Long whiteCardId,
                                         HttpSession session) {
        final Game game = gameService.getGame(gameId);
        final Player player = game.getPlayer(session.getId());
        WhiteCard card = player.getCard(whiteCardId);
        game.selectWhiteCard(player, card);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "listen.mvc", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    EventsEntity listen(HttpSession session) {
        Player player = gameService.getPlayer(session.getId());

        waitForEvents();

        return EventsEntity.events(player.getEvents()).build();
    }

    private void waitForEvents() {
        wait(4000);
    }

    private void wait(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (Exception e) {
            logger.error("error waiting", e);
        }
    }
}