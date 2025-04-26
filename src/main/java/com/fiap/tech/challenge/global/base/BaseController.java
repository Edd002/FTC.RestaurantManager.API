package com.fiap.tech.challenge.global.base;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse400;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse422;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse500;
import com.fiap.tech.challenge.global.exception.ApiException;
import com.fiap.tech.challenge.global.exception.ConstraintNotAssociatedWithEntityException;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log
@RestControllerAdvice
public abstract class BaseController {

    private final EntityManager entityManager;

    @Autowired
    public BaseController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, BindException.class, InvalidPropertyException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(Exception exception) {
        List<String> errors = new ArrayList<>();
        if (exception instanceof MethodArgumentNotValidException) {
            errors.addAll(((MethodArgumentNotValidException) exception).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
        } else if (exception instanceof BindException) {
            errors.addAll(((BindException) exception).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
        }
        if (exception instanceof HttpMessageNotReadableException) {
            if (exception.getCause() instanceof InvalidFormatException cause && ValidationUtil.isNotEmpty(cause.getPath())) {
                errors.add(String.format("Campo %s com valor %s inválido.",
                        cause.getPath().stream()
                                .map(JsonMappingException.Reference::getFieldName)
                                .filter(ValidationUtil::isNotNull)
                                .collect(Collectors.joining(".")),
                        cause.getValue()));
            } else {
                errors.add(StringUtils.substringBefore(exception.getMessage(), "; nested exception is"));
            }
        }
        if (exception instanceof InvalidPropertyException) {
            errors.add(String.format("Campo %s inválido.", ((InvalidPropertyException) exception).getPropertyName()));
        }
        return new BaseErrorResponse400(errors).buildResponse();
    }

    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        try {
            Class<?> entityClass = entityManager.getMetamodel().getEntities().stream().
                    filter(entityType -> Objects.requireNonNull(constraintViolationException.getConstraintName()).contains(Objects.requireNonNull(entityType.getJavaType().getAnnotation(Table.class)).name()))
                    .findFirst()
                    .orElseThrow(ConstraintNotAssociatedWithEntityException::new)
                    .getJavaType();
            if (Audit.class.isAssignableFrom(entityClass)) {
                Audit entity = ((Class<Audit>) entityClass).getDeclaredConstructor().newInstance();
                return new BaseErrorResponse422(List.of(entity.getConstraintErrorMessage(constraintViolationException.getConstraintName()))).buildResponse();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ConstraintNotAssociatedWithEntityException ignored) {
        }
        return new BaseErrorResponse422(List.of(String.format("Violação do restritor %s do banco de dados.", constraintViolationException.getConstraintName()))).buildResponse();
    }

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<?> handleExceptionApiException(ApiException apiException) {
        return apiException.getBaseErrorResponse().buildResponse();
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception exception) {
        return new BaseErrorResponse500(List.of(exception.getMessage())).buildResponse();
    }
}