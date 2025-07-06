package com.fiap.tech.challenge.domain.restaurant;

import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantGetFilter;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantPutRequestDTO;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
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
@RequestMapping(value = "/api/v1/restaurants")
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
@Tag(name = "Restaurantes - Endpoints de Restaurantes")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(method = "POST", summary = "Criar restaurante", description = "Criar restaurante.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<RestaurantResponseDTO>> create(@RequestBody @Valid RestaurantPostRequestDTO restaurantPostRequestDTO) {
        log.info("Criando restaurante...");
        return new BaseSuccessResponse201<>(restaurantService.create(restaurantPostRequestDTO)).buildResponse();
    }

    @Operation(method = "PUT", summary = "Atualizar restaurante", description = "Atualizar restaurante.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<RestaurantResponseDTO>> update(@PathVariable("hashId") String hashId, @RequestBody @Valid RestaurantPutRequestDTO restaurantPutRequestDTO) {
        log.info("Atualizando restaurante...");
        return new BaseSuccessResponse200<>(restaurantService.update(hashId, restaurantPutRequestDTO)).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar restaurante por filtro", description = "Buscar restaurante por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/filter")
    public ResponseEntity<BasePageableSuccessResponse200<RestaurantResponseDTO>> find(@ParameterObject @Valid RestaurantGetFilter filter) {
        log.info("Buscando restaurantes por filtro...");
        return new BasePageableSuccessResponse200<>(restaurantService.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar restaurante", description = "Buscar restaurante.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<RestaurantResponseDTO>> find(@PathVariable("hashId") String hashId) {
        log.info("Buscando restaurante...");
        return new BaseSuccessResponse200<>(restaurantService.find(hashId)).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir restaurante", description = "Excluir restaurante.")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping(value = "/{hashId}")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<RestaurantResponseDTO>> delete(@PathVariable("hashId") String hashId) {
        log.info("Excluindo restaurante...");
        restaurantService.delete(hashId);
        return new NoPayloadBaseSuccessResponse200<RestaurantResponseDTO>().buildResponseWithoutPayload();
    }
}