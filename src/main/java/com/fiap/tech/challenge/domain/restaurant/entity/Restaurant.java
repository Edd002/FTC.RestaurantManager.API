package com.fiap.tech.challenge.domain.restaurant.entity;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.RestaurantEntityListener;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.domain.restaurant.enumerated.constraint.RestaurantConstraint;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.audit.constraint.ConstraintMapper;
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
import java.util.List;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_restaurant")
@SQLDelete(sql = "UPDATE t_restaurant SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ RestaurantEntityListener.class })
@ConstraintMapper(constraintClass = RestaurantConstraint.class)
public class Restaurant extends Audit implements Serializable {

    protected Restaurant() {}

    public Restaurant(@NonNull Long id, @NonNull String name, @NonNull Date breakfastOpeningHours, @NonNull Date breakfastClosingHours, @NonNull Date lunchOpeningHours, @NonNull Date lunchClosingHours, @NonNull Date dinnerOpeningHours, @NonNull Date dinnerClosingHours, @NonNull RestaurantTypeEnum type, @NonNull Menu menu, @NonNull Address address) {
        this.setId(id);
        this.setName(name);
        this.setBreakfastOpeningHours(breakfastOpeningHours);
        this.setBreakfastClosingHours(breakfastClosingHours);
        this.setLunchOpeningHours(lunchOpeningHours);
        this.setLunchClosingHours(lunchClosingHours);
        this.setDinnerOpeningHours(dinnerOpeningHours);
        this.setDinnerClosingHours(dinnerClosingHours);
        this.setType(type);
        this.setMenu(menu);
        this.setAddress(address);
    }

    public Restaurant(@NonNull String name, @NonNull Date breakfastOpeningHours, @NonNull Date breakfastClosingHours, @NonNull Date lunchOpeningHours, @NonNull Date lunchClosingHours, @NonNull Date dinnerOpeningHours, @NonNull Date dinnerClosingHours, @NonNull RestaurantTypeEnum type, @NonNull Menu menu, @NonNull Address address) {
        this.setName(name);
        this.setBreakfastOpeningHours(breakfastOpeningHours);
        this.setBreakfastClosingHours(breakfastClosingHours);
        this.setLunchOpeningHours(lunchOpeningHours);
        this.setLunchClosingHours(lunchClosingHours);
        this.setDinnerOpeningHours(dinnerOpeningHours);
        this.setDinnerClosingHours(dinnerClosingHours);
        this.setType(type);
        this.setMenu(menu);
        this.setAddress(address);
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_RESTAURANT")
    @SequenceGenerator(name = "SQ_RESTAURANT", sequenceName = "SQ_RESTAURANT", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "breakfast_opening_hours")
    @Temporal(TemporalType.TIME)
    private Date breakfastOpeningHours;

    @Column(name = "breakfast_closing_hours")
    @Temporal(TemporalType.TIME)
    private Date breakfastClosingHours;

    @Column(name = "lunch_opening_hours")
    @Temporal(TemporalType.TIME)
    private Date lunchOpeningHours;

    @Column(name = "lunch_closing_hours")
    @Temporal(TemporalType.TIME)
    private Date lunchClosingHours;

    @Column(name = "dinner_opening_hours")
    @Temporal(TemporalType.TIME)
    private Date dinnerOpeningHours;

    @Column(name = "dinner_closing_hours")
    @Temporal(TemporalType.TIME)
    private Date dinnerClosingHours;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RestaurantTypeEnum type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<RestaurantUser> restaurantUsers;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "fk_menu", nullable = false)
    private Menu menu;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "fk_address", nullable = false)
    private Address address;

    @Transient
    private transient Restaurant restaurantSavedState;

    public void saveState(Restaurant restaurantSavedState) {
        this.restaurantSavedState = restaurantSavedState;
    }
}