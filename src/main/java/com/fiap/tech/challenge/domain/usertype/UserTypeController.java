package com.fiap.tech.challenge.domain.usertype;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypeGetFilter;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePutRequestDTO;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypeResponseDTO;
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
@RequestMapping(value = "/api/v1/user-types")
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
@Tag(name = "Tipos de Usuário - Endpoints de Tipos de Usuário")
public class UserTypeController {

    private final UserTypeService userTypeService;

    @Autowired
    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @Operation(method = "POST", summary = "Criar tipo de usuário", description = "Criar tipo de usuário.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<UserTypeResponseDTO>> create(@RequestBody @Valid UserTypePostRequestDTO userTypePostRequestDTO) {
        log.info("Criando tipo de usuário...");
        return new BaseSuccessResponse201<>(userTypeService.create(userTypePostRequestDTO)).buildResponse();
    }

    @Operation(method = "PUT", summary = "Atualizar tipo do usuário", description = "Atualizar tipo do usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<UserTypeResponseDTO>> update(@PathVariable("hashId") String hashId, @RequestBody @Valid UserTypePutRequestDTO userTypePutRequestDTO) {
        log.info("Criando tipo de usuário...");
        return new BaseSuccessResponse200<>(userTypeService.update(hashId, userTypePutRequestDTO)).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar tipo de usuário por filtro", description = "Buscar tipo de usuário por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/filter")
    public ResponseEntity<BasePageableSuccessResponse200<UserTypeResponseDTO>> find(@ParameterObject @Valid UserTypeGetFilter filter) {
        log.info("Buscando tipos de usuário por filtro...");
        return new BasePageableSuccessResponse200<>(userTypeService.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar tipo de usuário", description = "Buscar tipo de usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<UserTypeResponseDTO>> find(@PathVariable("hashId") String hashId) {
        log.info("Buscando tipo de usuário...");
        return new BaseSuccessResponse200<>(userTypeService.find(hashId)).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir tipo de usuário", description = "Excluir tipo de usuário.")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping(value = "/{hashId}")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<UserTypeResponseDTO>> delete(@PathVariable("hashId") String hashId) {
        log.info("Excluindo tipo de usuário...");
        userTypeService.delete(hashId);
        return new NoPayloadBaseSuccessResponse200<UserTypeResponseDTO>().buildResponseWithoutPayload();
    }
}