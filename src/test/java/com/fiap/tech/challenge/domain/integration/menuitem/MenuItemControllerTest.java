package com.fiap.tech.challenge.domain.integration.menuitem;

import com.fiap.tech.challenge.domain.menu.dto.MenuBatchPutRequestDTO;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemGetFilter;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse400;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.base.response.success.nocontent.NoPayloadBaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.pageable.BasePageableSuccessResponse200;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuItemControllerTest {

    private static final String PATH_RESOURCE_MENU_ITEM = "/mock/menuitem/menuitem.json";
    private static final String PATH_RESOURCE_MENU = "/mock/menu/menu.json";
    private static final String PATH_RESOURCE_RESTAURANT = "/mock/restaurant/restaurant.json";

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
                "persistence/jwt/before_test_jwt.sql",
                "persistence/menu/before_test_menu.sql",
                "persistence/restaurant/before_test_restaurant.sql",
                "persistence/restaurantuser/before_test_restaurant_user.sql",
                "persistence/menuitem/before_test_menuitem.sql"
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

    @DisplayName("Teste de falha - Criar um item de menu para um menu com dados inválidos")
    @Test
    public void createMenuItemWithInvalidDataFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantResponseDTO restaurantResponseDTO = this.createNewRestaurant();
        MenuBatchResponseDTO menuBatchResponseDTO = this.createNewMenu(restaurantResponseDTO);

        MenuItemPostRequestDTO menuItemPostRequestDTO = JsonUtil.loadMockJsonWithReplacement(
                PATH_RESOURCE_MENU_ITEM,
                "${RESTAURANT_HASH_ID}",
                menuBatchResponseDTO.getHashIdRestaurant(),
                "menuItemInvalidPostRequestDTO",
                MenuItemPostRequestDTO.class
        );

        ResponseEntity<?> menuItemResponseEntity = testRestTemplate.exchange(
                "/api/v1/menu-items",
                HttpMethod.POST,
                new HttpEntity<>(menuItemPostRequestDTO, headers),
                new ParameterizedTypeReference<>() {}
        );

        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(
                menuItemResponseEntity,
                new TypeToken<>() {}
        );

        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isFalse();
        assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(responseObject.getStatus());
        List<String> errorMessages = responseObject.getMessages();
        assertThat(errorMessages).isNotNull().isNotEmpty().hasSize(4);
        assertThat(errorMessages).containsExactlyInAnyOrder(
                "O nome do item do menu não pode ser nulo ou em branco.",
                "O número de caracteres máximo para a descrição do item do menu é 255 caracteres.",
                "A disponibilidade do item do menu não pode ser nula.",
                "O preço do item do menu deve ser positivo."
        );
    }

    @DisplayName("Teste de sucesso - Atualizar um item de menu para um Menu")
    @Test
    public void updateMenuItemSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String hashIdRestaurantDb = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        String hashIdMenuItemDb = "d921fcd786aeffcaa60e35ccf9f01313";

        MenuItemPutRequestDTO menuItemPutRequestDTO = JsonUtil.loadMockJsonWithReplacement(
                PATH_RESOURCE_MENU_ITEM,
                "${RESTAURANT_HASH_ID}",
                hashIdRestaurantDb,
                "menuItemPutRequestDTO",
                MenuItemPutRequestDTO.class
        );

        ResponseEntity<?> menuItemResponseEntity = testRestTemplate.exchange(
                "/api/v1/menu-items/{hashId}",
                HttpMethod.PUT,
                new HttpEntity<>(menuItemPutRequestDTO, headers),
                new ParameterizedTypeReference<>() {},
                hashIdMenuItemDb
        );

        BaseSuccessResponse200<MenuItemResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(
                menuItemResponseEntity,
                new TypeToken<>() {}
        );

        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getName()).isEqualTo(menuItemPutRequestDTO.getName());
        assertThat(responseObject.getItem().getDescription()).isEqualTo(menuItemPutRequestDTO.getDescription());
        assertThat(responseObject.getItem().getPrice()).isEqualTo(menuItemPutRequestDTO.getPrice());
        assertThat(responseObject.getItem().getAvailability()).isTrue();
        assertThat(responseObject.getItem().getPhotoUrl()).isEqualTo(menuItemPutRequestDTO.getPhotoUrl());
        assertThat(responseObject.getItem().getMenu()).isNotNull();
        assertThat(responseObject.getItem().getMenu().getHashIdRestaurant()).isEqualTo(hashIdRestaurantDb);
    }

    @DisplayName("Teste de sucesso - Encontrar um item de menu dado um filtro")
    @Test
    public void findMenuItemByName() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String hashIdRestaurantDb = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        MenuItemGetFilter filter = new MenuItemGetFilter(1, 10);
        filter.setHashIdRestaurant(hashIdRestaurantDb);
        filter.setName("Espa");

        String url = UriComponentsBuilder
                .fromPath("/api/v1/menu-items/filter")
                .queryParam("hashIdRestaurant", filter.getHashIdRestaurant())
                .queryParam("name", filter.getName())
                .queryParam("description", filter.getDescription())
                .queryParam("availability", filter.getAvailability())
                .queryParam("pageNumber", filter.getPageNumber())
                .queryParam("pageSize", filter.getPageSize())
                .toUriString();

        ResponseEntity<?> menuItemResponseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {}
        );

        BasePageableSuccessResponse200<MenuItemResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(
                menuItemResponseEntity,
                new TypeToken<>() {}
        );

        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getPageNumber()).isEqualTo(1);
        assertThat(responseObject.getPageSize()).isEqualTo(10);
        assertThat(responseObject.getTotalElements()).isEqualTo(1);
        MenuItemResponseDTO firstMenuItemFounded = ((ArrayList<MenuItemResponseDTO>) responseObject.getList()).getFirst();
        assertThat(firstMenuItemFounded.getName()).isEqualTo("Espaguete à Bolonhesa");
        assertThat(firstMenuItemFounded.getDescription()).isEqualTo("Espaguete tradicional com molho bolonhesa caseiro e queijo parmesão");
        assertThat(firstMenuItemFounded.getPrice()).isEqualTo(BigDecimal.valueOf(19.99));
        assertThat(firstMenuItemFounded.getAvailability()).isTrue();
    }

    @DisplayName("Teste de sucesso - Encontrar um item de menu dado seu hash id")
    @Test
    public void findMenuItemByHashIdWithSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String hashIdMenuItemDb = "d921fcd786aeffcaa60e35ccf9f01313";

        ResponseEntity<?> menuItemResponseEntity = testRestTemplate.exchange(
                "/api/v1/menu-items/{hashId}",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {},
                hashIdMenuItemDb
        );

        BaseSuccessResponse200<MenuItemResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(
                menuItemResponseEntity,
                new TypeToken<>() {}
        );

        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getName()).isEqualTo("Espaguete à Bolonhesa");
        assertThat(responseObject.getItem().getDescription()).isEqualTo("Espaguete tradicional com molho bolonhesa caseiro e queijo parmesão");
        assertThat(responseObject.getItem().getPrice()).isEqualTo(BigDecimal.valueOf(19.99));
        assertThat(responseObject.getItem().getAvailability()).isTrue();
    }

    @DisplayName("Teste de sucesso - Deve deletar um item de menu dado seu hash id")
    @Test
    public void deleteMenuItemByHashIdWithSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String hashIdMenuItemDb = "d921fcd786aeffcaa60e35ccf9f01313";

        ResponseEntity<NoPayloadBaseSuccessResponse200<MenuItemResponseDTO>> menuItemResponseEntity = testRestTemplate.exchange(
                "/api/v1/menu-items/{hashId}",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {},
                hashIdMenuItemDb
        );

        assertThat(menuItemResponseEntity).isNotNull();
        assertThat(menuItemResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(menuItemResponseEntity.getBody()).isNull();
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
}
