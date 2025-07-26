package com.adstart.domain_monitor.database.repositories;

import com.adstart.domain_monitor.database.entities.WebHookEntity;
import com.adstart.domain_monitor.database.repositories.jpa.WebHookJpaRepository;
import com.adstart.domain_monitor.domain.contracts.persistence.INotificationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class NotificationRepository implements INotificationRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationRepository.class);

    private WebHookJpaRepository webHookJpaRepository;

    @Override
    public void save(String callbackUrl) {
        final WebHookEntity webHookEntity = new WebHookEntity();
        webHookEntity.setCallbackUrl(callbackUrl);

        webHookJpaRepository.save(webHookEntity);

        LOGGER.info("Saved web hook for {}", webHookEntity.getCallbackUrl());
    }

    @Override
    public List<String> getAllUrls() {
        final List<WebHookEntity> webHookEntities = webHookJpaRepository.findAll();

        return webHookEntities.stream().map(WebHookEntity::getCallbackUrl).collect(Collectors.toList());
    }
}
