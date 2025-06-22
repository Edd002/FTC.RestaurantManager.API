package com.fiap.tech.challenge.domain.loadtable;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.domain.loadtable.usecase.LoadTableCreateUseCase;
import com.fiap.tech.challenge.domain.loadtable.usecase.LoadTableEntityLoadEnabledCase;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.exception.EntityNullException;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadTableService extends BaseService<ILoadTableRepository, LoadTable> {

    private final ILoadTableRepository loadTableRepository;

    @Autowired
    public LoadTableService(ILoadTableRepository loadTableRepository) {
        this.loadTableRepository = loadTableRepository;
    }

    public boolean isEntityLoadEnabled(String entityName) {
        return new LoadTableEntityLoadEnabledCase(loadTableRepository.findByEntityName(entityName)).isEntityLoadEnabled();
    }

    public void create(String entityName) {
        if (ValidationUtil.isBlank(entityName)) {
            throw new EntityNullException("Nenhuma entidade foi informada para ser cadastrada ou atualizada.");
        }
        save(new LoadTableCreateUseCase(loadTableRepository.findByEntityName(entityName), entityName).getBuiltedLoadTable());
    }

    @Override
    public LoadTable findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("A carregamento de tabela com o hash id %s não foi encontrado.", hashId));
    }
}