package com.fiap.tech.challenge.domain.city.entity;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.CityEntityListener;
import com.fiap.tech.challenge.domain.city.enumerated.constraint.CityConstraint;
import com.fiap.tech.challenge.domain.state.entity.State;
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
@Table(name = "t_city")
@SQLDelete(sql = "UPDATE t_city SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ CityEntityListener.class })
@ConstraintMapper(constraintClass = CityConstraint.class)
public class City extends Audit implements Serializable {

    protected City() {}

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_CITY")
    @SequenceGenerator(name = "SQ_CITY", sequenceName = "SQ_CITY", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_state", nullable = false)
    private State state;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Address> addressList;

    @Transient
    private transient City citySavedState;

    public void saveState(City citySavedState) {
        this.citySavedState = citySavedState;
    }
}