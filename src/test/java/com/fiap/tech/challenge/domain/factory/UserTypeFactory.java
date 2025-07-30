package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePutRequestDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.util.JsonUtil;

public class UserTypeFactory {
    private static final String PATH_RESOURCE_USER_TYPE = "/mock/usertype/user_type.json";

    public static UserTypePostRequestDTO loadValidChefPostRequestDTO(){
        return JsonUtil.objectFromJson(
                "userTypePostRequestDTOChef",
                PATH_RESOURCE_USER_TYPE,
                UserTypePostRequestDTO.class
        );
    }

    public static UserTypePutRequestDTO loadValidEmployeePutRequestDTO(){
        return JsonUtil.objectFromJson(
                "userTypePutRequestDTOEmployee",
                PATH_RESOURCE_USER_TYPE,
                UserTypePutRequestDTO.class
        );
    }

    public static UserType loadEntityUserTypeOwner(){
        return new UserType("OWNER");
    }

    public static UserType loadEntityUserTypeClient(){
        return new UserType("CLIENT");
    }

    public static UserType loadEntityUserTypeOther(){
        return new UserType("OTHER");
    }

    public static UserType loadEntityUserTypeAdmin(){
        return new UserType("ADMIN");
    }
}
