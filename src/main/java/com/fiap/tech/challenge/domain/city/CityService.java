package com.fiap.tech.challenge.domain.city;

import com.fiap.tech.challenge.global.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CityService extends BaseService<ICityRepository, City> {

    @Override
    public City findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("A cidade com hash id %s não foi encontrada.", hashId));
    }
}