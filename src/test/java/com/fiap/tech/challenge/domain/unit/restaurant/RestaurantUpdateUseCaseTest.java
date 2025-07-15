package com.fiap.tech.challenge.domain.unit.restaurant;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
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

public class RestaurantUpdateUseCaseTest {

    @Mock
    private RestaurantUpdateUseCase restaurantUpdateUseCase;

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
    void shouldUpdateRestaurantWithValidData(){

    }

    private Restaurant anExistingRestaurant() {
        long id = 1L;
        var menu = new Menu();

        var baseCalendar = Calendar.getInstance();
        baseCalendar.set(Calendar.YEAR, 2025);
        baseCalendar.set(Calendar.MONTH, Calendar.AUGUST);
        baseCalendar.set(Calendar.DAY_OF_MONTH, 14);
        baseCalendar.set(Calendar.HOUR_OF_DAY, 0);
        baseCalendar.set(Calendar.MINUTE, 0);
        baseCalendar.set(Calendar.SECOND, 0);
        baseCalendar.set(Calendar.MILLISECOND, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2025);
        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.HOUR_OF_DAY, 7); // 07:00
        calendar.set(Calendar.MINUTE, 0);
        Date breakfastOpen = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 10); // 10:00
        calendar.set(Calendar.MINUTE, 0);
        Date breakfastClose = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 12); // 12:00
        calendar.set(Calendar.MINUTE, 0);
        Date lunchOpen = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 15); // 15:00
        calendar.set(Calendar.MINUTE, 0);
        Date lunchClose = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 18); // 18:00
        calendar.set(Calendar.MINUTE, 0);
        Date dinnerOpen = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 22); // 22:00
        calendar.set(Calendar.MINUTE, 0);
        Date dinnerClose = calendar.getTime();

        String name = "Old Restaurant";
        RestaurantTypeEnum type = RestaurantTypeEnum.QUICK_SERVICE_RESTAURANTS_OR_FAST_FOOD;
        City city = new City();
        Address address = new Address("Av. Exemplo", "123", "", "Centro", "01000-000", "01000000", city);

        return new Restaurant(id, name, breakfastOpen, breakfastClose, lunchOpen, lunchClose, dinnerOpen, dinnerClose, type, menu, address);
    }

    private City aCity() {
        // Adjust the constructor according to your real City entity
        return new City(1L, "São Paulo", "SP");
    }


}