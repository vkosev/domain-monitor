package com.adstart.domain_monitor.rest.controllers;

import com.adstart.domain_monitor.rest.UriPaths;
import com.adstart.domain_monitor.rest.models.request.RegisterHookRequest;
import com.adstart.domain_monitor.rest.models.response.AddDomainResponse;
import com.adstart.domain_monitor.rest.models.response.ErrorResponse;
import com.adstart.domain_monitor.rest.models.response.RegisterHookResponse;
import com.adstart.domain_monitor.rest.processors.IWebHookProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UriPaths.BASE_API_VERSION)
@AllArgsConstructor
@Tag(name = "Web Hooks",
        description = "Endpoints for managing web hooks")
public class HooksController {

    private final IWebHookProcessor webHookProcessor;

    @PostMapping(value = UriPaths.REGISTER_HOOK, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Register an url on which to send notification",
            description = "Register an url on which to send notification when a certificate has expired",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Domains processed successfully",
                            content = @Content(schema = @Schema(implementation = AddDomainResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    public ResponseEntity<RegisterHookResponse> register(@RequestBody final RegisterHookRequest request) {
        webHookProcessor.save(request.getUrlCallback());

        final RegisterHookResponse response = new RegisterHookResponse();
        response.setCallbackUrl(request.getUrlCallback());

        return ResponseEntity.ok(response);
    }
}
