package com.fiap.tech.challenge.domain.loadtable.usecase;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public final class LoadTableEntityLoadEnabledCase {

    private final LoadTable loadTable;

    public LoadTableEntityLoadEnabledCase(LoadTable loadTable) {
        this.loadTable = loadTable;
    }

    public boolean entityLoadEnabled() {
        return ValidationUtil.isNull(this.loadTable) || this.loadTable.getEntityLoadEnabled();
    }
}