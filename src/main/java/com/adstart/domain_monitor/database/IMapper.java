package com.adstart.domain_monitor.database;

import com.adstart.domain_monitor.database.entities.CertificateExpirationEntity;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;

public interface IMapper {

     CertificateExpirationEntity mapDomainCertificationToEntity(CertificateExpiration certificateExpiration);
     CertificateExpiration mapEntityCertificateExpirationToDomainModel(CertificateExpirationEntity certificateExpirationEntity);
}
