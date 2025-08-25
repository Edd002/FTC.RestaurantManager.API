package com.fiap.tech.challenge.domain.reservation;

import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceGateway extends BaseServiceGateway<IReservationRepository, Reservation> {
}