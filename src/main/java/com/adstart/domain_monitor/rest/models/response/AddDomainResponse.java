package com.adstart.domain_monitor.rest.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "Response containing the list of domains certifications expiration dates added")
public class AddDomainResponse {

    @Schema(description = "List of added domains certificates expiration dates")
    private List<AddedDomain> domains;

    public AddDomainResponse(List<AddedDomain> domains) {
        this.domains = domains;
    }

    @Schema(description = "Represents a domain and its SSL certificate expiration date")
    public record AddedDomain(String domain, LocalDateTime expirationDate) { }
}
