package com.fiap.tech.challenge.domain.integration.restaurantuser;

import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserResponseDTO;
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
public class RestaurantUserControllerTest {

    private static final String PATH_RESOURCE_RESTAURANT_USER = "/mock/restaurantuser/restaurantuser.json";
    private static final String PATH_RESOURCE_RESTAURANT = "/mock/restaurant/restaurant.json";

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public RestaurantUserControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
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

    @DisplayName("Teste de sucesso - Cria uma associação entre Restaurante e Usuário")
    @Test
    public void createRestaurantUserSuccess() {
        HttpHeaders clientHeaders = httpHeaderComponent.generateHeaderWithClientBearerToken();
        RestaurantResponseDTO restaurantResponseDTO = this.createNewRestaurant();
        RestaurantUserPostRequestDTO restaurantUserPostRequestDTO = JsonUtil.loadMockJsonWithReplacement(PATH_RESOURCE_RESTAURANT_USER, "${RESTAURANT_HASH_ID}", restaurantResponseDTO.getHashId(), "restaurantUserRequestDTO", RestaurantUserPostRequestDTO.class);
        ResponseEntity<?> restaurantUserResponseEntity = testRestTemplate.exchange("/api/v1/restaurant-users", HttpMethod.POST, new HttpEntity<>(restaurantUserPostRequestDTO, clientHeaders), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<RestaurantUserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(restaurantUserResponseEntity, new TypeToken<>() {});
        assertNotNull(responseObject);
        assertTrue(responseObject.isSuccess());
        assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        assertThat(responseObject.getItem().getRestaurant().getHashId()).isEqualTo(restaurantResponseDTO.getHashId());
        assertThat(responseObject.getItem().getRestaurant().getName()).isEqualTo("Churrascaria teste");
        assertThat(responseObject.getItem().getUser().getHashId()).isEqualTo("d49690a919944be58fbe55b4f729bc3e");
        assertThat(responseObject.getItem().getUser().getName()).isEqualTo("Client");
    }

    private RestaurantResponseDTO createNewRestaurant() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantPostRequestDTO restaurantPostRequestDTO = JsonUtil.objectFromJson("restaurantPostRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<BaseSuccessResponse201<RestaurantResponseDTO>> restaurantResponseEntity = testRestTemplate.exchange("/api/v1/restaurants", HttpMethod.POST, new HttpEntity<>(restaurantPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        assertNotNull(restaurantResponseEntity.getBody());
        return restaurantResponseEntity.getBody().getItem();
    }
}