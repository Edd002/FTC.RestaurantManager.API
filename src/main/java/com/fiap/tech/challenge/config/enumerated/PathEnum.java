package com.fiap.tech.challenge.config.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum PathEnum {

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
    API_V1_RESTAURANTS_USERS_DELETE(HttpMethod.DELETE, "/api/v1/restaurant-users"),
    API_V1_RESTAURANTS_USERS_FILTER_GET(HttpMethod.GET, "/api/v1/restaurant-users/filter"),
    API_V1_RESTAURANTS_USERS_GET(HttpMethod.GET, "/api/v1/restaurant-users"),
    API_V1_RESTAURANTS_USERS_POST(HttpMethod.POST, "/api/v1/restaurant-users");

    private final HttpMethod httpMethod;
    private final String path;

    public String getCompletePath() {
        return "/restaurant-manager" + this.getPath();
    }

    public String getPathMatchingAll() {
        return this.getPath() + "/**";
    }

    public static List<PathEnum> getIgnoreRequestFilterPaths() {
        return Arrays.asList(
                API_V1_CITIES_FILTER_GET,
                API_V1_CITIES_GET,
                API_V1_MENU_ITEMS_FILTER_GET,
                API_V1_MENU_ITEMS_GET,
                API_V1_JWTS_GENERATE_POST,
                API_V1_RESTAURANTS_FILTER_GET,
                API_V1_RESTAURANTS_GET,
                API_V1_USERS_POST,
                API_V1_RESTAURANTS_USERS_DELETE,
                API_V1_RESTAURANTS_USERS_FILTER_GET,
                API_V1_RESTAURANTS_USERS_GET,
                API_V1_RESTAURANTS_USERS_POST
        );
    }

    public static List<PathEnum> getIgnoreResponseFilterPaths() {
        return Arrays.asList(
                API_V1_JWTS_VALIDATE_GET,
                API_V1_JWTS_GENERATE_POST,
                API_V1_JWTS_INVALIDATE_POST
        );
    }
}