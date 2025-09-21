package com.fiap.tech.challenge.config.security.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum PathFilterEnum {

    API_V1_CITIES_FILTER_GET(HttpMethod.GET, "/api/v1/cities/filter"),
    API_V1_CITIES_GET(HttpMethod.GET, "/api/v1/cities"),
    API_V1_MENU_ITEMS_DELETE(HttpMethod.DELETE, "/api/v1/menu-items"),
    API_V1_MENU_ITEMS_FILTER_GET(HttpMethod.GET, "/api/v1/menu-items/filter"),
    API_V1_MENU_ITEMS_GET(HttpMethod.GET, "/api/v1/menu-items"),
    API_V1_MENU_ITEMS_POST(HttpMethod.POST, "/api/v1/menu-items"),
    API_V1_MENU_ITEMS_PUT(HttpMethod.PUT, "/api/v1/menu-items"),
    API_V1_JWTS_VALIDATE_GET(HttpMethod.GET, "/api/v1/jwts/validate"),
    API_V1_JWTS_GENERATE_POST(HttpMethod.POST, "/api/v1/jwts/generate"),
    API_V1_JWTS_INVALIDATE_POST(HttpMethod.POST, "/api/v1/jwts/invalidate"),
    API_V1_MENUS_PUT(HttpMethod.PUT, "/api/v1/menus"),
    API_V1_ORDERS_FILTER_GET(HttpMethod.GET, "/api/v1/orders/filter"),
    API_V1_ORDERS_GET(HttpMethod.GET, "/api/v1/orders"),
    API_V1_ORDERS_CANCEL(HttpMethod.PATCH, "/api/v1/orders/cancel"),
    API_V1_ORDERS_CHANGE_STATUS(HttpMethod.PATCH, "/api/v1/orders/change-status"),
    API_V1_ORDERS_CHANGE_TYPE(HttpMethod.PATCH, "/api/v1/orders/change-type"),
    API_V1_ORDERS_CHANGE_POST(HttpMethod.POST, "/api/v1/orders"),
    API_V1_RESERVATIONS_FILTER_GET(HttpMethod.GET, "/api/v1/reservations/filter"),
    API_V1_RESERVATIONS_GET(HttpMethod.GET, "/api/v1/reservations"),
    API_V1_RESERVATIONS_CANCEL(HttpMethod.PATCH, "/api/v1/reservations/cancel"),
    API_V1_RESERVATIONS_CHANGE_STATUS(HttpMethod.PATCH, "/api/v1/reservations/change-status"),
    API_V1_RESERVATIONS_CHANGE_POST(HttpMethod.POST, "/api/v1/reservations"),
    API_V1_RESTAURANTS_DELETE(HttpMethod.DELETE, "/api/v1/restaurants"),
    API_V1_RESTAURANTS_FILTER_GET(HttpMethod.GET, "/api/v1/restaurants/filter"),
    API_V1_RESTAURANTS_GET(HttpMethod.GET, "/api/v1/restaurants"),
    API_V1_RESTAURANTS_POST(HttpMethod.POST, "/api/v1/restaurants"),
    API_V1_RESTAURANTS_PUT(HttpMethod.PUT, "/api/v1/restaurants"),
    API_V1_USERS_DELETE(HttpMethod.DELETE, "/api/v1/users"),
    API_V1_USERS_GET(HttpMethod.GET, "/api/v1/users"),
    API_V1_USERS_FILTER_GET(HttpMethod.GET, "/api/v1/users/filter"),
    API_V1_USERS_PATCH(HttpMethod.PATCH, "/api/v1/users/change-password"),
    API_V1_USERS_POST(HttpMethod.POST, "/api/v1/users"),
    API_V1_USERS_PUT(HttpMethod.PUT, "/api/v1/users"),
    API_V1_USER_TYPES_DELETE(HttpMethod.DELETE, "/api/v1/user-types"),
    API_V1_USER_TYPES_GET(HttpMethod.GET, "/api/v1/user-types"),
    API_V1_USER_TYPES_FILTER_GET(HttpMethod.GET, "/api/v1/user-types/filter"),
    API_V1_USER_TYPES_POST(HttpMethod.POST, "/api/v1/user-types"),
    API_V1_USER_TYPES_PUT(HttpMethod.PUT, "/api/v1/user-types"),
    API_V1_RESTAURANT_USERS_DELETE(HttpMethod.DELETE, "/api/v1/restaurant-users"),
    API_V1_RESTAURANT_USERS_FILTER_GET(HttpMethod.GET, "/api/v1/restaurant-users/filter"),
    API_V1_RESTAURANT_USERS_GET(HttpMethod.GET, "/api/v1/restaurant-users"),
    API_V1_RESTAURANT_USERS_POST(HttpMethod.POST, "/api/v1/restaurant-users");

    private final HttpMethod httpMethod;
    private final String path;

    public String getCompletePath() {
        return "/restaurant-manager" + this.getPath();
    }

    public static List<String> getIgnoreFilterConfigPaths() {
        return Arrays.asList(
                "/restaurant-manager/swagger-ui/index.html",
                "/restaurant-manager/swagger-ui/swagger-ui.css",
                "/restaurant-manager/swagger-ui/swagger-ui-standalone-preset.js",
                "/restaurant-manager/swagger-ui/swagger-ui-bundle.js",
                "/restaurant-manager/v3/api-docs/swagger-config",
                "/restaurant-manager/swagger-ui/favicon-32x32.png",
                "/restaurant-manager/swagger-ui/favicon-16x16.png",
                "/restaurant-manager/v3/api-docs",
                "/restaurant-manager/h2-console",
                "/actuator/health"
        );
    }

    public static List<PathFilterEnum> getIgnoreResponseFilterPaths() {
        return Arrays.asList(
                API_V1_JWTS_VALIDATE_GET,
                API_V1_JWTS_GENERATE_POST,
                API_V1_JWTS_INVALIDATE_POST
        );
    }

    public static List<PathFilterEnum> getAllowedPathsWithoutAuthorization() {
        return Arrays.asList(
                API_V1_CITIES_FILTER_GET,
                API_V1_CITIES_GET,
                API_V1_MENU_ITEMS_FILTER_GET,
                API_V1_MENU_ITEMS_GET,
                API_V1_JWTS_GENERATE_POST,
                API_V1_RESTAURANTS_FILTER_GET,
                API_V1_RESTAURANTS_GET,
                API_V1_USERS_POST,
                API_V1_RESTAURANT_USERS_DELETE,
                API_V1_RESTAURANT_USERS_FILTER_GET,
                API_V1_RESTAURANT_USERS_GET,
                API_V1_RESTAURANT_USERS_POST
        );
    }
}