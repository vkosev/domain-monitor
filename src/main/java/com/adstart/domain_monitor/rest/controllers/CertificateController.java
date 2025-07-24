package com.adstart.domain_monitor.rest.controllers;

import com.adstart.domain_monitor.domain.services.ICertificateExpirationService;
import com.adstart.domain_monitor.rest.processors.ICertificateWebProcessor;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.X509Certificate;

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
            final var certs = certificateExpirationService.getDomainCertificates("github.com");
            for(X509Certificate cert : certs) {
                LOGGER.info("Subject DN: {}", cert.getSubjectX500Principal());
                LOGGER.info("SAN: {}", cert.getSubjectAlternativeNames());
                LOGGER.info("Cert expiration date: {}", cert.getNotAfter());
            }

        } catch (Exception e) {
            LOGGER.error("Error while getting certificate from certificate-expiration service", e);
            return "Error while getting certificate from certificate-expiration service";
        }

        LOGGER.info("Test certificate hit");
        return "test";
    }
}
