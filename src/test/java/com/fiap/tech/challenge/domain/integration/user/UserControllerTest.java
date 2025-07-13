package com.fiap.tech.challenge.domain.integration.user;

import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse400;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse403;
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

import java.util.List;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public UserControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
        this.httpHeaderComponent = httpHeaderComponent;
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
        this.databaseManagementComponent = databaseManagementComponent;
    }

    private static final String PATH_RESOURCE_USER = "/mock/user/user.json";

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

    @DisplayName(value = "Teste de sucesso - Criar um usuário dono")
    @Test
    public void createUserOwnerSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        UserPostRequestDTO userPostRequestDTO = JsonUtil.objectFromJson("userPostRequestDTOOwner", PATH_RESOURCE_USER, UserPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, new HttpEntity<>(userPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<UserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de sucesso - Criar um usuário cliente")
    @Test
    public void createUserClientSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        UserPostRequestDTO userPostRequestDTO = JsonUtil.objectFromJson("userPostRequestDTOClient", PATH_RESOURCE_USER, UserPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, new HttpEntity<>(userPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<UserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de falha - Criar um outro usuário dono estando autenticado como um outro tipo de usuário")
    @Test
    public void createAnotherUserOwnerWithoutBeingAuthenticatedAsOwnerFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        UserPostRequestDTO userPostRequestDTO = JsonUtil.objectFromJson("userPostRequestDTOOwner", PATH_RESOURCE_USER, UserPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, new HttpEntity<>(userPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse403 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Criar um outro usuário dono não estando autenticado")
    @Test
    public void createUserOwnerWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        UserPostRequestDTO userPostRequestDTO = JsonUtil.objectFromJson("userPostRequestDTOOwner", PATH_RESOURCE_USER, UserPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, new HttpEntity<>(userPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse403 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Atualizar um usuário dono")
    @Test
    public void updateUserOwnerSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        UserPutRequestDTO userPutRequestDTO = JsonUtil.objectFromJson("userPutRequestDTOOwner", PATH_RESOURCE_USER, UserPutRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.PUT, new HttpEntity<>(userPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<UserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de sucesso - Atualizar um usuário cliente")
    @Test
    public void updateUserClientSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        UserPutRequestDTO userPutRequestDTO = JsonUtil.objectFromJson("userPutRequestDTOClient", PATH_RESOURCE_USER, UserPutRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.PUT, new HttpEntity<>(userPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<UserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de falha - Atualizar tipo para dono estando autenticado como cliente")
    @Test
    public void updateUserOwnerWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        UserPutRequestDTO userPutRequestDTO = JsonUtil.objectFromJson("userPutRequestDTOOwner", PATH_RESOURCE_USER, UserPutRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.PUT, new HttpEntity<>(userPutRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse403 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Atualizar senha de um usuário dono")
    @Test
    public void updateUserPasswordOwnerSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO = JsonUtil.objectFromJson("userUpdatePasswordPatchRequestDTOOwner", PATH_RESOURCE_USER, UserUpdatePasswordPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users/change-password", HttpMethod.PATCH, new HttpEntity<>(userUpdatePasswordPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        NoPayloadBaseSuccessResponse200<?> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertNull(responseObject);
    }

    @DisplayName(value = "Teste de sucesso - Atualizar senha de um usuário cliente")
    @Test
    public void updateUserPasswordClientSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO = JsonUtil.objectFromJson("userUpdatePasswordPatchRequestDTOClient", PATH_RESOURCE_USER, UserUpdatePasswordPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users/change-password", HttpMethod.PATCH, new HttpEntity<>(userUpdatePasswordPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        NoPayloadBaseSuccessResponse200<?> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertNull(responseObject);
    }

    @DisplayName(value = "Teste de falha - Atualizar senha de um usuário cliente com a senha atual informada incorretamente")
    @Test
    public void updateUserPasswordClientWrongActualPasswordFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO = JsonUtil.objectFromJson("userUpdatePasswordPatchRequestDTOClientWrongActualPassword", PATH_RESOURCE_USER, UserUpdatePasswordPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users/change-password", HttpMethod.PATCH, new HttpEntity<>(userUpdatePasswordPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Atualizar senha de um usuário cliente com a senha cadastrada igual a nova")
    @Test
    public void updateUserPasswordClientSamePasswordFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO = JsonUtil.objectFromJson("userUpdatePasswordPatchRequestDTOClientSamePassword", PATH_RESOURCE_USER, UserUpdatePasswordPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users/change-password", HttpMethod.PATCH, new HttpEntity<>(userUpdatePasswordPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - Atualizar senha de um usuário cliente com a senha cadastrada igual a nova")
    @Test
    public void updateUserPasswordClientWrongPasswordConfirmationFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO = JsonUtil.objectFromJson("userUpdatePasswordPatchRequestDTOClientWrongPasswordConfirmation", PATH_RESOURCE_USER, UserUpdatePasswordPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users/change-password", HttpMethod.PATCH, new HttpEntity<>(userUpdatePasswordPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Usuário existe ao verificar por filtro de nome")
    @Test
    public void findByFilterNameSuccess() {
        final String name = "Admin";
        String urlTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/users/filter")
                .queryParam("name", name)
                .encode()
                .toUriString();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<UserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(1, responseObject.getList().size());
        Assertions.assertEquals(1, responseObject.getTotalElements());
        Assertions.assertEquals(name, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getName());
    }

    @DisplayName(value = "Teste de sucesso - Usuário existe ao verificar por filtro de email")
    @Test
    public void findByFilterEmailSuccess() {
        final String email = "admin@email.com";
        String urlTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/users/filter")
                .queryParam("email", email)
                .encode()
                .toUriString();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<UserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(1, responseObject.getList().size());
        Assertions.assertEquals(1, responseObject.getTotalElements());
        Assertions.assertEquals(email, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getEmail());
    }

    @DisplayName(value = "Teste de sucesso - Busca de informações de usuário logado")
    @Test
    public void findSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<UserResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getAddress().getHashId()));
    }

    @DisplayName(value = "Teste de falha - Busca de informações de usuário sem estar autenticado")
    @Test
    public void findWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Deletar usuário")
    @Test
    public void deleteUserSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users", HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        NoPayloadBaseSuccessResponse200<?> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertNull(responseObject);
    }

    @DisplayName(value = "Teste de falha - Deletar usuário não estando autenticado")
    @Test
    public void deleteUserWithoutBeingAuthenticatedFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/users/change-password", HttpMethod.PATCH, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }
}