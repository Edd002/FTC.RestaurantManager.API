package com.fiap.tech.challenge.domain.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.address.dto.AddressResponseDTO;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RestaurantResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Nome do restaurante.", example = "Pizzaria Alonzo")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Horário de abertura do restaurante para o café da manhã.", example = "07:00")
    @JsonProperty("breakfastOpeningHoursStart")
    private Date breakfastOpeningHoursStart;

    @Schema(description = "Horário de fechamento do restaurante para o café da manhã.", example = "10:00")
    @JsonProperty("breakfastClosingHoursStart")
    private Date breakfastClosingHoursStart;

    @Schema(description = "Horário de abertura do restaurante para o almoço.", example = "11:00")
    @JsonProperty("lunchOpeningHoursStart")
    private Date lunchOpeningHoursStart;

    @Schema(description = "Horário de fechamento do restaurante para o almoço.", example = "14:30")
    @JsonProperty("lunchClosingHoursStart")
    private Date lunchClosingHoursStart;

    @Schema(description = "Horário de abertura do restaurante para a janta.", example = "19:00")
    @JsonProperty("dinnerOpeningHoursStart")
    private Date dinnerOpeningHoursStart;

    @Schema(description = "Horário de fechamento do restaurante para a janta.", example = "23:00")
    @JsonProperty("dinnerClosingHoursStart")
    private Date dinnerClosingHoursStart;

    @Schema(description = "Permissão do usuário.", example = "OWNER")
    @JsonProperty("type")
    private RestaurantTypeEnum type;

    @Schema(description = "Tipo do restaurante.", example = "STEAKHOUSE")
    @JsonProperty("address")
    private AddressResponseDTO address;
}