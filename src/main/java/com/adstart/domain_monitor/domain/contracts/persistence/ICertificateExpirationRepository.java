package com.adstart.domain_monitor.domain.contracts.persistence;

import com.adstart.domain_monitor.domain.models.CertificateExpiration;

import java.util.List;

public interface ICertificateExpirationRepository {
    CertificateExpiration save(CertificateExpiration certificateExpiration);

    List<CertificateExpiration> getAllByDomainName(List<String> domainNames);

    void saveAll(List<CertificateExpiration> certificateExpirations);

    List<CertificateExpiration> getAll();

    CertificateExpiration deleteByDomain(String domainName);
}
