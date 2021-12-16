package com.pintailconsultingllc.mockito.examples.argumentmatching;

public interface Response<T> {

    int getStatusCode();

    T getBody();
}
