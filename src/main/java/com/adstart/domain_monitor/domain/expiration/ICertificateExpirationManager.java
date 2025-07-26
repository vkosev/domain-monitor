package com.adstart.domain_monitor.domain.expiration;

import java.time.LocalDateTime;

public interface ICertificateExpirationManager {
    LocalDateTime getDomainExpirationDate(String url);
    Boolean isExpired(LocalDateTime expirationDate, int threshold);
    int daysLeft(LocalDateTime expirationDate);
}
