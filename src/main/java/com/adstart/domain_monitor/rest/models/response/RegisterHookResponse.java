package com.adstart.domain_monitor.rest.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterHookResponse {
    private String callbackUrl;
}
