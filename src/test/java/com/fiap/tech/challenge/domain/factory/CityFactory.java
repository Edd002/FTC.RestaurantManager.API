package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.city.entity.City;

public class CityFactory {
    public static City loadEntityCity(){
        return new City("São Paulo");
    }
}
