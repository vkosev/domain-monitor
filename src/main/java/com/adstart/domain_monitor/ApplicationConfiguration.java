package com.adstart.domain_monitor;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@Configuration
@EnableJpaRepositories(basePackages = {"com.adstart.domain_monitor.database.repositories"})
public class ApplicationConfiguration {
}
