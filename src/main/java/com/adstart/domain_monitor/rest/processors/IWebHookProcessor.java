package com.adstart.domain_monitor.rest.processors;

public interface IWebHookProcessor {
    void save(String callbackUrl);
}
