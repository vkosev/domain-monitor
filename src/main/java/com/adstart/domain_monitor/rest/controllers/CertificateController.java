package com.adstart.domain_monitor.rest.controllers;

import com.adstart.domain_monitor.domain.services.ICertificateExpirationService;
import com.adstart.domain_monitor.rest.processors.ICertificateWebProcessor;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CertificateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateController.class);
    private final ICertificateWebProcessor certificateWebProcessor;
    private final ICertificateExpirationService certificateExpirationService;

    @GetMapping("certificate-test")
    public String testCertificate() {
        try {
            final String url = "github.com";
            final LocalDateTime expirationDate = certificateExpirationService.getDomainExpirationDate(url);

            LOGGER.info("Expiration date for {} is {}", url, expirationDate);

        } catch (Exception e) {
            LOGGER.error("Error while getting certificate from certificate-expiration service", e);
            return "Error while getting certificate from certificate-expiration service";
        }

        LOGGER.info("Test certificate hit");
        return "test";
    }
}
