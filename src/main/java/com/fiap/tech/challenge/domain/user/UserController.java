package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.user.dto.UserGetFilter;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.global.base.BaseController;
import com.fiap.tech.challenge.global.base.response.error.*;
import com.fiap.tech.challenge.global.base.response.success.pageable.BasePageableSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.base.response.success.nocontent.NoPayloadBaseSuccessResponse200;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springdoc.core.annotations.ParameterObject;
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
public class UserController extends BaseController {

    @Operation(method = "POST", summary = "Criar usuário", description = "Criar usuário.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<UserResponseDTO>> create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        log.info("Criando usuário...");
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        return new BaseSuccessResponse201<UserResponseDTO>(userResponseDTO).buildResponse();
    }

    @Operation(method = "PUT", summary = "Atualizar usuário", description = "Atualizar usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<UserResponseDTO>> update(@PathVariable("hashId") String hashId, @RequestBody @Valid UserPutRequestDTO userPutRequestDTO) {
        log.info("Atualizando user...");
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        return new BaseSuccessResponse200<UserResponseDTO>(userResponseDTO).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar user por filtro", description = "Buscar user por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<BasePageableSuccessResponse200<UserResponseDTO>> findByFilter(@ParameterObject @Valid UserGetFilter filter) {
        log.info("Buscando users por filtro...");
        //return new BasePageableSuccessResponse200<UserResponseDTO>(null, null).buildPageableResponse();
        return null;
    }

    @Operation(method = "GET", summary = "Buscar user por hash id", description = "Buscar user por hash id.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<UserResponseDTO>> findOneByHashId(@PathVariable("hashId") String hashId) {
        log.info("Buscando user por hash id...");
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        return new BaseSuccessResponse200<UserResponseDTO>(userResponseDTO).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir user por hash id", description = "Excluir user por hash id.")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping(value = "/{hashId}")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<UserResponseDTO>> deleteOneByHashId(@PathVariable("hashId") String hashId) {
        log.info("Excluindo user por hash id...");
        return new NoPayloadBaseSuccessResponse200<UserResponseDTO>().buildResponseWithoutPayload();
    }
}