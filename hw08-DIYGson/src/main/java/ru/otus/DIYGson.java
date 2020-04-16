package ru.otus;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DIYGson {

    private static Map<Class<?>, Function<Object, JsonValue>> classesTransformers = new HashMap<>();
    static {
        classesTransformers.put(Integer.class, (object)->Json.createValue((Integer)object));
        classesTransformers.put(Long.class, (object)->Json.createValue((Long)object));
        classesTransformers.put(Byte.class, (object)->Json.createValue((Byte)object));
        classesTransformers.put(Short.class, (object)->Json.createValue((Short)object));
        classesTransformers.put(Double.class, (object)->Json.createValue((Double)object));
        classesTransformers.put(Float.class, (object)->Json.createValue((Float)object));
        classesTransformers.put(BigDecimal.class, (object)->Json.createValue((BigDecimal)object));
        classesTransformers.put(BigInteger.class, (object)->Json.createValue((BigInteger)object));
        classesTransformers.put(Character.class, (object)->Json.createValue(String.valueOf(object)));
        classesTransformers.put(Boolean.class, (object)->Json.createValue(String.valueOf(object)));
        classesTransformers.put(String.class, (object)->Json.createValue(String.valueOf(object)));
        classesTransformers.put(Object.class, (object)->null);
    }

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
        if (objClass.isArray())
            return arrayToJson(obj);
        //коллекции (set, list)
        if (obj instanceof Collection)
            return arrayToJson(((Collection) obj).toArray());
        //мапы
        if (obj instanceof Map)
            return mapToJson(obj);
        //функция специально для типов, от которых не унаследоваться

        Function<Object, JsonValue> foundFunction = classesTransformers.get(obj.getClass());
        if(foundFunction!= null){
            return foundFunction.apply(obj);
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