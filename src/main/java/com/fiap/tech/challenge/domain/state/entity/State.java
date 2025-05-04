package com.fiap.tech.challenge.domain.state.entity;

import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.state.StateEntityListener;
import com.fiap.tech.challenge.domain.state.enumerated.StateConstraintEnum;
import com.fiap.tech.challenge.global.audit.Audit;
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
@Table(name = "t_state")
@SQLDelete(sql = "UPDATE t_state SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ StateEntityListener.class })
public class State extends Audit implements Serializable {

    // TODO Achar alternativa para getDeclaredConstructor().newInstance() de BaseController e alterar construtor para protected
    public State() {}

    public State(@NonNull Long id) {
        this.setId(id);
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_STATE")
    @SequenceGenerator(name = "SQ_STATE", sequenceName = "SQ_STATE", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<City> cityList;

    @Transient
    private transient State stateSavedState;

    public void saveState(State stateSavedState) {
        this.stateSavedState = stateSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return StateConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}