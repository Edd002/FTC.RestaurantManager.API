package com.fiap.tech.challenge.domain.reservation;

import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public class ReservationEntityListener {

    @PostLoad
    public void postLoad(Reservation reservationEntity) {
        reservationEntity.saveState(SerializationUtils.clone(reservationEntity));
    }
}