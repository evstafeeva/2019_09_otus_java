package ru.otus.objects;

import java.util.*;

public class Person {
    static Integer a= 0;
    private String name;
    private int agePrimitive;
    private Integer ageObject;
    private Boolean isWorking;
    private Phone phone;
    private int[] arrayInt;
    private List<Integer> list;
    private Map<Integer, String> map;
    private Set<Integer> set;

    public Person(String name, int agePrimitive, Integer ageObject, Boolean isWorking, Phone phone, int[] arrayInt) {
        this.name = name;
        this.agePrimitive = agePrimitive;
        this.ageObject = ageObject;
        this.isWorking = isWorking;
        this.phone = phone;
        this.arrayInt = arrayInt;
        list = new ArrayList<>();
        map = new HashMap<>();
        set = new HashSet<>();
    }

    public void addToList(Integer value){
        list.add(value);
    }

    public void addToMap(Integer integer, String string){
        map.put(integer, string);
    }

    public void addToSet(Integer integer){
        set.add(integer);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", agePrimitive=" + agePrimitive +
                ", ageObject=" + ageObject +
                ", isWorking=" + isWorking +
                ", phone=" + phone +
                ", arrayInt=" + Arrays.toString(arrayInt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return agePrimitive == person.agePrimitive &&
                Objects.equals(name, person.name) &&
                Objects.equals(ageObject, person.ageObject) &&
                Objects.equals(isWorking, person.isWorking) &&
                Objects.equals(phone, person.phone) &&
                Arrays.equals(arrayInt, person.arrayInt) &&
                Objects.equals(list, person.list) &&
                Objects.equals(map, person.map) &&
                Objects.equals(set, person.set);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, agePrimitive, ageObject, isWorking, phone, list, map, set);
        result = 31 * result + Arrays.hashCode(arrayInt);
        return result;
    }
}
