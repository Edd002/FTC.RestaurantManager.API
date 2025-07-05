package com.fiap.tech.challenge.domain.restaurantuser;

import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserGetFilter;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserPostRequestDTO;
import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserResponseDTO;
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
@RequestMapping(value = "/api/v1/restaurant-users")
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
@Tag(name = "Usuários de Restaurante - Endpoints de Associação de Usuários com Restaurante")
public class RestaurantUserController {

    private final RestaurantUserService restaurantUserService;

    @Autowired
    public RestaurantUserController(RestaurantUserService restaurantUserService) {
        this.restaurantUserService = restaurantUserService;
    }

    @Operation(method = "POST", summary = "Criar associação de usuário com restaurante", description = "Criar associação de usuário com restaurante.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<RestaurantUserResponseDTO>> create(@RequestBody @Valid RestaurantUserPostRequestDTO restaurantUserPostRequestDTO) {
        log.info("Criando associação de usuário com restaurante...");
        return new BaseSuccessResponse201<>(restaurantUserService.create(restaurantUserPostRequestDTO)).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar associações de usuários com restaurantes por filtro", description = "Buscar associações de usuários com restaurantes por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/filter")
    public ResponseEntity<BasePageableSuccessResponse200<RestaurantUserResponseDTO>> find(@ParameterObject @Valid RestaurantUserGetFilter filter) {
        log.info("Buscando associações de usuários com restaurantes por filtro...");
        return new BasePageableSuccessResponse200<>(restaurantUserService.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar associação de usuário com restaurante", description = "Buscar associação de usuário com restaurante.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<RestaurantUserResponseDTO>> find(@PathVariable("hashId") String hashId) {
        log.info("Buscando associações de usuário com restaurante...");
        return new BaseSuccessResponse200<>(restaurantUserService.find(hashId)).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir associação de usuário com restaurante", description = "Excluir associação de usuário com restaurante.")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping(value = "/{hashId}")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<RestaurantUserResponseDTO>> delete(@PathVariable("hashId") String hashId) {
        log.info("Excluindo associação de usuário com restaurante...");
        restaurantUserService.delete(hashId);
        return new NoPayloadBaseSuccessResponse200<RestaurantUserResponseDTO>().buildResponseWithoutPayload();
    }
}