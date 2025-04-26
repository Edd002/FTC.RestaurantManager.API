package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.dto.JwtGeneratePostRequestDTO;
import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.global.base.BaseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class JwtService extends BaseService<IJwtRepository, Jwt> {

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
}