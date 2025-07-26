package com.adstart.domain_monitor.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ExpirationCheckRecord {
    private Integer id;
    private String domain;
    private LocalDateTime expirationDate;
    private int daysLeft;
    private boolean exceedsThreshold;
    private int threshold;
    private boolean isExpired;
    private LocalDateTime createdAt;
}
