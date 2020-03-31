package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Id;
import ru.otus.requestWriter.RequestWriter;
import ru.otus.requestWriter.SQLRequestWriter;
import ru.otus.sessionmanager.SessionManager;
import ru.otus.executor.DbExecutor;
import ru.otus.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class GenericDao<T> implements Dao<T> {
    private static Logger logger = LoggerFactory.getLogger(GenericDao.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private final RequestWriter<T> requestWriter = new SQLRequestWriter<>();

    public GenericDao(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<T> findById(long id, Class<T> clazz) {
        try {
            return dbExecutor.selectRecord(getConnection(), requestWriter.selectById(id, clazz), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        //создать объект класса с помощью рефлексии
                        try {
                            T t = clazz.newInstance();
                            for(Field field:clazz.getDeclaredFields()){
                                field.setAccessible(true);
                                field.set(t, resultSet.getObject(field.getName()));
                            }
                            return t;
                        } catch (InstantiationException e) {
                            logger.error(e.getMessage(), e);
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long save(T object) {
        try {
            return dbExecutor.insertRecord(getConnection(), requestWriter.insert(object));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    public long update(T object){
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            long idField = 0;
            for(Field field:fields){
                field.setAccessible(true);
                if(field.isAnnotationPresent(Id.class)) {
                    idField = field.getLong(object);
                }
            }
            return dbExecutor.updateRecord(getConnection(), requestWriter.updateById(object), idField);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
