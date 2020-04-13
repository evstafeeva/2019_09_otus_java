package ru.otus;

import ru.otus.testFramework.NotTestClassException;
import ru.otus.testFramework.TestStarter;

public class Main {
    public static void main(String[] args) throws Exception {
        try{
            TestStarter.start("ru.otus.Class1Test");
        }catch (ClassNotFoundException e){
            System.out.println("Класс не найдет!");
        }
        try{
            TestStarter.start("ru.otus.ClassTest1");
        }catch (NotTestClassException e){
            System.out.println("Класс не является тестовым!");
        }
        TestStarter.start("ru.otus.ClassTest");
    }
}
