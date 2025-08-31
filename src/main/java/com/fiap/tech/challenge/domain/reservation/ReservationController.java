package com.fiap.tech.challenge.domain.reservation;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationGetFilter;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPutRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.*;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.base.response.success.nocontent.NoPayloadBaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.pageable.BasePageableSuccessResponse200;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log
@Validated
@RestController
@RequestMapping(value = "/api/v1/reservations")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse400.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse401.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse403.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse404.class))),
        @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse405.class))),
        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse409.class))),
        @ApiResponse(responseCode = "412", description = "Precondition Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse412.class))),
        @ApiResponse(responseCode = "415", description = "Unsupported Media Type", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse415.class))),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse422.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse500.class)))
})
@Tag(name = "Reservas - Endpoints de Reservas")
public class ReservationController {

    private final ReservationServiceGateway reservationServiceGateway;

    @Autowired
    public ReservationController(ReservationServiceGateway reservationServiceGateway) {
        this.reservationServiceGateway = reservationServiceGateway;
    }

    @Operation(method = "POST", summary = "Criar reserva", description = "Criar reserva.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<ReservationResponseDTO>> create(@RequestBody @Valid ReservationPostRequestDTO reservationPostRequestDTO) {
        log.info("Criando reserva...");
        return new BaseSuccessResponse201<>(reservationServiceGateway.create(reservationPostRequestDTO)).buildResponse();
    }

    @Operation(method = "PUT", summary = "Atualizar reserva", description = "Atualizar reserva.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<ReservationResponseDTO>> update(@PathVariable("hashId") String hashId, @RequestBody @Valid ReservationPutRequestDTO reservationPutRequestDTO) {
        log.info("Atualizando reserva...");
        return new BaseSuccessResponse200<>(reservationServiceGateway.update(hashId, reservationPutRequestDTO)).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar reserva por filtro", description = "Buscar reserva por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/filter")
    public ResponseEntity<BasePageableSuccessResponse200<ReservationResponseDTO>> find(@ParameterObject @Valid ReservationGetFilter filter) {
        log.info("Buscando reservas por filtro...");
        return new BasePageableSuccessResponse200<>(reservationServiceGateway.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar reserva", description = "Buscar reserva.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<ReservationResponseDTO>> find(@PathVariable("hashId") String hashId) {
        log.info("Buscando reserva...");
        return new BaseSuccessResponse200<>(reservationServiceGateway.find(hashId)).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir reserva", description = "Excluir reserva.")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping(value = "/{hashId}")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<ReservationResponseDTO>> delete(@PathVariable("hashId") String hashId) {
        log.info("Excluindo reserva...");
        reservationServiceGateway.delete(hashId);
        return new NoPayloadBaseSuccessResponse200<ReservationResponseDTO>().buildResponseWithoutPayload();
    }
}