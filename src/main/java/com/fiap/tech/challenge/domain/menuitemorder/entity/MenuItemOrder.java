package com.fiap.tech.challenge.domain.menuitemorder.entity;

import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.menuitemorder.MenuItemOrderEntityListener;
import com.fiap.tech.challenge.domain.menuitemorder.enumerated.constraint.MenuItemOrderConstraint;
import com.fiap.tech.challenge.domain.order.entity.Order;
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
@Table(name = "t_menu_item_order")
@SQLDelete(sql = "UPDATE t_menu_item_order SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ MenuItemOrderEntityListener.class })
@ConstraintMapper(constraintClass = MenuItemOrderConstraint.class)
public class MenuItemOrder extends Audit implements Serializable {

    protected MenuItemOrder() {}

    public MenuItemOrder(@NonNull MenuItem menuItem, @NonNull Order order) {
        this.setMenuItem(menuItem);
        this.setOrder(order);
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_MENU_ITEM_ORDER")
    @SequenceGenerator(name = "SQ_MENU_ITEM_ORDER", sequenceName = "SQ_MENU_ITEM_ORDER", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fk_menu_item", nullable = false)
    private MenuItem menuItem;

    @ManyToOne(fetch= FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "fk_order", nullable = false)
    private Order order;

    @Transient
    private transient MenuItemOrder menuItemOrderSavedState;

    public void saveState(MenuItemOrder menuItemOrderSavedState) {
        this.menuItemOrderSavedState = menuItemOrderSavedState;
    }
}