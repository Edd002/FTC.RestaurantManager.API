package com.fiap.tech.challenge.config;

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
        configUserRequestDTOToUserMapper(modelMapper);
        configUserToUserResponseDTOMapper(modelMapper);
    }

    private void configUserToUserResponseDTOMapper(ModelMapper modelMapper) {
    }

    private void configUserRequestDTOToUserMapper(ModelMapper modelMapper) {
    }
}