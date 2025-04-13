package com.fiap.tech.challenge.global.base;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.base.error.*;
import com.fiap.tech.challenge.global.exception.*;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log
@RestControllerAdvice
public abstract class BaseController {

    @Autowired
    private EntityManager entityManager;

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, BindException.class, InvalidPropertyException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(Exception ex) {
        List<String> errors = new ArrayList<>();
        if (ex instanceof MethodArgumentNotValidException) {
            errors.addAll(((MethodArgumentNotValidException) ex).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
        }
        if (ex instanceof BindException) {
            errors.addAll(((BindException) ex).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
        }
        if (ex instanceof HttpMessageNotReadableException) {
            if (ex.getCause() instanceof InvalidFormatException cause && ValidationUtil.isNotEmpty(cause.getPath())) {
                errors.add(String.format("Campo '%s' com valor '%s' inválido.",
                        cause.getPath().stream()
                                .map(JsonMappingException.Reference::getFieldName)
                                .filter(ValidationUtil::isNotNull)
                                .collect(Collectors.joining(".")),
                        cause.getValue()));
            } else {
                errors.add(StringUtils.substringBefore(ex.getMessage(), "; nested exception is"));
            }
        }
        if (ex instanceof InvalidPropertyException) {
            errors.add(String.format("Campo '%s' inválido.", ((InvalidPropertyException) ex).getPropertyName()));
        }
        return new BaseErrorResponse400(errors).getResponse();
    }

    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        try {
            Class<?> entityClass = entityManager.getMetamodel().getEntities().stream().
                    filter(entityType -> Objects.requireNonNull(ex.getConstraintName()).contains(entityType.getJavaType().getAnnotation(Table.class).name()))
                    .findFirst()
                    .orElseThrow(ConstraintNotAssociatedWithEntityException::new)
                    .getJavaType();
            if (Audit.class.isAssignableFrom(entityClass)) {
                Audit entity = ((Class<Audit>) entityClass).getDeclaredConstructor().newInstance();
                return new BaseErrorResponse422(List.of(entity.getConstraintErrorMessage(ex.getConstraintName()))).getResponse();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ConstraintNotAssociatedWithEntityException ignored) {
        }
        return new BaseErrorResponse422(List.of("Violação do restritor " + ex.getConstraintName() + " do banco de dados.")).getResponse();
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        if (ex instanceof BadRequestException || ex instanceof InvalidUserTokenException || ex instanceof HttpMessageNotReadableException || ex instanceof MissingRequestHeaderException) {
            return new BaseErrorResponse400(List.of(ex.getMessage())).getResponse();
        }
        if (ex instanceof EntityNotFoundException) {
            return new BaseErrorResponse404(List.of(ex.getMessage())).getResponse();
        }
        if (ex instanceof UnauthorizedException) {
            return new BaseErrorResponse401(List.of(ex.getMessage())).getResponse();
        }
        if (ex instanceof PreConditionFailedException) {
            return new BaseErrorResponse412(List.of(ex.getMessage())).getResponse();
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return new BaseErrorResponse415(List.of(ex.getMessage())).getResponse();
        }
        if (ex instanceof AccessDeniedException || ex instanceof ForbiddenException) {
            return new BaseErrorResponse403(List.of(ex.getMessage())).getResponse();
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new BaseErrorResponse405(List.of(ex.getMessage())).getResponse();
        }
        if (ex instanceof MultipartException || ex instanceof UnprocessableEntityException) {
            return new BaseErrorResponse422(List.of(ex.getMessage())).getResponse();
        }
        return new BaseErrorResponse500(List.of(ex.getMessage())).getResponse();
    }
}