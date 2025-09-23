package com.fiap.tech.challenge.domain.integration.menu;

import com.fiap.tech.challenge.domain.menu.dto.MenuBatchPutRequestDTO;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse400;
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

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuControllerTest {

    private static final String PATH_RESOURCE_MENU = "/mock/menu/menu.json";
    private static final String PATH_RESOURCE_RESTAURANT = "/mock/restaurant/restaurant.json";

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public MenuControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
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

    @DisplayName("Teste de sucesso - Criar um menu para um restaurante")
    @Test
    public void createMenuSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String restaurantHashId = createNewRestaurant();
        MenuBatchPutRequestDTO menuBatchPutRequestDTO = JsonUtil.objectFromJsonWithReplacement(PATH_RESOURCE_MENU, "${RESTAURANT_HASH_ID}", restaurantHashId, "menuBatchPutRequestDTO", MenuBatchPutRequestDTO.class);
        ResponseEntity<?> menuBatchResponseEntity = testRestTemplate.exchange("/api/v1/menus", HttpMethod.PUT, new HttpEntity<>(menuBatchPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<MenuBatchResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(menuBatchResponseEntity, new TypeToken<>() {});
        Assertions.assertNotNull(responseObject);
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        assertThat(responseObject.getItem().getHashIdRestaurant()).isEqualTo(restaurantHashId);
        assertThat(responseObject.getItem().getMenuItems()).extracting("name", "description", "price", "availability", "photoUrl").containsExactlyInAnyOrder(
                tuple(
                        "Espaguete à Bolonhesa",
                        "Espaguete tradicional com molho bolonhesa caseiro e queijo parmesão",
                        new BigDecimal("19.99"),
                        true,
                        "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808"
                ),
                tuple(
                        "Pizza Margherita",
                        "Pizza tradicional com molho de tomate, mussarela de búfala e manjericão fresco",
                        new BigDecimal("29.99"),
                        true,
                        "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/pizza-margherita.jpg-20250614014808"
                )
        );
    }

    @DisplayName("Teste de falha - Criar um menu para um restaurante com dados inválidos")
    @Test
    public void createMenuWithInvalidDataFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String restaurantHashId = createNewRestaurant();
        MenuBatchPutRequestDTO menuBatchPutRequestDTO = JsonUtil.objectFromJsonWithReplacement(PATH_RESOURCE_MENU, "${RESTAURANT_HASH_ID}", restaurantHashId, "invalidMenuBatchPutRequestDTO", MenuBatchPutRequestDTO.class);
        ResponseEntity<?> menuBatchResponseEntity = testRestTemplate.exchange("/api/v1/menus", HttpMethod.PUT, new HttpEntity<>(menuBatchPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(menuBatchResponseEntity, new TypeToken<>() {});
        Assertions.assertNotNull(responseObject);
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        List<String> errorMessages = responseObject.getMessages();
        assertThat(errorMessages).isNotNull().isNotEmpty().hasSize(4);
        assertThat(errorMessages).containsExactlyInAnyOrder(
                "O nome do item do menu não pode ser nulo ou em branco.",
                "O número de caracteres máximo para a descrição do item do menu é 255 caracteres.",
                "A disponibilidade do item do menu não pode ser nula.",
                "O preço do item do menu deve ser positivo."
        );
    }

    private String createNewRestaurant() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantPostRequestDTO restaurantPostRequestDTO = JsonUtil.objectFromJson("restaurantPostRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<BaseSuccessResponse201<RestaurantResponseDTO>> restaurantResponseEntity = testRestTemplate.exchange("/api/v1/restaurants", HttpMethod.POST, new HttpEntity<>(restaurantPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        Assertions.assertNotNull(restaurantResponseEntity.getBody());
        return restaurantResponseEntity.getBody().getItem().getHashId();
    }
}
