package com.fiap.tech.challenge.domain.user;

import com.fiap.tech.challenge.domain.address.Address;
import com.fiap.tech.challenge.domain.jwt.Jwt;
import com.fiap.tech.challenge.domain.user.enumerated.UserConstraintEnum;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.bean.BeanComponent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
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
    private Long id;

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

    @Override
    public User buildWithId(Long id) {
        this.setId(id);
        return this;
    }

    @Override
    public void setHashId(String hashId) {
        User existingUser = BeanComponent.getBean(UserService.class).findByHashId(hashId);
        this.setId(existingUser.getId());
        this.setAddress(new Address().buildWithId(existingUser.getAddress().getId()));
        super.setHashId(hashId);
    }
}