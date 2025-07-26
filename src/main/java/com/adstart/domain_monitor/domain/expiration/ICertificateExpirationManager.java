package com.adstart.domain_monitor.domain.expiration;

import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface ICertificateExpirationManager {
    LocalDateTime getDomainExpirationDate(String url);
    Boolean isExpired(LocalDateTime expirationDate, int threshold);
    int daysLeft(LocalDateTime expirationDate);
    List<ExpirationCheckRecord> checkForExpirations();
    Boolean exceedsThreshold(LocalDateTime expirationDate, int threshold);
}
