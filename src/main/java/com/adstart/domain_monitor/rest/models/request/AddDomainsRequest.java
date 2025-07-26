package com.adstart.domain_monitor.rest.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddDomainsRequest {
    private List<String> domains;
}
