package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.user.dto.UserGetFilter;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
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
import jakarta.persistence.EntityManager;
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
@RequestMapping(value = "/api/v1/users")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse400.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse401.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse403.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse404.class))),
        @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse405.class))),
        @ApiResponse(responseCode = "412", description = "Precondition Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse412.class))),
        @ApiResponse(responseCode = "415", description = "Unsupported Media Type", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse415.class))),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse422.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseErrorResponse500.class)))
})
@Tag(name = "Usuários - Endpoints de Usuários")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, EntityManager entityManager) {
        this.userService = userService;
    }

    @Operation(method = "POST", summary = "Criar usuário", description = "Criar usuário.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<UserResponseDTO>> create(@RequestBody @Valid UserPostRequestDTO userPostRequestDTO) {
        log.info("Criando usuário...");
        return new BaseSuccessResponse201<>(userService.create(userPostRequestDTO)).buildResponse();
    }

    @Operation(method = "PUT", summary = "Atualizar usuário", description = "Atualizar usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<UserResponseDTO>> update(@PathVariable("hashId") String hashId, @RequestBody @Valid UserPutRequestDTO userPutRequestDTO) {
        log.info("Atualizando usuário...");
        return new BaseSuccessResponse200<>(userService.update(hashId, userPutRequestDTO)).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar usuário por filtro", description = "Buscar usuário por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<BasePageableSuccessResponse200<UserResponseDTO>> find(@ParameterObject @Valid UserGetFilter filter) {
        log.info("Buscando usuários por filtro...");
        return new BasePageableSuccessResponse200<>(userService.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar usuário por hash id", description = "Buscar usuário por hash id.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<UserResponseDTO>> find(@PathVariable("hashId") String hashId) {
        log.info("Buscando usuário por hash id...");
        return new BaseSuccessResponse200<>(userService.find(hashId)).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir usuário por hash id", description = "Excluir usuário por hash id.")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping(value = "/{hashId}")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<UserResponseDTO>> deleteByHashId(@PathVariable("hashId") String hashId) {
        log.info("Excluindo usuário por hash id...");
        userService.delete(hashId);
        return new NoPayloadBaseSuccessResponse200<UserResponseDTO>().buildResponseWithoutPayload();
    }
}