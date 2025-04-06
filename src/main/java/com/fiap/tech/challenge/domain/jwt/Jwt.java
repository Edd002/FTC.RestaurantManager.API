package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.enumerated.JwtConstraintEnum;
import com.fiap.tech.challenge.domain.usertoken.UserToken;
import com.fiap.tech.challenge.global.audity.Audity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_jwt")
@SQLDelete(sql = "UPDATE t_jwt SET excluido = true WHERE id = ?")
@Where(clause = "excluido = false")
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id"})
@EntityListeners({ JwtEntityListener.class })
public class Jwt extends Audity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_JWT")
    @SequenceGenerator(name = "SQ_JWT", sequenceName = "SQ_JWT", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @ManyToOne(fetch= FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fk_user_token", nullable = false)
    private UserToken userToken;

    @Transient
    private transient Jwt jwtSavedState;

    public void saveState(Jwt jwtSavedState) {
        this.jwtSavedState = jwtSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return JwtConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}