package com.fiap.tech.challenge.domain.state;

import com.fiap.tech.challenge.domain.city.City;
import com.fiap.tech.challenge.domain.state.enumerated.StateConstraintEnum;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.bean.BeanComponent;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "t_state")
@SQLDelete(sql = "UPDATE t_state SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ StateEntityListener.class })
public class State extends Audit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_STATE")
    @SequenceGenerator(name = "SQ_STATE", sequenceName = "SQ_STATE", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter @Setter private Long id;

    @Column(name = "name", nullable = false)
    @Getter private String name;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<City> cityList;

    @Transient
    @Getter private transient State stateSavedState;

    public void saveState(State stateSavedState) {
        this.stateSavedState = stateSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return StateConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }

    @Override
    public void setHashId(String hashId) {
        BeanComponent.getBean(IStateRepository.class).findByHashId(hashId).ifPresentOrElse(state -> this.setId(state.getId()), () -> {
            throw new EntityNotFoundException(String.format("O estado com o hash id %s não foi encontrada.", hashId));
        });
        super.setHashId(hashId);
    }
}