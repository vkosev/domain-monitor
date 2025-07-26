package com.adstart.domain_monitor;

import com.adstart.domain_monitor.domain.expiration.ICertificateExpirationManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledJobs {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledJobs.class);

    private final ICertificateExpirationManager certificateExpirationManager;

    @Scheduled(cron = "${scheduler.time}")
    public void checkForCertificatesExpirations() {
        LOGGER.info("Starting certificates expirations scheduled job");
        certificateExpirationManager.checkForExpirations();
    }

}
