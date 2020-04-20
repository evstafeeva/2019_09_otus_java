package ru.otus.hibernate.service;

public class DbServiceException extends RuntimeException {
  public DbServiceException(Exception e) {
    super(e);
  }
}
