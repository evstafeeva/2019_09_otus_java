package ru.otus.testFramework;

import java.lang.reflect.Method;

public class TestStarter {

    public static void start(String className) throws Exception {
        TestClass testClass = new TestClass(getClassFromName(className));
        testClass.makeCombinations();
        runTests(testClass);
        printStatistic(testClass);
        printFullInformation(testClass);
    }

    private static Class<?> getClassFromName(String className) throws NotTestClassException, ClassNotFoundException {
        if(!className.endsWith("Test"))
            throw new NotTestClassException();
        return Class.forName(className);
    }

    private static void runTests(TestClass testClass) throws Exception {
        Object instance = testClass.getClazz().getDeclaredConstructor().newInstance();
        try {
            for (Method beforeAllMethod : testClass.getBeforeAllMethods()) {
                beforeAllMethod.invoke(instance);
            }
            for (TestCombination testCombination : testClass.getTestCombinations()) {
                testCombination.startTest(instance);
            }
        } catch (Exception e) {
            //значит поймали исключение в beforeAll, тк в комбинациях исклюение ловится внутри
            testClass.getBeforeAllExceptions().add(e);
            testClass.getTestCombinations().forEach((testCombination) -> {
                testCombination.setSuccessful(false);
                testCombination.addException(e);
            });
        } finally {
            for (Method afterAllMethod : testClass.getAfterAllMethods()) {
                try {
                    afterAllMethod.invoke(instance);
                } catch (Exception e) {
                    testClass.getAfterAllExceptions().add(e);
                }
            }
        }
    }

    private static void printStatistic(TestClass testClass) {
        System.out.println("\nAll tests: " + testClass.getTestCombinations().size());
        int passedTest = 0;
        for (TestCombination testCombination : testClass.getTestCombinations()) {
            if (testCombination.getSuccessful())
                passedTest++;
        }
        System.out.println("Passed: " + passedTest);
        System.out.println("Failed: " + (testClass.getTestCombinations().size() - passedTest));
        System.out.println("\nInformation about all tests:");

    }

    private static void printFullInformation(TestClass testClass) {
        if (!testClass.getBeforeAllExceptions().isEmpty()) {
            System.out.println("BeforeAll method(s) FAILED with exception: ");
            testClass.getBeforeAllExceptions().forEach((e) -> System.out.println("    " + e.getCause() + "; "));
        }
        for (TestCombination testCombination : testClass.getTestCombinations()) {
            testCombination.statistic();
        }
        if (!testClass.getAfterAllExceptions().isEmpty()) {
            System.out.println("AfterAll method(s) FAILED with exception: ");
            testClass.getAfterAllExceptions().forEach((e) -> System.out.println("    " + e.getCause() + "; "));
        }
    }
}
