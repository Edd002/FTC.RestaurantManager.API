package com.fiap.tech.challenge.domain.user.enumerated;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum UserRoleEnum {

    OWNER("Autorização de dono."),
    CLIENT("Autorização de cliente.");

    private final String description;

    UserRoleEnum(String description) {
        this.description = description;
    }

    public SimpleGrantedAuthority getSimpleGrantedAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}