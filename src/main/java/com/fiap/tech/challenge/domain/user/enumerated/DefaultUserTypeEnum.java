package com.fiap.tech.challenge.domain.user.enumerated;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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
        return ValidationUtil.isNotNull(type) && StringUtils.upperCase(type).equals(DefaultUserTypeEnum.ADMIN.name());
    }

    public static boolean isTypeOwner(String type) {
        return ValidationUtil.isNotNull(type) && StringUtils.upperCase(type).equals(DefaultUserTypeEnum.OWNER.name());
    }

    public static boolean isTypeAdminOrOwner(String type) {
        return isTypeAdmin(type) || isTypeOwner(type);
    }

    public static boolean isTypeClient(String type) {
        return ValidationUtil.isNotNull(type) && StringUtils.upperCase(type).equals(DefaultUserTypeEnum.CLIENT.name());
    }

    public static boolean isUserAdmin(User user) {
        return user.getType().getName().equals(DefaultUserTypeEnum.ADMIN.name());
    }

    public static boolean isUserOwner(User user) {
        return user.getType().getName().equals(DefaultUserTypeEnum.OWNER.name());
    }

    public static boolean isUserAdminOrOwner(User user) {
        return isUserAdmin(user) || isUserOwner(user);
    }

    public static boolean isUserClient(User user) {
        return user.getType().getName().equals(DefaultUserTypeEnum.CLIENT.name());
    }
}