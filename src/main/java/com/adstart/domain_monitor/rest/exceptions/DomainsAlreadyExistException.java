package com.adstart.domain_monitor.rest.exceptions;

public class DomainsAlreadyExistException extends RuntimeException {

    public DomainsAlreadyExistException(String message) {
        super(message);
    }
}
