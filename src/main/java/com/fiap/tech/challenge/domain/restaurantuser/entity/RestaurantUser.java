package com.fiap.tech.challenge.domain.restaurantuser.entity;

import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserEntityListener;
import com.fiap.tech.challenge.domain.restaurantuser.enumerated.constraint.RestaurantUserConstraint;
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
@Table(name = "t_restaurant_user")
@SQLDelete(sql = "UPDATE t_restaurant_user SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ RestaurantUserEntityListener.class })
@ConstraintMapper(constraintClass = RestaurantUserConstraint.class)
public class RestaurantUser extends Audit implements Serializable {

    protected RestaurantUser() {}

    public RestaurantUser(@NonNull Restaurant restaurant, @NonNull User user) {
        this.setRestaurant(restaurant);
        this.setUser(user);
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_RESTAURANT_USER")
    @SequenceGenerator(name = "SQ_RESTAURANT_USER", sequenceName = "SQ_RESTAURANT_USER", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fk_restaurant", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Transient
    private transient RestaurantUser restaurantUserSavedState;

    public void saveState(RestaurantUser restaurantUserSavedState) {
        this.restaurantUserSavedState = restaurantUserSavedState;
    }
}