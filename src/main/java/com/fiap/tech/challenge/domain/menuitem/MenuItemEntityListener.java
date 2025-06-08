package com.fiap.tech.challenge.domain.menuitem;

import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public class MenuItemEntityListener {

    @PostLoad
    public void postLoad(MenuItem menuItemEntity) {
        menuItemEntity.saveState(SerializationUtils.clone(menuItemEntity));
    }
}