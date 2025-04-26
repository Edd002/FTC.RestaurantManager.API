package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.enumerated.JwtConstraintEnum;
import com.fiap.tech.challenge.domain.user.User;
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
@Table(name = "t_jwt")
@SQLDelete(sql = "UPDATE t_jwt SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ JwtEntityListener.class })
public class Jwt extends Audit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_JWT")
    @SequenceGenerator(name = "SQ_JWT", sequenceName = "SQ_JWT", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter @Setter private Long id;

    @Column(name = "bearer_token", nullable = false)
    private String bearerToken;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Transient
    @Getter private transient Jwt jwtSavedState;

    public void saveState(Jwt jwtSavedState) {
        this.jwtSavedState = jwtSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return JwtConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }

    @Override
    public void setHashId(String hashId) {
        BeanComponent.getBean(IJwtRepository.class).findByHashId(hashId).ifPresentOrElse(jwt -> this.setId(jwt.getId()), () -> {
            throw new EntityNotFoundException(String.format("O JWT com o hash id %s não foi encontrado.", hashId));
        });
        super.setHashId(hashId);
    }
}