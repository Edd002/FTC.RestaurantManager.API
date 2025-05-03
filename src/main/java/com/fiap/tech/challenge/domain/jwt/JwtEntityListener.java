package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public final class JwtEntityListener {

    @PostLoad
    public void postLoad(Jwt jwtEntity) {
        jwtEntity.saveState(SerializationUtils.clone(jwtEntity));
    }
}