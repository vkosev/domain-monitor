package com.adstart.domain_monitor.rest.processors;

import com.adstart.domain_monitor.domain.contracts.notification.INotificationService;
import com.adstart.domain_monitor.domain.expiration.ICertificateExpirationManager;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import com.adstart.domain_monitor.domain.services.ICertificateExpirationService;
import com.adstart.domain_monitor.rest.DomainProperties;
import com.adstart.domain_monitor.rest.exceptions.DomainsAlreadyExistException;
import com.adstart.domain_monitor.rest.exceptions.InvalidDomainsException;
import com.adstart.domain_monitor.rest.models.response.AddDomainResponse;
import com.adstart.domain_monitor.rest.models.response.CertificateExpirationCheckResponse;
import com.adstart.domain_monitor.rest.models.response.DeleteCertificateResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.DomainValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CertificateWebProcessor implements ICertificateWebProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateWebProcessor.class);

    private final ICertificateExpirationService certificateExpirationService;
    private final ICertificateExpirationManager certificateExpirationManager;
    private final INotificationService          notificationService;
    private final DomainProperties              domainProperties;

    @Override
    public AddDomainResponse addDomains(List<String> domains) {
        if(!allDomainsAreValid(domains)) {
            throw new InvalidDomainsException(String.format("Some of the domains %s are invalid", domains));
        }

        final List<CertificateExpiration> existingDomains = certificateExpirationService
                .getAllByDomainName(domains);

        if(!existingDomains.isEmpty()) {
            LOGGER.error("Found already existing Certificate Expiration for domains {}", domains);

            throw new DomainsAlreadyExistException(String.format("Some of the domains already exist %s", domains));
        }

        List<CertificateExpiration> certificateExpirations = new ArrayList<>();
        for(String domain : domains) {
            final LocalDateTime expirationDate = certificateExpirationManager.getDomainExpirationDate(domain);

            final CertificateExpiration certificateExpiration = new CertificateExpiration(domain, expirationDate);
            certificateExpirations.add(certificateExpiration);
        }

        certificateExpirationService.saveAll(certificateExpirations);
        LOGGER.info("Certificate Expiration for all domains added successfully");

        final List<AddDomainResponse.AddedDomain> addedDomains = certificateExpirations.stream()
                .map(c -> new AddDomainResponse.AddedDomain(c.getDomain(), c.getExpirationDate()))
                .toList();

        return new AddDomainResponse(addedDomains);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<CertificateExpirationCheckResponse> checkCertificatesExpiration() {
        final List<ExpirationCheckRecord> records = certificateExpirationManager.checkForExpirations();

        final List<ExpirationCheckRecord> uniqueDomainRecords = records.stream()
                .filter(distinctByKey(ExpirationCheckRecord::getDomain))
                .toList();

        return uniqueDomainRecords.stream()
                .map(r -> CertificateExpirationCheckResponse.builder()
                       .domain(r.getDomain())
                       .expirationDate(r.getExpirationDate())
                       .isExpired(r.isExpired())
                       .daysLeft(r.getDaysLeft())
                       .expirationChecks(records.stream()
                               .filter(rr -> rr.getDomain().equals(r.getDomain()))
                               .map(rr -> new CertificateExpirationCheckResponse.ExpirationCheck(rr.getThreshold(),
                                       rr.isExceedsThreshold()))
                               .toList())
                       .build())
                .toList();
    }

    @Override
    public DeleteCertificateResponse deleteCertificate(String domain) {
        final CertificateExpiration certificateExpiration = certificateExpirationService.deleteByDomain(domain);

        final DeleteCertificateResponse response = new DeleteCertificateResponse();
        response.setDomain(certificateExpiration.getDomain());

        return response;
    }

    private boolean allDomainsAreValid(List<String> domains) {
        DomainValidator validator = DomainValidator.getInstance();

        for (String domain : domains) {
            if(!validator.isValid(domain)) {
                LOGGER.error("Invalid domain name {}", domain);
                return false;
            }
        }

        return true;
    }
}
