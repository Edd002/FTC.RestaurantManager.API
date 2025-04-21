package com.fiap.tech.challenge.domain.city;

import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public final class CityEntityListener {

    @PostLoad
    public void postLoad(City cityEntity) {
        cityEntity.saveState(SerializationUtils.clone(cityEntity));
    }
}