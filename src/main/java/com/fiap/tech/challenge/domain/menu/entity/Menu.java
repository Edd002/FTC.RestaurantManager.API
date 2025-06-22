package com.fiap.tech.challenge.domain.menu.entity;

import com.fiap.tech.challenge.domain.menu.MenuEntityListener;
import com.fiap.tech.challenge.domain.menu.enumerated.constraint.MenuConstraint;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
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
import java.util.List;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_menu")
@SQLDelete(sql = "UPDATE t_menu SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ MenuEntityListener.class })
@ConstraintMapper(constraintClass = MenuConstraint.class)
public class Menu extends Audit implements Serializable {

    protected Menu() {}

    public Menu(@NonNull Long id, @NonNull List<MenuItem> menuItems) {
        this.setId(id);
        this.setMenuItems(menuItems);
    }

    public Menu(@NonNull List<MenuItem> menuItems) {
        this.setMenuItems(menuItems);
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_MENU")
    @SequenceGenerator(name = "SQ_MENU", sequenceName = "SQ_MENU", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    private List<MenuItem> menuItems;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "menu", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Restaurant restaurant;

    @Transient
    private transient Menu menuSavedState;

    public void saveState(Menu menuSavedState) {
        this.menuSavedState = menuSavedState;
    }
}