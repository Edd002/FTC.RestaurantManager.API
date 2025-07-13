package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import lombok.NonNull;

public final class UserCreateUseCase {

    private final User user;

    public UserCreateUseCase(@NonNull User loggedUser, @NonNull UserType userType, @NonNull City city, @NonNull UserPostRequestDTO userPostRequestDTO, @NonNull String passwordCryptoKey) {
        if (!DefaultUserTypeEnum.isUserOwner(loggedUser) && DefaultUserTypeEnum.isTypeOwner(userPostRequestDTO.getType())) {
            throw new AuthorizationException("O usuário deve ser dono de restaurante para criar outros usuários com esse mesmo tipo.");
        }
        this.user = buildUser(userType, city, userPostRequestDTO, passwordCryptoKey);
    }

    public UserCreateUseCase(@NonNull UserType userType, @NonNull City city, @NonNull UserPostRequestDTO userPostRequestDTO, @NonNull String passwordCryptoKey) {
        if (DefaultUserTypeEnum.isTypeOwner(userPostRequestDTO.getType())) {
            throw new AuthorizationException("Para criar um usuário como dono de restaurante é necessário estar autenticado com um usuário com esse mesmo tipo.");
        }
        this.user = buildUser(userType, city, userPostRequestDTO, passwordCryptoKey);
    }

    private User buildUser(UserType userType, City city, UserPostRequestDTO userPostRequestDTO, String passwordCryptoKey) {
        return new User(
                userPostRequestDTO.getName(),
                userPostRequestDTO.getEmail(),
                userPostRequestDTO.getLogin(),
                passwordCryptoKey,
                userPostRequestDTO.getPassword(),
                userType,
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