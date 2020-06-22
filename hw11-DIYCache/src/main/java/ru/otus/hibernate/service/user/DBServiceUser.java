package ru.otus.hibernate.service.user;

import ru.otus.hibernate.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);
  long updateUser(User user);
  Optional<User> getUser(long id);
  long saveOrUpdateUser(User user);
}
