package ru.otus.requestWriter;

public interface RequestWriter <T> {
    String selectById(long id, Class<T> clazz);
    String insert(T object) throws IllegalAccessException;
    String updateById(T object) throws IllegalAccessException;
}
