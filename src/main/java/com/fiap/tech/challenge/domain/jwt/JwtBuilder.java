package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.config.properties.JwtSecurityProperty;
import com.fiap.tech.challenge.domain.jwt.dto.TokenInternalDTO;
import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.global.exception.AuthenticationHttpException;
import com.fiap.tech.challenge.global.exception.EntityNullException;
import com.fiap.tech.challenge.global.exception.TokenValidationException;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log
@Component
@RequiredArgsConstructor
public class JwtBuilder {

    private final JwtSecurityProperty jwtSecurityProperty;

    private final JwtService jwtService;

    private final String bearerTokenSecretKey;

    @Autowired
    public JwtBuilder(JwtSecurityProperty jwtSecurityProperty, @Lazy JwtService jwtService) {
        this.jwtSecurityProperty = jwtSecurityProperty;
        this.jwtService = jwtService;
        this.bearerTokenSecretKey = jwtSecurityProperty.getBearerTokenSecretKey();
    }

    public JwtClaims resolveBearerToken(String bearerToken) throws TokenValidationException {
        return new JwtClaims(bearerToken, jwtSecurityProperty.getBearerTokenSecretKey());
    }

    public JwtClaims resolveBearerToken(HttpServletRequest httpServletRequest) throws AuthenticationHttpException {
        return resolveBearerToken(getJwtFromHeader(httpServletRequest));
    }

    public TokenInternalDTO createToken(User user) {
        if (ValidationUtil.isNull(user)) {
            throw new EntityNullException("O usuário deve ser informado para criação do token.");
        }
        TokenInternalDTO tokenInternalDTO = new TokenInternalDTO(user.getLogin(), createBearerToken(user));
        jwtService.create(tokenInternalDTO, user);
        return tokenInternalDTO;
    }

    private String createBearerToken(User user) {
        return buildJwt(user);
    }

    private String buildJwt(User user) {
        if (ValidationUtil.isNull(user)) {
            throw new EntityNullException("O usuário deve ser informado para que o JWT possa ser gerado.");
        }
        Claims claims = Jwts.claims()
                .subject(user.getLogin())
                .add("userName", user.getName())
                .add("userCreatedIn", user.getCreatedIn())
                .add("userRequestIn", new Date().getTime())
                .build();
        return Jwts.builder().claims(claims)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(bearerTokenSecretKey)))
                .compact();
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String bearer = "Bearer ";
        String bearerToken = request.getHeader("Authorization");
        if (ValidationUtil.isNull(bearerToken)) {
            log.severe("Authorization Header ausente - Path da requisição: " + request.getServletPath());
            throw new AuthenticationHttpException("O header de autorização deve estar presente.");
        }
        if (!bearerToken.startsWith(bearer)) {
            log.severe("Header não iniciando com Bearer - Path da requisição: " + request.getServletPath());
            throw new AuthenticationHttpException("O header de autorização deve começar com Bearer.");
        }
        return bearerToken.substring(bearer.length());
    }
}