package com.adstart.domain_monitor.database;

import com.adstart.domain_monitor.database.entities.CertificateExpirationEntity;
import com.adstart.domain_monitor.database.entities.ExpirationCheckRecordEntity;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Mapper implements IMapper {
    @Override
    public CertificateExpirationEntity mapDomainCertificationToEntity(CertificateExpiration certificateExpiration) {
        if(Objects.isNull(certificateExpiration)){
            return null;
        }

        final CertificateExpirationEntity certificateExpirationEntity = new CertificateExpirationEntity();
        certificateExpirationEntity.setExpirationDate(certificateExpiration.getExpirationDate());
        certificateExpirationEntity.setId(certificateExpiration.getId());
        certificateExpirationEntity.setDomain(certificateExpiration.getDomain());

        return certificateExpirationEntity;
    }

    @Override
    public CertificateExpiration mapEntityCertificateExpirationToDomainModel(CertificateExpirationEntity certificateExpirationEntity) {
        if(Objects.isNull(certificateExpirationEntity)){
            return null;
        }

        final CertificateExpiration certificateExpiration = new CertificateExpiration();
        certificateExpiration.setExpirationDate(certificateExpirationEntity.getExpirationDate());
        certificateExpiration.setId(certificateExpirationEntity.getId());
        certificateExpiration.setDomain(certificateExpirationEntity.getDomain());

        return certificateExpiration;
    }

    @Override
    public ExpirationCheckRecord mapEntityExpirationCheckRecordToDomainModel(ExpirationCheckRecordEntity entity) {
        return ExpirationCheckRecord
                .builder()
                .id(entity.getId())
                .domain(entity.getDomain())
                .expirationDate(entity.getExpirationDate())
                .daysLeft(entity.getDaysLeft())
                .exceedsThreshold(entity.isExceedsThreshold())
                .threshold(entity.getThreshold())
                .isExpired(entity.isExpired())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public ExpirationCheckRecordEntity mapDomainExpirationCheckRecordToEntity(ExpirationCheckRecord record) {
        final ExpirationCheckRecordEntity entity = new ExpirationCheckRecordEntity();
        entity.setId(record.getId());
        entity.setDomain(record.getDomain());
        entity.setExpirationDate(record.getExpirationDate());
        entity.setDaysLeft(record.getDaysLeft());
        entity.setExceedsThreshold(record.isExceedsThreshold());
        entity.setThreshold(record.getThreshold());
        entity.setExpired(record.isExpired());
        entity.setCreatedAt(record.getCreatedAt());

        return entity;
    }
}
