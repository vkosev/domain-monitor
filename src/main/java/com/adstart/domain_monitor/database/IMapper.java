package com.adstart.domain_monitor.database;

import com.adstart.domain_monitor.database.entities.CertificateExpirationEntity;
import com.adstart.domain_monitor.database.entities.ExpirationCheckRecordEntity;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;

public interface IMapper {

     CertificateExpirationEntity mapDomainCertificationToEntity(CertificateExpiration certificateExpiration);
     CertificateExpiration mapEntityCertificateExpirationToDomainModel(CertificateExpirationEntity certificateExpirationEntity);
     ExpirationCheckRecord mapEntityExpirationCheckRecordToDomainModel(ExpirationCheckRecordEntity entity);
     ExpirationCheckRecordEntity mapDomainExpirationCheckRecordToEntity(ExpirationCheckRecord record);
}
