package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.address.entity.Address;

import static com.fiap.tech.challenge.domain.factory.CityFactory.loadEntityCity;

public class AddressFactory {
    public static Address loadAddressEntity() {
        return new Address(
                "Rua das Flores",
                "456",
                "Sala 2",
                "Bela Vista",
                "01310-000",
                "01310000",
                loadEntityCity()
        );
    }
}
