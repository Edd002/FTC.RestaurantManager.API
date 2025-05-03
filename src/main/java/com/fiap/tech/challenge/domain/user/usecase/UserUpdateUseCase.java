package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;

public final class UserUpdateUseCase {

    private final User user;

    public UserUpdateUseCase(User actualUser, String passwordCryptoKey, UserPutRequestDTO userPutRequestDTO, City city) {
        this.user = new User(
                actualUser.getId(),
                userPutRequestDTO.getName(),
                userPutRequestDTO.getEmail(),
                userPutRequestDTO.getLogin(),
                passwordCryptoKey,
                userPutRequestDTO.getPassword(),
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