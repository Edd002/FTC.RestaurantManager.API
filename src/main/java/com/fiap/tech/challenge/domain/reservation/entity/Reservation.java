package com.fiap.tech.challenge.domain.reservation.entity;

import com.fiap.tech.challenge.domain.menuitemorder.MenuItemOrderEntityListener;
import com.fiap.tech.challenge.domain.menuitemorder.enumerated.constraint.MenuItemOrderConstraint;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationStatusEnum;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.constraint.ConstraintMapper;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_reservation")
@SQLDelete(sql = "UPDATE t_reservation SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ MenuItemOrderEntityListener.class })
@ConstraintMapper(constraintClass = MenuItemOrderConstraint.class)
public class Reservation extends Audit implements Serializable {

    protected Reservation() {}

    public Reservation(@NonNull Restaurant restaurant, @NonNull User user) {
        this.setRestaurant(restaurant);
        this.setUser(user);
    }

    public Reservation rebuild(@NonNull Restaurant restaurant, @NonNull User user) {
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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatusEnum status;

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