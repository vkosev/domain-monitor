package com.adstart.domain_monitor.domain.exceptions;

public class PersistenceFailureException extends RuntimeException {

    public PersistenceFailureException(String message) {
        super(message);
    }

    public PersistenceFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
