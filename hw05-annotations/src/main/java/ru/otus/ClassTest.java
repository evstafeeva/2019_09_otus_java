package ru.otus;

import ru.otus.annotations.*;

public class ClassTest {
    @BeforeAll
    public static void mBeforeAll1() {
        System.out.println("before all 1");
    }

    @BeforeAll
    public static void mBeforeAll2() throws Exception {
        System.out.println("before all 2");
        //throw new Exception("ОШИБКА");
    }

    @Before
    public void mBefore1() {
        System.out.println("  before 1");
    }

    @After
    public void mAfter1() {
        System.out.println("  after 1");
    }

    @After
    public void mAfter2() {
        System.out.println("  after 2");
        //throw new NullPointerException("after 2");
    }

    @AfterAll
    public static void mAfterAll1() throws Exception {
        System.out.println("after all 1");
        //throw new Exception("ОШИБКА5");
    }

    @AfterAll
    public static void mAfterAll2() throws Exception {
        System.out.println("after all 2");
        //throw new Exception("ОШИБКА51");
    }

    @AfterAll
    public static void mAfterAll3() throws Exception {
        System.out.println("after all 3");
        //throw new Exception("ОШИБКА52");
    }

    @Test
    public void method1() {
        System.out.println("    method 1");
        throw new IllegalArgumentException();
    }
    @Test
    public void method2(){
        System.out.println("    method 2");
    }
    @Test
    public void method3(){
        System.out.println("    method 3");
    }
    @Test
    public void method4() throws Exception {
        System.out.println("    method 4");
        throw new Exception("Exception in test 10");
    }
    @Test
    public void method5(){
        System.out.println("    method 5");
    }
    @Test
    public void method6(){
        System.out.println("    method 6");
    }
}
