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
public class RestaurantService extends BaseService<IRestaurantRepository, Restaurant> {

    private final CityService cityService;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(CityService cityService, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.cityService = cityService;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public RestaurantResponseDTO create(RestaurantPostRequestDTO restaurantPostRequestDTO) {
        City city = cityService.findByHashId(restaurantPostRequestDTO.getAddress().getHashIdCity());
        Restaurant newRestaurant = new RestaurantCreateUseCase(AuthUserContextHolder.getAuthUser(), city, restaurantPostRequestDTO).getBuiltedRestaurant();
        return modelMapper.map(save(newRestaurant), RestaurantResponseDTO.class);
    }

    @Transactional
    public RestaurantResponseDTO update(String hashId, RestaurantPutRequestDTO restaurantPutRequestDTO) {
        Restaurant existingRestaurant = findByHashId(hashId);
        City city = cityService.findByHashId(restaurantPutRequestDTO.getAddress().getHashIdCity());
        Restaurant updatedRestaurant = new RestaurantUpdateUseCase(AuthUserContextHolder.getAuthUser(), existingRestaurant, city, restaurantPutRequestDTO).getBuiltedRestaurant();
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
        deleteByHashId(hashId);
    }

    @Override
    public Restaurant findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O restaurante com o hash id %s não foi encontrado.", hashId));
    }
}