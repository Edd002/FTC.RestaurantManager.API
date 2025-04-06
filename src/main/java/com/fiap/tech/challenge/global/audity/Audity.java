package com.fiap.tech.challenge.global.audity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import static com.fiap.tech.challenge.global.util.HashUtil.generateHash;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class, AudityEntityListener.class })
public abstract class Audity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Builder.Default
    @Column(name = "hash_id", nullable = false, updatable = false)
    private String hashId = generateHash();

    @Builder.Default
    @CreatedDate
    @Column(name = "created_in", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdIn = new Date();

    @Builder.Default
    @LastModifiedDate
    @Column(name = "updated_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedIn = new Date();

    @Column(name = "deleted_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedIn;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String created_by;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updated_by;

    @Column(name = "deleted_by")
    private String deleted_by;

    @Builder.Default
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @Transient
    private transient Audity auditySavedState;

    public void saveState(Audity auditoriaSavedState) {
        this.auditySavedState = auditoriaSavedState;
    }

    public abstract String getConstraintErrorMessage(String constraintName);
}