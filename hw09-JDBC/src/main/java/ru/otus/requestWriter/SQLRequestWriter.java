package ru.otus.requestWriter;

import ru.otus.model.Id;

import java.lang.reflect.Field;

public class SQLRequestWriter<T> implements RequestWriter<T> {
    @Override
    public String selectById(long id, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String idField = null;
        //ищем ID поле
        //не делаем исключение на случай отсутствия аннотации,
        //так как по заданию она должна быть
        for(Field field:fields){
            if(field.isAnnotationPresent(Id.class)) {
                idField = field.getName();
                break;
            }
        }
        return "SELECT * FROM " + clazz.getSimpleName() + " WHERE " + idField + " =  ?";
    }

    @Override
    public String insert(T object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder fieldsNames = new StringBuilder();
        StringBuilder fieldsValues = new StringBuilder();

        for(Field field:fields){
            fieldsNames.append(field.getName()).append(",");
            field.setAccessible(true);
            if(field.getType().equals(String.class)){
                String value = (String) field.get(object);
                fieldsValues.append("'").append(value).append("',");
            }else{
                fieldsValues.append(field.get(object).toString()).append(",");
            }
        }
        fieldsNames.deleteCharAt(fieldsNames.length()-1);
        fieldsValues.deleteCharAt(fieldsValues.length()-1);
        return "INSERT INTO " + object.getClass().getSimpleName() + "(" + fieldsNames.toString()+") VALUES (" + fieldsValues.toString()+")";
    }

    @Override
    public String updateById(T object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder fieldsValues = new StringBuilder();
        String idField = null;

        for(Field field:fields){
            if(field.isAnnotationPresent(Id.class) && idField == null) {
                idField = field.getName();
            }
            field.setAccessible(true);
            if(field.getType().equals(String.class)){
                String value = (String) field.get(object);
                fieldsValues.append(field.getName() + " = '" + field.get(object).toString()+"',");
            }else{
                fieldsValues.append(field.getName() + " = " + field.get(object).toString() + ",");
            }
        }
        fieldsValues.deleteCharAt(fieldsValues.length()-1);

        return "UPDATE " + object.getClass().getSimpleName() + " SET " + fieldsValues.toString() + " WHERE " + idField + " = ?";
    }
}
