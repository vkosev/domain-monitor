package com.adstart.domain_monitor.database.repositories;

import com.adstart.domain_monitor.database.IMapper;
import com.adstart.domain_monitor.database.entities.ExpirationCheckRecordEntity;
import com.adstart.domain_monitor.database.repositories.jpa.ExpirationCheckRecordJpaRepository;
import com.adstart.domain_monitor.domain.contracts.persistence.IExpirationCheckRecordRepository;
import com.adstart.domain_monitor.domain.models.ExpirationCheckRecord;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ExpirationCheckRecordRepository implements IExpirationCheckRecordRepository {
    private static final Logger                       LOGGER = LoggerFactory.getLogger(ExpirationCheckRecordRepository.class);

    private final ExpirationCheckRecordJpaRepository  expirationCheckRecordJpaRepository;
    private final IMapper                             mapper;

    @Override
    public ExpirationCheckRecord save(ExpirationCheckRecord record) {
        final ExpirationCheckRecordEntity entity = mapper.mapDomainExpirationCheckRecordToEntity(record);

        final ExpirationCheckRecordEntity savedEntity = expirationCheckRecordJpaRepository.save(entity);

        return mapper.mapEntityExpirationCheckRecordToDomainModel(savedEntity);
    }

    @Override
    public void saveAll(List<ExpirationCheckRecord> records) {
        final List<ExpirationCheckRecordEntity> entities = records.stream()
                .map(mapper::mapDomainExpirationCheckRecordToEntity)
                .toList();

        expirationCheckRecordJpaRepository.saveAllAndFlush(entities);

        LOGGER.info("Expiration Check Records saved successfully");
    }

    @Override
    public Page<ExpirationCheckRecord> findAll(Pageable pageable) {
        final Page<ExpirationCheckRecordEntity> entities = expirationCheckRecordJpaRepository.findAll(pageable);

        return entities.map(mapper::mapEntityExpirationCheckRecordToDomainModel);
    }
}
