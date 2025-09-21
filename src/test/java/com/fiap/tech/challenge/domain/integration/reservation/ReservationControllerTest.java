package com.fiap.tech.challenge.domain.integration.reservation;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import com.fiap.tech.challenge.global.adapter.MultiFormatDateDeserializerTypeAdapter;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    private static final String PATH_RESOURCE_RESERVATION = "/mock/reservation/reservation.json";

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public ReservationControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
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

    @DisplayName("Teste de sucesso - Criar uma Reserva no Restaurante")
    @Test
    public void createReservationSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        ReservationPostRequestDTO reservationPostRequestDTO = JsonUtil.objectFromJson("reservationPostRequestDTO", PATH_RESOURCE_RESERVATION, ReservationPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH.getValue());
        ResponseEntity<?> reservationResponseEntity = testRestTemplate.exchange("/api/v1/reservations", HttpMethod.POST, new HttpEntity<>(reservationPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<ReservationResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(reservationResponseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH, new MultiFormatDateDeserializerTypeAdapter());
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.CREATED.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getBookingDate()).hasYear(2025).hasMonth(11).hasDayOfMonth(11);
        assertThat(responseObject.getItem().getBookingQuantity()).isEqualTo(2);
        assertThat(responseObject.getItem().getBookingStatus()).isEqualTo(ReservationBookingStatusEnum.REQUESTED);
        assertThat(responseObject.getItem().getBookingTime()).isEqualTo(ReservationBookingTimeEnum.BREAKFAST);
    }
}
