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
        result.render("id", game.getId());

        return result;
    }

    public Result indexPost(@Param("letter") String letter,
                            @Param("id") Long id) {
        Game game = gameDao.getGameById(id);
        game.setGuesses(game.getGuesses() + letter);

        gameDao.updateByGameId(id, game);

        return Results.json().render(game);
    }
}
