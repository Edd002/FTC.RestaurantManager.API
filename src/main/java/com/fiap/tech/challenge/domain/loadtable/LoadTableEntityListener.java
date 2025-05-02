package com.fiap.tech.challenge.domain.loadtable;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public final class LoadTableEntityListener {

    @PostLoad
    public void postLoad(LoadTable loadTableEntity) {
        loadTableEntity.saveState(SerializationUtils.clone(loadTableEntity));
    }
}