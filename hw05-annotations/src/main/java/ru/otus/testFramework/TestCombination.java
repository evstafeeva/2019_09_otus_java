package ru.otus.testFramework;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Getter
@Setter
public class TestCombination {
    private ArrayList<Method> beforeMethods = new ArrayList<Method>();
    private Method testMethod;
    private ArrayList<Method> afterMethods = new ArrayList<Method>();
    private boolean successful = true;
    private Exception e = null;

    public TestCombination(ArrayList<Method> beforeMethods, Method testMethod, ArrayList<Method> afterMethods){
        this.beforeMethods = beforeMethods;
        this.testMethod = testMethod;
        this.afterMethods = afterMethods;
    }

    public void startTest(Object instance) throws Exception{
        for(Method beforeMethod : beforeMethods){
            beforeMethod.invoke(instance);
        }
        try {
            testMethod.invoke(instance);
        }catch(Exception e){
            this.e = e;
            successful = false;
        }
        for(Method afterMethod : afterMethods){
            afterMethod.invoke(instance);
        }
    }

    public void statistic(){
        System.out.print("Test: "+ testMethod.getName());
        if(successful == true){
            System.out.print(" PASSED\n");
        }
        else{
            System.out.print(" FAILED with exception: " + e.getCause() + "\n");
        }
    }

    public boolean getSuccessful(){
        return successful;
    }

}
