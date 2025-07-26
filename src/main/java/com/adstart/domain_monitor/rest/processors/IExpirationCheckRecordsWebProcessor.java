package com.adstart.domain_monitor.rest.processors;

import com.adstart.domain_monitor.rest.models.response.GetExpirationCheckRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IExpirationCheckRecordsWebProcessor {
    Page<GetExpirationCheckRecordResponse> getExpirationCheckRecords(Pageable pageable);
}
