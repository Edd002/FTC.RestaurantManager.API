package com.fiap.tech.challenge.domain.loadtable;

import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.exception.EntityNullException;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoadTableService extends BaseService<ILoadTableRepository, LoadTable> {

    private final ILoadTableRepository loadTableRepository;

    @Autowired
    public LoadTableService(ILoadTableRepository loadTableRepository) {
        this.loadTableRepository = loadTableRepository;
    }

    public boolean load(String entityName) {
        Optional<LoadTable> loadTable = loadTableRepository.findByEntityName(entityName);
        return loadTable.isEmpty() || loadTable.get().getEntityLoad();
    }

    public void save(String entityName) {
        if (ValidationUtil.isBlank(entityName)) {
            throw new EntityNullException("Nenhuma entidade foi informada para ser cadastrada ou atualizada.");
        }
        LoadTable loadTable = loadTableRepository.findByEntityName(entityName).orElse(new LoadTable(entityName));
        loadTable.setEntityLoad(false);
        save(loadTable);
    }
}