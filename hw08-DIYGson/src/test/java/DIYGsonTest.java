import com.google.gson.Gson;
import org.junit.Test;
import ru.otus.DIYGson;
import ru.otus.objects.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class DIYGsonTest {

    DIYGson diyGson = new DIYGson();
    Gson gson = new Gson();

    @Test
    public void oneTest() throws IllegalAccessException {
        One one = new One(1);
        String s = diyGson.makeJson(one);
        System.out.println(s);
        One one1 = gson.fromJson(s, One.class);
        assert one.equals(one1);
    }

    @Test
    public void threeTest() throws IllegalAccessException {
        Three three = new Three(1, 2, new Two(3, 4, new One(5)));
        String s = diyGson.makeJson(three);
        System.out.println(s);
        Three three1 = gson.fromJson(s, Three.class);
        assert three.equals(three1);
    }

    @Test
    public void staticTest() throws IllegalAccessException {
        Stat stat = new Stat();
        String s = diyGson.makeJson(stat);
        System.out.println(s);
        Stat stat1 = gson.fromJson(s, Stat.class);
        System.out.println(gson.toJson(stat));
    }

    @Test
    public void personTest() throws IllegalAccessException {
        Person person = new Person("Alena", 19, 19, false, new Phone(89_111_952, "MTC"), new int[]{1, 2, 3});
        person.addToList(1);
        person.addToList(2);
        person.addToList(3);
        person.addToList(4);
        person.addToMap(1, "a");
        person.addToMap(2, "b");
        person.addToMap(3, "c");
        person.addToSet(11);
        person.addToSet(12);
        person.addToSet(13);
        person.addToSet(14);
        String s = diyGson.makeJson(person);
        System.out.println(s);
        Person person1 = gson.fromJson(s, Person.class);
        assert person.equals(person1);
    }

    @Test
    public void mapTest() throws IllegalAccessException {
        Map<Integer, Integer> mapa = new HashMap<>();
        mapa.put(1, 1);
        mapa.put(2, 2);
        mapa.put(3,3);
        String s = diyGson.makeJson(mapa);
        System.out.println(s);
        Map mapa1 = gson.fromJson(s, Map.class);
        System.out.println(mapa);
        System.out.println(mapa1);
    }

    @Test
    public void listTest() throws IllegalAccessException {
        List<Three> threeList = new LinkedList<>();
        threeList.add(new Three(1, 2, new Two(3, 4, new One(5))));
        threeList.add(new Three(11, 22, new Two(33, 44, new One(55))));
        threeList.add(new Three(111, 222, new Two(333, 444, new One(555))));
        threeList.add(new Three(1111, 2222, new Two(3333, 4444, new One(5555))));

        String s = diyGson.makeJson(threeList);
        System.out.println(s);
        List<Three> threeList1 = gson.fromJson(s, List.class);
        System.out.println(threeList);
        System.out.println(threeList1);
    }

    @Test
    public void nullTest() throws IllegalAccessException {
        String s = diyGson.makeJson(null);
        assert s.equals("null");
    }

    @Test
    public void primitiveTest() throws IllegalAccessException{
        Integer integer = 1;
        String s = diyGson.makeJson(integer);
        System.out.println(s);
        Integer integer1 = gson.fromJson(s, Integer.class);
        assert integer.equals(integer1);

        String string = "aaa";
        s = diyGson.makeJson(string);
        System.out.println(s);
        String string1 = gson.fromJson(s, String.class);
        assert string.equals(string1);

        char char1 = '1';
        s = diyGson.makeJson(char1);
        System.out.println(s);
        char char2 = gson.fromJson(s, char.class);
        assert char1 == char2;

        long long1 = 225l;
        s = diyGson.makeJson(long1);
        System.out.println(s);
        long long2 = gson.fromJson(s, long.class);
        assert long1 == long2;
    }

    @Test
    public void manyDifferentTests() throws IllegalAccessException {
        Gson gson = new Gson();
        DIYGson serializer = new DIYGson();

        assertEquals(gson.toJson(null), serializer.makeJson(null));
        assertEquals(gson.toJson((byte)1), serializer.makeJson((byte)1));
        assertEquals(gson.toJson((short)1f), serializer.makeJson((short)1f));
        assertEquals(gson.toJson(1), serializer.makeJson(1));
        assertEquals(gson.toJson(1L), serializer.makeJson(1L));
        assertEquals(gson.toJson(1f), serializer.makeJson(1f));
        assertEquals(gson.toJson(1d), serializer.makeJson(1d));
        assertEquals(gson.toJson("aaa"), serializer.makeJson("aaa"));
        assertEquals(gson.toJson('a'), serializer.makeJson('a'));
        assertEquals(gson.toJson(new int[] {1, 2, 3}), serializer.makeJson(new int[] {1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2 ,3)), serializer.makeJson(List.of(1, 2 ,3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), serializer.makeJson(Collections.singletonList(1)));
    }
}
