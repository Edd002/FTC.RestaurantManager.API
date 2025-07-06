package com.fiap.tech.challenge.domain.restaurant;

import com.fiap.tech.challenge.domain.city.CityService;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantGetFilter;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.specification.RestaurantSpecificationBuilder;
import com.fiap.tech.challenge.domain.restaurant.usecase.RestaurantCreateUseCase;
import com.fiap.tech.challenge.domain.restaurant.usecase.RestaurantUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserService;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.CheckForDeleteRestaurantUserOnlyOwnerUseCase;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.BaseService;
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
public class RestaurantService extends BaseService<IRestaurantRepository, Restaurant> {

    private final CityService cityService;
    private final RestaurantUserService restaurantUserService;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(CityService cityService, @Lazy RestaurantUserService restaurantUserService, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.cityService = cityService;
        this.restaurantUserService = restaurantUserService;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public RestaurantResponseDTO create(RestaurantPostRequestDTO restaurantPostRequestDTO) {
        City city = cityService.findByHashId(restaurantPostRequestDTO.getAddress().getHashIdCity());
        Restaurant newSavedRestaurant = save(new RestaurantCreateUseCase(city, restaurantPostRequestDTO).getBuiltedRestaurant());
        restaurantUserService.save(new RestaurantUser(newSavedRestaurant, AuthUserContextHolder.getAuthUser()));
        return modelMapper.map(newSavedRestaurant, RestaurantResponseDTO.class);
    }

    @Transactional
    public RestaurantResponseDTO update(String hashId, RestaurantPutRequestDTO restaurantPutRequestDTO) {
        Restaurant existingRestaurant = restaurantUserService.findByRestaurantAndUser(findByHashId(hashId), AuthUserContextHolder.getAuthUser()).getRestaurant();
        City city = cityService.findByHashId(restaurantPutRequestDTO.getAddress().getHashIdCity());
        Restaurant updatedRestaurant = new RestaurantUpdateUseCase(existingRestaurant, city, restaurantPutRequestDTO).getBuiltedRestaurant();
        return modelMapper.map(save(updatedRestaurant), RestaurantResponseDTO.class);
    }

    @Transactional
    public Page<RestaurantResponseDTO> find(RestaurantGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<Restaurant>> specification = new RestaurantSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(restaurant -> modelMapper.map(restaurant, RestaurantResponseDTO.class));
    }

    @Transactional
    public RestaurantResponseDTO find(String hashId) {
        return modelMapper.map(findByHashId(hashId), RestaurantResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        Restaurant existingRestaurant = restaurantUserService.findByRestaurantAndUser(findByHashId(hashId), AuthUserContextHolder.getAuthUser()).getRestaurant();
        if (new CheckForDeleteRestaurantUserOnlyOwnerUseCase(loggedUser, existingRestaurant.getRestaurantUsers()).isAllowedToDelete()) {
            delete(existingRestaurant);
        }
    }

    @Override
    public Restaurant findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O restaurante com o hash id %s não foi encontrado.", hashId));
    }
}