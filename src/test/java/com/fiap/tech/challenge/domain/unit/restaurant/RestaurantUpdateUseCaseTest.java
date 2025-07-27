package com.fiap.tech.challenge.domain.unit.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fiap.tech.challenge.domain.address.dto.AddressPutRequestDTO;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.factory.RestaurantTestFactory;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.domain.restaurant.usecase.RestaurantUpdateUseCase;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RestaurantUpdateUseCaseTest {

    @Mock
    private RestaurantPutRequestDTO dtoRestaurant;
    @Mock
    private AddressPutRequestDTO dtoAddress;

    @Mock
    private City city;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception{
        openMocks.close();
    }

    @Test
    @DisplayName("Teste de sucesso - Deve atualizar o restaurante com dados válidos.")
    void shouldUpdateRestaurantWithValidData() {
        Restaurant existingRestaurant = RestaurantTestFactory.loadEntityRestaurant();

        when(dtoRestaurant.getName()).thenReturn("New Restaurant");
        when(dtoRestaurant.getType()).thenReturn(RestaurantTypeEnum.CAFETERIA.name());

        Date breakfastOpen = RestaurantTestFactory.getDateWithTime(2025, Calendar.AUGUST, 14, 6, 0);
        Date breakfastClose = RestaurantTestFactory.getDateWithTime(2025, Calendar.AUGUST, 14, 10, 0);
        Date lunchOpen = RestaurantTestFactory.getDateWithTime(2025, Calendar.AUGUST, 14, 12, 0);
        Date lunchClose = RestaurantTestFactory.getDateWithTime(2025, Calendar.AUGUST, 14, 15, 0);
        Date dinnerOpen = RestaurantTestFactory.getDateWithTime(2025, Calendar.AUGUST, 14, 18, 0);
        Date dinnerClose = RestaurantTestFactory.getDateWithTime(2025, Calendar.AUGUST, 14, 22, 0);

        when(dtoRestaurant.getBreakfastOpeningHours()).thenReturn(breakfastOpen);
        when(dtoRestaurant.getBreakfastClosingHours()).thenReturn(breakfastClose);
        when(dtoRestaurant.getLunchOpeningHours()).thenReturn(lunchOpen);
        when(dtoRestaurant.getLunchClosingHours()).thenReturn(lunchClose);
        when(dtoRestaurant.getDinnerOpeningHours()).thenReturn(dinnerOpen);
        when(dtoRestaurant.getDinnerClosingHours()).thenReturn(dinnerClose);

        when(dtoAddress.getDescription()).thenReturn("Rua das Flores");
        when(dtoAddress.getNumber()).thenReturn("456");
        when(dtoAddress.getComplement()).thenReturn("Sala 2");
        when(dtoAddress.getNeighborhood()).thenReturn("Bela Vista");
        when(dtoAddress.getCep()).thenReturn("01310-000");
        when(dtoAddress.getPostalCode()).thenReturn("01310000");

        when(dtoRestaurant.getAddress()).thenReturn(dtoAddress);

        Restaurant updated = new RestaurantUpdateUseCase(existingRestaurant, city, dtoRestaurant).getRebuiltedRestaurant();

        assertEquals("New Restaurant", updated.getName());
        assertEquals(RestaurantTypeEnum.CAFETERIA, updated.getType());
        assertEquals(breakfastOpen, updated.getBreakfastOpeningHours());
        assertEquals(dtoAddress.getCep(), updated.getAddress().getCep());
    }

}