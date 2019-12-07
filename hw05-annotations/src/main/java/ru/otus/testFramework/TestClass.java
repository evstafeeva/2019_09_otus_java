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
    private ArrayList<Method> beforeMethods = new ArrayList<Method>();
    private ArrayList<Method> afterMethods = new ArrayList<Method>();
    private ArrayList<Method> beforeAllMethods = new ArrayList<Method>();
    private ArrayList<Method> afterAllMethods = new ArrayList<Method>();
    private ArrayList<Method> testMethods = new ArrayList<Method>();
    private ArrayList<TestCombination> testCombinations = new ArrayList<TestCombination>();
    private List<Exception> beforeAllExceptions = new ArrayList<>();
    private List<Exception> afterAllExceptions = new ArrayList<>();


    void addBeforeMethod(Method method){
        beforeMethods.add(method);
    }
    void addAfterMethods(Method method){
        afterMethods.add(method);
    }
    void addBeforeAllMethods(Method method){
        beforeAllMethods.add(method);
    }
    void addAfterAllMethods(Method method){
        afterAllMethods.add(method);
    }
    void addTestMethods(Method method){
        testMethods.add(method);
    }

    void initFrom(){
        for(Method method : clazz.getMethods()){
            if(method.isAnnotationPresent(After.class)){
                this.addAfterMethods(method);
            }
            if(method.isAnnotationPresent(AfterAll.class)){
                this.addAfterAllMethods(method);
            }
            if(method.isAnnotationPresent(Before.class)){
                this.addBeforeMethod(method);
            }
            if(method.isAnnotationPresent(BeforeAll.class)){
                this.addBeforeAllMethods(method);
            }
            if(method.isAnnotationPresent(Test.class)){
                this.addTestMethods(method);
            }
        }
    }

    void makeCombinations() {
        for(Method testMethod : testMethods){
            TestCombination testCombination = new TestCombination(this, testMethod);
            testCombinations.add(testCombination);
        }
    }

}
