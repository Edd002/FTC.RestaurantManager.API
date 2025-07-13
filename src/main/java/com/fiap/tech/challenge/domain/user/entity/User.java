package com.fiap.tech.challenge.domain.user.entity;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.UserEntityListener;
import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
import com.fiap.tech.challenge.domain.user.enumerated.constraint.UserConstraint;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.audit.constraint.ConstraintMapper;
import com.fiap.tech.challenge.global.exception.UserTypeAdminNotAllowedException;
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
@ConstraintMapper(constraintClass = UserConstraint.class)
public class User extends Audit implements Serializable {

    protected User() {}

    public User(@NonNull Long id) {
        this.setId(id);
    }

    public User(@NonNull Long id, @NonNull String name, @NonNull String email, @NonNull String login, @NonNull String passwordCryptoKey, @NonNull String password, @NonNull UserType type, @NonNull Address address) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setLogin(login);
        this.setEncryptedPassword(passwordCryptoKey, CryptoUtil.newInstance(passwordCryptoKey).decrypt(password));
        this.setType(type);
        this.setAddress(address);
    }

    public User(@NonNull String name, @NonNull String email, @NonNull String login, @NonNull String passwordCryptoKey, @NonNull String password, @NonNull UserType type, @NonNull Address address) {
        this.setName(name);
        this.setEmail(email);
        this.setLogin(login);
        this.setEncryptedPassword(passwordCryptoKey, password);
        this.setType(type);
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

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "fk_user_type", nullable = false)
    private UserType type;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "fk_address", nullable = false)
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<RestaurantUser> restaurantUsers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Jwt> jwts;

    @Transient
    private transient User userSavedState;

    public void saveState(User userSavedState) {
        this.userSavedState = userSavedState;
    }

    public void setEncryptedPassword(@NonNull String passwordCryptoKey, @NonNull String password) {
        this.password = CryptoUtil.newInstance(passwordCryptoKey).encode(password);
    }

    public void setType(@NonNull UserType type) {
        if (DefaultUserTypeEnum.isTypeAdmin(type.getName())) {
            throw new UserTypeAdminNotAllowedException("Usuários administradores são pré cadastrados pelo sistema e não podem ser persistidos.");
        }
        this.type = type;
    }
}