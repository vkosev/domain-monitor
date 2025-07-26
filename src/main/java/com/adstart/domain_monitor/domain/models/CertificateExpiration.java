package com.adstart.domain_monitor.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CertificateExpiration {
    private Integer id;
    private String domain;
    private LocalDateTime expirationDate;

    public CertificateExpiration(String domain, LocalDateTime expirationDate) {
        this.domain = domain;
        this.expirationDate = expirationDate;
    }
}
