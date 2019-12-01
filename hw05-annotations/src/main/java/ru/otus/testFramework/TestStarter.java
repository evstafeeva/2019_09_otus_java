package ru.otus.testFramework;

import ru.otus.annotations.*;
import ru.otus.testFramework.TestClass;
import ru.otus.testFramework.TestCombination;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestStarter {
    //private String className;
    private static Class<?> clazz;
    private static ArrayList<TestCombination> testCombinations = new ArrayList<TestCombination>();
    private static int passedTest;
    private static int failedTest;


    public static void start(String className) throws Exception {
        if(className.endsWith("Test")){
            try{
                clazz = Class.forName(className);
            }catch(ClassNotFoundException e){
                System.out.println("Class \"" + className + "\" not found!");
                return;
            }
        }
        else{
            System.out.println("This class is not test class!");
            return;
        }

        //sort methods
        TestClass testClass = new TestClass();
        for(Method method : clazz.getMethods()){
            if(method.isAnnotationPresent(After.class)){
                testClass.addAfterMethods(method);
            }
            if(method.isAnnotationPresent(AfterAll.class)){
                testClass.addAfterAllMethods(method);
            }
            if(method.isAnnotationPresent(Before.class)){
                testClass.addBeforeMethod(method);
            }
            if(method.isAnnotationPresent(BeforeAll.class)){
                testClass.addBeforeAllMethods(method);
            }
            if(method.isAnnotationPresent(Test.class)){
                testClass.addTestMethods(method);
            }
        }

        //make combination before-test-after
        for(Method testMethod : testClass.getTestMethods()){
            TestCombination testCombination = new TestCombination(testClass.getBeforeMethods(), testMethod, testClass.getAfterMethods());
            testCombinations.add(testCombination);
        }

        //run tests
        Object instance = clazz.getDeclaredConstructor().newInstance();
        for(Method beforeAllMethod : testClass.getBeforeAllMethods()){
            beforeAllMethod.invoke(instance);
        }
        for(TestCombination testCombination : testCombinations){
            testCombination.startTest(instance);
        }
        for(Method afterAllMethod : testClass.getAfterAllMethods()){
            afterAllMethod.invoke(instance);
        }

        //statistic
        System.out.println("\nAll tests: " + testCombinations.size());
        for(TestCombination testCombination:testCombinations){
            if(testCombination.getSuccessful())
                passedTest++;
            else
                failedTest++;
        }
        System.out.println("Passed: " + passedTest);
        System.out.println("Failed: " + failedTest);
        System.out.println("\nInformation about all tests:");
        for (TestCombination testCombination:testCombinations){
            testCombination.statistic();
        }
    }
}
