package com.fiap.tech.challenge.domain.factory;

import static org.mockito.Mockito.mock;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;

import java.util.Calendar;
import java.util.Date;

public class RestaurantTestFactory {

    private static final String PATH_RESOURCE_USER = "/mock/restaurant/restaurant.json";

    public static Menu loadEmptyMenu() {
        return new Menu();
    }

    public static Address loadDefaultAddress() {
        City mockCity = mock(City.class);
        return new Address(
                "Av. Exemplo",
                "123",
                "",
                "Centro",
                "01000-000",
                "01000000",
                mockCity
        );
    }

    public static RestaurantPostRequestDTO loadValidPostRequestDTO() {
        return JsonUtil.objectFromJson(
                "restaurantPostRequestDTO",
                PATH_RESOURCE_USER,
                RestaurantPostRequestDTO.class,
                DatePatternEnum.DATE_FORMAT_HH_mm.getValue()
        );
    }

    public static Restaurant loadEntityRestaurant() {
        var name = "Old Restaurant";
        var type = RestaurantTypeEnum.QUICK_SERVICE_RESTAURANTS_OR_FAST_FOOD;

        Date breakfastOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 7, 0);
        Date breakfastClose = getDateWithTime(2025, Calendar.AUGUST, 14, 10, 0);
        Date lunchOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 12, 0);
        Date lunchClose = getDateWithTime(2025, Calendar.AUGUST, 14, 15, 0);
        Date dinnerOpen = getDateWithTime(2025, Calendar.AUGUST, 14, 18, 0);
        Date dinnerClose = getDateWithTime(2025, Calendar.AUGUST, 14, 22, 0);

        return new Restaurant(name, breakfastOpen, breakfastClose, lunchOpen, lunchClose, dinnerOpen, dinnerClose, type, loadEmptyMenu(), loadDefaultAddress());
    }

    public static Date getDateWithTime(int year, int month, int day, int hour, int minute) {
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
