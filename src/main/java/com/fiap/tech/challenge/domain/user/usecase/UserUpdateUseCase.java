package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;

public final class UserUpdateUseCase {

    private final User user;

    public UserUpdateUseCase(User actualUser, String passwordCryptoKey, UserPutRequestDTO userPutRequestDTO, City city) {
        if (!new UserCheckLoggedOwnerUseCase().isLoggedOwner() && userPutRequestDTO.getRole().equals(UserRoleEnum.OWNER.name())) {
            throw new AuthorizationException("Usuário não tem permissão para alterar o seu tipo para DONO (OWNER).");
        }
        this.user = new User(
                actualUser.getId(),
                userPutRequestDTO.getName(),
                userPutRequestDTO.getEmail(),
                userPutRequestDTO.getLogin(),
                passwordCryptoKey,
                actualUser.getPassword(),
                userPutRequestDTO.getRole(),
                new Address(
                        actualUser.getAddress().getId(),
                        userPutRequestDTO.getAddress().getDescription(),
                        userPutRequestDTO.getAddress().getNumber(),
                        userPutRequestDTO.getAddress().getComplement(),
                        userPutRequestDTO.getAddress().getNeighborhood(),
                        userPutRequestDTO.getAddress().getCep(),
                        userPutRequestDTO.getAddress().getPostalCode(),
                        city
                )
        );
    }

    public User getBuiltedUser() {
        return this.user;
    }
}