package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;

public final class UserUpdateUseCase {

    private final User user;

    public UserUpdateUseCase(User actualUser, UserPutRequestDTO userPutRequestDTO) {
        this.user = new User(
                actualUser.getId(),
                userPutRequestDTO.getName(),
                userPutRequestDTO.getEmail(),
                userPutRequestDTO.getLogin(),
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
                        userPutRequestDTO.getAddress().getHashIdCity()
                )
        );
    }

    public User getBuiltedUser() {
        return this.user;
    }
}