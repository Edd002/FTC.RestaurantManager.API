package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends IBaseRepository<User> {

    Optional<User> findByLogin(String login);
}