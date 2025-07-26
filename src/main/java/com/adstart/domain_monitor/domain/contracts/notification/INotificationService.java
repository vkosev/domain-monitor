package com.adstart.domain_monitor.domain.contracts.notification;

import java.time.LocalDateTime;

public interface INotificationService {
    void notifyAllRegisters(String domain, LocalDateTime expirationDate, int threshold);
    void save(String callbackUrl);
}
