package com.adstart.domain_monitor.domain.services;

import com.adstart.domain_monitor.database.repositories.ExpirationCheckRecordRepository;
import com.adstart.domain_monitor.domain.contracts.persistence.ICertificateExpirationRepository;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CertificateExpirationService implements ICertificateExpirationService {
    private final ICertificateExpirationRepository certificateExpirationRepository;
    private final ExpirationCheckRecordRepository expirationCheckRecordRepository;

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

    @Override
    public CertificateExpiration deleteByDomain(String domainName) {
        return certificateExpirationRepository.deleteByDomain(domainName);
    }

    @Override
    public Page<ExpirationCheckRecord> getExpirationCheckRecords(Pageable pageable) {
        return expirationCheckRecordRepository.findAll(pageable);
    }
}
