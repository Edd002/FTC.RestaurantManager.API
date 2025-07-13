package com.fiap.tech.challenge.domain.usertype;

import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public final class UserTypeEntityListener {

    @PostLoad
    public void postLoad(UserType userTypeEntity) {
        userTypeEntity.saveState(SerializationUtils.clone(userTypeEntity));
    }
}