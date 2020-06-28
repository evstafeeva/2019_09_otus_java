package ru.otus.dao;

import java.util.Optional;
import ru.otus.sessionmanager.SessionManager;

public interface Dao <T> {

  Optional<T> findById(long id, Class<T> clazz);
  long save(T t);
  long update(T t);
  SessionManager getSessionManager();
}
