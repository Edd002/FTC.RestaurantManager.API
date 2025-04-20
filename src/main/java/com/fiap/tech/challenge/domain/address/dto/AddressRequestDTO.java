package com.fiap.tech.challenge.domain.address.dto;

import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;

public abstract class AddressRequestDTO extends BaseRequestDTO {

    private String description;

    private String number;

    private String complement;

    private String neighborhood;

    private String cep;

    private String postalCode;

    private String hashIdCity;
}