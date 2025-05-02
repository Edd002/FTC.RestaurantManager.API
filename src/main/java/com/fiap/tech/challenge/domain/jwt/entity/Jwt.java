package com.fiap.tech.challenge.domain.jwt.entity;

import com.fiap.tech.challenge.domain.jwt.JwtEntityListener;
import com.fiap.tech.challenge.domain.jwt.enumerated.JwtConstraintEnum;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.audit.Audit;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
@Table(name = "t_jwt")
@SQLDelete(sql = "UPDATE t_jwt SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ JwtEntityListener.class })
public class Jwt extends Audit implements Serializable {

    protected Jwt() {}

    public Jwt(Long id) {
        this.id = id;
    }

    public Jwt(String bearerToken, User user) {
        this.bearerToken = bearerToken;
        this.user = user;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_JWT")
    @SequenceGenerator(name = "SQ_JWT", sequenceName = "SQ_JWT", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "bearer_token", nullable = false)
    private String bearerToken;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Transient
    private transient Jwt jwtSavedState;

    public void saveState(Jwt jwtSavedState) {
        this.jwtSavedState = jwtSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return JwtConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }

    @Override
    public void setUpdatedIn(Date updatedIn) {
        super.setUpdatedIn(updatedIn);
    }
}