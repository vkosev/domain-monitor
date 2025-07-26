package com.adstart.domain_monitor.domain.contracts.persistence;

import java.util.List;

public interface INotificationRepository {

    void save(String callbackUrl);

    List<String> getAllUrls();
}
