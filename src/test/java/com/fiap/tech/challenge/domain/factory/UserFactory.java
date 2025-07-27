package com.fiap.tech.challenge.domain.factory;


import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
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

    public static UserUpdatePasswordPatchRequestDTO loadValidPatchRequestDTO(){
        return JsonUtil.objectFromJson(
                "userUpdatePasswordPatchRequestDTOClient",
                PATH_RESOURCE_USER,
                UserUpdatePasswordPatchRequestDTO.class
        );
    }

    public static UserUpdatePasswordPatchRequestDTO loadInvalidWrongPasswordPatchRequestDTO(){
        return JsonUtil.objectFromJson(
                "userUpdatePasswordPatchRequestDTOClientWrongActualPassword",
                PATH_RESOURCE_USER,
                UserUpdatePasswordPatchRequestDTO.class
        );
    }

    public static UserUpdatePasswordPatchRequestDTO loadInvalidSamePasswordPatchRequestDTO(){
        return JsonUtil.objectFromJson(
                "userUpdatePasswordPatchRequestDTOClientSamePassword",
                PATH_RESOURCE_USER,
                UserUpdatePasswordPatchRequestDTO.class
        );
    }

    public static UserUpdatePasswordPatchRequestDTO loadInvalidWrongPasswordConfirmationPatchRequestDTO(){
        return JsonUtil.objectFromJson(
                "userUpdatePasswordPatchRequestDTOClientWrongPasswordConfirmation",
                PATH_RESOURCE_USER,
                UserUpdatePasswordPatchRequestDTO.class
        );
    }

    public static UserPutRequestDTO loadValidClientUserPutRequestDTO(){
        return JsonUtil.objectFromJson(
                "userPutRequestDTOClient",
                PATH_RESOURCE_USER,
                UserPutRequestDTO.class
        );
    }

    public static UserPutRequestDTO loadValidOwnerUserPutRequestDTO(){
        return JsonUtil.objectFromJson(
                "userPutRequestDTOOwner",
                PATH_RESOURCE_USER,
                UserPutRequestDTO.class
        );
    }

    public static UserPutRequestDTO loadInvalidOwnerToClientUserPutRequestDTO() {
        return JsonUtil.objectFromJson(
                "userPutRequestDTOOwnerToOtherType",
                PATH_RESOURCE_USER,
                UserPutRequestDTO.class
        );
    }
}
