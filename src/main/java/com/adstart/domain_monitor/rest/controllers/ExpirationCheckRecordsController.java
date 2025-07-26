package com.adstart.domain_monitor.rest.controllers;

import com.adstart.domain_monitor.rest.UriPaths;
import com.adstart.domain_monitor.rest.models.response.ErrorResponse;
import com.adstart.domain_monitor.rest.models.response.GetExpirationCheckRecordResponse;
import com.adstart.domain_monitor.rest.processors.IExpirationCheckRecordsWebProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UriPaths.BASE_API_VERSION)
@AllArgsConstructor
@Tag(name = "Expiration Check Records",
        description = "Endpoints for managing expiration checks records")
public class ExpirationCheckRecordsController {

    private final IExpirationCheckRecordsWebProcessor expirationCheckRecordsWebProcessor;


    @GetMapping(value = UriPaths.GET_EXPIRATION_CHECK_RECORDS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get expiration checks history records",
            description = "Get expiration checks history records",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Records Retrieved successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                        @Schema(implementation = GetExpirationCheckRecordResponse.class)))
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
    public List<GetExpirationCheckRecordResponse> getAllRecords() {
        return null;
    }
}
