package com.adstart.domain_monitor.domain.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collection;

public interface ICertificateExpirationService {
    Collection<X509Certificate> getDomainCertificates(String urlstr)throws KeyManagementException,
            NoSuchAlgorithmException, IOException, URISyntaxException;
}
