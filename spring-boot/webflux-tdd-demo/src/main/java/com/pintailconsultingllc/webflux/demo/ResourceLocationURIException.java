package com.pintailconsultingllc.webflux.demo;

import java.net.URISyntaxException;

public class ResourceLocationURIException extends RuntimeException {

    public ResourceLocationURIException(URISyntaxException e) {
        super(e);
    }
}
