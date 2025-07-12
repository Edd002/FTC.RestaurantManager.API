package com.fiap.tech.challenge.domain.usertype;

import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypeGetFilter;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePutRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypeResponseDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.domain.usertype.specification.UserTypeSpecificationBuilder;
import com.fiap.tech.challenge.domain.usertype.usecase.UserTypeCheckForDeleteNoAssociateUserUseCase;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
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
public class UserTypeService extends BaseService<IUserTypeRepository, UserType> {

    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserTypeService(PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserTypeResponseDTO create(UserTypePostRequestDTO userTypePostRequestDTO) {
        UserType newUserType = new UserType(userTypePostRequestDTO.getName());
        return modelMapper.map(save(newUserType), UserTypeResponseDTO.class);
    }

    @Transactional
    public UserResponseDTO update(String hashId, UserTypePutRequestDTO userTypePutRequestDTO) {
        UserType existingUserType = findByHashId(hashId);
        UserType updatedUserType = new UserType(existingUserType.getId(), userTypePutRequestDTO.getName());
        return modelMapper.map(save(updatedUserType), UserResponseDTO.class);
    }

    @Transactional
    public Page<UserTypeResponseDTO> find(UserTypeGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<UserType>> specification = new UserTypeSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(userType -> modelMapper.map(userType, UserTypeResponseDTO.class));
    }

    @Transactional
    public UserTypeResponseDTO find(String hashId) {
        return modelMapper.map(findByHashId(hashId), UserTypeResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        UserType existingUserType = findByHashId(hashId);
        if (new UserTypeCheckForDeleteNoAssociateUserUseCase(existingUserType).hasNoUser()) {
            delete(existingUserType);
        }
    }

    @Override
    public UserType findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O tipo de usuário com o hash id %s não foi encontrado.", hashId));
    }
}