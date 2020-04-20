package ru.otus.hibernate.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import java.util.Optional;
import ru.otus.hibernate.model.User;

public class UserDaoHibernate implements Dao<User> {
    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id, Class<User> clazz) {
        Session currentSession = sessionManager.getCurrentSession().getHibernateSession();
        try {
            return Optional.ofNullable(currentSession.find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long save(User user) {
        try {
            Session currentSession = sessionManager.getCurrentSession().getHibernateSession();
            if (user.getId() > 0) {
                currentSession.merge(user);
            } else {
                currentSession.persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    public long update(User user) {
        try {
            Session currentSession = sessionManager.getCurrentSession().getHibernateSession();
            if (user.getId() > 0) {
                currentSession.update(user);
            } else {
                save(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
