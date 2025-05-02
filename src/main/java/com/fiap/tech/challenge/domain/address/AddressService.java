package com.fiap.tech.challenge.domain.address;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.global.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends BaseService<IAddressRepository, Address> {

    @Override
    public Address findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O endereço com hash id %s não foi encontrado.", hashId));
    }
}