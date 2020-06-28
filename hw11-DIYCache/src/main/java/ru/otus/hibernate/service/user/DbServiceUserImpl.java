package ru.otus.hibernate.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HWCacheDemo;
import ru.otus.cachehw.MyCache;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.model.User;
import ru.otus.hibernate.service.DbServiceException;
import ru.otus.hibernate.sessionmanager.SessionManager;

import ru.otus.cachehw.*;
import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final UserDaoHibernate userDao;
    private final HwCache<String, User> cache = new MyCache<>();

    public DbServiceUserImpl(UserDaoHibernate userDao) {
        this.userDao = userDao;
        cache.addListener(new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {
                logger.info("FROM CACHE: key:{"+key+"}, value:{"+value+"}, action: {"+action+"}");
            }
        });
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.save(user);
                sessionManager.commitSession();
                //сохраняем в кэш
                cache.put(String.valueOf(userId), user);
                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public long updateUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.update(user);
                sessionManager.commitSession();
                //обновляем в кэше
                cache.put(String.valueOf(userId), user);
                logger.info("update user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        Optional<User> user = Optional.ofNullable(cache.get(String.valueOf(id)));
        if(!user.isEmpty())
            return user;
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id, User.class);
                cache.put(String.valueOf(id), userOptional.get());
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
    public long saveOrUpdateUser(User user) {
        return getUser(user.getId()).isEmpty() ? saveUser(user) : updateUser(user);
    }
}

