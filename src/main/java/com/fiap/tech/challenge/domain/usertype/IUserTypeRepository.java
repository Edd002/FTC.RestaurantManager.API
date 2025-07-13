package com.fiap.tech.challenge.domain.usertype;

import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserTypeRepository extends IBaseRepository<UserType> {

    Optional<UserType> findByName(String name);
}