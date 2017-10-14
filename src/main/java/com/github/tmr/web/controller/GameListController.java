package com.github.tmr.web.controller;

import com.github.tmr.model.Game;
import com.github.tmr.model.User;
import com.github.tmr.service.GameService;
import com.github.tmr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by Mindaugas on 2016-05-07.
 */
@Controller
public class GameListController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @RequestMapping("list.mvc")
    public ModelAndView viewList(ModelMap modelMap) {
        modelMap.put("games", gameService.getGames());
        return new ModelAndView("list");
    }

    @RequestMapping(value = "createGame.mvc", method = RequestMethod.POST)
    public ModelAndView createGame(HttpSession session) {
        final User user = userService.getUser(session.getId());
        final Game game = gameService.createGame(user);
        return new ModelAndView("redirect:game.mvc").addObject("gameId", game.getId());
    }

    @RequestMapping(value = "joinGame.mvc", method = RequestMethod.POST)
    public ModelAndView joinGame(@RequestParam("gameId") Long gameId, HttpSession session) {
        final User user = userService.getUser(session.getId());
        final Game game = gameService.getGame(gameId);
        game.joinGame(user);
        return new ModelAndView("redirect:game.mvc").addObject("gameId", game.getId());
    }
}
