package com.fiap.tech.challenge.domain.loadtable.usecase;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public final class LoadTableEntityLoadEnabledCase {

    private final Boolean isEntityLoadEnabled;

    public LoadTableEntityLoadEnabledCase(LoadTable loadTable) {
        this.isEntityLoadEnabled = ValidationUtil.isNull(loadTable) || loadTable.getEntityLoadEnabled();
    }

    public Boolean isEntityLoadEnabled() {
        return this.isEntityLoadEnabled;
    }
}