package com.adstart.domain_monitor.rest.processors;

import com.adstart.domain_monitor.rest.models.response.AddDomainResponse;

import java.util.List;

public interface ICertificateWebProcessor {
    AddDomainResponse addDomains(List<String> domains);
}
