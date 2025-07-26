package com.adstart.domain_monitor.domain.services;

import com.adstart.domain_monitor.domain.models.CertificateExpiration;

import java.time.LocalDateTime;
import java.util.List;

public interface ICertificateExpirationService {
    LocalDateTime getDomainExpirationDate(String url);
    CertificateExpiration save(CertificateExpiration certificateExpiration);
    void saveAll(List<CertificateExpiration> certificateExpirations);
    List<CertificateExpiration> getAllByDomainName(List<String> domainNames);
}
