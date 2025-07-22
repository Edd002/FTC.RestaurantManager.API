package com.fiap.tech.challenge.domain.factory;

import static com.fiap.tech.challenge.domain.factory.AddressFactory.loadAddressEntity;
import static com.fiap.tech.challenge.domain.factory.UserTypeFactory.loadEntityUserTypeClient;
import static com.fiap.tech.challenge.domain.factory.UserTypeFactory.loadEntityUserTypeOwner;

import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.util.JsonUtil;

public class UserFactory {
    private static final String passwordCryptoKey = "5E50F405ACE6CBDF17379F4B9F2B0C9F4144C5E380EA0B9298CB02EBD8FFE511";
    private static final String PATH_RESOURCE_USER = "/mock/user/user.json";

    public static UserPostRequestDTO loadValidOwnerPostRequestDTO(){
        return JsonUtil.objectFromJson(
                "userPostRequestDTOOwner",
                PATH_RESOURCE_USER,
                UserPostRequestDTO.class
        );
    }

    public static UserPostRequestDTO loadValidClientPostRequestDTO(){
        return JsonUtil.objectFromJson(
                "userPostRequestDTOClient",
                PATH_RESOURCE_USER,
                UserPostRequestDTO.class
        );
    }

    public static User loadEntityOwnerUser(){
        return new User(
                "Usuário teste",
                "teste@teste.com",
                "teste",
                passwordCryptoKey,
                "teste123",
                loadEntityUserTypeOwner(),
                loadAddressEntity()
        );
    }

    public static User loadEntityClientUser(){
        return new User(
                "Usuário teste",
                "teste@teste.com",
                "teste",
                passwordCryptoKey,
                "teste123",
                loadEntityUserTypeClient(),
                loadAddressEntity()
        );
    }
}
