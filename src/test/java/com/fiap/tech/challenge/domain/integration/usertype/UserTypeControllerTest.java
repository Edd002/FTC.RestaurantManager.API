package com.fiap.tech.challenge.domain.integration.usertype;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePutRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypeResponseDTO;
import com.fiap.tech.challenge.domain.usertype.enumerated.constraint.UserTypeConstraintEnum;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse409;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse422;
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
public class UserTypeControllerTest {

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public UserTypeControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
        this.httpHeaderComponent = httpHeaderComponent;
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
        this.databaseManagementComponent = databaseManagementComponent;
    }

    private static final String PATH_RESOURCE_USER_TYPE = "/mock/usertype/user_type.json";

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

    @DisplayName(value = "Teste de sucesso - Criar um tipo de usuário")
    @Test
    public void createUserTypeSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        UserTypePostRequestDTO userTypePostRequestDTO = JsonUtil.objectFromJson("userTypePostRequestDTOEmployee", PATH_RESOURCE_USER_TYPE, UserTypePostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types", HttpMethod.POST, new HttpEntity<>(userTypePostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<UserTypeResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertEquals(userTypePostRequestDTO.getName(), responseObject.getItem().getName());
    }

    @DisplayName(value = "Teste de falha - Criar um tipo de usuário com nome já existente")
    @Test
    public void createUserTypeExistingNameFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        UserTypePostRequestDTO userTypePostRequestDTO = JsonUtil.objectFromJson("userTypePostRequestDTOChef", PATH_RESOURCE_USER_TYPE, UserTypePostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types", HttpMethod.POST, new HttpEntity<>(userTypePostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse422 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains(UserTypeConstraintEnum.T_USER_TYPE__NAME_UK.getErrorMessage()));
    }

    @DisplayName(value = "Teste de falha - Criar tipo de usuário não estando autenticado como administrador")
    @Test
    public void createUserTypeWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        UserTypePostRequestDTO userTypePostRequestDTO = JsonUtil.objectFromJson("userTypePostRequestDTOEmployee", PATH_RESOURCE_USER_TYPE, UserTypePostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types", HttpMethod.POST, new HttpEntity<>(userTypePostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Atualizar um tipo de usuário")
    @Test
    public void updateUserTypeSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        final String EXISTING_USER_TYPE_CHEF_HASH_ID = "23as85as485va5ffh4c1z4s4aes6d88a";
        UserTypePutRequestDTO userTypePutRequestDTO = JsonUtil.objectFromJson("userTypePutRequestDTOEmployee", PATH_RESOURCE_USER_TYPE, UserTypePutRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_CHEF_HASH_ID, HttpMethod.PUT, new HttpEntity<>(userTypePutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<UserTypeResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(EXISTING_USER_TYPE_CHEF_HASH_ID, responseObject.getItem().getHashId());
        Assertions.assertEquals(userTypePutRequestDTO.getName(), responseObject.getItem().getName());
    }

    @DisplayName(value = "Teste de falha - Atualizar um tipo de usuário padrão")
    @Test
    public void updateDefaultUserTypeFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        final String EXISTING_USER_TYPE_OWNER_HASH_ID = "91cbb591b10c43b1ab55023858b6bc9f";
        UserTypePutRequestDTO userTypePutRequestDTO = JsonUtil.objectFromJson("userTypePutRequestDTOEmployee", PATH_RESOURCE_USER_TYPE, UserTypePutRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_OWNER_HASH_ID, HttpMethod.PUT, new HttpEntity<>(userTypePutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse409 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Atualizar tipo de usuário não estando autenticado como administrador")
    @Test
    public void updateUserTypeWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        final String EXISTING_USER_TYPE_CHEF_HASH_ID = "23as85as485va5ffh4c1z4s4aes6d88a";
        UserTypePutRequestDTO userTypePutRequestDTO = JsonUtil.objectFromJson("userTypePutRequestDTOEmployee", PATH_RESOURCE_USER_TYPE, UserTypePutRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_CHEF_HASH_ID, HttpMethod.PUT, new HttpEntity<>(userTypePutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Tipo de usuário existe ao verificar por filtro de nome")
    @Test
    public void findByFilterNameSuccess() {
        final String name = "CLIENT";
        URI uriTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/user-types/filter")
                .queryParam("name", name)
                .build().encode()
                .toUri();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(uriTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<UserTypeResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(1, responseObject.getList().size());
        Assertions.assertEquals(1, responseObject.getTotalElements());
        Assertions.assertEquals(name, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getName());
    }

    @DisplayName(value = "Teste de sucesso - Busca de informações de tipo de usuário por hash id")
    @Test
    public void findSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_USER_TYPE_CHEF_HASH_ID = "23as85as485va5ffh4c1z4s4aes6d88a";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_CHEF_HASH_ID, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<UserTypeResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(EXISTING_USER_TYPE_CHEF_HASH_ID, responseObject.getItem().getHashId());
    }

    @DisplayName(value = "Teste de sucesso - Deletar tipo de usuário")
    @Test
    public void deleteUserTypeSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        final String EXISTING_USER_TYPE_WAITER_HASH_ID = "56a9qew74ac5gh5416q9ew4s5s4w8a48";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_WAITER_HASH_ID, HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        NoPayloadBaseSuccessResponse200<?> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertNull(responseObject);
    }

    @DisplayName(value = "Teste de falha - Deletar tipo de usuário padrão")
    @Test
    public void deleteDefaultUserTypeFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        final String EXISTING_USER_TYPE_OWNER_HASH_ID = "91cbb591b10c43b1ab55023858b6bc9f";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_OWNER_HASH_ID, HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse409 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Deletar tipo de usuário com usuários associados")
    @Test
    public void deleteUserTypeWithAssociateUsersFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        final String EXISTING_USER_TYPE_CHEF_HASH_ID = "23as85as485va5ffh4c1z4s4aes6d88a";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_CHEF_HASH_ID, HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse409 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Deletar tipo de usuário não estando autenticado como administrador")
    @Test
    public void deleteUserTypeWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        final String EXISTING_USER_TYPE_CHEF_HASH_ID = "23as85as485va5ffh4c1z4s4aes6d88a";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types/" + EXISTING_USER_TYPE_CHEF_HASH_ID, HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }
}