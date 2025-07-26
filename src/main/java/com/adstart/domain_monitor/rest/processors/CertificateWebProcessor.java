package com.adstart.domain_monitor.rest.processors;

import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.services.ICertificateExpirationService;
import com.adstart.domain_monitor.rest.exceptions.DomainsAlreadyExistException;
import com.adstart.domain_monitor.rest.exceptions.InvalidDomainsException;
import com.adstart.domain_monitor.rest.models.response.AddDomainResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.DomainValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CertificateWebProcessor implements ICertificateWebProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateWebProcessor.class);

    private final ICertificateExpirationService certificateExpirationService;

    @Override
    public AddDomainResponse addDomains(List<String> domains) {
        if(!allDomainsAreValid(domains)) {
            throw new InvalidDomainsException(String.format("Some of the domains %s are invalid", domains));
        }

        final List<CertificateExpiration> existingDomains = certificateExpirationService
                .getAllByDomainName(domains);

        if(!existingDomains.isEmpty()) {
            LOGGER.error("Found already existing Certificate Expiration for domains {}", domains);

            throw  new DomainsAlreadyExistException(String.format("Some of the domains already exist %s", domains));
        }

        List<CertificateExpiration> certificateExpirations = new ArrayList<>();
        for(String domain : domains) {
            final LocalDateTime expirationDate = certificateExpirationService.getDomainExpirationDate(domain);

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
