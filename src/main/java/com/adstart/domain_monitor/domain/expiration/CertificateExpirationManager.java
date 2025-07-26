package com.adstart.domain_monitor.domain.expiration;

import com.adstart.domain_monitor.database.repositories.ExpirationCheckRecordRepository;
import com.adstart.domain_monitor.domain.contracts.notification.INotificationService;
import com.adstart.domain_monitor.domain.exceptions.FailedCertificateExtractionException;
import com.adstart.domain_monitor.domain.models.CertificateExpiration;
import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import com.adstart.domain_monitor.domain.services.ICertificateExpirationService;
import com.adstart.domain_monitor.rest.DomainProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CertificateExpirationManager implements ICertificateExpirationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateExpirationManager.class);

    private final ICertificateExpirationService     certificateExpirationService;
    private final ExpirationCheckRecordRepository   expirationCheckRecordRepository;
    private final DomainProperties                  domainProperties;
    private final INotificationService              notificationService;

    @Override
    @Retryable(retryFor  = FailedCertificateExtractionException.class)
    public LocalDateTime getDomainExpirationDate(String url) {
        try {
            return getDomainCertificates(url).getNotAfter()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (URISyntaxException e) {
            LOGGER.error("Failed to construct an uri for domain {}", url);

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
    public Boolean isExpired(LocalDateTime expirationDate, int threshold) {
        return null;
    }

    @Override
    public Boolean exceedsThreshold(LocalDateTime expirationDate, int threshold) {
        final int daysLeft = daysLeft(expirationDate);

        return daysLeft < threshold;
    }

    @Override
    public int daysLeft(LocalDateTime expirationDate) {
        LocalDateTime now = LocalDateTime.now();

        long daysLeft = ChronoUnit.DAYS.between(now, expirationDate);

        return Long.valueOf(daysLeft).intValue();
    }

    @Override
    public List<ExpirationCheckRecord> checkForExpirations() {
        final List<CertificateExpiration> certificateExpirations = certificateExpirationService.getAll();

        final List<ExpirationCheckRecord> records = new ArrayList<>();

        final List<Integer> thresholds = domainProperties.getThresholds();

        for(CertificateExpiration certificateExpiration : certificateExpirations) {
            final LocalDateTime expirationDate = certificateExpiration.getExpirationDate();

            for(Integer threshold : thresholds) {
                final Boolean exceedsThreshold = this.exceedsThreshold(expirationDate, threshold);

                if(exceedsThreshold) {
                    LOGGER.info("Certificate for domain {} exceeds threshold {} days! Sending Notifications", certificateExpiration.getDomain(), threshold);
                    notificationService.notifyAllRegisters(certificateExpiration.getDomain(),
                            expirationDate,
                            threshold);

                }

                final int daysLeft = this.daysLeft(expirationDate);
                records.add(ExpirationCheckRecord.builder()
                        .daysLeft(daysLeft)
                        .isExpired(daysLeft < 0)
                        .domain(certificateExpiration.getDomain())
                        .expirationDate(expirationDate)
                        .threshold(threshold)
                        .createdAt(LocalDateTime.now())
                        .exceedsThreshold(exceedsThreshold)
                        .build());
            }
        }

        expirationCheckRecordRepository.saveAll(records);

        return records;
    }

    private X509Certificate getDomainCertificates(String url)
            throws KeyManagementException,
            NoSuchAlgorithmException,
            IOException,
            URISyntaxException {

        final SSLContext ctx = SSLContext.getInstance("TLS");

        ctx.init(new KeyManager[0], new TrustManager[] {new CertificateExpirationManager.NotSecureTrustManager()}, new SecureRandom());

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
