package com.fiap.tech.challenge.global.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import static com.fiap.tech.challenge.global.util.HashUtil.generateHash;

@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class, AuditEntityListener.class })
public abstract class Audit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "hash_id", nullable = false, updatable = false)
    private final String hashId = generateHash();

    @CreatedDate
    @Column(name = "created_in", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private final Date createdIn = new Date();

    @Getter @Setter
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

    @Column(name = "deleted", nullable = false)
    private final Boolean deleted = Boolean.FALSE;

    @Getter
    @Transient
    private transient Audit auditSavedState;

    public void saveState(Audit auditSavedState) {
        this.auditSavedState = auditSavedState;
    }

    public abstract String getConstraintErrorMessage(String constraintName);

}