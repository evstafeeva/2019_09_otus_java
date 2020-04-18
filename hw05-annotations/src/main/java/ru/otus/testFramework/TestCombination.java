package ru.otus.testFramework;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
class TestCombination {
    private List<Method> beforeMethods;
    private Method testMethod;
    private List<Method> afterMethods;
    private Boolean successful = true;
    private List<Exception> e = new ArrayList<Exception>();

    TestCombination(Method testMethod, List<Method> beforeMethods, List<Method> afterMethods) {
        this.beforeMethods = beforeMethods;
        this.testMethod = testMethod;
        this.afterMethods = afterMethods;
    }

    void startTest(Object instance) throws Exception {
        try {
            for (Method beforeMethod : beforeMethods) {
                beforeMethod.invoke(instance);
            }
            testMethod.invoke(instance);
        } catch (Exception e) {
            this.e.add(e);
            successful = false;
        } finally {
            for (Method afterMethod : afterMethods) {
                try {
                    afterMethod.invoke(instance);
                } catch (Exception e) {
                    this.e.add(e);
                    successful = false;
                }
            }
        }
    }

    void statistic() {
        System.out.print("Test: " + testMethod.getName());
        System.out.print(successful ? " PASSED\n" : " FAILED with exception: \n");
        e.forEach((e) -> System.out.println("    "+e.getCause() + "; "));
    }

    void addException(Exception exception){
        this.e.add(exception);
    }

}
