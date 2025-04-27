package com.fiap.tech.challenge.config.security;

import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class SecurityAuditorAware implements AuditorAware<String> {

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            if (AuthUserContextHolder.hasNoAuthUser()) {
                return Optional.empty();
            }
            User user = AuthUserContextHolder.getAuthUser();
            if (ValidationUtil.isNotNull(user) && ValidationUtil.isNotNull(user.getId())) {
                return Optional.ofNullable(user.getLogin());
            }
        } catch (Exception ignore) {
        }
        return Optional.empty();
    }
}