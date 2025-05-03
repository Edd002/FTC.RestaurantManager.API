package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.dto.JwtGeneratePostRequestDTO;
import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.domain.jwt.dto.TokenInternalDTO;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.jwt.usecase.JwtIsActiveUseCase;
import com.fiap.tech.challenge.domain.jwt.usecase.JwtRefreshByBearerTokenUserCase;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.BaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        return new JwtIsActiveUseCase(jwtRepository.findByBearerToken(bearerToken), millisecondsToExpireJwt).isActive();
    }

    public void refreshByBearerToken(String bearerToken) {
        save(new JwtRefreshByBearerTokenUserCase(jwtRepository.findByBearerToken(bearerToken), millisecondsToExpireJwt).getRefreshedJwt());
    }
}