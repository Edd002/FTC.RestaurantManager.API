package com.fiap.tech.challenge.domain.city;

import com.fiap.tech.challenge.domain.city.dto.CityGetFilter;
import com.fiap.tech.challenge.domain.city.dto.CityResponseDTO;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.city.specification.CitySpecificationBuilder;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CityService extends BaseService<ICityRepository, City> {

    private final PageableBuilder pageableBuilder;

    private final ModelMapper modelMapper;

    @Autowired
    public CityService(PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Page<CityResponseDTO> find(CityGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<City>> specification = new CitySpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(city -> modelMapper.map(city, CityResponseDTO.class));
    }

    @Transactional
    public CityResponseDTO find(String hashId) {
        return modelMapper.map(this.findByHashId(hashId), CityResponseDTO.class);
    }

    @Override
    public City findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("A cidade com hash id %s não foi encontrada.", hashId));
    }
}