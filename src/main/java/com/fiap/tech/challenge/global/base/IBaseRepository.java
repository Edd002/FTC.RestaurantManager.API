package com.fiap.tech.challenge.global.base;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<E> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    boolean existsByHashId(String hashId);

    Optional<E> findByHashId(String hashId);

    List<E> findAllByHashIdIn(List<String> hashIds);

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    Long deleteByHashId(String hashId);

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    List<E> removeByHashId(String hashId);
}