package com.adstart.domain_monitor.domain.contracts.persistence;

import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IExpirationCheckRecordRepository {
    ExpirationCheckRecord save(ExpirationCheckRecord record);

    void saveAll(List<ExpirationCheckRecord> records);

    Page<ExpirationCheckRecord> findAll(Pageable pageable);
}
