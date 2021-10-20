package com.pintailconsultingllc.mockito.examples;

/**
 * Example class that acts as a system under test.
 */
public class BasicMockito {

    private final BasicMockitoDependency basicMockitoDependency;

    public BasicMockito(final BasicMockitoDependency basicMockitoDependency) {
        this.basicMockitoDependency = basicMockitoDependency;
    }

    public void doSomething() {
        basicMockitoDependency.doSomethingDelegated();
    }
}
