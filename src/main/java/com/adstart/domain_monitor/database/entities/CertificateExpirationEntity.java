package com.adstart.domain_monitor.database.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static com.adstart.domain_monitor.database.Constants.TABLE_NAME_CERTIFICATE_EXPIRATIONS;

@Entity
@Data
@Table(name = TABLE_NAME_CERTIFICATE_EXPIRATIONS)
public class CertificateExpirationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime expirationDate;

    private String domain;
}
