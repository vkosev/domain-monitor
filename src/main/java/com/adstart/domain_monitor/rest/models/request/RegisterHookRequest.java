package com.adstart.domain_monitor.rest.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterHookRequest {
    @Schema(description = "The url on which to send a notification", example = "endpoint.com")
    private String urlCallback;
}
