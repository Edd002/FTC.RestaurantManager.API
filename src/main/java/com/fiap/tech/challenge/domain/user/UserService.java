package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.user.dto.UserGetFilter;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.domain.user.specification.UserSpecificationBuilder;
import com.fiap.tech.challenge.domain.user.usecase.UserCreateUseCase;
import com.fiap.tech.challenge.domain.user.usecase.UserUpdateUseCase;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.entity.builder.PageableBuilder;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService extends BaseService<IUserRepository, User> {

    private final PageableBuilder pageableBuilder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserResponseDTO create(UserPostRequestDTO userPostRequestDTO) {
        User newUser = new UserCreateUseCase(userPostRequestDTO).buildUser();
        return modelMapper.map(save(newUser), UserResponseDTO.class);
    }

    @Transactional
    public UserResponseDTO update(String hashId, UserPutRequestDTO userPutRequestDTO) {
        User updatedUser = new UserUpdateUseCase(hashId, userPutRequestDTO).buildUser();
        return modelMapper.map(save(updatedUser), UserResponseDTO.class);
    }

    @Transactional
    public Page<UserResponseDTO> find(UserGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<User>> specification = new UserSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Transactional
    public UserResponseDTO find(String hashId) {
        return modelMapper.map(this.findByHashId(hashId), UserResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        deleteByHashId(hashId, String.format("O usuário com hash id %s não foi encontrado para ser excluído.", hashId));
    }

    @Override
    public User findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O usuário com o hash id %s não foi encontrado.", hashId));
    }
}