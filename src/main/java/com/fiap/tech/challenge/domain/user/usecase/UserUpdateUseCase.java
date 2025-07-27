package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.EntityCannotBeUpdatedException;
import lombok.NonNull;

public final class UserUpdateUseCase {

    private final User user;

    public UserUpdateUseCase(@NonNull User loggedUser, @NonNull UserType userType, @NonNull City city, @NonNull UserPutRequestDTO userPutRequestDTO, @NonNull String passwordCryptoKey) {
        if (!loggedUser.getRestaurantUsers().isEmpty() && (DefaultUserTypeEnum.isTypeOwner(loggedUser.getType().getName()) && !DefaultUserTypeEnum.isTypeOwner(userPutRequestDTO.getType()))) {
            throw new EntityCannotBeUpdatedException("O usuário é um dono de restaurante com restaurantes associados e por isso não pode ter seu tipo alterado.");
        }
        this.user = rebuildUser(loggedUser, userType, city, userPutRequestDTO, passwordCryptoKey);
    }

    private User rebuildUser(User loggedUser, UserType userType, City city, UserPutRequestDTO userPutRequestDTO, String passwordCryptoKey) {
        return loggedUser.rebuild(
                userPutRequestDTO.getName(),
                userPutRequestDTO.getEmail(),
                userPutRequestDTO.getLogin(),
                userType,
                loggedUser.getAddress().rebuild(
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

    public User getRebuiltedUser() {
        return this.user;
    }
}