package com.fiap.tech.challenge.domain.reservation.dto;

import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public final class ReservationGetFilter extends BasePaginationFilter {

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0")
    @NotBlank(message = "O hash id do restaurante não pode ser nulo ou em branco.")
    private String hashIdRestaurant;

    @Schema(description = "Status da reserva do restaurante.", example = "REQUESTED")
    private ReservationBookingStatusEnum bookingStatus;

    @Schema(description = "Horário da reserva do restaurante.", example = "BREAKFAST")
    private ReservationBookingTimeEnum bookingTime;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Data da reserva do restaurante.", type = "string", format = "date", example = "30/08/2025")
    private Date bookingDate;

    public ReservationGetFilter(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}