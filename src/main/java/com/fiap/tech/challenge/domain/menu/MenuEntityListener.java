package com.fiap.tech.challenge.domain.menu;

import com.fiap.tech.challenge.domain.menu.entity.Menu;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public class MenuEntityListener {

    @PostLoad
    public void postLoad(Menu menuEntity) {
        menuEntity.saveState(SerializationUtils.clone(menuEntity));
    }
}