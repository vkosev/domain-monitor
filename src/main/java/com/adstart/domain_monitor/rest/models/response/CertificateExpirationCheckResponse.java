package com.adstart.domain_monitor.rest.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "Response containing the list of checked for expiration certificates")
@Builder
public class CertificateExpirationCheckResponse {
    private String domain;

    private LocalDateTime expirationDate;

    private Boolean isExpired;

    private Integer daysLeft;

    private List<ExpirationCheck> expirationChecks;

    public record ExpirationCheck(int threshold, boolean exceedsThreshold) {}
}
