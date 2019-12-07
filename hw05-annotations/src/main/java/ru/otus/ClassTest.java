package ru.otus;

import ru.otus.annotations.*;

public class ClassTest {
    @BeforeAll
    public static void method1(){
    }
    @BeforeAll
    public static void method2() throws Exception {
        throw new Exception("ОШИБКА");
    }
    @Before
    public void method3(){
    }
    @After
    public void method4(){
    }
    @After
    public void method9(){
        //throw new NullPointerException("after 2");
    }
    @AfterAll
    public static void method5() throws Exception {
        //throw new Exception("ОШИБКА5");
    }
    @AfterAll
    public static void method51() throws Exception {
        throw new Exception("ОШИБКА51");
    }
    @AfterAll
    public static void method52() throws Exception {
        //throw new Exception("ОШИБКА52");
    }
    @Test
    public void method6(){
        throw new IllegalArgumentException();
    }
    @Test
    public void method7(){

    }
    @Test
    public void method8(){

    }
    @Test
    public void method10() throws Exception {
        throw new Exception("Exception in test 10");
    }
    @Test
    public void method11(){

    }
    @Test
    public void method12(){
    }
}
