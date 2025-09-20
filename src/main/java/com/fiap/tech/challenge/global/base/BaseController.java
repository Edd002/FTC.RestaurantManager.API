package com.fiap.tech.challenge.global.base;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.tech.challenge.global.base.response.error.*;
import com.fiap.tech.challenge.global.constraint.ConstraintComponent;
import com.fiap.tech.challenge.global.constraint.ConstraintMapper;
import com.fiap.tech.challenge.global.exception.ApiException;
import com.fiap.tech.challenge.global.exception.ConstraintNotAssociatedWithEntityException;
import com.fiap.tech.challenge.global.util.HttpUtil;
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
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log
@RestControllerAdvice
public class BaseController {

    private final EntityManager entityManager;
    private final ConstraintComponent constraintComponent;

    @Autowired
    public BaseController(EntityManager entityManager, ConstraintComponent  constraintComponent) {
        this.entityManager = entityManager;
        this.constraintComponent = constraintComponent;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, BindException.class, InvalidPropertyException.class})
    public ResponseEntity<?> handleBeanValidationException(Exception exception) {
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

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        if (ValidationUtil.isNotNull(constraintViolationException.getConstraintName())) {
            final String cleanConstraintName = constraintComponent.extractCleanConstraintNameFromConstraintName(constraintViolationException.getConstraintName());
            final String cleanTableName = constraintComponent.extractCleanTableNameFromConstraintName(constraintViolationException.getConstraintName());
            try {
                String constraintErrorMessage = entityManager.getMetamodel().getEntities().stream().
                        filter(entityType -> cleanTableName.equalsIgnoreCase(Objects.requireNonNull(entityType.getJavaType().getAnnotation(Table.class)).name()))
                        .findFirst()
                        .map(entityType -> Objects.requireNonNull(entityType.getJavaType().getAnnotation(ConstraintMapper.class)).constraintClass())
                        .orElseThrow(ConstraintNotAssociatedWithEntityException::new)
                        .getDeclaredConstructor().newInstance().getErrorMessage(cleanConstraintName);
                return new BaseErrorResponse422(List.of(constraintErrorMessage)).buildResponse();
            } catch (Exception ignored) {
            }
            return new BaseErrorResponse422(List.of(String.format("Erro de violação do restritor %s do banco de dados.", cleanConstraintName))).buildResponse();
        }
        return new BaseErrorResponse422(List.of("Erro de violação de um restritor no banco de dados.")).buildResponse();
    }

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<?> handleApiException(ApiException apiException) {
        return apiException.getBaseErrorResponse().buildResponse();
    }

    @ExceptionHandler(value = {AuthorizationDeniedException.class})
    ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException authorizationDeniedException) {
        if (HttpUtil.hasCurrentRequest() && ValidationUtil.isNotNull(HttpUtil.getCurrentHttpRequest().getAttribute("jwtError"))) {
            return new BaseErrorResponse401(List.of(HttpUtil.getCurrentHttpRequest().getAttribute("jwtError").toString())).buildResponse();
        }
        return new BaseErrorResponse403(List.of("O usuário não possui permissão para a operação solicitada.")).buildResponse();
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception exception) {
        return new BaseErrorResponse500(List.of(exception.getMessage())).buildResponse();
    }
}