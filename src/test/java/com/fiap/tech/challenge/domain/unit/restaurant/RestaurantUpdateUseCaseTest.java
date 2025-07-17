package com.fiap.tech.challenge.domain.unit.restaurant;

import com.fiap.tech.challenge.domain.address.dto.AddressPutRequestDTO;
import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.domain.restaurant.usecase.RestaurantUpdateUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RestaurantUpdateUseCaseTest {

    @Mock
    private RestaurantPutRequestDTO dtoRestaurant;
    @Mock
    private AddressPutRequestDTO dtoAddress;

    private final Long id = 1L;

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
        Restaurant existingRestaurant = anExistingRestaurant();
        City city = new City(id);

        when(dtoRestaurant.getName()).thenReturn("New Restaurant");
        when(dtoRestaurant.getType()).thenReturn(RestaurantTypeEnum.CAFETERIA.name());

        Date breakfastOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 6, 0);
        Date breakfastClose = getDateWithTime(2025, Calendar.AUGUST, 14, 10, 0);
        Date lunchOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 12, 0);
        Date lunchClose = getDateWithTime(2025, Calendar.AUGUST, 14, 15, 0);
        Date dinnerOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 18, 0);
        Date dinnerClose = getDateWithTime(2025, Calendar.AUGUST, 14, 22, 0);

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

        Restaurant updated = new RestaurantUpdateUseCase(existingRestaurant, city, dtoRestaurant).getBuiltedRestaurant();

        assertEquals("New Restaurant", updated.getName());
        assertEquals(RestaurantTypeEnum.CAFETERIA, updated.getType());
        assertEquals(breakfastOpen, updated.getBreakfastOpeningHours());
        assertEquals(dtoAddress.getCep(), updated.getAddress().getCep());
    }


    private Restaurant anExistingRestaurant() {
        var menu = new Menu();
        var name = "Old Restaurant";
        var type = RestaurantTypeEnum.QUICK_SERVICE_RESTAURANTS_OR_FAST_FOOD;

        Date breakfastOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 7, 0);
        Date breakfastClose = getDateWithTime(2025, Calendar.AUGUST, 14, 10, 0);
        Date lunchOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 12, 0);
        Date lunchClose = getDateWithTime(2025, Calendar.AUGUST, 14, 15, 0);
        Date dinnerOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 18, 0);
        Date dinnerClose = getDateWithTime(2025, Calendar.AUGUST, 14, 22, 0);

        City city = new City(id);
        Address address = new Address(
                "Av. Exemplo",
                "123",
                "",
                "Centro",
                "01000-000",
                "01000000",
                city
        );
        Restaurant restaurant = new Restaurant(id, name, breakfastOpen, breakfastClose, lunchOpen, lunchClose, dinnerOpen, dinnerClose, type, menu, address);

        return restaurant;
    }

    private Date getDateWithTime(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}