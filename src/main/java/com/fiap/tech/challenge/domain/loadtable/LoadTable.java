package com.fiap.tech.challenge.domain.loadtable;

import com.fiap.tech.challenge.domain.loadtable.enumerated.LoadTableConstraintEnum;
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

@Entity
@NoArgsConstructor
@Table(name = "t_load_table")
@SQLDelete(sql = "UPDATE t_load_table SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ LoadTableEntityListener.class })
public class LoadTable extends Audit implements Serializable {

    public LoadTable(String entityName) {
        this.entityName = entityName;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_LOAD_TABLE")
    @SequenceGenerator(name = "SQ_LOAD_TABLE", sequenceName = "SQ_LOAD_TABLE", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter @Setter private Long id;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Column(name = "entity_load_enabled", nullable = false)
    @Getter @Setter private Boolean entityLoadEnabled = Boolean.FALSE;

    @Transient
    @Getter private transient LoadTable loadTableSavedState;

    public void saveState(LoadTable loadTableSavedState) {
        this.loadTableSavedState = loadTableSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return LoadTableConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }

    @Override
    public void setHashId(String hashId) {
        BeanComponent.getBean(ILoadTableRepository.class).findByHashId(hashId).ifPresentOrElse(loadTable -> this.setId(loadTable.getId()), () -> {
            throw new EntityNotFoundException(String.format("A carregamento de tabela com o hash id %s não foi encontrado.", hashId));
        });
        super.setHashId(hashId);
    }
}