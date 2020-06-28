package ru.otus.bestorm.impl;

import java.util.HashMap;
import java.util.Map;

public enum FieldType {
    INTEGER,
    BYTE,
    SHORT,
    LONG,
    DOUBLE,
    FLOAT,
    CHARACTER,
    STRING
    ;

    private static Map<Class<?>, FieldType> mapa = new HashMap<>();
    static {
        mapa.put(int.class, INTEGER);
        mapa.put(Integer.class, INTEGER);
        mapa.put(long.class, LONG);
        mapa.put(Long.class, LONG);
        mapa.put(byte.class, BYTE);
        mapa.put(Byte.class, BYTE);
        mapa.put(short.class, SHORT);
        mapa.put(Short.class, SHORT);
        mapa.put(double.class, DOUBLE);
        mapa.put(Double.class, DOUBLE);
        mapa.put(float.class, FLOAT);
        mapa.put(Float.class, FLOAT);
        mapa.put(char.class, CHARACTER);
        mapa.put(Character.class, CHARACTER);
        mapa.put(boolean.class, BYTE);
        mapa.put(Boolean.class, BYTE);
        mapa.put(String.class, STRING);
    }

    public static FieldType typeOf(Class<?> type){
        return mapa.get(type);
    }

    @Override
    public String toString() {
        switch (this){
            case INTEGER:
                return "INT";
            case LONG:
                return "BIGINT";
            case BYTE:
                return "TINYINT";
            case SHORT:
                return "SMALLINT";
            case DOUBLE:
                return "DOUBLE";
            case FLOAT:
                return "FLOAT";
            case CHARACTER:
                return "TINYTEXT";
            case STRING:
                return "TEXT";
        }
        return null;
    }
}