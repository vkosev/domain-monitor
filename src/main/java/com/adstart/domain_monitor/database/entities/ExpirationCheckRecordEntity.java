package com.adstart.domain_monitor.database.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static com.adstart.domain_monitor.database.Constants.TABLE_NAME_EXPIRATION_CHECK_RECORDS;

@Entity
@Data
@Table(name = TABLE_NAME_EXPIRATION_CHECK_RECORDS)
public class ExpirationCheckRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String domain;

    private LocalDateTime expirationDate;

    private int daysLeft;

    private boolean exceedsThreshold;

    private int threshold;

    private boolean isExpired;

    private LocalDateTime createdAt;
}
