package com.fiap.tech.challenge.domain.reservation.entity;

import com.fiap.tech.challenge.domain.reservation.ReservationEntityListener;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.constraint.ReservationConstraint;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.constraint.ConstraintMapper;
import com.fiap.tech.challenge.global.exception.ReservationCreateException;
import com.fiap.tech.challenge.global.util.DateUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_reservation")
@SQLDelete(sql = "UPDATE t_reservation SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ ReservationEntityListener.class })
@ConstraintMapper(constraintClass = ReservationConstraint.class)
public class Reservation extends Audit implements Serializable {

    protected Reservation() {}

    public Reservation(@NonNull ReservationBookingStatusEnum bookingStatus, @NonNull ReservationBookingTimeEnum bookingTime, @NonNull Date bookingDate, @NonNull Long bookingQuantity, @NonNull Restaurant restaurant, @NonNull User user) {
        long totalReservationQuantityInBookingTimeAndBookingDate = restaurant.getReservations().stream()
                .filter(restaurantReservation -> !restaurantReservation.getBookingStatus().equals(ReservationBookingStatusEnum.REJECTED)
                        && (restaurantReservation.getBookingTime().equals(bookingTime)
                        && restaurantReservation.getBookingDate().equals(bookingDate)))
                .mapToLong(Reservation::getBookingQuantity)
                .sum();
        if (bookingDate.before(DateUtil.nowTruncate())) {
            throw new ReservationCreateException("A reserva não pode ser realizada para um dia anterior à hoje.");
        }
        if ((totalReservationQuantityInBookingTimeAndBookingDate + bookingQuantity) > restaurant.getLimitReservations(bookingTime)) {
            throw new ReservationCreateException("A quantidade de reservas solicitadas ultrapassa o limite que o restaurante tem disponível.");
        }
        this.setBookingStatus(bookingStatus);
        this.setBookingTime(bookingTime);
        this.setBookingDate(bookingDate);
        this.setBookingQuantity(bookingQuantity);
        this.setRestaurant(restaurant);
        this.setUser(user);
    }

    public Reservation rebuild(@NonNull ReservationBookingStatusEnum bookingStatus, @NonNull ReservationBookingTimeEnum bookingTime, @NonNull Date bookingDate, @NonNull Long bookingQuantity, @NonNull Restaurant restaurant, @NonNull User user) {
        this.setBookingStatus(bookingStatus);
        this.setBookingTime(bookingTime);
        this.setBookingDate(bookingDate);
        this.setBookingQuantity(bookingQuantity);
        this.setRestaurant(restaurant);
        this.setUser(user);
        return this;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_RESERVATION")
    @SequenceGenerator(name = "SQ_RESERVATION", sequenceName = "SQ_RESERVATION", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "booking_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationBookingStatusEnum bookingStatus;

    @Column(name = "booking_time", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationBookingTimeEnum bookingTime;

    @Column(name = "booking_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @Column(name = "booking_quantity", nullable = false)
    private Long bookingQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_restaurant", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Transient
    private transient Reservation reservationSavedState;

    public void saveState(Reservation reservationSavedState) {
        this.reservationSavedState = reservationSavedState;
    }
}