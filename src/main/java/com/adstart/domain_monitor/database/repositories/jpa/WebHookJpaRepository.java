package com.adstart.domain_monitor.database.repositories.jpa;

import com.adstart.domain_monitor.database.entities.WebHookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebHookJpaRepository extends JpaRepository<WebHookEntity,Integer> {
}
