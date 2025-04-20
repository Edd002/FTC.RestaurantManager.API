package com.fiap.tech.challenge.domain.loadtable;

import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILoadTableRepository extends IBaseRepository<LoadTable> {

    Optional<LoadTable> findByEntityName(String entityName);
}