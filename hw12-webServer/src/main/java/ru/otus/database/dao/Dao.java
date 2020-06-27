package ru.otus.database.dao;

import java.util.Optional;
import ru.otus.database.sessionmanager.SessionManager;
import ru.otus.database.model.User;

public interface Dao <T> {

  Optional<T> findById(long id);
  Optional<User> findByLogin(String Login);
  Optional<T> findRandom();
  long save(T t);
  long update(T t);
  SessionManager getSessionManager();
}
