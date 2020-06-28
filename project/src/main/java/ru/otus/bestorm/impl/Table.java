package ru.otus.bestorm.impl;

import ru.otus.bestorm.*;
import ru.otus.bestorm.filters.ValueFilter;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Table<T> implements ContainableObjectFactory<T> {

    private static final String SURROGATE_KEY_NAME = "ID";


    public class TableField {
        private final String name;
        private final FieldType type;
        private final Table<?>.TableField referent;

        public Table<T> getTable() {
            return Table.this;
        }

        public Table<?>.TableField getReferent() {
            return referent;
        }

        public FieldType getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        /**
         * Для обычного поля
         *
         * @param field
         */
        public TableField(Field field) throws SQLException {
            this.name = field.getName() + "_";
            FieldType type = FieldType.typeOf(field.getType());
            if (null != type) {
                this.referent = null;
                this.type = type;
            } else {
                this.type = FieldType.INTEGER;
                Table<?> table = registrar.getTable(field.getType());
                this.referent = table.primaryKey;
            }
        }

        /**
         * Для суррогатного ключа
         *
         * @param name название
         * @param type тип
         */
        public TableField(String name, FieldType type) {
            this.name = name;
            this.type = type;
            this.referent = null;
        }
    }

    private final Class<T> classObject;
    private final Registrar registrar;
    private final Map<Field, TableField> declaredFields = new HashMap<>();
    private final TableField primaryKey;
    private final String name;

    public TableField getPrimaryKey() {
        return primaryKey;
    }

    public String getName() {
        return name;
    }

    public Map<Field, TableField> getDeclaredFields() {
        return declaredFields;
    }

    public Collection<TableField> getFields(){
        return  declaredFields.values();
    }

    @Override
    public Containable<T> get() throws SQLException {
        // TODO create prepared statement and return Row<T> (statement.getResultSet, this)
        return null;
    }

    @Override
    public Containable<T> get(ValueFilter<T> filter) throws SQLException {
        // TODO create prepared statement and return Row<T> (statement.getResultSet, this)
        return null;
    }

    @Override
    public ContainableCollection<T> select(ValueFilter<T> filter) throws SQLException {
        // TODO create prepared statement and return RowSet<T> (statement, this)
        return null;
    }

    public Table(Registrar registrar, Class<T> classObject) throws SQLException {
        this.registrar = registrar;
        this.classObject = classObject;
        this.name = classObject.getName().replaceAll("\\.", "_");
        primaryKey = new TableField(SURROGATE_KEY_NAME, FieldType.INTEGER);
        for(Field field: classObject.getDeclaredFields()){
            if(null == FieldType.typeOf(field.getType()))
                registrar.register(field.getType());
        }
        String sqlQuery = createTableQuery();
        Statement statement = registrar.getConnection().createStatement();
        statement.execute(sqlQuery);
        statement.close();
    }

    private String createTableQuery() {
        return "CREATE TABLE `" + name + "` ( `" + primaryKey.name + "` "
                + primaryKey.type + " PRIMARY KEY AUTO_INCREMENT);";
    }

}
