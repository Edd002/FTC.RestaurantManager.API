package com.fiap.tech.challenge.domain.restaurantuser;

import com.fiap.tech.challenge.domain.restaurant.RestaurantService;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserGetFilter;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserResponseDTO;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.restaurantuser.specification.RestaurantUserSpecificationBuilder;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCreateUseCase;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
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
public class RestaurantUserService extends BaseService<IRestaurantUserRepository, RestaurantUser> {

    private final RestaurantService restaurantService;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantUserService(RestaurantService restaurantService, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.restaurantService = restaurantService;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public RestaurantUserResponseDTO create(RestaurantUserPostRequestDTO restaurantUserPostRequestDTO) {
        Restaurant restaurant = restaurantService.findByHashId(restaurantUserPostRequestDTO.getHashIdRestaurant());
        RestaurantUser newRestaurantUser = new RestaurantUserCreateUseCase(AuthUserContextHolder.getAuthUser(), restaurant).getBuiltedURestaurantUser();
        return modelMapper.map(save(newRestaurantUser), RestaurantUserResponseDTO.class);
    }

    @Transactional
    public Page<RestaurantUserResponseDTO> find(RestaurantUserGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<RestaurantUser>> specification = new RestaurantUserSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(restaurantUser -> modelMapper.map(restaurantUser, RestaurantUserResponseDTO.class));
    }

    @Transactional
    public RestaurantUserResponseDTO find(String hashId) {
        return modelMapper.map(findByHashId(hashId), RestaurantUserResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        deleteByHashId(hashId);
    }

    @Override
    public RestaurantUser findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O associação de usuário com restaurante com o hash id %s não foi encontrada.", hashId));
    }
}