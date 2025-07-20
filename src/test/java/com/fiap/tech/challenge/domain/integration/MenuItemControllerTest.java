package com.fiap.tech.challenge.domain.integration;

import com.fiap.tech.challenge.domain.menu.dto.MenuBatchPutRequestDTO;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserResponseDTO;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.component.DatabaseManagementComponent;
import com.fiap.tech.challenge.global.component.HttpBodyComponent;
import com.fiap.tech.challenge.global.component.HttpHeaderComponent;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuItemControllerTest {

    private static final String PATH_RESOURCE_MENU_ITEM = "/mock/menuitem/menuitem.json";
    private static final String PATH_RESOURCE_MENU = "/mock/menu/menu.json";
    private static final String PATH_RESOURCE_RESTAURANT = "/mock/restaurant/restaurant.json";
    private static final String PATH_RESOURCE_RESTAURANT_USER = "/mock/restaurantuser/restaurantuser.json";

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public MenuItemControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
        this.httpHeaderComponent = httpHeaderComponent;
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
        this.databaseManagementComponent = databaseManagementComponent;
    }

    @BeforeEach
    public void populateDatabase() {
        List<String> sqlFileScripts = List.of(
                "persistence/state/before_test_state.sql",
                "persistence/city/before_test_city.sql",
                "persistence/address/before_test_address.sql",
                "persistence/loadtable/before_test_load_table.sql",
                "persistence/usertype/before_test_user_type.sql",
                "persistence/user/before_test_user.sql",
                "persistence/jwt/before_test_jwt.sql"
        );
        databaseManagementComponent.populateDatabase(sqlFileScripts);
    }

    @AfterEach
    public void clearDatabase() {
        databaseManagementComponent.clearDatabase();
    }

    @DisplayName("Teste de sucesso - Criar um item de menu para um Menu")
    @Test
    public void createMenuItemSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantResponseDTO restaurantResponseDTO = this.createNewRestaurant();
        MenuBatchResponseDTO menuBatchResponseDTO = this.createNewMenu(restaurantResponseDTO);
//        RestaurantUserResponseDTO restaurantUserResponseDTO = this.createNewRestaurantUserAssociation(restaurantResponseDTO);

        MenuItemPostRequestDTO menuItemPostRequestDTO = JsonUtil.loadMockJsonWithReplacement(
                PATH_RESOURCE_MENU_ITEM,
                "${RESTAURANT_HASH_ID}",
                menuBatchResponseDTO.getHashIdRestaurant(),
                "menuItemPostRequestDTO",
                MenuItemPostRequestDTO.class
        );

        ResponseEntity<?> menuItemResponseEntity = testRestTemplate.exchange(
                "/api/v1/menu-items",
                HttpMethod.POST,
                new HttpEntity<>(menuItemPostRequestDTO, headers),
                new ParameterizedTypeReference<>() {}
        );

        BaseSuccessResponse201<MenuItemResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(
                menuItemResponseEntity,
                new TypeToken<>() {}
        );

        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.CREATED.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getName()).isEqualTo(menuItemPostRequestDTO.getName());
        assertThat(responseObject.getItem().getDescription()).isEqualTo(menuItemPostRequestDTO.getDescription());
        assertThat(responseObject.getItem().getPrice()).isEqualTo(menuItemPostRequestDTO.getPrice());
        assertThat(responseObject.getItem().getAvailability()).isTrue();
        assertThat(responseObject.getItem().getPhotoUrl()).isEqualTo(menuItemPostRequestDTO.getPhotoUrl());
        assertThat(responseObject.getItem().getMenu()).isNotNull();
        assertThat(responseObject.getItem().getMenu().getHashIdRestaurant()).isEqualTo(restaurantResponseDTO.getHashId());
    }

    private RestaurantResponseDTO createNewRestaurant() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();

        RestaurantPostRequestDTO restaurantPostRequestDTO = JsonUtil.objectFromJson(
                "restaurantPostRequestDTO",
                PATH_RESOURCE_RESTAURANT,
                RestaurantPostRequestDTO.class,
                DatePatternEnum.DATE_FORMAT_HH_mm.getValue()
        );

        ResponseEntity<BaseSuccessResponse201<RestaurantResponseDTO>> restaurantResponseEntity = testRestTemplate.exchange(
                "/api/v1/restaurants",
                HttpMethod.POST, new HttpEntity<>(restaurantPostRequestDTO, headers),
                new ParameterizedTypeReference<>() {}
        );

        assertNotNull(restaurantResponseEntity.getBody());
        return restaurantResponseEntity.getBody().getItem();
    }

    private MenuBatchResponseDTO createNewMenu(RestaurantResponseDTO restaurantResponseDTO) {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();

        String restaurantHashId = restaurantResponseDTO.getHashId();

        MenuBatchPutRequestDTO menuBatchPutRequestDTO = JsonUtil.loadMockJsonWithReplacement(
                PATH_RESOURCE_MENU,
                "${RESTAURANT_HASH_ID}",
                restaurantHashId,
                "menuBatchPutRequestDTO",
                MenuBatchPutRequestDTO.class
        );

        ResponseEntity<BaseSuccessResponse200<MenuBatchResponseDTO>> menuBatchResponseEntity = testRestTemplate.exchange(
                "/api/v1/menus",
                HttpMethod.PUT,
                new HttpEntity<>(menuBatchPutRequestDTO, headers),
                new ParameterizedTypeReference<>() {}
        );

        assertNotNull(menuBatchResponseEntity.getBody());
        return menuBatchResponseEntity.getBody().getItem();
    }

    private RestaurantUserResponseDTO createNewRestaurantUserAssociation(RestaurantResponseDTO restaurantResponseDTO) {
        HttpHeaders clientHeaders = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String restaurantHashId = restaurantResponseDTO.getHashId();

        RestaurantUserPostRequestDTO restaurantUserPostRequestDTO = JsonUtil.loadMockJsonWithReplacement(
                PATH_RESOURCE_RESTAURANT_USER,
                "${RESTAURANT_HASH_ID}",
                restaurantHashId,
                "restaurantUserRequestDTO",
                RestaurantUserPostRequestDTO.class
        );

        ResponseEntity<BaseSuccessResponse201<RestaurantUserResponseDTO>> restaurantUserResponseDTO = testRestTemplate.exchange(
                "/api/v1/restaurant-users",
                HttpMethod.POST,
                new HttpEntity<>(restaurantUserPostRequestDTO, clientHeaders),
                new ParameterizedTypeReference<>() {}
        );

        assertNotNull(restaurantUserResponseDTO.getBody());
        return restaurantUserResponseDTO.getBody().getItem();
    }
}
