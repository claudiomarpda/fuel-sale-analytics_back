package com.example.selecaojava.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class AppProperties {

    private String jwtSecret;
    private int jwtExpirationInMs;

}
