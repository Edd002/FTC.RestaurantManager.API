package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;

public class UserUpdateUseCase {

    private final User user;

    public UserUpdateUseCase(String hashId, UserPutRequestDTO userPutRequestDTO) {
        user = new User();
        this.user.setHashId(hashId);
        this.user.setName(userPutRequestDTO.getName());
        this.user.setEmail(userPutRequestDTO.getEmail());
        this.user.setLogin(userPutRequestDTO.getLogin());
        this.user.setPassword(userPutRequestDTO.getPassword());
        this.user.getAddress().setDescription(userPutRequestDTO.getAddress().getDescription());
        this.user.getAddress().setNumber(userPutRequestDTO.getAddress().getNumber());
        this.user.getAddress().setComplement(userPutRequestDTO.getAddress().getComplement());
        this.user.getAddress().setComplement(userPutRequestDTO.getAddress().getComplement());
        this.user.getAddress().setNeighborhood(userPutRequestDTO.getAddress().getNeighborhood());
        this.user.getAddress().setCep(userPutRequestDTO.getAddress().getCep());
        this.user.getAddress().setPostalCode(userPutRequestDTO.getAddress().getPostalCode());
        this.user.getAddress().setCityByHashId(userPutRequestDTO.getAddress().getHashIdCity());
    }

    public User buildUser() {
        return this.user;
    }
}