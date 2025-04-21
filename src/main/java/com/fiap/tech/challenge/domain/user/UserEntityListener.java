package com.fiap.tech.challenge.domain.user;

import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public final class UserEntityListener {

    @PostLoad
    public void postLoad(User userEntity) {
        userEntity.saveState(SerializationUtils.clone(userEntity));
    }
}