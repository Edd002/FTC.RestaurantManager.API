package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.user.dto.*;
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
@RequestMapping(value = "/api/v1/users")
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
@Tag(name = "Usuários - Endpoints de Usuários")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
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
    @PutMapping
    public ResponseEntity<BaseSuccessResponse200<UserResponseDTO>> update(@RequestBody @Valid UserPutRequestDTO userPutRequestDTO) {
        log.info("Atualizando usuário...");
        return new BaseSuccessResponse200<>(userService.update(userPutRequestDTO)).buildResponse();
    }

    @Operation(method = "PATCH", summary = "Atualizar senha do usuário", description = "Atualizar senha do usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PatchMapping(value = "/change-password")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<UserResponseDTO>> updatePassword(@RequestBody @Valid UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO) {
        log.info("Atualizando senha do usuário...");
        userService.updatePassword(userUpdatePasswordPatchRequestDTO);
        return new NoPayloadBaseSuccessResponse200<UserResponseDTO>().buildResponseWithoutPayload();
    }

    @Operation(method = "GET", summary = "Buscar usuário por filtro - Permissão necessária: [ADMIN, OWNER]", description = "Buscar usuário por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/filter")
    public ResponseEntity<BasePageableSuccessResponse200<UserResponseDTO>> find(@ParameterObject @Valid UserGetFilter filter) {
        log.info("Buscando usuários por filtro...");
        return new BasePageableSuccessResponse200<>(userService.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar usuário", description = "Buscar usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<BaseSuccessResponse200<UserResponseDTO>> find() {
        log.info("Buscando usuário...");
        return new BaseSuccessResponse200<>(userService.find()).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir usuário", description = "Excluir usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping
    public ResponseEntity<NoPayloadBaseSuccessResponse200<UserResponseDTO>> delete() {
        log.info("Excluindo usuário...");
        userService.delete();
        return new NoPayloadBaseSuccessResponse200<UserResponseDTO>().buildResponseWithoutPayload();
    }
}