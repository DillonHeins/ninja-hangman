package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import models.HangmanUser;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by dillon on 2017/01/22.
 * HangmanUserDao
 */
@Singleton
public class HangmanUserDao {
    @Inject
    private Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public HangmanUser getUserByUsername(String username) {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM HangmanUser x WHERE x.username = :usernameParam");
        return (HangmanUser) q.setParameter("usernameParam", username).getSingleResult();
    }

    @Transactional
    public boolean registerUser(String username, byte[] password, byte[] salt) {
        if (username != null && password != null && salt != null) {
            EntityManager entityManager = entityManagerProvider.get();

            HangmanUser hangmanUser = new HangmanUser(username, password, salt);
            entityManager.persist(hangmanUser);

            return true;
        }

        return false;
    }

    /**
     * Get single result without throwing NoResultException, you can of course just catch the
     * exception and return null, it's up to you.
     */
    private static <T> T getSingleResult(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
}
