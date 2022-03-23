package com.pintailconsultingllc.webflux.demo.webclients;

public class WebServiceException extends RuntimeException {

    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceException(String message) {
        super(message);
    }
}
