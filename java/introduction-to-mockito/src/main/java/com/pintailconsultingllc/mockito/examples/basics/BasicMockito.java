package com.pintailconsultingllc.mockito.examples.basics;

/**
 * Example class that acts as a system under test.
 */
public class BasicMockito {

    private final BasicMockitoDependency basicMockitoDependency;

    public BasicMockito(final BasicMockitoDependency basicMockitoDependency) {
        this.basicMockitoDependency = basicMockitoDependency;
    }

    public String doSomething() {
        return basicMockitoDependency.doSomethingDelegated();
    }

    public void doSomethingAsync(String eventName) {
        basicMockitoDependency.fireEvent(eventName);
    }
}
