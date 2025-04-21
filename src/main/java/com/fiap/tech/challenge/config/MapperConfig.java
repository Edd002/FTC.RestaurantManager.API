package com.fiap.tech.challenge.config;

import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
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
        configUserPostRequestDTOToUserMapper(modelMapper);
        configUserPutRequestDTOToUserMapper(modelMapper);
        configUserToUserResponseDTOMapper(modelMapper);
    }

    private void configUserPostRequestDTOToUserMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(UserPostRequestDTO.class, User.class)
                .addMappings(mapper -> mapper.map(userPostRequestDTO -> userPostRequestDTO.getAddress().getHashIdCity(), (user, hashIdCity) -> user.getAddress().getCity().setHashId((String) hashIdCity)));
    }

    private void configUserPutRequestDTOToUserMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(UserPutRequestDTO.class, User.class)
                .addMappings(mapper -> mapper.map(userPutRequestDTO -> userPutRequestDTO.getAddress().getHashIdCity(), (user, hashIdCity) -> user.getAddress().getCity().setHashId((String) hashIdCity)));
    }

    private void configUserToUserResponseDTOMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(User.class, UserResponseDTO.class)
                .addMappings(mapper -> mapper.map(user -> user.getAddress().getCity().getHashId(), (userResponseDTO, hashIdCity) -> userResponseDTO.getAddress().setHashIdCity((String) hashIdCity)));
    }
}