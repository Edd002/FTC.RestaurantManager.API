package com.fiap.tech.challenge.domain.menuitem.entity;

import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.MenuItemEntityListener;
import com.fiap.tech.challenge.domain.menuitem.enumerated.constraint.MenuItemConstraint;
import com.fiap.tech.challenge.domain.menuitemorder.entity.MenuItemOrder;
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
import java.math.BigDecimal;
import java.util.List;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_menu_item")
@SQLDelete(sql = "UPDATE t_menu_item SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ MenuItemEntityListener.class })
@ConstraintMapper(constraintClass = MenuItemConstraint.class)
public class MenuItem extends Audit implements Serializable {

    protected MenuItem() {}

    public MenuItem(@NonNull String name, @NonNull String description, @NonNull BigDecimal price, @NonNull Boolean availability, @NonNull String photoUrl, @NonNull Menu menu) {
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setAvailability(availability);
        this.setPhotoUrl(photoUrl);
        this.setMenu(menu);
    }

    public MenuItem rebuild(@NonNull String name, @NonNull String description, @NonNull BigDecimal price, @NonNull Boolean availability, @NonNull String photoUrl, @NonNull Menu menu) {
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setAvailability(availability);
        this.setPhotoUrl(photoUrl);
        this.setMenu(menu);
        return this;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_MENU_ITEM")
    @SequenceGenerator(name = "SQ_MENU_ITEM", sequenceName = "SQ_MENU_ITEM", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", precision = 7, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "availability", nullable = false)
    private Boolean availability;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menuItem", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    private List<MenuItemOrder> menuItemOrders;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fk_menu", nullable = false)
    private Menu menu;

    @Transient
    private transient MenuItem menuItemSavedState;

    public void saveState(MenuItem menuItemSavedState) {
        this.menuItemSavedState = menuItemSavedState;
    }
}