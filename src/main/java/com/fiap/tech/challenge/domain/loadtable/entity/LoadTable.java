package com.fiap.tech.challenge.domain.loadtable.entity;

import com.fiap.tech.challenge.domain.loadtable.LoadTableEntityListener;
import com.fiap.tech.challenge.domain.loadtable.enumerated.LoadTableConstraintEnum;
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

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_load_table")
@SQLDelete(sql = "UPDATE t_load_table SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ LoadTableEntityListener.class })
public class LoadTable extends Audit implements Serializable {

    // TODO Achar alternativa para getDeclaredConstructor().newInstance() de BaseController e alterar construtor para protected
    public LoadTable() {}

    public LoadTable(@NonNull Long id) {
        this.setId(id);
    }

    public LoadTable(@NonNull String entityName) {
        this.setEntityName(entityName);
        this.setEntityLoadEnabled(false);
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_LOAD_TABLE")
    @SequenceGenerator(name = "SQ_LOAD_TABLE", sequenceName = "SQ_LOAD_TABLE", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Column(name = "entity_load_enabled", nullable = false)
    private Boolean entityLoadEnabled;

    @Transient
    private transient LoadTable loadTableSavedState;

    public void saveState(LoadTable loadTableSavedState) {
        this.loadTableSavedState = loadTableSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return LoadTableConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}