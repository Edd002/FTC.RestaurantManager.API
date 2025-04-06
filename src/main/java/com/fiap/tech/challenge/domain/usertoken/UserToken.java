package com.fiap.tech.challenge.domain.usertoken;

import com.fiap.tech.challenge.domain.jwt.Jwt;
import com.fiap.tech.challenge.domain.jwt.JwtEntityListener;
import com.fiap.tech.challenge.domain.usertoken.enumerated.UserTokenConstraintEnum;
import com.fiap.tech.challenge.global.audity.Audity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user_token")
@SQLDelete(sql = "UPDATE t_user_token SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id"})
@EntityListeners({ JwtEntityListener.class })
public class UserToken extends Audity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_USER_TOKEN")
    @SequenceGenerator(name = "SQ_USER_TOKEN", sequenceName = "SQ_USER_TOKEN", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "client_id", nullable = false, updatable = false)
    private String clientId;

    @Column(name = "client_secret", nullable = false, updatable = false)
    private String clientSecret;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userToken", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Jwt> jwts;

    @Transient
    private transient UserToken userTokenSavedState;

    public void saveState(UserToken userTokenSavedState) {
        this.userTokenSavedState = userTokenSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return UserTokenConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}