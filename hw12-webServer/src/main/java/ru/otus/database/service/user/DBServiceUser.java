package ru.otus.database.service.user;

import ru.otus.database.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);
  long updateUser(User user);
  Optional<User> getUser(long id);
  long saveOrUpdateUser(User user);
}
