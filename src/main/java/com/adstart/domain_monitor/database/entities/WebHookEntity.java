package com.adstart.domain_monitor.database.entities;

import jakarta.persistence.*;
import lombok.Data;

import static com.adstart.domain_monitor.database.Constants.TABLE_NAME_WEB_HOOKS;

@Entity
@Data
@Table(name = TABLE_NAME_WEB_HOOKS)
public class WebHookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String callbackUrl;
}
