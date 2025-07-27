package com.fiap.tech.challenge.config;

import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
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
        configMenuToMenuBatchResponseDTOMapper(modelMapper);
        configMenuItemToMenuItemResponseDTOMapper(modelMapper);
        configUserToUserResponseDTO(modelMapper);
        return modelMapper;
    }

    private void configModelMapper(ModelMapper modelMapper) {
        configJwtToJwtResponseDTOMapper(modelMapper);
    }

    private void configJwtToJwtResponseDTOMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(Jwt.class, JwtResponseDTO.class)
                .addMappings(mapper -> mapper.map(jwt -> jwt.getUser().getLogin(), JwtResponseDTO::setUserLogin));
    }

    private void configMenuToMenuBatchResponseDTOMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(Menu.class, MenuBatchResponseDTO.class)
                .addMappings(mapper -> mapper.map(menu -> menu.getRestaurant().getHashId(), (menuBatchResponseDTO, hashIdRestaurant) -> menuBatchResponseDTO.setHashIdRestaurant((String) hashIdRestaurant)));
    }

    private void configMenuItemToMenuItemResponseDTOMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(MenuItem.class, MenuItemResponseDTO.class)
                .addMappings(mapper -> mapper.map(menuItem -> menuItem.getMenu().getRestaurant().getHashId(), (menuItemResponseDTO, hashIdRestaurant) -> menuItemResponseDTO.getMenu().setHashIdRestaurant((String) hashIdRestaurant)));
    }

    private void configUserToUserResponseDTO(ModelMapper modelMapper) {
        modelMapper.typeMap(User.class, UserResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getType().getName(), UserResponseDTO::setType);
        });
    }
}