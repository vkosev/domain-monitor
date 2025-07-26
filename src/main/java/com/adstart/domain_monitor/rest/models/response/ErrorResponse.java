package com.adstart.domain_monitor.rest.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@Schema(description = "Represents a standard error response returned by the API")
public class ErrorResponse {

    @Schema(description = "The type/category of error", example = "Bad Request")
    private String errorType;

    @Schema(description = "Human-readable error message", example = "Domain name is invalid")
    private String message;

    @Schema(description = "The timestamp when the error occurred", example = "2025-07-25T15:42:10")
    private LocalDateTime timestamp;
}
