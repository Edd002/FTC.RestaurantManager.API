package com.fiap.tech.challenge.domain.reservation;

import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends IBaseRepository<Reservation> {
}