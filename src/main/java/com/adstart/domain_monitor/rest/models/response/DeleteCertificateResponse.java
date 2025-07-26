package com.adstart.domain_monitor.rest.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response containing the deleted domain")
public class DeleteCertificateResponse {
    @Schema(description = "The delete domain name", example = "github.com")
    private String domain;
}
