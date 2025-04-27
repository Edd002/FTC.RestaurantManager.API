package com.fiap.tech.challenge.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Validated
@NoArgsConstructor
@ConfigurationProperties(prefix = "security.jwt.tokens")
public class JwtSecurityProperty {

    private String bearerTokenSecretKey;

    @Setter
    @Getter
    private Long bearerTokenValidityInMilliseconds;

    public void setBearerTokenSecretKey(String bearerTokenSecretKey) {
        this.bearerTokenSecretKey = Base64.getEncoder().encodeToString(bearerTokenSecretKey.getBytes(UTF_8));
    }

    public String getBearerTokenSecretKey() {
        return bearerTokenSecretKey;
    }
}