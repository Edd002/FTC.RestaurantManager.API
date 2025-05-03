package com.fiap.tech.challenge.config;

import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true)
                .setDeepCopyEnabled(true)
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true);
        configModelMapper(modelMapper);
        return modelMapper;
    }

    private void configModelMapper(ModelMapper modelMapper) {
        configJwtToJwtResponseDTOMapper(modelMapper);
    }

    private void configJwtToJwtResponseDTOMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(Jwt.class, JwtResponseDTO.class)
                .addMappings(mapper -> mapper.map(jwt -> jwt.getUser().getLogin(), JwtResponseDTO::setUserLogin));
    }
}