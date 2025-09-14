package com.fiap.tech.challenge.domain.menuitem;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemGetFilter;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log
@Validated
@RestController
@RequestMapping(value = "/api/v1/menu-items")
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
@Tag(name = "Itens do Menu - Endpoints de Itens do Menu")
public class MenuItemController {

    private final MenuItemServiceGateway menuItemServiceGateway;

    @Autowired
    public MenuItemController(MenuItemServiceGateway menuItemServiceGateway) {
        this.menuItemServiceGateway = menuItemServiceGateway;
    }

    @Operation(method = "POST", summary = "Criar item do menu.", description = "Criar item do menu.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PreAuthorize(value = "hasAuthority('OWNER')")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<MenuItemResponseDTO>> create(@RequestBody @Valid MenuItemPostRequestDTO menuItemPostRequestDTO) {
        log.info("Criando item do menu...");
        return new BaseSuccessResponse201<>(menuItemServiceGateway.create(menuItemPostRequestDTO)).buildResponse();
    }

    @Operation(method = "PUT", summary = "Atualizar item do menu.", description = "Atualizar item do menu.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "hasAuthority('OWNER')")
    @PutMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<MenuItemResponseDTO>> update(@PathVariable("hashId") String hashId, @RequestBody @Valid MenuItemPutRequestDTO menuItemPutRequestDTO) {
        log.info("Atualizando item do menu...");
        return new BaseSuccessResponse200<>(menuItemServiceGateway.update(hashId, menuItemPutRequestDTO)).buildResponse();
    }

    @Operation(method = "GET", summary = "Buscar item do menu por filtro.", description = "Buscar item do menu por filtro.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/filter")
    public ResponseEntity<BasePageableSuccessResponse200<MenuItemResponseDTO>> find(@ParameterObject @Valid MenuItemGetFilter filter) {
        log.info("Buscando itens do menu por filtro...");
        return new BasePageableSuccessResponse200<>(menuItemServiceGateway.find(filter)).buildPageableResponse();
    }

    @Operation(method = "GET", summary = "Buscar item do menu.", description = "Buscar item do menu.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{hashId}")
    public ResponseEntity<BaseSuccessResponse200<MenuItemResponseDTO>> find(@PathVariable("hashId") String hashId) {
        log.info("Buscando item do menu...");
        return new BaseSuccessResponse200<>(menuItemServiceGateway.find(hashId)).buildResponse();
    }

    @Operation(method = "DELETE", summary = "Excluir item do menu.", description = "Excluir item do menu.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "hasAuthority('OWNER')")
    @DeleteMapping(value = "/{hashId}")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<MenuItemResponseDTO>> delete(@PathVariable("hashId") String hashId) {
        log.info("Excluindo item do menu...");
        menuItemServiceGateway.delete(hashId);
        return new NoPayloadBaseSuccessResponse200<MenuItemResponseDTO>().buildResponseWithoutPayload();
    }
}