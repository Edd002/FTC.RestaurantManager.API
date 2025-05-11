package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.city.CityService;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.domain.user.dto.*;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.specification.UserSpecificationBuilder;
import com.fiap.tech.challenge.domain.user.usecase.UserCreateUseCase;
import com.fiap.tech.challenge.domain.user.usecase.UserUpdatePasswordUseCase;
import com.fiap.tech.challenge.domain.user.usecase.UserUpdateUseCase;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService extends BaseService<IUserRepository, User> {

    @Value("${crypto.key}")
    private String cryptoKey;

    private final IUserRepository userRepository;
    private final CityService cityService;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(IUserRepository userRepository, CityService cityService, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cityService = cityService;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserResponseDTO create(UserPostRequestDTO userPostRequestDTO) {
        User newUser = new UserCreateUseCase(userPostRequestDTO, cryptoKey, cityService.findByHashId(userPostRequestDTO.getAddress().getHashIdCity())).getBuiltedUser();
        return modelMapper.map(save(newUser), UserResponseDTO.class);
    }

    @Transactional
    public UserResponseDTO update(UserPutRequestDTO userPutRequestDTO) {
        User updatedUser = new UserUpdateUseCase(AuthUserContextHolder.getAuthUser(), cryptoKey, userPutRequestDTO, cityService.findByHashId(userPutRequestDTO.getAddress().getHashIdCity())).getBuiltedUser();
        return modelMapper.map(save(updatedUser), UserResponseDTO.class);
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO) {
        save(new UserUpdatePasswordUseCase(AuthUserContextHolder.getAuthUser(), cryptoKey, userUpdatePasswordPatchRequestDTO).getBuiltedUser());
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
    public UserResponseDTO find() {
        return modelMapper.map(AuthUserContextHolder.getAuthUser(), UserResponseDTO.class);
    }

    @Transactional
    public void delete() {
        delete(AuthUserContextHolder.getAuthUser());
        SecurityContextHolder.clearContext();
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException(String.format("O usuário com o login %s não foi encontrado.", login)));
    }

    @Override
    public User findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O usuário com o hash id %s não foi encontrado.", hashId));
    }
}