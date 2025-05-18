package com.fiap.tech.challenge.domain.city;

import com.fiap.tech.challenge.domain.city.dto.CityResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse404;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.pageable.BasePageableSuccessResponse200;
import com.fiap.tech.challenge.global.component.DatabaseManagementComponent;
import com.fiap.tech.challenge.global.component.HttpBodyComponent;
import com.fiap.tech.challenge.global.component.HttpHeaderComponent;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerTest {

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public CityControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
        this.httpHeaderComponent = httpHeaderComponent;
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
        this.databaseManagementComponent = databaseManagementComponent;
    }

    private static final String PATH_RESOURCE_CITY = "/mock/city/city.json";

    @BeforeEach
    public void populateDatabase() {
        List<String> sqlFileScripts = List.of(
                "persistence/state/before_test_state.sql",
                "persistence/city/before_test_city.sql",
                "persistence/address/before_test_address.sql",
                "persistence/loadtable/before_test_load_table.sql",
                "persistence/user/before_test_user.sql"
        );
        databaseManagementComponent.populateDatabase(sqlFileScripts);
    }

    @AfterEach
    public void clearDatabase() {
        databaseManagementComponent.clearDatabase();
    }

    @DisplayName(value = "Teste de sucesso - Cidade existe ao verificar por filtro de nome")
    @Test
    public void findByFilterNameSuccess() {
        final String name = "Ariquemes";
        String urlTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/cities/filter")
                .queryParam("name", name)
                .encode()
                .toUriString();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<CityResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertEquals(1, responseObject.getList().size());
        Assertions.assertEquals(1, responseObject.getTotalElements());
        Assertions.assertEquals(name, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getName());
    }

    @DisplayName(value = "Teste de sucesso - Cidade existe ao verificar por filtro de UF do estado")
    @Test
    public void findByFilterUfStateSuccess() {
        final String UfState = "AC";
        String urlTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/cities/filter")
                .queryParam("ufState", UfState)
                .encode()
                .toUriString();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<CityResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertEquals(22, responseObject.getList().size());
        Assertions.assertEquals(22, responseObject.getTotalElements());
        Assertions.assertEquals(UfState, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getState().getUf());
    }

    @DisplayName(value = "Teste de sucesso - Cidade existe ao verificar por hash id")
    @Test
    public void findByHashIdSuccess() {
        final String hashId = "d6a42563ee504e11858dfc73579171fd";
        String urlTemplate = UriComponentsBuilder.fromUriString("/api/v1/cities/{hashId}")
                .encode()
                .build(hashId)
                .toString();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<CityResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertEquals(hashId, responseObject.getItem().getHashId());
    }

    @DisplayName(value = "Teste de falha - Cidade não existe ao verificar por hash id")
    @Test
    public void findByHashIdFailure() {
        final String hashId = "1a1a1a1a1a1a1a1a1a1a1a1a1a1a1a1a";
        String urlTemplate = UriComponentsBuilder.fromUriString("/api/v1/cities/{hashId}")
                .encode()
                .build(hashId)
                .toString();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse404 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseObject.getStatus());
    }
}