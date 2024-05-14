package com.tdubuis.authapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "token")
@Data
public class TokenProperties {
    String refreshSecretKey;
    Integer refreshTokenExpiration;
    String accessSecretKey;
    Integer accessTokenExpiration;
}
