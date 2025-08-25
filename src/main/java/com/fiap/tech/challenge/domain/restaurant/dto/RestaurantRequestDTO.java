package com.fiap.tech.challenge.domain.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictIntegerDeserializer;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import com.fiap.tech.challenge.global.util.deserializer.TimeDeserializer;
import com.fiap.tech.challenge.global.util.enumerated.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public abstract class RestaurantRequestDTO extends BaseRequestDTO {

    @Schema(description = "Nome do restaurante.", example = "Pizzaria Alonzo", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o nome do restaurante é 255 caracteres.")
    @NotBlank(message = "O nome do restaurante não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("name")
    private String name;

    @Schema(description = "Horário de abertura do restaurante para o café da manhã.", example = "07:00")
    @NotNull(message = "O horário de abertura do restaurante para o café da manhã não pode ser nulo.")
    @JsonDeserialize(using = TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT-3")
    @JsonProperty("breakfastOpeningHours")
    private Date breakfastOpeningHours;

    @Schema(description = "Horário de fechamento do restaurante para o café da manhã.", example = "10:00")
    @NotNull(message = "O horário de fechamento do restaurante para o café da manhã não pode ser nulo.")
    @JsonDeserialize(using = TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT-3")
    @JsonProperty("breakfastClosingHours")
    private Date breakfastClosingHours;

    @Schema(description = "Quantidade máxima de reservas para o horário de café da manhã.", example = "100")
    @NotNull(message = "A quantidade máxima de reservas para o horário de café da manhã não pode ser nula.")
    @Positive(message = "A quantidade máxima de reservas para o horário de café da manhã ser um número positivo.")
    @JsonDeserialize(using = StrictIntegerDeserializer.class)
    @JsonProperty("breakfastLimitReservations")
    private Integer breakfastLimitReservations;

    @Schema(description = "Horário de abertura do restaurante para o almoço.", example = "11:00")
    @NotNull(message = "O horário de abertura do restaurante para o almoço não pode ser nulo.")
    @JsonDeserialize(using = TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT-3")
    @JsonProperty("lunchOpeningHours")
    private Date lunchOpeningHours;

    @Schema(description = "Horário de fechamento do restaurante para o almoço.", example = "14:30")
    @NotNull(message = "O horário de fechamento do restaurante para o almoço não pode ser nulo.")
    @JsonDeserialize(using = TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT-3")
    @JsonProperty("lunchClosingHours")
    private Date lunchClosingHours;

    @Schema(description = "Quantidade máxima de reservas para o horário de almoço.", example = "100")
    @NotNull(message = "A quantidade máxima de reservas para o horário de almoço não pode ser nula.")
    @Positive(message = "A quantidade máxima de reservas para o horário de almoço ser um número positivo.")
    @JsonDeserialize(using = StrictIntegerDeserializer.class)
    @JsonProperty("lunchLimitReservations")
    private Integer lunchLimitReservations;

    @Schema(description = "Horário de abertura do restaurante para a janta.", example = "19:00")
    @NotNull(message = "O horário de abertura do restaurante para a janta não pode ser nulo.")
    @JsonDeserialize(using = TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT-3")
    @JsonProperty("dinnerOpeningHours")
    private Date dinnerOpeningHours;

    @Schema(description = "Horário de fechamento do restaurante para a janta.", example = "23:00")
    @NotNull(message = "O horário de fechamento do restaurante para a janta não pode ser nulo.")
    @JsonDeserialize(using = TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT-3")
    @JsonProperty("dinnerClosingHours")
    private Date dinnerClosingHours;

    @Schema(description = "Quantidade máxima de reservas para o horário de janta.", example = "100")
    @NotNull(message = "A quantidade máxima de reservas para o horário de janta não pode ser nula.")
    @Positive(message = "A quantidade máxima de reservas para o horário de janta ser um número positivo.")
    @JsonDeserialize(using = StrictIntegerDeserializer.class)
    @JsonProperty("dinnerLimitReservations")
    private Integer dinnerLimitReservations;

    @Schema(description = "Tipo do restaurante.", example = "STEAKHOUSE", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o tipo do restaurante é 255 caracteres.")
    @ValueOfEnum(enumClass = RestaurantTypeEnum.class, message = "Tipo do restaurante inválido.")
    @NotBlank(message = "O tipo do restaurante não pode ser nulo ou em branco.")
    @JsonProperty("type")
    private String type;
}