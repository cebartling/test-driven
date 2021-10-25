package com.pintailconsultingllc.mockito.examples;

public class MockedStaticMethodDemo {

    public static final String NAME = "foobar";

    private MockedStaticMethodDemo() {
    }

    public static String getName() {
        return NAME;
    }
}
