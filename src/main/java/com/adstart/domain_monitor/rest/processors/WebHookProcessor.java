package com.adstart.domain_monitor.rest.processors;

import com.adstart.domain_monitor.domain.contracts.notification.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebHookProcessor implements IWebHookProcessor {
    private final INotificationService notificationService;

    @Override
    public void save(String callbackUrl) {
        notificationService.save(callbackUrl);
    }
}
