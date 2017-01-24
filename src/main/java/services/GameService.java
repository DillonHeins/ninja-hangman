package services;

import com.google.inject.Inject;
import dao.GameDao;
import dao.HangmanUserDao;
import models.Game;
import models.HangmanUser;
import ninja.session.Session;

/**
 * Created by dillon on 2017/01/23.
 */
public class GameService {
    @Inject
    private GameDao gameDao;
    @Inject
    private HangmanUserDao hangmanUserDao;

    String[] words = {
        "variable",
        "framework",
        "class",
        "http",
        "model",
        "service",
        "dao",
        "views",
        "templating",
        "migration",
        "postgresql",
        "ninja",
        "routes",
        "controller",
        "filters",
        "sql",
        "persistence"
    };

    public Long newGame(Session session) {
        HangmanUser hangmanUser = hangmanUserDao.getUserByUsername(session.get("username"));

        int randomWord = (int)(Math.random() * words.length);
        Game game = new Game(words[randomWord], "", hangmanUser);
        return gameDao.createGame(game);
    }
}
