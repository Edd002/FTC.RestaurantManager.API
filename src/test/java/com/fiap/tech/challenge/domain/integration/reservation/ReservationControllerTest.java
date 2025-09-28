package com.fiap.tech.challenge.domain.integration.reservation;

import com.fiap.tech.challenge.domain.factory.ReservationTestFactory;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationUpdateStatusPatchRequestDTO;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.global.adapter.MultiFormatDateDeserializerTypeAdapter;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
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

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum.*;
import static com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

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
                "persistence/restaurantuser/before_test_restaurant_user.sql",
                "persistence/reservation/before_test_reservation.sql"
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
        ReservationPostRequestDTO reservationPostRequestDTO = ReservationTestFactory.loadValidPostRequestDTO();
        ResponseEntity<?> reservationResponseEntity = testRestTemplate.exchange("/api/v1/reservations", HttpMethod.POST, new HttpEntity<>(reservationPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<ReservationResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(reservationResponseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH, new MultiFormatDateDeserializerTypeAdapter());
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.CREATED.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        LocalDate reservationDate = LocalDate.now();
        assertThat(responseObject.getItem().getBookingDate()).hasYear(reservationDate.getYear()).hasMonth(reservationDate.getMonthValue()).hasDayOfMonth(reservationDate.getDayOfMonth());
        assertThat(responseObject.getItem().getBookingQuantity()).isEqualTo(2);
        assertThat(responseObject.getItem().getBookingStatus()).isEqualTo(REQUESTED);
        assertThat(responseObject.getItem().getBookingTime()).isEqualTo(BREAKFAST);
    }

    @DisplayName("Teste de sucesso - Atualizar status de uma Reserva no Restaurante")
    @Test
    public void updateReservationStatusSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        ReservationUpdateStatusPatchRequestDTO reservationUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("reservationUpdateStatusPatchRequestDTO", PATH_RESOURCE_RESERVATION, ReservationUpdateStatusPatchRequestDTO.class);
        final String EXISTING_RESERVATION_HASH_ID = "b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7";
        ResponseEntity<?> reservationResponseEntity = testRestTemplate.exchange("/api/v1/reservations/change-status/" + EXISTING_RESERVATION_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(reservationUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<ReservationResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(reservationResponseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH, new MultiFormatDateDeserializerTypeAdapter());
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getBookingStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("Teste de sucesso - Cancelar Reserva no Restaurante")
    @Test
    public void cancelReservationSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        final String EXISTING_RESERVATION_HASH_ID = "b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7";
        ResponseEntity<?> reservationResponseEntity = testRestTemplate.exchange("/api/v1/reservations/cancel/" + EXISTING_RESERVATION_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<ReservationResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(reservationResponseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH, new MultiFormatDateDeserializerTypeAdapter());
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getBookingStatus()).isEqualTo(CANCELED);
    }

    @DisplayName("Teste de sucesso - Buscar Reserva no Restaurante por filtro")
    @Test
    public void findReservationByFilterSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        final String hashIdRestaurant = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        final ReservationBookingStatusEnum reservationBookingStatusEnum = ACCEPTED;
        URI uriTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/reservations/filter")
                .queryParam("hashIdRestaurant", hashIdRestaurant)
                .queryParam("bookingStatus", reservationBookingStatusEnum)
                .build().encode()
                .toUri();
        ResponseEntity<?> reservationResponseEntity = testRestTemplate.exchange(uriTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<ReservationResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(reservationResponseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH, new MultiFormatDateDeserializerTypeAdapter());
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getList().size()).isEqualTo(2);
        assertThat(responseObject.getList())
                .extracting(ReservationResponseDTO::getBookingQuantity, ReservationResponseDTO::getBookingStatus, ReservationResponseDTO::getBookingTime)
                .containsExactlyInAnyOrder(
                    tuple(3L, ACCEPTED, DINNER),
                    tuple(2L, ACCEPTED, BREAKFAST)
            );
    }

    @DisplayName("Teste de sucesso - Buscar Reserva no Restaurante por hashId")
    @Test
    public void findReservationByHashIdSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        final String EXISTING_RESERVATION_HASH_ID = "b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7";
        ResponseEntity<?> reservationResponseEntity = testRestTemplate.exchange("/api/v1/reservations/" + EXISTING_RESERVATION_HASH_ID, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<ReservationResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(reservationResponseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH, new MultiFormatDateDeserializerTypeAdapter());
        assertThat(responseObject).isNotNull();
        assertThat(responseObject.isSuccess()).isTrue();
        assertThat(HttpStatus.OK.value()).isEqualTo(responseObject.getStatus());
        assertThat(responseObject.getItem().getHashId()).isNotNull();
        assertThat(responseObject.getItem().getBookingStatus()).isEqualTo(REQUESTED);
        assertThat(responseObject.getItem().getBookingTime()).isEqualTo(LUNCH);
        assertThat(responseObject.getItem().getBookingQuantity()).isEqualTo(4);
    }
}
