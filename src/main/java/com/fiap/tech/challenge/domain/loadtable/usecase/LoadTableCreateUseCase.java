package com.fiap.tech.challenge.domain.loadtable.usecase;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public final class LoadTableCreateUseCase {

    private final LoadTable loadTable;

    public LoadTableCreateUseCase(LoadTable loadTable, String entityName) {
        this.loadTable = ValidationUtil.isNotNull(loadTable) ? loadTable : new LoadTable(entityName);
    }

    public LoadTable getBuiltedLoadTable() {
        return this.loadTable;
    }
}