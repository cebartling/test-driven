package com.pintailconsultingllc.exceptions;

public class ApiException extends RuntimeException {

    public ApiException(Exception e) {
        super(e);
    }
}
