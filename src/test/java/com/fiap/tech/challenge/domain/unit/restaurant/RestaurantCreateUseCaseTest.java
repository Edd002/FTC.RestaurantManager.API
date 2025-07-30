package com.fiap.tech.challenge.domain.unit.restaurant;

import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.factory.RestaurantTestFactory;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.domain.restaurant.usecase.RestaurantCreateUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantCreateUseCaseTest {

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
    @DisplayName("Teste de sucesso - Deve criar um restaurante com as informações fornecidas")
    void shouldCreateRestaurant() {
        RestaurantPostRequestDTO restaurantPostRequestDTO = RestaurantTestFactory.loadValidPostRequestDTO();

        Restaurant restaurant = new RestaurantCreateUseCase(city, restaurantPostRequestDTO).getBuiltedRestaurant();

        assertEquals("Churrascaria teste", restaurant.getName());
        assertEquals(parseHour("07:00"), restaurant.getBreakfastOpeningHours());
        assertEquals(parseHour("10:00"), restaurant.getBreakfastClosingHours());
        assertEquals(parseHour("14:30"), restaurant.getLunchOpeningHours());
        assertEquals(parseHour("19:00"), restaurant.getLunchClosingHours());
        assertEquals(parseHour("19:00"), restaurant.getDinnerOpeningHours());
        assertEquals(parseHour("23:00"), restaurant.getDinnerClosingHours());
        assertEquals(RestaurantTypeEnum.STEAKHOUSE, restaurant.getType());
        assertEquals("Teste Descrição New", restaurant.getAddress().getDescription());
        assertEquals("100", restaurant.getAddress().getNumber());
        assertEquals("Sala 03", restaurant.getAddress().getComplement());
        assertEquals("Bairro teste", restaurant.getAddress().getNeighborhood());
        assertEquals("12345-789", restaurant.getAddress().getCep());
        assertEquals("1234-4321", restaurant.getAddress().getPostalCode());
    }

    private Date parseHour(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date hour = sdf.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(hour);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}