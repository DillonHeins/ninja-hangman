package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.GameDao;
import filters.AuthenticationFilter;
import models.Game;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import services.GameService;

import javax.persistence.NoResultException;

/**
 * Created by dillon on 2017/01/23.
 * GameController
 */
@Singleton
public class GameController {
    @Inject
    GameService gameService;
    @Inject
    GameDao gameDao;

    @FilterWith(AuthenticationFilter.class)
    public Result index(Context context) {
        Long id = gameService.newGame(context.getSession());

        return Results.redirect("/game/" + id);
    }

    @FilterWith(AuthenticationFilter.class)
    public Result indexPost(@Param("letter") String letter,
                            @Param("id") Long id) {
        Game game = gameDao.getGameById(id);
        game.setGuesses(game.getGuesses() + letter);

        gameDao.updateByGameId(id, game);

        return Results.json().render(game);
    }

    @FilterWith(AuthenticationFilter.class)
    public Result getGame(@PathParam("id") String id,
                          Context context) {
        Long lId = Long.parseLong(id);
        Game game;

        try {
            game = gameDao.getGameById(lId);

            Result result = Results.html();
            result.render("length", game.getWord().length());
            result.render("id", game.getId());
            result.render("word", game.getWord());
            result.render("guesses", game.getGuesses());

            return result;
        } catch (NoResultException noResultException) {
            context.getFlashScope().error("game.noGameFoundError");

            return Results.redirect("/");
        }
    }
}
