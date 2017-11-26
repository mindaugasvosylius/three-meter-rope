package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.event.GameDto;
import game.model.Game;
import game.model.Player;
import game.service.GameService;
import game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/game-list", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String getGames() {
        List<GameDto> games = gameService.getGames().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return gson.toJson(games);
    }

    @RequestMapping(value = "/game", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String createGame(HttpServletRequest request) {
        Player player = playerService.find(request.getSession().getId());
        Game game = gameService.create(player);
        request.getSession().setAttribute("gameId", game.getId());
        return gson.toJson(mapToDto(game));
    }

    @RequestMapping(value = "/game/join", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String joinGame(@RequestParam(value="id") String id, HttpServletRequest request) {
        Game game = gameService.find(id);
        Player player = playerService.find(request.getSession().getId());
        game.addPlayer(player);

        request.getSession().setAttribute("gameId", game.getId());

        return gson.toJson(mapToDto(game));
    }

    @RequestMapping(value = "/game/leave", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String leaveGame(@RequestParam(value="id") String id, HttpServletRequest request) {
        Game game = gameService.find(id);
        Player player = playerService.find(request.getSession().getId());
        game.removePlayer(player);

        request.getSession().setAttribute("gameId", null);

        return gson.toJson(mapToDto(game));
    }

    @RequestMapping(value = "/game/get", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String getGame(@RequestParam(value="id") String id, HttpServletRequest request) {
        Game game = gameService.find(id);
        return gson.toJson(mapToDto(game));
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String ping(HttpServletRequest request) {
        return gson.toJson(request.getSession().getId());
    }

    private GameDto mapToDto(Game game) {
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setMaxPlayerSize(game.getMaxPlayerSize());
        dto.setCurrentPlayerSize(game.getCurrentPlayerSize());
        return dto;
    }
}
