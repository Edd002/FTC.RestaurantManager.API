package com.fiap.tech.challenge.domain.restaurantuser;

import com.fiap.tech.challenge.domain.restaurant.RestaurantServiceGateway;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserGetFilter;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserResponseDTO;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.restaurantuser.specification.RestaurantUserSpecificationBuilder;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCheckForDeleteUseCase;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCreateUseCase;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
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
public class RestaurantUserServiceGateway extends BaseServiceGateway<IRestaurantUserRepository, RestaurantUser> {

    private final IRestaurantUserRepository restaurantUserRepository;
    private final RestaurantServiceGateway restaurantServiceGateway;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public RestaurantUserServiceGateway(IRestaurantUserRepository restaurantUserRepository, RestaurantServiceGateway restaurantServiceGateway, PageableBuilder pageableBuilder, ModelMapper modelMapperPresenter) {
        this.restaurantUserRepository = restaurantUserRepository;
        this.restaurantServiceGateway = restaurantServiceGateway;
        this.pageableBuilder = pageableBuilder;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public RestaurantUserResponseDTO create(RestaurantUserPostRequestDTO restaurantUserPostRequestDTO) {
        Restaurant restaurant = restaurantServiceGateway.findByHashId(restaurantUserPostRequestDTO.getHashIdRestaurant());
        RestaurantUser newRestaurantUser = new RestaurantUserCreateUseCase(AuthUserContextHolder.getAuthUser(), restaurant).getBuiltedURestaurantUser();
        return modelMapperPresenter.map(save(newRestaurantUser), RestaurantUserResponseDTO.class);
    }

    @Transactional
    public Page<RestaurantUserResponseDTO> find(RestaurantUserGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<RestaurantUser>> specification = new RestaurantUserSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(restaurantUser -> modelMapperPresenter.map(restaurantUser, RestaurantUserResponseDTO.class));
    }

    @Transactional
    public RestaurantUserResponseDTO find(String hashId) {
        return modelMapperPresenter.map(findByHashIdAndUser(hashId, AuthUserContextHolder.getAuthUser()), RestaurantUserResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        RestaurantUser existingRestaurantUser = findByHashIdAndUser(hashId, AuthUserContextHolder.getAuthUser());
        if (new RestaurantUserCheckForDeleteUseCase(loggedUser, restaurantUserRepository.findByRestaurant(existingRestaurantUser.getRestaurant())).isAllowedToDelete()) {
            flush();
            deleteByHashId(existingRestaurantUser.getHashId());
        }
    }

    @Transactional
    public RestaurantUser findByHashIdAndUser(String hashId, User user) {
        return restaurantUserRepository.findByHashIdAndUser(hashId, user).orElseThrow(() -> new EntityNotFoundException(String.format("Nenhuma associação para o usuário com o restaurante com hash id %s foi encontrada.", hashId)));
    }

    @Transactional
    public RestaurantUser findByRestaurantHashIdAndUser(String hashId, User user) {
        return restaurantUserRepository.findByRestaurantHashIdAndUser(hashId, user).orElseThrow(() -> new EntityNotFoundException(String.format("Nenhuma associação para o usuário com o restaurante com hash id %s foi encontrada.", hashId)));
    }

    @Transactional
    public RestaurantUser findByRestaurantAndUser(Restaurant restaurant, User user) {
        return restaurantUserRepository.findByRestaurantAndUser(restaurant, user).orElseThrow(() -> new EntityNotFoundException("Nenhuma associação do usuário com o restaurante foi encontrada."));
    }

    @Override
    public RestaurantUser findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("A associação do usuário com restaurante com o hash id %s não foi encontrada.", hashId));
    }
}