package com.fiap.tech.challenge.domain.restaurant.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import lombok.NonNull;

public class RestaurantUpdateUseCase {

    private final Restaurant restaurant;

    public RestaurantUpdateUseCase(@NonNull Restaurant existingRestaurant, @NonNull City city, @NonNull RestaurantPutRequestDTO restaurantPutRequestDTO) {
        this.restaurant = buildRestaurant(existingRestaurant, city, restaurantPutRequestDTO);
    }

    private Restaurant buildRestaurant(Restaurant existingRestaurant, City city, RestaurantPutRequestDTO restaurantPutRequestDTO) {
        return new Restaurant(
                existingRestaurant.getId(),
                restaurantPutRequestDTO.getName(),
                restaurantPutRequestDTO.getBreakfastOpeningHours(),
                restaurantPutRequestDTO.getBreakfastClosingHours(),
                restaurantPutRequestDTO.getLunchOpeningHours(),
                restaurantPutRequestDTO.getLunchClosingHours(),
                restaurantPutRequestDTO.getDinnerOpeningHours(),
                restaurantPutRequestDTO.getDinnerClosingHours(),
                RestaurantTypeEnum.valueOf(restaurantPutRequestDTO.getType()),
                existingRestaurant.getMenu(),
                new Address(
                        restaurantPutRequestDTO.getAddress().getDescription(),
                        restaurantPutRequestDTO.getAddress().getNumber(),
                        restaurantPutRequestDTO.getAddress().getComplement(),
                        restaurantPutRequestDTO.getAddress().getNeighborhood(),
                        restaurantPutRequestDTO.getAddress().getCep(),
                        restaurantPutRequestDTO.getAddress().getPostalCode(),
                        city
                )
        );
    }

    public Restaurant getBuiltedRestaurant() {
        return this.restaurant;
    }
}