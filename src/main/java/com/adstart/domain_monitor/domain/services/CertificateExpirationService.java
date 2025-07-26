package com.adstart.domain_monitor.domain.services;

import com.adstart.domain_monitor.domain.contracts.persistence.ICertificateExpirationRepository;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CertificateExpirationService implements ICertificateExpirationService {
    private final ICertificateExpirationRepository certificateExpirationRepository;

    @Override
    public CertificateExpiration save(CertificateExpiration certificateExpiration) {
        return certificateExpirationRepository.save(certificateExpiration);
    }

    @Override
    public void saveAll(List<CertificateExpiration> certificateExpirations) {
        certificateExpirationRepository.saveAll(certificateExpirations);
    }

    @Override
    public List<CertificateExpiration> getAllByDomainName(List<String> domainNames) {
        return certificateExpirationRepository.getAllByDomainName(domainNames);
    }

    @Override
    public List<CertificateExpiration> getAll() {
        return certificateExpirationRepository.getAll();
    }
}
