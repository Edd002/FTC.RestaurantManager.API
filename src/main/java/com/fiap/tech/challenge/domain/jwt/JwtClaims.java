package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.global.exception.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Getter
public class JwtClaims {

    private final Jws<Claims> claims;
    private final String bearerToken;

    public JwtClaims(String bearerToken, String bearerTokenSecretKey) {
        try {
            this.bearerToken = bearerToken;
            claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(bearerTokenSecretKey)))
                    .build()
                    .parseSignedClaims(bearerToken);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenValidationException("O JWT expirou ou é inválido.");
        }
    }

    public String getLogin() {
        return this.claims.getPayload().getSubject();
    }
}