package com.fiap.tech.challenge.domain.user.enumerated;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import lombok.Getter;

@Getter
public enum DefaultUserTypeEnum {

    ADMIN("Tipo de usuário administrador."),
    OWNER("Tipo de usuário de dono de restaurante."),
    CLIENT("Tipo de usuário de cliente de restaurante.");

    private final String description;

    DefaultUserTypeEnum(String description) {
        this.description = description;
    }

    public static boolean isTypeAdmin(String type) {
        return ValidationUtil.isNotNull(type) && type.equals(DefaultUserTypeEnum.ADMIN.name());
    }

    public static boolean isTypeOwner(String type) {
        return ValidationUtil.isNotNull(type) && type.equals(DefaultUserTypeEnum.OWNER.name());
    }

    public static boolean isTypeClient(String type) {
        return ValidationUtil.isNotNull(type) && type.equals(DefaultUserTypeEnum.CLIENT.name());
    }

    public static boolean isUserAdmin(User user) {
        return user.getType().getName().equals(DefaultUserTypeEnum.ADMIN.name());
    }

    public static boolean isUserOwner(User user) {
        return user.getType().getName().equals(DefaultUserTypeEnum.OWNER.name());
    }

    public static boolean isUserClient(User user) {
        return user.getType().getName().equals(DefaultUserTypeEnum.CLIENT.name());
    }
}