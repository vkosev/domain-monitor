package com.adstart.domain_monitor.rest.processors;

import com.adstart.domain_monitor.rest.models.response.AddDomainResponse;
import com.adstart.domain_monitor.rest.models.response.CertificateExpirationCheckResponse;

import java.util.List;

public interface ICertificateWebProcessor {
    AddDomainResponse addDomains(List<String> domains);
    List<CertificateExpirationCheckResponse> checkCertificatesExpiration();
}
