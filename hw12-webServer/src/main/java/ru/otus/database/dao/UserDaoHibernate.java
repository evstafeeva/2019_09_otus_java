package ru.otus.database.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.database.sessionmanager.SessionManager;
import ru.otus.database.sessionmanager.hibernate.SessionManagerHibernate;
import java.util.List;
import java.util.Optional;
import ru.otus.database.model.*;


public class UserDaoHibernate implements Dao<User> {
    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;
    private final Class<?> clazz = User.class;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        Session currentSession = sessionManager.getCurrentSession().getHibernateSession();
        try {
            return (Optional<User>) Optional.ofNullable(currentSession.find(clazz, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        //TODO разобраться со случаем, если 2 с одинаковым логином
        Session currentSession = sessionManager.getCurrentSession().getHibernateSession();
        Query<User> query = currentSession.createQuery("SELECT u FROM User u WHERE u.login = \'" + login + "\'", User.class);

        System.out.println(query.getResultList());
        System.out.println(query.getResultList().get(0).getPassword());
        return Optional.ofNullable(query.getResultList().get(0));
    }

    @Override
    public Optional<User> findRandom() {
        Session currentSession = sessionManager.getCurrentSession().getHibernateSession();
        //currentSession.beginTransaction();
        List<?> list =  currentSession.createQuery("SELECT u FROM User u").getResultList();
        return Optional.ofNullable((User) list.get((int) (Math.random()*(list.size()))));
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
