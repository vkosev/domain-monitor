package com.adstart.domain_monitor.database.repositories.jpa;

import com.adstart.domain_monitor.database.entities.ExpirationCheckRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpirationCheckRecordJpaRepository extends JpaRepository<ExpirationCheckRecordEntity, Integer> {
}
