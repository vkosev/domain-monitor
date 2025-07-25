package com.adstart.domain_monitor.database.repositories;

import com.adstart.domain_monitor.database.IMapper;
import com.adstart.domain_monitor.database.entities.CertificateExpirationEntity;
import com.adstart.domain_monitor.database.repositories.jpa.CertificateExpirationJpaRepository;
import com.adstart.domain_monitor.domain.contracts.persistence.ICertificateExpirationRepository;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CertificateExpirationRepository implements ICertificateExpirationRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateExpirationRepository.class);

    private final CertificateExpirationJpaRepository certificateExpirationJpaRepository;
    private final IMapper                            mapper;

    @Override
    public CertificateExpiration save(CertificateExpiration certificateExpiration) {
        final CertificateExpirationEntity newEntity = mapper
                .mapDomainCertificationToEntity(certificateExpiration);

        if(Objects.isNull(newEntity)) {
            LOGGER.error("Certificate expiration is null");

            return null;
        }

        final CertificateExpirationEntity savedEntity = certificateExpirationJpaRepository.save(newEntity);

        LOGGER.info("Saved certificate expiration for domain {}", savedEntity.getDomain());
        return mapper.mapEntityCertificateExpirationToDomainModel(savedEntity);
    }

    @Override
    public List<CertificateExpiration> getAllByDomainName(List<String> domainNames) {
        final List<CertificateExpirationEntity> existingCertificates = certificateExpirationJpaRepository
                .findByDomainIn(domainNames);

        return existingCertificates.stream()
                .map(mapper::mapEntityCertificateExpirationToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveAll(List<CertificateExpiration> certificateExpirations) {
        final List<CertificateExpirationEntity> entities = certificateExpirations.stream()
                .map(mapper::mapDomainCertificationToEntity)
                .toList();

        LOGGER.info("Certificates expirations for all domain added successfully");

        certificateExpirationJpaRepository.saveAll(entities);
    }

    @Override
    public List<CertificateExpiration> getAll() {
        final List<CertificateExpirationEntity> entities = certificateExpirationJpaRepository.findAll();

        return entities.stream().map(mapper::mapEntityCertificateExpirationToDomainModel).collect(Collectors.toList());
    }

    @Override
    public CertificateExpiration deleteByDomain(String domainName) {
        final CertificateExpirationEntity existingEntity = certificateExpirationJpaRepository.findByDomain(domainName);
        if(Objects.isNull(existingEntity)) {
            LOGGER.error("Certificate for domain {} not found. Deletion failed!", domainName);

            return new CertificateExpiration(domainName, null);
        }

        certificateExpirationJpaRepository.delete(existingEntity);

        LOGGER.info("Deleted certificate expiration for domain {}", existingEntity.getDomain());

        return mapper.mapEntityCertificateExpirationToDomainModel(existingEntity);
    }
}
