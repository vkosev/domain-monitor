package com.adstart.domain_monitor.domain.services;

import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ICertificateExpirationService {
    CertificateExpiration save(CertificateExpiration certificateExpiration);
    void saveAll(List<CertificateExpiration> certificateExpirations);
    List<CertificateExpiration> getAllByDomainName(List<String> domainNames);
    List<CertificateExpiration> getAll();
    CertificateExpiration deleteByDomain(String domainName);
    Page<ExpirationCheckRecord> getExpirationCheckRecords(Pageable pageable);
}
