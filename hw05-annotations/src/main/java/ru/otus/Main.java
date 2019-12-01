package ru.otus;

import ru.otus.testFramework.TestStarter;

public class Main {
    public static void main(String[] args) throws Exception {
        TestStarter.start("ru.otus.Class1Test");
        TestStarter.start("ru.otus.ClassTest1");
        TestStarter.start("ru.otus.ClassTest");
    }

}
