package com.fiap.tech.challenge.domain.city;

import com.fiap.tech.challenge.global.component.DatabaseManagementComponent;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerTest {

    private final DatabaseManagementComponent databaseManagementComponent;
    private final CityController cityController;

    @Autowired
    public CityControllerTest(DatabaseManagementComponent databaseManagementComponent, CityController cityController) {
        this.databaseManagementComponent = databaseManagementComponent;
        this.cityController = cityController;
    }

    private static final String PATH_RESOURCE_CITY = "/mock/city/city.json";

    @BeforeEach
    public void populateDatabase() {
        List<String> sqlFileScripts = List.of(
                "persistence/user/before_test_user.sql",
                "persistence/state/before_test_state.sql",
                "persistence/city/before_test_city.sql"
        );
        databaseManagementComponent.populateDatabase(sqlFileScripts);
    }

    @AfterEach
    public void clearDatabase() {
        databaseManagementComponent.clearDatabase();
    }

    @DisplayName(value = "Teste de sucesso - Cidade existe ao verificar por hashId")
    @Test
    public void existsByHashIdOrCodigoIbgeOrCodigoIbge7_ExistingWithHashIdAndCodigoIbgeAndCodigoIbge7ParamsSuccess() {
        final String EXISTING_CITY_HASH_ID = "0bb456675ed54168b207606ce026a8f8";
        //Assertions.assertThrows();
        TestRestTemplate testRestTemplate;
        assertThat(cityController.find(EXISTING_CITY_HASH_ID)).isNotNull();
    }
}