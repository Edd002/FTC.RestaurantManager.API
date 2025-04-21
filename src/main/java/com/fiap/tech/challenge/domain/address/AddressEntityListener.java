package com.fiap.tech.challenge.domain.address;

import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public final class AddressEntityListener {

    @PostLoad
    public void postLoad(Address addressEntity) {
        addressEntity.saveState(SerializationUtils.clone(addressEntity));
    }
}