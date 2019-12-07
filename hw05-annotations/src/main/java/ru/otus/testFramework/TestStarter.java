package ru.otus.testFramework;

import java.lang.reflect.Method;

public class TestStarter {
    private static TestClass testClass;

    public static void start(String className) throws Exception {
        testClass = new TestClass();
        if (!CheckName(className))
            return;
        testClass.initFrom();
        testClass.makeCombinations();
        runTests();
        printStatistic();
        printFullInformation();
    }

    private static boolean CheckName(String className) {
        if(className.endsWith("Test")){
            try{
                testClass.setClazz(Class.forName(className));
            }catch(ClassNotFoundException e){
                System.out.println("Class \"" + className + "\" not found!");
                return false;
            }
        }
        else{
            System.out.println("This class is not test class!");
            return false;
        }
        return true;
    }

    private static void runTests() throws Exception {
        Object instance = testClass.getClazz().getDeclaredConstructor().newInstance();
        try {
            for (Method beforeAllMethod : testClass.getBeforeAllMethods()) {
                beforeAllMethod.invoke(instance);
            }
            for (TestCombination testCombination : testClass.getTestCombinations()) {
                testCombination.startTest(instance);
            }
        }catch (Exception e){
            //значит поймали исключение в beforeAll, тк в комбинациях исклюение ловится внутри
            testClass.getBeforeAllExceptions().add(e);
            testClass.getTestCombinations().forEach((testCombination)->{testCombination.setSuccessful(false); testCombination.addException(e);});
        }finally {
            for (Method afterAllMethod : testClass.getAfterAllMethods()) {
                try {
                    afterAllMethod.invoke(instance);
                }catch(Exception e){
                    testClass.getAfterAllExceptions().add(e);
                }
            }
        }
    }

    private static void printStatistic() {
        System.out.println("\nAll tests: " + testClass.getTestCombinations().size());
        int passedTest = 0;
        for(TestCombination testCombination:testClass.getTestCombinations()){
            if(testCombination.getSuccessful())
                passedTest++;
        }
        System.out.println("Passed: " + passedTest);
        System.out.println("Failed: " + (testClass.getTestCombinations().size()-passedTest));
        System.out.println("\nInformation about all tests:");

    }

    private static void printFullInformation() {
        if(!testClass.getBeforeAllExceptions().isEmpty()){
            System.out.println("BeforeAll method(s) FAILED with exception: ");
            testClass.getBeforeAllExceptions().forEach((e) -> System.out.println("    " + e.getCause() + "; "));
        }
        for (TestCombination testCombination:testClass.getTestCombinations()){
            testCombination.statistic();
        }
        if(!testClass.getAfterAllExceptions().isEmpty()){
            System.out.println("AfterAll method(s) FAILED with exception: ");
            testClass.getAfterAllExceptions().forEach((e) -> System.out.println("    " + e.getCause() + "; "));
        }
    }
}
