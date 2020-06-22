package ru.otus.testFramework;

import lombok.Getter;
import lombok.Setter;
import ru.otus.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@Getter
public class TestClass {
    @Setter
    private Class<?> clazz;
    private final List<Method> beforeAllMethods = new ArrayList<>();
    private final List<Method> afterAllMethods = new ArrayList<>();
    private final List<TestCombination> testCombinations = new ArrayList<>();
    private final List<Exception> beforeAllExceptions = new ArrayList<>();
    private final List<Exception> afterAllExceptions = new ArrayList<>();

    public TestClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void makeCombinations(){
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        for(Method method : clazz.getMethods()){
            if(method.isAnnotationPresent(After.class)){
                afterMethods.add(method);
            }
            if(method.isAnnotationPresent(AfterAll.class)){
                afterAllMethods.add(method);
            }
            if(method.isAnnotationPresent(Before.class)){
                beforeMethods.add(method);
            }
            if(method.isAnnotationPresent(BeforeAll.class)){
                beforeAllMethods.add(method);
            }
            if(method.isAnnotationPresent(Test.class)){
                testMethods.add(method);
            }
        }
        for(Method testMethod : testMethods){
            TestCombination testCombination = new TestCombination(testMethod, beforeMethods, afterMethods);
            testCombinations.add(testCombination);
        }
    }
}
