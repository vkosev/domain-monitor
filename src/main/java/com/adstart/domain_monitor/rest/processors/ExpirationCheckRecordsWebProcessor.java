package com.adstart.domain_monitor.rest.processors;

import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import com.adstart.domain_monitor.domain.services.ICertificateExpirationService;
import com.adstart.domain_monitor.rest.models.response.GetExpirationCheckRecordResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ExpirationCheckRecordsWebProcessor implements IExpirationCheckRecordsWebProcessor {
    private ICertificateExpirationService  certificateExpirationService;

    @Override
    public Page<GetExpirationCheckRecordResponse> getExpirationCheckRecords(Pageable pageable) {
        final Page<ExpirationCheckRecord> records = certificateExpirationService.getExpirationCheckRecords(pageable);

        return records.map(r -> GetExpirationCheckRecordResponse.builder()
                .id(r.getId())
                .expirationDate(r.getExpirationDate())
                .isExpired(r.isExpired())
                .daysLeft(r.getDaysLeft())
                .threshold(r.getThreshold())
                .exceedsThreshold(r.isExceedsThreshold())
                .createdAt(r.getCreatedAt())
                .domain(r.getDomain())
                .build());
    }
}
