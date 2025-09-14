package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.dto.JwtGeneratePostRequestDTO;
import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.global.base.response.error.*;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.base.response.success.nocontent.NoPayloadBaseSuccessResponse200;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log
@Validated
@RestController
@RequestMapping(value = "/api/v1/jwts")
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
@Tag(name = "JWT - Endpoints de JSON Web Token (JWT)")
public class JwtController {

    private final JwtServiceGateway jwtServiceGateway;

    @Autowired
    public JwtController(JwtServiceGateway jwtServiceGateway) {
        this.jwtServiceGateway = jwtServiceGateway;
    }

    @Operation(method = "POST", summary = "Gerar um novo JWT.", description = "Gerar um novo JWT.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping("/generate")
    public ResponseEntity<BaseSuccessResponse201<JwtResponseDTO>> generate(@RequestBody @Valid JwtGeneratePostRequestDTO jwtGeneratePostRequestDTO) {
        log.info("Gerando JWT...");
        return new BaseSuccessResponse201<>(jwtServiceGateway.generate(jwtGeneratePostRequestDTO)).buildResponse();
    }

    @Operation(method = "GET", summary = "Verificar se o JWT está válido.", description = "Verificar se o JWT está válido.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("/validate")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<JwtResponseDTO>> validate(@Parameter(required = true, hidden = true) @RequestHeader("Authorization") String bearerToken) {
        log.info("Validando JWT...");
        jwtServiceGateway.validate(bearerToken);
        return new NoPayloadBaseSuccessResponse200<JwtResponseDTO>().buildResponseWithoutPayload();
    }

    @Operation(method = "POST", summary = "Invalidar o JWT.", description = "Invalidar o JWT.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping("/invalidate")
    public ResponseEntity<NoPayloadBaseSuccessResponse200<JwtResponseDTO>> invalidate(@Parameter(required = true, hidden = true) @RequestHeader("Authorization") String bearerToken) {
        log.info("Invalidando JWT...");
        jwtServiceGateway.invalidate(bearerToken);
        return new NoPayloadBaseSuccessResponse200<JwtResponseDTO>().buildResponseWithoutPayload();
    }
}