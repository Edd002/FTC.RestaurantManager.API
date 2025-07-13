package com.fiap.tech.challenge.domain.integration.restaurant;

import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse403;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.component.DatabaseManagementComponent;
import com.fiap.tech.challenge.global.component.HttpBodyComponent;
import com.fiap.tech.challenge.global.component.HttpHeaderComponent;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;
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

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantControllerTest {

    private static final String PATH_RESOURCE_RESTAURANT = "/mock/restaurant/restaurant.json";

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public RestaurantControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
        this.httpHeaderComponent = httpHeaderComponent;
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
        this.databaseManagementComponent = databaseManagementComponent;
    }

    @BeforeEach
    public void populateDatabase() {
        List<String> sqlFileScripts =
                List.of(
                        "persistence/state/before_test_state.sql",
                        "persistence/city/before_test_city.sql",
                        "persistence/address/before_test_address.sql",
                        "persistence/loadtable/before_test_load_table.sql",
                        "persistence/usertype/before_test_user_type.sql",
                        "persistence/user/before_test_user.sql",
                        "persistence/jwt/before_test_jwt.sql");
        databaseManagementComponent.populateDatabase(sqlFileScripts);
    }

    @AfterEach
    public void clearDatabase() {
        databaseManagementComponent.clearDatabase();
    }

    @DisplayName("Teste de sucesso - Criar um restaurante")
    @Test
    public void createRestaurantSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();

        RestaurantPostRequestDTO restaurantPostRequestDTO =
                JsonUtil.objectFromJson(
                        "restaurantPostRequestDTO",
                        PATH_RESOURCE_RESTAURANT,
                        RestaurantPostRequestDTO.class,
                        DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<?> responseEntity =
                testRestTemplate.exchange(
                        "/api/v1/restaurants",
                        HttpMethod.POST,
                        new HttpEntity<>(restaurantPostRequestDTO, headers),
                        new ParameterizedTypeReference<>() {
                        });

        BaseSuccessResponse201<RestaurantResponseDTO> responseObject =
                httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {
                });
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(
                ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de falha - Criar um restaurante com um usuário que não é o dono")
    @Test
    public void createRestaurantWithNoOwnerUserFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        RestaurantPostRequestDTO restaurantPostRequestDTO =
                JsonUtil.objectFromJson(
                        "restaurantPostRequestDTO",
                        PATH_RESOURCE_RESTAURANT,
                        RestaurantPostRequestDTO.class,
                        DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<?> responseEntity =
                testRestTemplate.exchange(
                        "/api/v1/restaurants",
                        HttpMethod.POST,
                        new HttpEntity<>(restaurantPostRequestDTO, headers),
                        new ParameterizedTypeReference<>() {
                        });

        BaseErrorResponse403 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {
        });
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));

    }
}