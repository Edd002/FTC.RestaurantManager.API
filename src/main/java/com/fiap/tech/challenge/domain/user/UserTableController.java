package com.fiap.tech.challenge.domain.user;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public final class UserTableController {

    /*@Operation(method = "POST", summary = "Criar usuário", description = "Criar usuário.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BaseSuccessResponse201<UserResponseDTO>> create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        log.info("Criando usuário...");

        return (ResponseEntity<BaseSuccessResponse201<UserResponseDTO>>) new BaseSuccessResponse201<UserResponseDTO>(userResponseDTO).getResponse();
    }*/
}