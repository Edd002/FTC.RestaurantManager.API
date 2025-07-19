package com.fiap.tech.challenge.domain.integration.usertype;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypeResponseDTO;
import com.fiap.tech.challenge.domain.usertype.enumerated.constraint.UserTypeConstraintEnum;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse422;
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
        // APONTAR ROLES NECESSÁRIAS NO SWAGGER PARA CADA ENDPOINT
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithAdminBearerToken();
        UserTypePostRequestDTO userTypePostRequestDTO = JsonUtil.objectFromJson("userTypePostRequestDTOOwner", PATH_RESOURCE_USER_TYPE, UserTypePostRequestDTO.class, DatePatternEnum.DATE_FORMAT_mm_dd_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/user-types", HttpMethod.POST, new HttpEntity<>(userTypePostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse422 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains(UserTypeConstraintEnum.T_USER_TYPE__NAME_UK.getErrorMessage()));
    }
}