package com.adstart.domain_monitor.database.repositories.jpa;

import com.adstart.domain_monitor.database.entities.CertificateExpirationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateExpirationJpaRepository extends JpaRepository<CertificateExpirationEntity, Integer> {

    List<CertificateExpirationEntity> findByDomainIn(List<String> domains);
}
