package ru.otus.testFramework;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;



@Getter
public class TestClass {
    private ArrayList<Method> beforeMethods = new ArrayList<Method>();
    private ArrayList<Method> afterMethods = new ArrayList<Method>();
    private ArrayList<Method> beforeAllMethods = new ArrayList<Method>();
    private ArrayList<Method> afterAllMethods = new ArrayList<Method>();
    private ArrayList<Method> testMethods = new ArrayList<Method>();

    public void addBeforeMethod(Method method){
        beforeMethods.add(method);
    }
    public void addAfterMethods(Method method){
        afterMethods.add(method);
    }
    public void addBeforeAllMethods(Method method){
        beforeAllMethods.add(method);
    }
    public void addAfterAllMethods(Method method){
        afterAllMethods.add(method);
    }
    public void addTestMethods(Method method){
        testMethods.add(method);
    }
}
