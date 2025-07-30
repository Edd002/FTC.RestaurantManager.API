package com.fiap.tech.challenge.domain.factory;

import static org.mockito.Mockito.mock;

import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.user.entity.User;
import java.util.Date;

public class JwtTestFactory {

    public Jwt loadDefaultJwt(){
        User mockUser = mock(User.class);

        var bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                + "eyJzdWIiOiIxMjM0NTY3ODkwIiwiZXhwIjoxNzAwMDAwMDAwLCJpYXQiOjE2MDAwMDAwMDB9."
                + "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        Jwt jwt = new Jwt(bearerToken, mockUser);

        jwt.setUpdatedIn(new Date(System.currentTimeMillis() - 1000));

        return jwt;
    }

    public Jwt loadExpiredTokenJwt(){
        User mockUser = mock(User.class);

        var bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                + "eyJzdWIiOiJ1c2VyX3Rlc3RlIiwiaWF0IjoxNzAwMDAwMDAwLCJleHAiOjE3MDAwMDA5MDB9."
                + "dGVzdFNpZ25hdHVyZUJhc2U2NA==";

        Jwt jwt = new Jwt(bearerToken, mockUser);

        jwt.setUpdatedIn(new Date(System.currentTimeMillis() - 30000));

        return jwt;
    }
}
