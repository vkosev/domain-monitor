package com.adstart.domain_monitor.rest.controllers;

import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.services.ICertificateExpirationService;
import com.adstart.domain_monitor.rest.DomainProperties;
import com.adstart.domain_monitor.rest.UriPaths;
import com.adstart.domain_monitor.rest.models.request.AddDomainsRequest;
import com.adstart.domain_monitor.rest.models.response.AddDomainResponse;
import com.adstart.domain_monitor.rest.processors.ICertificateWebProcessor;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CertificateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateController.class);
    private final ICertificateWebProcessor certificateWebProcessor;
    private final ICertificateExpirationService certificateExpirationService;
    private final DomainProperties domainProperties;

    @GetMapping("certificate-test")
    public String testCertificate() {
        for(String domainName : domainProperties.getNames() ) {
            final LocalDateTime expirationDate = certificateExpirationService.getDomainExpirationDate(domainName);

            LOGGER.info("Expiration date for {} is {}", domainName, expirationDate);

            final List<Integer> thresholdDays = domainProperties.getThresholds();

            for(Integer thresholdDay : thresholdDays) {
                LOGGER.info("Checking expiration for {} days", thresholdDay);
                checkExpiration(expirationDate, thresholdDay);

                final CertificateExpiration certificateExpiration = new CertificateExpiration();
                certificateExpiration.setExpirationDate(expirationDate);
                certificateExpiration.setDomain(domainName);

                final var savedCertificate = certificateExpirationService.save(certificateExpiration);
                LOGGER.info("Saved expiration date for {} is {}", domainName, savedCertificate);
            }
        }

        return "test";
    }

    @PostMapping(UriPaths.ADD_DOMAINS)
    public ResponseEntity<AddDomainResponse> addDomain(@RequestBody final AddDomainsRequest request) {
        final AddDomainResponse response = certificateWebProcessor.addDomains(request.getDomains());

        return ResponseEntity.ok(response);
    }

    private void checkExpiration(LocalDateTime expirationDate, int days) {
        LocalDateTime now = LocalDateTime.now();

        long daysLeft = ChronoUnit.DAYS.between(now, expirationDate);

        if (daysLeft > days) {
            LOGGER.info("Certificate is valid. Days left: {}", daysLeft);
        } else if (daysLeft <= days) {
           LOGGER.warn("Certificate is expiring soon! Days left: {}", daysLeft);
        } else {
            LOGGER.error("Expired {} days ago.", Math.abs(daysLeft));
        }
    }
}
