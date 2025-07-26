package com.adstart.domain_monitor.rest.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddDomainsRequest {

    @Schema(description = "List of domains to monitor", example = "github.com")
    private List<String> domains;
}
