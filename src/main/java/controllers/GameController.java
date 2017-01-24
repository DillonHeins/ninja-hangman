package controllers;

import com.google.inject.Inject;
import dao.GameDao;
import filters.AuthenticationFilter;
import models.Game;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
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
        Long id = gameService.newGame(context.getSession());
        Game game = gameDao.getGameById(id);

        Result result = Results.html();
        result.render("length", game.getWord().length());

        return result;
    }

    public Result indexPost(@Param("letter") String letter,
                            @Param("id") Long id) {
        Long num = 2L;

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<" + letter + " " + id);

        Game game = gameDao.getGameById(num);
        game.setGuesses(game.getGuesses() + letter);

        //update game here

        return Results.json().render(game);
    }
}
