package ru.otus;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DIYGson {

    //JsonObject jsonObject;

    public String makeJson(Object obj) throws IllegalAccessException {
        return objToJson(obj).toString();
    }

    private JsonValue objToJson(Object obj) throws IllegalAccessException {
        //если объект null возвращаем null
        if (obj == null)
            return JsonValue.NULL;
        //получаю класс объекта
        Class<?> objClass = obj.getClass();
        //массив
        if (objClass.isArray()) {
            return arrayToJson(obj);
        }
        //коллекции (set, list)
        if (obj instanceof Collection) {
            return arrayToJson(((Collection) obj).toArray());
        }
        //мапы
        if (obj instanceof Map) {
            return mapToJson(obj);
        }
        //примитивные типы
        if (objClass.isPrimitive() || obj instanceof Character || obj instanceof Boolean
                || obj instanceof Number || obj instanceof String) {
            return Json.createValue(String.valueOf(obj));
        }
        //другой объект
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            //работаем с приватныйми полями
            field.setAccessible(true);
            //проверяем на static
            if (!Modifier.isStatic(field.getModifiers())) {
                Object o = field.get(obj);
                jsonObjectBuilder.add(field.getName(), objToJson(o));
            }
        }
        return jsonObjectBuilder.build();
    }

    private JsonValue stringToJson(Object obj) {
        return Json.createValue((String)obj);
    }

    private JsonValue mapToJson(Object obj) throws IllegalAccessException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Map map = (Map) obj;
        Object[] mapKeys = map.keySet().toArray();
        for(int i = 0; i < mapKeys.length; i++){
            jsonObjectBuilder.add(mapKeys[i].toString(), objToJson(map.get(mapKeys[i])));
        }
        return jsonObjectBuilder.build();
    }

    private JsonValue arrayToJson(Object obj) throws IllegalAccessException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(int i = 0; i < Array.getLength(obj); i++){
            jsonArrayBuilder.add(objToJson(Array.get(obj, i)));
        }
        return jsonArrayBuilder.build();
    }
}
