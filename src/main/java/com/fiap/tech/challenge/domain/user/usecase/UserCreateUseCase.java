package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;

public final class UserCreateUseCase {

    private final User user;

    public UserCreateUseCase(UserPostRequestDTO userPostRequestDTO, String passwordCryptoKey, City city) {
        if (!new UserCheckLoggedOwnerUseCase().isLoggedOwner() && userPostRequestDTO.getRole().equals(UserRoleEnum.OWNER.name())) {
            throw new AuthorizationException("Apenas usuários do tipo DONO (OWNER) podem criar outros usuários com esse mesmo tipo.");
        }
        this.user = new User(
                userPostRequestDTO.getName(),
                userPostRequestDTO.getEmail(),
                userPostRequestDTO.getLogin(),
                passwordCryptoKey,
                userPostRequestDTO.getPassword(),
                userPostRequestDTO.getRole(),
                new Address(
                        userPostRequestDTO.getAddress().getDescription(),
                        userPostRequestDTO.getAddress().getNumber(),
                        userPostRequestDTO.getAddress().getComplement(),
                        userPostRequestDTO.getAddress().getNeighborhood(),
                        userPostRequestDTO.getAddress().getCep(),
                        userPostRequestDTO.getAddress().getPostalCode(),
                        city
                )
        );
    }

    public User getBuiltedUser() {
        return this.user;
    }
}