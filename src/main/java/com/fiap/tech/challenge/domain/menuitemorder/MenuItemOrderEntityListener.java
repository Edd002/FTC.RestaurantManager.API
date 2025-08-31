package com.fiap.tech.challenge.domain.menuitemorder;

import com.fiap.tech.challenge.domain.menuitemorder.entity.MenuItemOrder;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public class MenuItemOrderEntityListener {

    @PostLoad
    public void postLoad(MenuItemOrder menuItemOrderEntity) {
        menuItemOrderEntity.saveState(SerializationUtils.clone(menuItemOrderEntity));
    }
}