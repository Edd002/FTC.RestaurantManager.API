package com.fiap.tech.challenge.domain.restaurant;

import com.fiap.tech.challenge.domain.city.CityServiceGateway;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantGetFilter;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.specification.RestaurantSpecificationBuilder;
import com.fiap.tech.challenge.domain.restaurant.usecase.RestaurantCreateUseCase;
import com.fiap.tech.challenge.domain.restaurant.usecase.RestaurantUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserServiceGateway;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RestaurantServiceGateway extends BaseServiceGateway<IRestaurantRepository, Restaurant> {

    private final CityServiceGateway cityServiceGateway;
    private final RestaurantUserServiceGateway restaurantUserServiceGateway;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public RestaurantServiceGateway(CityServiceGateway cityServiceGateway, @Lazy RestaurantUserServiceGateway restaurantUserServiceGateway, PageableBuilder pageableBuilder, ModelMapper modelMapperPresenter) {
        this.cityServiceGateway = cityServiceGateway;
        this.restaurantUserServiceGateway = restaurantUserServiceGateway;
        this.pageableBuilder = pageableBuilder;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public RestaurantResponseDTO create(RestaurantPostRequestDTO restaurantPostRequestDTO) {
        City city = cityServiceGateway.findByHashId(restaurantPostRequestDTO.getAddress().getHashIdCity());
        Restaurant newSavedRestaurant = save(new RestaurantCreateUseCase(city, restaurantPostRequestDTO).getBuiltedRestaurant());
        restaurantUserServiceGateway.save(new RestaurantUser(newSavedRestaurant, AuthUserContextHolder.getAuthUser()));
        return modelMapperPresenter.map(newSavedRestaurant, RestaurantResponseDTO.class);
    }

    @Transactional
    public RestaurantResponseDTO update(String hashId, RestaurantPutRequestDTO restaurantPutRequestDTO) {
        Restaurant existingRestaurant = restaurantUserServiceGateway.findByRestaurantAndUser(findByHashId(hashId), AuthUserContextHolder.getAuthUser()).getRestaurant();
        City city = cityServiceGateway.findByHashId(restaurantPutRequestDTO.getAddress().getHashIdCity());
        Restaurant updatedRestaurant = new RestaurantUpdateUseCase(existingRestaurant, city, restaurantPutRequestDTO).getRebuiltedRestaurant();
        return modelMapperPresenter.map(save(updatedRestaurant), RestaurantResponseDTO.class);
    }

    @Transactional
    public Page<RestaurantResponseDTO> find(RestaurantGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<Restaurant>> specification = new RestaurantSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(restaurant -> modelMapperPresenter.map(restaurant, RestaurantResponseDTO.class));
    }

    @Transactional
    public RestaurantResponseDTO find(String hashId) {
        return modelMapperPresenter.map(findByHashId(hashId), RestaurantResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        Restaurant existingRestaurant = restaurantUserServiceGateway.findByRestaurantAndUser(findByHashId(hashId), AuthUserContextHolder.getAuthUser()).getRestaurant();
        delete(existingRestaurant);
    }

    @Override
    public Restaurant findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O restaurante com o hash id %s não foi encontrado.", hashId));
    }
}