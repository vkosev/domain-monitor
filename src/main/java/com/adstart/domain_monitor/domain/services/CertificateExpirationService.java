package com.adstart.domain_monitor.domain.services;

import com.adstart.domain_monitor.domain.contracts.persistence.ICertificateExpirationRepository;
import com.adstart.domain_monitor.domain.exceptions.FailedCertificateExtractionException;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CertificateExpirationService implements ICertificateExpirationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateExpirationService.class);

    private final ICertificateExpirationRepository certificateExpirationRepository;

    @Override
    @Retryable(retryFor  = FailedCertificateExtractionException.class)
    public LocalDateTime getDomainExpirationDate(String url) {
            try {
                return getDomainCertificates(url).getNotAfter()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
            } catch (URISyntaxException e) {
                throw new UnsupportedOperationException(e);
            } catch (Exception e) {
                final Integer retryCount = Optional.ofNullable(RetrySynchronizationManager.getContext())
                                .map(RetryContext::getRetryCount)
                                .orElse(null);

                LOGGER.warn("Failed to retrieve expiration date for domain {} / Retrying attempt {}", url, retryCount);

                throw new FailedCertificateExtractionException(String.format("Failed to retrieve expiration date for domain %s", url), e);
            }
    }

    @Override
    public CertificateExpiration save(CertificateExpiration certificateExpiration) {
        return certificateExpirationRepository.save(certificateExpiration);
    }

    @Override
    public void saveAll(List<CertificateExpiration> certificateExpirations) {
        certificateExpirationRepository.saveAll(certificateExpirations);
    }

    @Override
    public List<CertificateExpiration> getAllByDomainName(List<String> domainNames) {
        return certificateExpirationRepository.getAllByDomainName(domainNames);
    }

    private X509Certificate getDomainCertificates(String url)
            throws KeyManagementException,
            NoSuchAlgorithmException,
            IOException,
            URISyntaxException {

        final SSLContext ctx = SSLContext.getInstance("TLS");

        ctx.init(new KeyManager[0], new TrustManager[] {new NotSecureTrustManager()}, new SecureRandom());

        SSLContext.setDefault(ctx);

        final URI uri = new URI("https://" + url);

        final HttpsURLConnection conn = (HttpsURLConnection) uri.toURL().openConnection();

        conn.setHostnameVerifier((String arg0, SSLSession arg1) -> true);
        conn.connect();

        X509Certificate xcert = (X509Certificate) conn.getServerCertificates()[0];

        conn.disconnect();

        return xcert;
    }

    private static class NotSecureTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
