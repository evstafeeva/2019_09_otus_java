package ru.otus.services;

import ru.otus.database.dao.Dao;
import ru.otus.database.model.User;

public class UserAuthServiceImpl implements UserAuthService {

    private final Dao<User> userDao;

    public UserAuthServiceImpl(Dao<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String login, String pass) {
        userDao.getSessionManager().beginSession();
        return userDao.findByLogin(login).get().getPassword().equals(pass);
    }
}
