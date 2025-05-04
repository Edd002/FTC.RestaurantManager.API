package com.fiap.tech.challenge.domain.address.entity;

import com.fiap.tech.challenge.domain.address.AddressEntityListener;
import com.fiap.tech.challenge.domain.address.enumerated.AddressConstraintEnum;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.audit.Audit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_address")
@SQLDelete(sql = "UPDATE t_address SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
@EntityListeners({ AddressEntityListener.class })
public class Address extends Audit implements Serializable {

    // TODO Achar alternativa para getDeclaredConstructor().newInstance() de BaseController e alterar construtor para protected
    public Address() {}

    public Address(@NonNull Long id) {
        this.setId(id);
    }

    public Address(@NonNull Long id, @NonNull String description, @NonNull String number, String complement, @NonNull String neighborhood, @NonNull String cep, @NonNull String postalCode, @NonNull City city) {
        this.setId(id);
        this.setDescription(description);
        this.setNumber(number);
        this.setComplement(complement);
        this.setNeighborhood(neighborhood);
        this.setCep(cep);
        this.setPostalCode(postalCode);
        this.setCity(city);
    }

    public Address(@NonNull String description, @NonNull String number, String complement, @NonNull String neighborhood, @NonNull String cep, @NonNull String postalCode, @NonNull City city) {
        this.setDescription(description);
        this.setNumber(number);
        this.setComplement(complement);
        this.setNeighborhood(neighborhood);
        this.setCep(cep);
        this.setPostalCode(postalCode);
        this.setCity(city);
    }

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

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "address", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private User user;

    @Transient
    private transient Address addressSavedState;

    public void saveState(Address addressSavedState) {
        this.addressSavedState = addressSavedState;
    }

    @Override
    public String getConstraintErrorMessage(String constraintName) {
        return AddressConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}