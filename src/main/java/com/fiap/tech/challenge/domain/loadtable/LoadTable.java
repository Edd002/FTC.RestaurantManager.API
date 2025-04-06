package com.fiap.tech.challenge.domain.loadtable;

import com.fiap.tech.challenge.domain.loadtable.enumerated.LoadTableConstraintEnum;
import com.fiap.tech.challenge.global.audity.Audity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_load_table")
@SQLDelete(sql = "UPDATE t_load_table SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id"})
@EntityListeners({ LoadTableEntityListener.class })
public class LoadTable extends Audity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_LOAD_TABLE")
    @SequenceGenerator(name = "SQ_LOAD_TABLE", sequenceName = "SQ_LOAD_TABLE", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Builder.Default
    @Column(name = "entity_load", nullable = false)
    private Boolean entityLoad = Boolean.FALSE;

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