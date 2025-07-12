package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserTypeEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import lombok.NonNull;

public final class UserCreateUseCase {

    private final User user;

    public UserCreateUseCase(@NonNull User loggedUser, @NonNull City city, @NonNull UserPostRequestDTO userPostRequestDTO, @NonNull String passwordCryptoKey) {
        if (!loggedUser.getType().equals(UserTypeEnum.OWNER) && userPostRequestDTO.getType().equals(UserTypeEnum.OWNER.name())) {
            throw new AuthorizationException("O usuário deve ser do tipo DONO (OWNER) para criar outros usuários com esse mesmo tipo.");
        }
        this.user = buildUser(city, userPostRequestDTO, passwordCryptoKey);
    }

    public UserCreateUseCase(@NonNull City city, @NonNull UserPostRequestDTO userPostRequestDTO, @NonNull String passwordCryptoKey) {
        if (userPostRequestDTO.getType().equals(UserTypeEnum.OWNER.name())) {
            throw new AuthorizationException("Para criar um usuário do tipo DONO (OWNER) é necessário estar autenticado com um usuário com esse mesmo tipo.");
        }
        this.user = buildUser(city, userPostRequestDTO, passwordCryptoKey);
    }

    private User buildUser(City city, UserPostRequestDTO userPostRequestDTO, String passwordCryptoKey) {
        return new User(
                userPostRequestDTO.getName(),
                userPostRequestDTO.getEmail(),
                userPostRequestDTO.getLogin(),
                passwordCryptoKey,
                userPostRequestDTO.getPassword(),
                userPostRequestDTO.getType(),
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