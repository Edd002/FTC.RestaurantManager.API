package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserTypeEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import lombok.NonNull;

public final class UserUpdateUseCase {

    private final User user;

    public UserUpdateUseCase(@NonNull User loggedUser, @NonNull City city, @NonNull UserPutRequestDTO userPutRequestDTO, @NonNull String passwordCryptoKey) {
        if (!loggedUser.getType().equals(UserTypeEnum.OWNER) && userPutRequestDTO.getType().equals(UserTypeEnum.OWNER.name())) {
            throw new AuthorizationException("O usuário não tem permissão para alterar o seu tipo para DONO (OWNER).");
        }
        this.user = buildUser(loggedUser, city, userPutRequestDTO, passwordCryptoKey);
    }

    private User buildUser(User loggedUser, City city, UserPutRequestDTO userPutRequestDTO, String passwordCryptoKey) {
        return new User(
                loggedUser.getId(),
                userPutRequestDTO.getName(),
                userPutRequestDTO.getEmail(),
                userPutRequestDTO.getLogin(),
                passwordCryptoKey,
                loggedUser.getPassword(),
                userPutRequestDTO.getType(),
                new Address(
                        loggedUser.getAddress().getId(),
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