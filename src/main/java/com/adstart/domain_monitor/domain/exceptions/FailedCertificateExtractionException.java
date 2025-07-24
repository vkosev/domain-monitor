package com.adstart.domain_monitor.domain.exceptions;

public class FailedCertificateExtractionException extends RuntimeException {
    public FailedCertificateExtractionException(String msg, Throwable e) {
        super(msg, e);
    }

    public FailedCertificateExtractionException(String msg) {
        super(msg);
    }
}
