package controllers;

import com.google.inject.Inject;
import dao.GameDao;
import filters.AuthenticationFilter;
import models.Game;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import services.GameService;

/**
 * Created by dillon on 2017/01/23.
 * GameController
 */
public class GameController {
    @Inject
    GameService gameService;
    @Inject
    GameDao gameDao;

    @FilterWith(AuthenticationFilter.class)
    public Result index(Context context) {
        Result result = Results.html();

        Long id = gameService.newGame(context.getSession());

        Game game = gameDao.getGameById(id);

        result.render("length", game.getWord().length());

        return result;
    }
}
