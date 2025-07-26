package com.adstart.domain_monitor.rest;

import com.adstart.domain_monitor.domain.exceptions.FailedCertificateExtractionException;
import com.adstart.domain_monitor.rest.exceptions.DomainsAlreadyExistException;
import com.adstart.domain_monitor.rest.exceptions.InvalidDomainsException;
import com.adstart.domain_monitor.rest.models.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(FailedCertificateExtractionException.class)
    public ResponseEntity<ErrorResponse> handleFailedExtractException(Exception exception) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Failed certificate extraction")
                .timestamp(LocalDateTime.now())
                .errorType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Internal Server Error")
                .timestamp(LocalDateTime.now())
                .errorType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();

        LOGGER.error(exception.getMessage(), exception);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDomainsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDomainsException(Exception exception) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .errorType(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainsAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleDomainAlreadyExistException(Exception exception) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .errorType(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
