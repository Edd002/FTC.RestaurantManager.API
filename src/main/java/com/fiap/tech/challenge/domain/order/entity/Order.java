package com.fiap.tech.challenge.domain.order.entity;

import com.fiap.tech.challenge.domain.menuitemorder.entity.MenuItemOrder;
import com.fiap.tech.challenge.domain.order.OrderEntityListener;
import com.fiap.tech.challenge.domain.order.enumerated.OrderStatusEnum;
import com.fiap.tech.challenge.domain.order.enumerated.OrderTypeEnum;
import com.fiap.tech.challenge.domain.order.enumerated.constraint.OrderConstraint;
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
import java.util.List;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_order")
@SQLDelete(sql = "UPDATE t_order SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({OrderEntityListener.class})
@ConstraintMapper(constraintClass = OrderConstraint.class)
public class Order extends Audit implements Serializable {

    protected Order() {}

    public Order(@NonNull OrderStatusEnum status, @NonNull OrderTypeEnum type, @NonNull Restaurant restaurant, @NonNull User user) {
        this.setStatus(status);
        this.setType(type);
        this.setRestaurant(restaurant);
        this.setUser(user);
    }

    public Order rebuild(@NonNull OrderStatusEnum status, @NonNull OrderTypeEnum type, @NonNull Restaurant restaurant, @NonNull User user) {
        this.setStatus(status);
        this.setType(type);
        this.setRestaurant(restaurant);
        this.setUser(user);
        return this;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_ORDER")
    @SequenceGenerator(name = "SQ_ORDER", sequenceName = "SQ_ORDER", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    private List<MenuItemOrder> menuItemOrders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_restaurant", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Transient
    private transient Order orderSavedState;

    public void saveState(Order orderSavedState) {
        this.orderSavedState = orderSavedState;
    }
}