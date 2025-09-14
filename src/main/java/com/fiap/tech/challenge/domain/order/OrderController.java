package com.fiap.tech.challenge.domain.order;

import com.fiap.tech.challenge.domain.order.dto.*;
import com.fiap.tech.challenge.global.base.response.error.*;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log
@Validated
@RestController
@RequestMapping(value = "/api/v1/orders")
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
@Tag(name = "Pedidos - Endpoints de Pedidos")
public class OrderController {

    private final OrderServiceGateway orderServiceGateway;

    @Autowired
    public OrderController(OrderServiceGateway orderServiceGateway) {
        this.orderServiceGateway = orderServiceGateway;
    }

    @Operation(method = "POST", summary = "Criar pedido", description = "Criar pedido.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<OrderResponseDTO>> create(@RequestBody @Valid OrderPostRequestDTO orderPostRequestDTO) {
        log.info("Criando pedido...");
        return new BaseSuccessResponse201<>(orderServiceGateway.create(orderPostRequestDTO)).buildResponse();
    }

    @Operation(method = "PATCH", summary = "Atualizar status do pedido", description = "Atualizar status do pedido.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "hasAuthority('OWNER')")
    @PatchMapping(value = "/change-status/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<OrderResponseDTO>> updateStatus(@PathVariable("hashId") String hashId, @RequestBody @Valid OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO) {
        log.info("Atualizando status do pedido...");
        return new BaseSuccessResponse200<>(orderServiceGateway.updateStatus(hashId, orderUpdateStatusPatchRequestDTO)).buildResponse();
    }

    @Operation(method = "PATCH", summary = "Atualizar tipo do pedido", description = "Atualizar tipo do pedido.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @PatchMapping(value = "/change-type/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<OrderResponseDTO>> updateType(@PathVariable("hashId") String hashId, @RequestBody @Valid OrderUpdateTypePatchRequestDTO orderUpdateTypePatchRequestDTO) {
        log.info("Atualizando tipo do pedido...");
        return new BaseSuccessResponse200<>(orderServiceGateway.updateType(hashId, orderUpdateTypePatchRequestDTO)).buildResponse();
    }

    @Operation(method = "PATCH", summary = "Cancelar pedido", description = "Cancelar pedido.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @PatchMapping(value = "/cancel/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<OrderResponseDTO>> cancel(@PathVariable("hashId") String hashId) {
        log.info("Cancelando pedido...");
        return new BaseSuccessResponse200<>(orderServiceGateway.cancel(hashId)).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar pedido por filtro", description = "Buscar pedido por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/filter")
    public ResponseEntity<BasePageableSuccessResponse200<OrderResponseDTO>> find(@ParameterObject @Valid OrderGetFilter filter) {
        log.info("Buscando pedidos por filtro...");
        return new BasePageableSuccessResponse200<>(orderServiceGateway.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar pedido", description = "Buscar pedido.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<OrderResponseDTO>> find(@PathVariable("hashId") String hashId) {
        log.info("Buscando pedido...");
        return new BaseSuccessResponse200<>(orderServiceGateway.find(hashId)).buildResponse();
    }
}