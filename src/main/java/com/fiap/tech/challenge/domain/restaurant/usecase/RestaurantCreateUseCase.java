package com.fiap.tech.challenge.domain.restaurant.usecase;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import lombok.NonNull;

public class RestaurantCreateUseCase {

    private final Restaurant restaurant;

    public RestaurantCreateUseCase(@NonNull User loggedUser, @NonNull City city, @NonNull RestaurantPostRequestDTO restaurantPostRequestDTO) {
        if (!loggedUser.getRole().equals(UserRoleEnum.OWNER)) {
            throw new AuthorizationException("O usuário deve ser do tipo DONO (OWNER) para cadastrar um restaurante.");
        }
        this.restaurant = buildRestaurant(city, restaurantPostRequestDTO);
    }

    private Restaurant buildRestaurant(City city, RestaurantPostRequestDTO restaurantPostRequestDTO) {
        return new Restaurant(
                restaurantPostRequestDTO.getName(),
                restaurantPostRequestDTO.getBreakfastOpeningHoursStart(),
                restaurantPostRequestDTO.getBreakfastClosingHoursStart(),
                restaurantPostRequestDTO.getLunchOpeningHoursStart(),
                restaurantPostRequestDTO.getLunchClosingHoursStart(),
                restaurantPostRequestDTO.getDinnerOpeningHoursStart(),
                restaurantPostRequestDTO.getDinnerClosingHoursStart(),
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