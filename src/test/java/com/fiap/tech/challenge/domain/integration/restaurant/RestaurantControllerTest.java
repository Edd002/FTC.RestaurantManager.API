package com.fiap.tech.challenge.domain.integration.restaurant;

import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.adapter.TimeDeserializerTypeAdapter;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse403;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse404;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.base.response.success.nocontent.NoPayloadBaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.pageable.BasePageableSuccessResponse200;
import com.fiap.tech.challenge.global.component.DatabaseManagementComponent;
import com.fiap.tech.challenge.global.component.HttpBodyComponent;
import com.fiap.tech.challenge.global.component.HttpHeaderComponent;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
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

    @DisplayName("Teste de sucesso - Criar um restaurante")
    @Test
    public void createRestaurantSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantPostRequestDTO restaurantPostRequestDTO = JsonUtil.objectFromJson("restaurantPostRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants", HttpMethod.POST, new HttpEntity<>(restaurantPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<RestaurantResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_HH_mm, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de falha - Criar um restaurante com um usuário não autenticado como dono de restuarane")
    @Test
    public void createRestaurantWithNoOwnerUserFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        RestaurantPostRequestDTO restaurantPostRequestDTO = JsonUtil.objectFromJson("restaurantPostRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants", HttpMethod.POST, new HttpEntity<>(restaurantPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse403 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
    }

    @DisplayName("Teste de sucesso - Atualizar um restaurante")
    @Test
    public void updateRestaurantSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_RESTAURANT_HASH_ID = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        RestaurantPutRequestDTO restaurantPutRequestDTO = JsonUtil.objectFromJson("restaurantPutRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPutRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants/" + EXISTING_RESTAURANT_HASH_ID, HttpMethod.PUT, new HttpEntity<>(restaurantPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<RestaurantResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_HH_mm, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(EXISTING_RESTAURANT_HASH_ID, responseObject.getItem().getHashId());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de falha - Atualizar um restaurante com um usuário que não é o dono")
    @Test
    public void updateRestaurantWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        final String EXISTING_RESTAURANT_HASH_ID = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        RestaurantPutRequestDTO restaurantPutRequestDTO = JsonUtil.objectFromJson("restaurantPutRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPutRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants/" + EXISTING_RESTAURANT_HASH_ID, HttpMethod.PUT, new HttpEntity<>(restaurantPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Atualizar um restaurante com um usuário que não é o dono do restaurante")
    @Test
    public void updateRestaurantWithoutRestaurantUserAssociationFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerWithoutRestaurantBearerToken();
        final String EXISTING_RESTAURANT_HASH_ID = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        RestaurantPutRequestDTO restaurantPutRequestDTO = JsonUtil.objectFromJson("restaurantPutRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPutRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants/" + EXISTING_RESTAURANT_HASH_ID, HttpMethod.PUT, new HttpEntity<>(restaurantPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse404 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Restaurante existe ao verificar por filtro de nome")
    @Test
    public void findByFilterNameSuccess() {
        final String name = "Restaurante do João";
        URI uriTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/restaurants/filter")
                .queryParam("name", name)
                .build().encode()
                .toUri();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(uriTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<RestaurantResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_HH_mm, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(1, responseObject.getList().size());
        Assertions.assertEquals(1, responseObject.getTotalElements());
        Assertions.assertEquals(name, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getName());
    }

    @DisplayName(value = "Teste de sucesso - Restaurante existe ao verificar por filtro de tipo")
    @Test
    public void findByFilterTypeSuccess() {
        final RestaurantTypeEnum restaurantType = RestaurantTypeEnum.FAST_CASUAL_CONCEPTS;
        URI uriTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/restaurants/filter")
                .queryParam("type", restaurantType)
                .build().encode()
                .toUri();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(uriTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<RestaurantResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_HH_mm, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(3, responseObject.getList().size());
        Assertions.assertEquals(3, responseObject.getTotalElements());
        Assertions.assertEquals(restaurantType, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getType());
    }

    @DisplayName(value = "Teste de sucesso - Busca de informações de restaurante por hash id")
    @Test
    public void findSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_RESTAURANT_HASH_ID = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants/" + EXISTING_RESTAURANT_HASH_ID, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<RestaurantResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_HH_mm, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(EXISTING_RESTAURANT_HASH_ID, responseObject.getItem().getHashId());
    }

    @DisplayName(value = "Teste de sucesso - Deletar restaurante")
    @Test
    public void deleteUserSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerWithOneRestaurantBearerToken();
        final String EXISTING_RESTAURANT_HASH_ID = "65cd4as415f15d4fas5155ds54sa65f4";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants/" + EXISTING_RESTAURANT_HASH_ID, HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        NoPayloadBaseSuccessResponse200<?> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertNull(responseObject);
    }

    @DisplayName(value = "Teste de falha - Deletar um restaurante com um usuário não autenticado")
    @Test
    public void deleteRestaurantWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        final String EXISTING_RESTAURANT_HASH_ID = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants/" + EXISTING_RESTAURANT_HASH_ID, HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Deletar um restaurante com um usuário que não é o dono do restaurante")
    @Test
    public void deleteRestaurantWithoutRestaurantUserAssociationFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerWithoutRestaurantBearerToken();
        final String EXISTING_RESTAURANT_HASH_ID = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/restaurants/" + EXISTING_RESTAURANT_HASH_ID, HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse404 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }
}