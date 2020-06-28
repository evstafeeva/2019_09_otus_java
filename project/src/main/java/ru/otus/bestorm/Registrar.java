package ru.otus.bestorm;

import ru.otus.bestorm.impl.Table;
import ru.otus.bestorm.impl.util.Destructable;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.util.*;

/**
 * Позволяет при необходимости создать отношения соответствующие объектам и использовать их для
 * хранения данных об объектах
 */
public class Registrar extends Destructable {

    private final Connection connection;
    private final Map<Class<?>, Table<?>> classMap;
    private final Set<Class<?>> registeredClasses = new HashSet<>();

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
        for(Map.Entry<Class<?>, Table<?>> entry : classMap.entrySet()){
            for(Field field:entry.getKey().getDeclaredFields()){
                //все правильно, просто по-другому почему-то не работает
                Table.TableField tableField = (entry.getValue()).new TableField(field);
                entry.getValue().getDeclaredFields().put(field, tableField);
                Statement statement = connection.createStatement();
                statement.execute(addFieldQuery(tableField));
                statement.close();
            }
        }
        for (Table<?> table : classMap.values()) {
            for (Table<?>.TableField tableField : table.getFields()) {
                if (null != tableField.getReferent()) {
                  Statement statement = connection.createStatement();
                  statement.execute(foreignKeyQuery(tableField));
                  statement.close();
                }
            }
        }
    }

    private String addFieldQuery(Table<?>.TableField tableField){
        return "ALTER TABLE `" + tableField.getTable().getName() + "` ADD `" + tableField.getName() + "` "
                + tableField.getType() + ";";
    }

    private String foreignKeyQuery(Table<?>.TableField tableField) {
        return "ALTER TABLE `" + tableField.getTable().getName() + "` ADD FOREIGN KEY (`" + tableField.getName()
                + "`) REFERENCES `" + tableField.getReferent().getTable().getName() + "` ( `"
                + tableField.getReferent().getTable().getPrimaryKey().getName() + "` );";
    }

    public void register(Class<?> classObject) throws SQLException {
        if (!registeredClasses.contains(classObject)) {
            registeredClasses.add(classObject);
            classMap.put(classObject, new Table(this, classObject));
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
