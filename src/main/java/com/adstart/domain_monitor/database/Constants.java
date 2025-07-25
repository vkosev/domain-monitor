package com.adstart.domain_monitor.database;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String TABLE_NAME_CERTIFICATE_EXPIRATIONS = "certificate_expirations";
    public static final String TABLE_NAME_WEB_HOOKS = "web_hooks";
    public static final String TABLE_NAME_EXPIRATION_CHECK_RECORDS = "expiration_check_records";
}
