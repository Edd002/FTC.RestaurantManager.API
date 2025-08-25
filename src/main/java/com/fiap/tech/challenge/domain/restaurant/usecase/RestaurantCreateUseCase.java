package com.fiap.tech.challenge.domain.restaurant.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import lombok.NonNull;

public class RestaurantCreateUseCase {

    private final Restaurant restaurant;

    public RestaurantCreateUseCase(@NonNull City city, @NonNull RestaurantPostRequestDTO restaurantPostRequestDTO) {
        this.restaurant = buildRestaurant(city, restaurantPostRequestDTO);
    }

    private Restaurant buildRestaurant(City city, RestaurantPostRequestDTO restaurantPostRequestDTO) {
        return new Restaurant(
                restaurantPostRequestDTO.getName(),
                restaurantPostRequestDTO.getBreakfastOpeningHours(),
                restaurantPostRequestDTO.getBreakfastClosingHours(),
                restaurantPostRequestDTO.getBreakfastLimitReservations(),
                restaurantPostRequestDTO.getLunchOpeningHours(),
                restaurantPostRequestDTO.getLunchClosingHours(),
                restaurantPostRequestDTO.getLunchLimitReservations(),
                restaurantPostRequestDTO.getDinnerOpeningHours(),
                restaurantPostRequestDTO.getDinnerClosingHours(),
                restaurantPostRequestDTO.getDinnerLimitReservations(),
                RestaurantTypeEnum.valueOf(restaurantPostRequestDTO.getType()),
                new Menu(),
                new Address(
                        restaurantPostRequestDTO.getAddress().getDescription(),
                        restaurantPostRequestDTO.getAddress().getNumber(),
                        restaurantPostRequestDTO.getAddress().getComplement(),
                        restaurantPostRequestDTO.getAddress().getNeighborhood(),
                        restaurantPostRequestDTO.getAddress().getCep(),
                        restaurantPostRequestDTO.getAddress().getPostalCode(),
                        city
                )
        );
    }

    public Restaurant getBuiltedRestaurant() {
        return this.restaurant;
    }
}