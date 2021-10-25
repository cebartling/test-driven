package com.pintailconsultingllc.mockito.examples;

public class MockingStaticMethodDemo {

    public static final String NAME = "foobar";

    private MockingStaticMethodDemo() {
    }

    public static String getName() {
        return NAME;
    }
}
