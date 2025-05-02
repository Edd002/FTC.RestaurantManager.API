package com.fiap.tech.challenge.domain.loadtable;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoadTableRepository extends IBaseRepository<LoadTable> {

    LoadTable findByEntityName(String entityName);
}