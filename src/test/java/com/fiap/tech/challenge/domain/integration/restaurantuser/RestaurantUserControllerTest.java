package com.fiap.tech.challenge.domain.integration.restaurantuser;

import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserGetFilter;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse409;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum.FAST_CASUAL_CONCEPTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
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
                "persistence/jwt/before_test_jwt.sql",
                "persistence/menu/before_test_menu.sql",
                "persistence/restaurant/before_test_restaurant.sql",
                "persistence/restaurantuser/before_test_restaurant_user.sql"
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

    @DisplayName("Teste de sucesso - Encontrar uma associação entre restaurante e usuário dado um filtro por nome do usuário")
    @Test
    public void findRestaurantUserByUserNameSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantUserGetFilter filter = new RestaurantUserGetFilter(1, 10);
        filter.setUserName("Owner");
        String url = UriComponentsBuilder
                .fromPath("/api/v1/restaurant-users/filter")
                .queryParam("hashIdRestaurant", filter.getUserName())
                .queryParam("pageNumber", filter.getPageNumber())
                .queryParam("pageSize", filter.getPageSize())
                .toUriString();
        ResponseEntity<?> restaurantUserResponseEntity = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<RestaurantUserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(restaurantUserResponseEntity, new TypeToken<>() {});
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getPageNumber()).isEqualTo(1);
        assertThat(responseObject.getPageSize()).isEqualTo(10);
        assertThat(responseObject.getTotalElements()).isEqualTo(3);
        assertThat(responseObject.getList())
                .extracting(dto -> dto.getRestaurant().getHashId(), dto -> dto.getRestaurant().getName(), dto -> dto.getRestaurant().getType(),
                        dto -> dto.getUser().getHashId(),dto -> dto.getUser().getName(),dto -> dto.getUser().getType())
                .containsExactly(
                        tuple("6d4b62960a6aa2b1fff43a9c1d95f7b2", "Restaurante do João", FAST_CASUAL_CONCEPTS,
                                "ab15a4s1a5qa7af15a41s8a4sa15d1fa", "Owner", "OWNER"),
                        tuple("6d4b62960a6aa2b1fff43a9c1d95f7b2", "Restaurante do João", FAST_CASUAL_CONCEPTS,
                                "d49690a919944be58fbe55b4f729bc3e", "Client", "CLIENT"),
                        tuple("6d4b62960a6aa2b1fff43a9c1d95f7b2", "Restaurante do João", FAST_CASUAL_CONCEPTS,
                                "ed3a9d7639d84a20a57ecf20d27176da", "Admin", "ADMIN")
                );
    }

    @DisplayName("Teste de sucesso - Encontrar uma associação entre restaurante e usuário dado um filtro por nome do restaurante")
    @Test
    public void findRestaurantUserByRestaurantNameSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantUserGetFilter filter = new RestaurantUserGetFilter(1, 10);
        filter.setRestaurantName("Restaurante do João");
        String url = UriComponentsBuilder
                .fromPath("/api/v1/restaurant-users/filter")
                .queryParam("hashIdRestaurant", filter.getUserName())
                .queryParam("pageNumber", filter.getPageNumber())
                .queryParam("pageSize", filter.getPageSize())
                .toUriString();
        ResponseEntity<?> restaurantUserResponseEntity = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<RestaurantUserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(restaurantUserResponseEntity, new TypeToken<>() {});
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getPageNumber()).isEqualTo(1);
        assertThat(responseObject.getPageSize()).isEqualTo(10);
        assertThat(responseObject.getTotalElements()).isEqualTo(3);
        assertThat(responseObject.getList())
                .extracting(dto -> dto.getRestaurant().getHashId(), dto -> dto.getRestaurant().getName(), dto -> dto.getRestaurant().getType(),
                        dto -> dto.getUser().getHashId(),dto -> dto.getUser().getName(),dto -> dto.getUser().getType())
                .containsExactlyInAnyOrder(
                        tuple("6d4b62960a6aa2b1fff43a9c1d95f7b2", "Restaurante do João", FAST_CASUAL_CONCEPTS,
                                "ab15a4s1a5qa7af15a41s8a4sa15d1fa", "Owner", "OWNER"),
                        tuple("6d4b62960a6aa2b1fff43a9c1d95f7b2", "Restaurante do João", FAST_CASUAL_CONCEPTS,
                                "d49690a919944be58fbe55b4f729bc3e", "Client", "CLIENT"),
                        tuple("6d4b62960a6aa2b1fff43a9c1d95f7b2", "Restaurante do João", FAST_CASUAL_CONCEPTS,
                                "ed3a9d7639d84a20a57ecf20d27176da", "Admin", "ADMIN")
                );
    }

    @DisplayName("Teste de sucesso - Encontrar uma associação entre restaurante e usuário dado seu hash id")
    @Test
    public void findRestaurantUserByHashIdSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String hashIdRestaurantUserDb = "8d6ab84ca2af9fccd4e4048694176ebf";
        ResponseEntity<?> restaurantUserResponseEntity = testRestTemplate.exchange("/api/v1/restaurant-users/{hashId}", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {}, hashIdRestaurantUserDb);
        BaseSuccessResponse200<RestaurantUserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(restaurantUserResponseEntity, new TypeToken<>() {});
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getRestaurant().getHashId()).isEqualTo("6d4b62960a6aa2b1fff43a9c1d95f7b2");
        assertThat(responseObject.getItem().getRestaurant().getName()).isEqualTo("Restaurante do João");
        assertThat(responseObject.getItem().getRestaurant().getType()).isEqualTo(FAST_CASUAL_CONCEPTS);
        assertThat(responseObject.getItem().getUser().getHashId()).isEqualTo("ab15a4s1a5qa7af15a41s8a4sa15d1fa");
        assertThat(responseObject.getItem().getUser().getName()).isEqualTo("Owner");
        assertThat(responseObject.getItem().getUser().getType()).isEqualTo("OWNER");
    }

    @DisplayName("Teste de sucesso - Deve deletar uma associação entre restaurante e usuário dado seu hash id")
    @Test
    public void deleteRestaurantUserByHashIdSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        String hashIdRestaurantUserDb = "bd7e15fcd3f1083ccd3e6e447e4d956e";
        ResponseEntity<NoPayloadBaseSuccessResponse200<RestaurantUserResponseDTO>> restaurantUserResponseEntity = testRestTemplate.exchange("/api/v1/restaurant-users/{hashId}", HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {}, hashIdRestaurantUserDb);
        assertThat(restaurantUserResponseEntity).isNotNull();
        assertThat(restaurantUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(restaurantUserResponseEntity.getBody()).isNull();
    }

    @DisplayName("Teste de falha - Deve dar erro ao deletar uma associação entre restaurante e dono de restaurante se existir somente uma associação")
    @Test
    public void deleteRestaurantUserAsOwnerByHashIdFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        String hashIdRestaurantUserDb = "8d6ab84ca2af9fccd4e4048694176ebf";
        ResponseEntity<?> restaurantUserResponseEntity = restaurantUserResponseEntity = testRestTemplate.exchange("/api/v1/restaurant-users/{hashId}", HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {}, hashIdRestaurantUserDb);
        BaseErrorResponse409 responseObject = httpBodyComponent.responseEntityToObject(restaurantUserResponseEntity, new TypeToken<>() {});
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isFalse();
        assertThat(HttpStatus.CONFLICT.value()).isEqualTo(responseObject.getStatus());
        List<String> errorMessages = responseObject.getMessages();
        assertThat(errorMessages).isNotNull().isNotEmpty().hasSize(1);
        assertThat(errorMessages).containsExactlyInAnyOrder(
                "Não foi possível realizar a exclusão pois deve existir pelo menos uma associação de usuário dono de restaurante com o restaurante."
        );
    }

    @DisplayName("Teste de falha - Deve dar erro ao deletar uma associação entre restaurante e admin")
    @Test
    public void deleteRestaurantUserAsAdminByHashIdFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        String hashIdRestaurantUserDb = "202d227187ff7a6d2b2e3371a81fa633";
        ResponseEntity<?> restaurantUserResponseEntity = testRestTemplate.exchange("/api/v1/restaurant-users/{hashId}", HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {}, hashIdRestaurantUserDb);
        BaseErrorResponse409 responseObject = httpBodyComponent.responseEntityToObject(restaurantUserResponseEntity, new TypeToken<>() {});
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isFalse();
        assertThat(HttpStatus.CONFLICT.value()).isEqualTo(responseObject.getStatus());
        List<String> errorMessages = responseObject.getMessages();
        assertThat(errorMessages).isNotNull().isNotEmpty().hasSize(1);
        assertThat(errorMessages).containsExactlyInAnyOrder(
                "Usuários administradores não podem ser excluídos."
        );
    }

    private RestaurantResponseDTO createNewRestaurant() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        RestaurantPostRequestDTO restaurantPostRequestDTO = JsonUtil.objectFromJson("restaurantPostRequestDTO", PATH_RESOURCE_RESTAURANT, RestaurantPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        ResponseEntity<BaseSuccessResponse201<RestaurantResponseDTO>> restaurantResponseEntity = testRestTemplate.exchange("/api/v1/restaurants", HttpMethod.POST, new HttpEntity<>(restaurantPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        assertNotNull(restaurantResponseEntity.getBody());
        return restaurantResponseEntity.getBody().getItem();
    }
}