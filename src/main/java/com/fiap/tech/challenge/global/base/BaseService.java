package com.fiap.tech.challenge.global.base;

import com.fiap.tech.challenge.global.exception.EntityListEmptyException;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import com.fiap.tech.challenge.global.exception.EntityNullException;
import com.fiap.tech.challenge.global.util.CollectionUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<R extends IBaseRepository<E>, E> {

    @Autowired
    private R repository;

    public boolean existsByHashId(String hashId) {
        return repository.existsByHashId(hashId);
    }

    public E save(E entity) {
        return save(entity, "Nenhuma entidade foi informada para ser persistida.");
    }

    public E save(E entity, String entityNullErrorMessage) {
        if (ValidationUtil.isNull(entity)) {
            throw new EntityNullException(entityNullErrorMessage);
        }
        return repository.save(entity);
    }

    public E saveAndFlush(E entity) {
        return saveAndFlush(entity, "Nenhuma entidade foi informada para ser persistida.");
    }

    public E saveAndFlush(E entity, String entityNullErrorMessage) {
        if (ValidationUtil.isNull(entity)) {
            throw new EntityNullException(entityNullErrorMessage);
        }
        return repository.saveAndFlush(entity);
    }

    public E saveWithoutException(E entity) {
        return ValidationUtil.isNotNull(entity) ? repository.save(entity) : null;
    }

    public List<E> saveAll(List<E> entityList) {
        return saveAll(entityList, "Nenhum registro foi informado para ser persistido.");
    }

    public List<E> saveAll(List<E> entityList, String entityNullErrorMessage) {
        if (ValidationUtil.isEmpty(entityList)) {
            throw new EntityListEmptyException(entityNullErrorMessage);
        }
        return repository.saveAll(entityList);
    }

    public List<E> saveAllWithoutException(List<E> entityList) {
        return ValidationUtil.isNotEmpty(entityList) ? repository.saveAll(entityList) : new ArrayList<>();
    }

    public E findByHashId(String hashId) {
        return findByHashId(hashId, "Nenhum registro com hash id " + hashId + " foi encontrado.");
    }

    public E findByHashId(String hashId, String entityNotFoundErrorMessage) {
        return ValidationUtil.isNotNull(hashId) ? repository.findByHashId(hashId).orElseThrow(() -> new EntityNotFoundException(entityNotFoundErrorMessage)) : null;
    }

    public List<E> findAllByHashIdIn(List<String> hashIds) {
        return findAllByHashIdIn(hashIds, "Algum registro com os hash ids informados não foi encontrado.");
    }

    public List<E> findAllByHashIdIn(List<String> hashIds, String entityNotFoundErrorMessage) {
        List<String> hashIdsWithoutDuplicates = CollectionUtil.removeDuplicates(hashIds);
        List<E> entityList = ValidationUtil.isNotEmpty(hashIdsWithoutDuplicates) ? repository.findAllByHashIdIn(hashIdsWithoutDuplicates) : null;
        if (ValidationUtil.isEmpty(entityList) || hashIdsWithoutDuplicates.size() != entityList.size()) {
            throw new EntityNotFoundException(entityNotFoundErrorMessage);
        }
        return entityList;
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public Optional<E> findWithoutExceptionByHashId(String hashId) {
        return ValidationUtil.isNotNull(hashId) ? repository.findByHashId(hashId) : Optional.empty();
    }

    public void delete(E entity) {
        delete(entity, "Nenhum registro foi encontrado para ser excluído.");
    }
    
    public void delete(E entity, String entityNullErrorMessag) {
        if (ValidationUtil.isNull(entity)) {
            throw new EntityNullException(entityNullErrorMessag);
        }
        repository.delete(entity);
    }

    public Long deleteByHashId(String hashId) {
        return deleteByHashId(hashId, "Nenhum registro com hash id " + hashId + " foi encontrado para ser excluído.");
    }

    public Long deleteByHashId(String hashId, String entityNotFoundErrorMessage) {
        Long quantityDeleted = repository.deleteByHashId(hashId);
        if (quantityDeleted < NumberUtils.LONG_ONE) {
            throw new EntityNotFoundException(entityNotFoundErrorMessage);
        }
        return quantityDeleted;
    }

    public List<E> removeByHashId(String hashId) {
        return removeByHashId(hashId, "Nenhum registro com hash id " + hashId + " foi encontrado para ser excluído.");
    }

    public List<E> removeByHashId(String hashId, String entityNotFoundErrorMessage) {
        List<E> listRemoved = repository.removeByHashId(hashId);
        if (listRemoved.size() < NumberUtils.INTEGER_ONE) {
            throw new EntityNotFoundException(entityNotFoundErrorMessage);
        }
        return listRemoved;
    }

    public void flush() {
        repository.flush();
    }
}