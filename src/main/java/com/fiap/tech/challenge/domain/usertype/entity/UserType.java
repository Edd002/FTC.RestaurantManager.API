package com.fiap.tech.challenge.domain.usertype.entity;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.usertype.UserTypeEntityListener;
import com.fiap.tech.challenge.domain.usertype.enumerated.constraint.UserTypeConstraint;
import com.fiap.tech.challenge.global.audit.Audit;
import com.fiap.tech.challenge.global.constraint.ConstraintMapper;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_user_type")
@SQLDelete(sql = "UPDATE t_user_type SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ UserTypeEntityListener.class })
@ConstraintMapper(constraintClass = UserTypeConstraint.class)
public class UserType extends Audit implements Serializable {

    protected UserType() {}

    public UserType(@NonNull String name) {
        this.setName(name);
    }

    public UserType rebuild(@NonNull String name) {
        this.setName(name);
        return this;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_USER_TYPE")
    @SequenceGenerator(name = "SQ_USER_TYPE", sequenceName = "SQ_USER_TYPE", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<User> users;

    @Transient
    private transient UserType userTypeSavedState;

    public void saveState(UserType userTypeSavedState) {
        this.userTypeSavedState = userTypeSavedState;
    }

    public void setName(@NonNull String name) {
        this.name = StringUtils.upperCase(name);
    }
}