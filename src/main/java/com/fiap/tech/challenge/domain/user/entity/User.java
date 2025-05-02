package com.fiap.tech.challenge.domain.user.entity;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.user.UserEntityListener;
import com.fiap.tech.challenge.domain.user.enumerated.UserConstraintEnum;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.util.CriptoUtil;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serial;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Getter
@Entity
@Table(name = "t_user")
@SQLDelete(sql = "UPDATE t_user SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ UserEntityListener.class })
public class User extends Audit implements Serializable {

    protected User() {}

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String name, String email, String login, String password, String role, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = UserRoleEnum.valueOf(role);
        this.address = address;
    }

    public User(String name, String email, String login, String password, String role, Address address) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = UserRoleEnum.valueOf(role);
        this.address = address;
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

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
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

    public void setPassword(String criptoKey, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException {
        this.password = CriptoUtil.newInstance(criptoKey).encrypt(password);
    }
}