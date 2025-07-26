package com.adstart.domain_monitor.database;

import com.adstart.domain_monitor.database.entities.CertificateExpirationEntity;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
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
}
