package com.adstart.domain_monitor.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriPaths {
    public static final String BASE_API_VERSION = "/api/v1";

    // Certificate Expirations related URIs
    public static final String ADD_DOMAINS = "/domains/add";
    public static final String CHECK_EXPIRATIONS = "/domains/check-certificates";
    public static final String DELETE_DOMAINS = "/domains/delete/{domain}";
    public static final String GET_DOMAINS = "/domains/get";

    // Hooks related URIs
    public static final String REGISTER_HOOK = "/hook/register";
}
