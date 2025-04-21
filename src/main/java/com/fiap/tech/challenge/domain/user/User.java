package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.address.Address;
import com.fiap.tech.challenge.domain.jwt.Jwt;
import com.fiap.tech.challenge.domain.user.enumerated.UserConstraintEnum;
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
@Table(name = "t_user")
@SQLDelete(sql = "UPDATE t_user SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ UserEntityListener.class })
public class User extends Audit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_USER")
    @SequenceGenerator(name = "SQ_USER", sequenceName = "SQ_USER", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter @Setter private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Jwt> jwtList;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "fk_address", nullable = false)
    @Getter private Address address;

    @Transient
    @Getter private transient User userSavedState;

    public void saveState(User userSavedState) {
        this.userSavedState = userSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return UserConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }

    @Override
    public void setHashId(String hashId) {
        super.setHashId(hashId);
        BeanComponent.getBean(IUserRepository.class).findByHashId(hashId).ifPresentOrElse(user -> {
            this.setId(user.getId());
            this.getAddress().setId(user.getAddress().getId());
        }, () -> {
            throw new EntityNotFoundException(String.format("O usuário com o hash id %s não foi encontrado.", hashId));
        });
    }
}