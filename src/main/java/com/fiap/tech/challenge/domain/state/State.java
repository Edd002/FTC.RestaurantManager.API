package com.fiap.tech.challenge.domain.state;

import com.fiap.tech.challenge.domain.city.City;
import com.fiap.tech.challenge.domain.state.enumerated.StateConstraintEnum;
import com.fiap.tech.challenge.global.audity.Audit;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_state")
@SQLDelete(sql = "UPDATE t_state SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ StateEntityListener.class })
public final class State extends Audit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_STATE")
    @SequenceGenerator(name = "SQ_STATE", sequenceName = "SQ_STATE", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<City> cityList;

    @Getter
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