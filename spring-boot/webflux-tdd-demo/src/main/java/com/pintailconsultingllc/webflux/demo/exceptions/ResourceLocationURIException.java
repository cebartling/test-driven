package com.pintailconsultingllc.webflux.demo.exceptions;

import java.net.URISyntaxException;

public class ResourceLocationURIException extends RuntimeException {

    public ResourceLocationURIException(URISyntaxException e) {
        super(e);
    }
}
