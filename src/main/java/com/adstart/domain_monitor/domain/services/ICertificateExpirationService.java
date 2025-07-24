package com.adstart.domain_monitor.domain.services;

import java.time.LocalDateTime;

public interface ICertificateExpirationService {
    LocalDateTime getDomainExpirationDate(String url);
}
