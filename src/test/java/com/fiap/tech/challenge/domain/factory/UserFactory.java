package com.fiap.tech.challenge.domain.factory;


import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.global.util.JsonUtil;

public class UserFactory {
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
}
