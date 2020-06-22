package ru.otus.service.user;

import ru.otus.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);
  long updateUser(User user);
  Optional<User> getUser(long id);
  long saveOrUpdateUser(User user);
}
