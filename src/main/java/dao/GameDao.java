package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.Game;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by dillon on 2017/01/24.
 * GameDao
 */
public class GameDao {
    @Inject
    private Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public Game getGameById(Long id) {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM Game x WHERE x.id = :idParam");
        return (Game) q.setParameter("idParam", id).getSingleResult();
    }

    @Transactional
    public void updateByGameId(Long id, Game updatedGame) {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM Game x WHERE x.id = :idParam");
        Game game = (Game) q.setParameter("idParam", id).getSingleResult();

        game.setGuesses(updatedGame.getGuesses());
    }

    @Transactional
    public Long createGame(Game game) {
        if (game != null && game.getWord() != null && game.getGuesses() != null) {
            EntityManager entityManager = entityManagerProvider.get();

            entityManager.persist(game);
            entityManager.flush();

            return game.getId();
        }

        return null;
    }
}
