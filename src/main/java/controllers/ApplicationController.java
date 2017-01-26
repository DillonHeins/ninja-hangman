package controllers;

import com.google.inject.Inject;
import dao.GameDao;
import dao.HangmanUserDao;
import filters.AuthenticationFilter;
import models.Game;
import models.HangmanUser;
import ninja.*;

import com.google.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ApplicationController {
    @Inject
    GameDao gameDao;
    @Inject
    HangmanUserDao hangmanUserDao;

    @FilterWith(AuthenticationFilter.class)
    public Result index(Context context) {
        Result result = Results.html();

        result.render("page", "index");

        List<Game> games = gameDao.getUserGames(hangmanUserDao.getUserByUsername(context.getSession().get("username")));

        result.render("ids",
                games.stream()
                .map(Game::getId)
                .collect(Collectors.toList())
                .toString());

        return result;
    }
}
