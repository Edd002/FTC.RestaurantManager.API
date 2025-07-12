package com.fiap.tech.challenge.domain.user.enumerated;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum UserTypeEnum {

    ADMIN("Tipo de usuário de admin."),
    OWNER("Tipo de usuário de dono."),
    CLIENT("Tipo de usuário de cliente.");

    private final String description;

    UserTypeEnum(String description) {
        this.description = description;
    }

    public SimpleGrantedAuthority getSimpleGrantedAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}