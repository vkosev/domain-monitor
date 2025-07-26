package com.adstart.domain_monitor.rest.controllers;

import com.adstart.domain_monitor.rest.UriPaths;
import com.adstart.domain_monitor.rest.models.request.AddDomainsRequest;
import com.adstart.domain_monitor.rest.models.response.*;
import com.adstart.domain_monitor.rest.processors.ICertificateWebProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UriPaths.BASE_API_VERSION)
@AllArgsConstructor
@Tag(name = "Certificate Expiration",
        description = "Endpoints for managing and monitoring domain certificate expiration")
public class CertificateController {

    private final ICertificateWebProcessor certificateWebProcessor;

    @PostMapping(value = UriPaths.ADD_DOMAINS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Add domains and retrieve their certificate expiration",
            description = "Retrieves the certificate expiration date for each provided domain and stores it.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Domains added successfully",
                            content = @Content(schema = @Schema(implementation = AddDomainResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            summary = "A generic server error response",
                                            value = """
                                    {
                                      "errorType": "Internal Server Error",
                                      "message": "Internal Server Error",
                                      "timestamp": "2025-07-25T15:42:10"
                                    }
                                    """
                                    ))
                    )
            }
    )
    public ResponseEntity<AddDomainResponse> addDomain(@RequestBody final AddDomainsRequest request) {
        final AddDomainResponse response = certificateWebProcessor.addDomains(request.getDomains());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = UriPaths.DELETE_DOMAINS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Delete existing certificate expiration monitoring for a domain",
            description = "Delete existing certificate expiration monitoring for a domain",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Domain deleted successfully",
                            content = @Content(schema = @Schema(implementation = DeleteCertificateResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            summary = "A generic server error response",
                                            value = """
                                    {
                                      "errorType": "Internal Server Error",
                                      "message": "Internal Server Error",
                                      "timestamp": "2025-07-25T15:42:10"
                                    }
                                    """
                                    ))
                    )
            }
    )
    public ResponseEntity<DeleteCertificateResponse> deleteDomain(@PathVariable final String domain) {
        final DeleteCertificateResponse response = certificateWebProcessor.deleteCertificate(domain);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = UriPaths.CHECK_EXPIRATIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Check for certificates expiration status on added domains",
            description = "Check for certificates expiration status on added domains",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate expiration check successful",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = CertificateExpirationCheckResponse.class)))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                    name = "Internal Server Error",
                                    summary = "A generic server error response",
                                    value = """
                                    {
                                      "errorType": "Internal Server Error",
                                      "message": "Internal Server Error",
                                      "timestamp": "2025-07-25T15:42:10"
                                    }
                                    """
                            ))
                    )
            }
    )
    public ResponseEntity<List<CertificateExpirationCheckResponse>> checkCertificatesExpiration() {
        final List<CertificateExpirationCheckResponse> responses = certificateWebProcessor.checkCertificatesExpiration();

        return ResponseEntity.ok(responses);
    }

}
