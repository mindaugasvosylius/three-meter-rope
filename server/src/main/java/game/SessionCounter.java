package game;

import game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class SessionCounter implements HttpSessionListener {

    @Autowired
    private PlayerService playerService;

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        playerService.remove(event.getSession().getId());
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
    }
}