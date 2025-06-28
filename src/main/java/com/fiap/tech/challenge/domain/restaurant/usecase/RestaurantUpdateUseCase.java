package com.fiap.tech.challenge.domain.restaurant.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import lombok.NonNull;

public class RestaurantUpdateUseCase {

    private final Restaurant restaurant;

    public RestaurantUpdateUseCase(@NonNull User loggedUser, @NonNull Restaurant existingRestaurant, @NonNull City city, @NonNull RestaurantPutRequestDTO restaurantPutRequestDTO) {
        if (!loggedUser.getRole().equals(UserRoleEnum.OWNER)) {
            throw new AuthorizationException("O usuário deve ser do tipo DONO (OWNER) para atualizar um restaurante.");
        }
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