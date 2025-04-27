package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJwtRepository extends IBaseRepository<Jwt> {

    Jwt findByBearerToken(String bearerToken);
}