package com.fiap.tech.challenge.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.DateDeserializer;
import com.fiap.tech.challenge.global.util.deserializer.StrictLongDeserializer;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import com.fiap.tech.challenge.global.util.enumerated.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public abstract class ReservationRequestDTO extends BaseRequestDTO {

    @Schema(description = "Horário da reserva do restaurante.", example = "BREAKFAST", maxLength = 255, allowableValues = { "BREAKFAST", "LUNCH", "DINNER" })
    @Size(max = 255, message = "O número de caracteres máximo para o horário da reserva do restaurante é 255 caracteres.")
    @ValueOfEnum(enumClass = ReservationBookingTimeEnum.class, message = "Horário da reserva do restaurante inválido.")
    @NotBlank(message = "O horário da reserva do restaurante não pode ser nulo ou em branco.")
    @JsonProperty("bookingTime")
    private String bookingTime;

    @Schema(description = "Data da reserva do restaurante.", example = "30/08/2025")
    @NotNull(message = "A data da reserva do restaurante não pode ser nula.")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @JsonProperty("bookingDate")
    private Date bookingDate;

    @Schema(description = "Quantidade de reservas do restaurante.", example = "3")
    @NotNull(message = "A quantidade de reservas do restaurante não pode ser nula.")
    @JsonDeserialize(using = StrictLongDeserializer.class)
    @JsonProperty("bookingQuantity")
    private Long bookingQuantity;

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o hash id do restaurante é 255 caracteres.")
    @NotBlank(message = "O hash id do restaurante não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("hashIdRestaurant")
    private String hashIdRestaurant;
}