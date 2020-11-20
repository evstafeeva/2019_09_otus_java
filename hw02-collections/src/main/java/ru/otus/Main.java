package ru.otus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Integer> array = new DIYarrayList<>();
        array.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,12,13,14,15,16,17,78,89,90,100));
        System.out.println("Исходный список: " + Arrays.toString(array.toArray()));

        Collections.addAll(array, 5, 6, 7, 8, 9, 10, 11);
        System.out.println("\nДобавление в список элементов с помощью Collections.addAll(Collection<? super T> c, T... elements): [5, 6, 7, 8, 9, 10, 11]" );
        System.out.println("Результат: " + Arrays.toString(array.toArray()));
        List<Integer> src = new DIYarrayList<>();
        src.addAll(Arrays.asList(120,9,115));
        Collections.copy(array, src);
        System.out.println("\nКопирование в исходный список другого списка с помощью Collections.static <T> void copy(List<? super T> dest, List<? extends T> src):"+ Arrays.toString(src.toArray()));
        System.out.println("Результат: " + Arrays.toString(array.toArray()));

        Collections.sort(array);
        System.out.println("\nСортировка списка с помощью Collections.static <T> void sort(List<T> list, Comparator<? super T> c).");
        System.out.println("Результат: " + Arrays.toString(array.toArray()));

    }
}
