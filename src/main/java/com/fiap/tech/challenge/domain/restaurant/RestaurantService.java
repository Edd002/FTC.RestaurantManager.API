package com.fiap.tech.challenge.domain.restaurant;

import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService extends BaseService<IRestaurantRepository, Restaurant> {

    private final IRestaurantRepository restaurantRepository;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(IRestaurantRepository restaurantRepository, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Override
    public Restaurant findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O restaurante com o hash id %s não foi encontrado.", hashId));
    }
}