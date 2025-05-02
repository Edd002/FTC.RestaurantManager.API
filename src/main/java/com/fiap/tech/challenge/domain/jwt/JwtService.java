package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.dto.JwtGeneratePostRequestDTO;
import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.domain.jwt.dto.TokenInternalDTO;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import com.fiap.tech.challenge.global.exception.InvalidBearerTokenHttpException;
import com.fiap.tech.challenge.global.exception.JwtNotFoundHttpException;
import com.fiap.tech.challenge.global.util.DateUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService extends BaseService<IJwtRepository, Jwt> {

    @Value("${security.jwt.expiration-in-milliseconds}")
    private int millisecondsToExpireJwt;

    private final IJwtRepository jwtRepository;

    @Autowired
    public JwtService(IJwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    @Transactional
    public JwtResponseDTO generate(JwtGeneratePostRequestDTO jwtGeneratePostRequestDTO) {
        return null;
    }

    @Transactional
    public void validate(String bearerToken) {
    }

    @Transactional
    public void invalidate(String bearerToken) {
    }

    @Override
    public Jwt findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O JWT com hash id %s não foi encontrado.", hashId));
    }

    public void create(TokenInternalDTO tokenInternalDTO, User user) {
        save(new Jwt(tokenInternalDTO.getBearerToken(), user));
    }

    public Boolean isJwtActiveByBearerToken(String bearerToken) {
        return isJwtActive(jwtRepository.findByBearerToken(bearerToken));
    }

    public void refreshByBearerToken(String bearerToken) {
        try {
            Jwt existingJwt = jwtRepository.findByBearerToken(bearerToken);
            if (!isJwtActive(existingJwt)) {
                throw new InvalidBearerTokenHttpException();
            }
            existingJwt.setUpdatedIn(new Date());
            save(existingJwt);
        } catch (JwtException | JwtNotFoundHttpException | EntityNotFoundException | InvalidBearerTokenHttpException exception) {
            throw new InvalidBearerTokenHttpException();
        }
    }

    private Boolean isJwtActive(Jwt jwt) {
        return ValidationUtil.isNotNull(jwt) && DateUtil.beforeNow(DateUtil.addMilliseconds(jwt.getUpdatedIn(), millisecondsToExpireJwt));
    }
}