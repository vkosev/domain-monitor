package com.adstart.domain_monitor.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "domain")
@Getter
@Setter
public class DomainProperties {
    private List<String> names;
    private List<Integer> thresholds;
}
