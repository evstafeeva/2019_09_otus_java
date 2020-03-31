package ru.otus.service.user;

import ru.otus.model.User;

import java.util.Optional;

public interface DBServiceUser {

  void saveUser(User user);
  void updateUser(User user);
  Optional<User> getUser(long id);
  void saveOrUpdateUser(User user);
}
