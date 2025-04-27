package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.address.Address;
import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;

public final class UserCreateUseCase {

    private final User user;

    public UserCreateUseCase(UserPostRequestDTO userPostRequestDTO) {
        user = new User();
        this.user.setName(userPostRequestDTO.getName());
        this.user.setEmail(userPostRequestDTO.getEmail());
        this.user.setLogin(userPostRequestDTO.getLogin());
        this.user.setPassword(userPostRequestDTO.getPassword());
        this.user.setAddress(new Address());
        this.user.getAddress().setDescription(userPostRequestDTO.getAddress().getDescription());
        this.user.getAddress().setNumber(userPostRequestDTO.getAddress().getNumber());
        this.user.getAddress().setComplement(userPostRequestDTO.getAddress().getComplement());
        this.user.getAddress().setComplement(userPostRequestDTO.getAddress().getComplement());
        this.user.getAddress().setNeighborhood(userPostRequestDTO.getAddress().getNeighborhood());
        this.user.getAddress().setCep(userPostRequestDTO.getAddress().getCep());
        this.user.getAddress().setPostalCode(userPostRequestDTO.getAddress().getPostalCode());
        this.user.getAddress().setCityByHashId(userPostRequestDTO.getAddress().getHashIdCity());
    }

    public User getBuiltedUser() {
        return this.user;
    }
}