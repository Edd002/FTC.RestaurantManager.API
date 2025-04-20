package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.address.Address;
import com.fiap.tech.challenge.domain.user.dto.UserGetFilter;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.domain.user.specification.UserSpecificationBuilder;
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

    private final ModelMapper modelMapper;
    private final PageableBuilder pageableBuilder;

    @Autowired
    public UserService(ModelMapper modelMapper, PageableBuilder pageableBuilder) {
        this.modelMapper = modelMapper;
        this.pageableBuilder = pageableBuilder;
    }

    @Transactional
    public UserResponseDTO create(UserPostRequestDTO userPostRequestDTO) {
        User newUser = modelMapper.map(userPostRequestDTO, User.class);
        newUser.setAddress(new Address());
        return modelMapper.map(save(newUser), UserResponseDTO.class);
    }

    @Transactional
    public UserResponseDTO update(String hashId, UserPutRequestDTO userPutRequestDTO) {
        User updatedUser = modelMapper.map(userPutRequestDTO, User.class);
        updatedUser.setId(findByHashId(hashId).getId());
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
        return modelMapper.map(findByHashId(hashId, String.format("O usuário com hash id %s não foi encontrado.", hashId)), UserResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        deleteByHashId(hashId, String.format("O usuário com hash id %s não foi encontrado para ser excluído.", hashId));
    }
}