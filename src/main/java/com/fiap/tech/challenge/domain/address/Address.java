package com.fiap.tech.challenge.domain.address;

import com.fiap.tech.challenge.domain.address.enumerated.AddressConstraintEnum;
import com.fiap.tech.challenge.domain.city.City;
import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.global.audit.Audit;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "t_address")
@SQLDelete(sql = "UPDATE t_address SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ AddressEntityListener.class })
public final class Address extends Audit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SQ_ADDRESS")
    @SequenceGenerator(name = "SQ_ADDRESS", sequenceName = "SQ_ADDRESS", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "complement")
    private String complement;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_city", nullable = false)
    private City city;

    @OneToOne(mappedBy = "address", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private User user;

    @Transient
    @Getter private transient Address addressSavedState;

    public void saveState(Address addressSavedState) {
        this.addressSavedState = addressSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return AddressConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}