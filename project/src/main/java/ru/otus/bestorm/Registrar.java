package ru.otus.bestorm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import ru.otus.bestorm.impl.Table;
import ru.otus.bestorm.impl.util.Destructable;

/**
 * Позволяет при необходимости создать отношения соответствующие объектам и использовать их для
 * хранения данных об объектах
 */
public class Registrar extends Destructable {

  private final Connection connection;
  private final Map<Class<?>, Table<?>> classMap;

  public Registrar(Iterable<Class<?>> classes, Connection connection) throws Exception {
    super(() -> {
      try {
        if (!connection.isClosed())
          connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
    this.connection = Objects.requireNonNull(connection);
    classMap = new HashMap<>();
    for (Class<?> classObject : classes) {
      register(classObject);
    }
  }

  public Table<?> register(Class<?> classObject) {
    if (!classMap.containsKey(classObject)) {
      return classMap.put(classObject, new Table(this, classObject));
    } else {
      return classMap.get(classObject);
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public Transaction createTransaction() {
    // TODO Auto-generated method stub
    return null;
  }

  public <T> ContainableObjectFactory<T> createFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @SuppressWarnings("unchecked")
  public <T> Table<T> getTable(Class<T> classObject) {
    return (Table<T>) classMap.get(classObject);
  }

}
