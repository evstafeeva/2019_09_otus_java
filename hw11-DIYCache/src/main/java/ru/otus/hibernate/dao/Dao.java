package ru.otus.hibernate.dao;

import java.util.Optional;
import ru.otus.hibernate.sessionmanager.SessionManager;

public interface Dao <T> {

  Optional<T> findById(long id, Class<T> clazz);
  long save(T t);
  long update(T t);
  SessionManager getSessionManager();
}
