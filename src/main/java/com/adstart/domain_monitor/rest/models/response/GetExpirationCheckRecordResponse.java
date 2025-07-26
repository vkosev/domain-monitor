package com.adstart.domain_monitor.rest.models.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetExpirationCheckRecordResponse {
    private Integer id;
    private String domain;
    private LocalDateTime expirationDate;
    private int daysLeft;
    private boolean exceedsThreshold;
    private int threshold;
    private boolean isExpired;
    private LocalDateTime createdAt;
}
