package com.fiap.tech.challenge.domain.integration.jwt;

import com.fiap.tech.challenge.domain.jwt.dto.JwtGeneratePostRequestDTO;
import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.base.response.success.nocontent.NoPayloadBaseSuccessResponse200;
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
public class JwtControllerTest {

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public JwtControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
        this.httpHeaderComponent = httpHeaderComponent;
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
        this.databaseManagementComponent = databaseManagementComponent;
    }

    private static final String PATH_RESOURCE_JWT = "/mock/jwt/jwt.json";

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

    @DisplayName(value = "Teste de sucesso - Gerar um JWT")
    @Test
    public void generateJwtSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        JwtGeneratePostRequestDTO jwtGeneratePostRequestDTO = JsonUtil.objectFromJson("jwtGeneratePostRequestDTOOwner", PATH_RESOURCE_JWT, JwtGeneratePostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/jwts/generate", HttpMethod.POST, new HttpEntity<>(jwtGeneratePostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<JwtResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getBearerToken()));
        Assertions.assertEquals(jwtGeneratePostRequestDTO.getLogin(), responseObject.getItem().getUserLogin());
    }

    @DisplayName(value = "Teste de falha - Credenciais do usuário inválidas ao gerar um JWT")
    @Test
    public void generateJwtNotFoundFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        JwtGeneratePostRequestDTO jwtGeneratePostRequestDTO = JsonUtil.objectFromJson("jwtGeneratePostRequestDTOInvalidCredentials", PATH_RESOURCE_JWT, JwtGeneratePostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/jwts/generate", HttpMethod.POST, new HttpEntity<>(jwtGeneratePostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Validar um JWT")
    @Test
    public void validateJwtSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/jwts/validate", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        NoPayloadBaseSuccessResponse200<?> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertNull(responseObject);
    }

    @DisplayName(value = "Teste de falha - JWT ausente ao tentar validá-lo")
    @Test
    public void validateJwtMissingFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithoutBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/jwts/validate", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de falha - JWT expirado ao tentar validá-lo")
    @Test
    public void validateJwtExpiredFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithExpiredBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/jwts/validate", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
    }

    @DisplayName(value = "Teste de sucesso - Invalidar um JWT")
    @Test
    public void invalidateJwtSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();

        ResponseEntity<?> responseEntityInvalidate = testRestTemplate.exchange("/api/v1/jwts/invalidate", HttpMethod.POST, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        NoPayloadBaseSuccessResponse200<?> responseObjectInvalidate = httpBodyComponent.responseEntityToObject(responseEntityInvalidate, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntityInvalidate.getStatusCode().value());
        Assertions.assertNull(responseObjectInvalidate);

        ResponseEntity<?> responseEntityValidate = testRestTemplate.exchange("/api/v1/jwts/validate", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse401 responseObjectValidate = httpBodyComponent.responseEntityToObject(responseEntityValidate, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntityValidate.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseObjectValidate.getStatus());
        Assertions.assertFalse(responseObjectValidate.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObjectValidate.getMessages()));
    }
}