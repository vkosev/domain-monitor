package com.adstart.domain_monitor.rest.models.request;

import java.time.LocalDateTime;


public class ExpirationNotificationRequest {
    private String domain;
    private LocalDateTime expirationDate;
}
