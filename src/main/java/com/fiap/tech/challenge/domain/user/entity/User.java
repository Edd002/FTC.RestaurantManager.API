package com.fiap.tech.challenge.domain.user.entity;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.user.UserEntityListener;
import com.fiap.tech.challenge.domain.user.enumerated.UserConstraintEnum;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_user")
@SQLDelete(sql = "UPDATE t_user SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ UserEntityListener.class })
public class User extends Audit implements Serializable {

    protected User() {}

    public User(@NonNull Long id) {
        this.setId(id);
    }

    public User(@NonNull Long id, @NonNull String name, @NonNull String email, @NonNull String login, @NonNull String passwordCryptoKey, @NonNull String password, @NonNull String role, @NonNull Address address) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setLogin(login);
        this.setEncryptedPassword(passwordCryptoKey, password);
        this.setRole(UserRoleEnum.valueOf(role));
        this.setAddress(address);
    }

    public User(@NonNull String name, @NonNull String email, @NonNull String login, @NonNull String passwordCryptoKey, @NonNull String password, @NonNull String role, @NonNull Address address) {
        this.setName(name);
        this.setEmail(email);
        this.setLogin(login);
        this.setEncryptedPassword(passwordCryptoKey, password);
        this.setRole(UserRoleEnum.valueOf(role));
        this.setAddress(address);
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_USER")
    @SequenceGenerator(name = "SQ_USER", sequenceName = "SQ_USER", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Jwt> jwtList;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "fk_address", nullable = false)
    private Address address;

    @Transient
    private transient User userSavedState;

    public void saveState(User userSavedState) {
        this.userSavedState = userSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return UserConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }

    public void setEncryptedPassword(@NonNull String passwordCryptoKey, @NonNull String password) {
        this.password = CryptoUtil.newInstance(passwordCryptoKey).encode(password);
    }
}