package ru.otus;

import ru.otus.annotations.*;

public class ClassTest {
    @BeforeAll
    public void method1(){
        System.out.println("BeforeAll 1");
    }
    @BeforeAll
    public void method2(){
        System.out.println("BeforeAll 2");
    }
    @Before
    public void method3(){
        System.out.println("Before 1");
    }
    @After
    public void method4(){
        System.out.println("after 1");
    }
    @After
    public void method9(){
        System.out.println("after 2");
    }
    @AfterAll
    public void method5(){
        System.out.println("after All 1");
    }
    @Test
    public void method6(){
        System.out.println("Test 1");
        throw new IllegalArgumentException();
    }
    @Test
    public void method7(){
        System.out.println("Test 2");
    }
    @Test
    public void method8(){
        System.out.println("Test 3");
    }
    @Test
    public void method10() throws Exception {
        System.out.println("Test 10");
        throw new Exception("Exception in test 10");
    }
    @Test
    public void method11(){
        System.out.println("Test 11");
    }
    @Test
    public void method12(){
        System.out.println("Test 12");
    }
}
