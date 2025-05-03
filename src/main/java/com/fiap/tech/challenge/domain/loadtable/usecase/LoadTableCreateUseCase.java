package com.fiap.tech.challenge.domain.loadtable.usecase;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public final class LoadTableCreateUseCase {

    private final LoadTable loadTable;

    public LoadTableCreateUseCase(LoadTable loadTable) {
        this.loadTable = loadTable;
    }

    public LoadTable buildLoadTable(String entityName) {
        return ValidationUtil.isNotNull(this.loadTable) ? this.loadTable : new LoadTable(entityName);
    }
}