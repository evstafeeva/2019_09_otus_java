package ru.otus.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.GenericDao;
import ru.otus.model.User;
import ru.otus.service.DbServiceException;
import ru.otus.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final GenericDao<User> userDao;

    public DbServiceUserImpl(GenericDao<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.save(user);
                sessionManager.commitSession();

                logger.info("created user: {}", userId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void updateUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.update(user);
                sessionManager.commitSession();

                logger.info("update user: {}", userId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id, User.class);
                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public void saveOrUpdateUser(User user) {
        if (getUser(user.getId()).isEmpty()) {
            saveUser(user);
            return;
        }
        else {
            updateUser(user);
            return;
        }
    }
}

