package ru.otus.hibernate.dao;

public class DaoException extends RuntimeException {
  public DaoException(Exception ex) {
    super(ex);
  }
}
