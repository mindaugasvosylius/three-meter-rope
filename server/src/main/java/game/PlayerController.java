package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.event.PlayerDto;
import game.model.Player;
import game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/player/edit", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String addOrUpdatePlayer(@RequestParam(value="name", required=true) String name, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        playerService.addOrUpdate(sessionId, name);
        return "ok";
    }

    @RequestMapping(value = "/player/get", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public String findPlayer(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        Player player = playerService.find(sessionId);
        return gson.toJson(mapToDto(player));
    }

    public PlayerDto mapToDto(Player player) {
        return new PlayerDto(player.getId(), player.getName());
    }
}
