package com.fiap.tech.challenge.domain.loadtable.usecase;

import com.fiap.tech.challenge.domain.loadtable.LoadTable;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public final class LoadTableCreateUseCase {

    private final LoadTable loadTable;

    public LoadTableCreateUseCase(LoadTable loadTable) {
        this.loadTable = loadTable;
    }

    public LoadTable create(String entityName) {
        LoadTable loadTable = ValidationUtil.isNotNull(this.loadTable) ? this.loadTable : new LoadTable(entityName);
        loadTable.setEntityLoadEnabled(false);
        return loadTable;
    }
}