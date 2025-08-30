package com.fiap.tech.challenge.config;

import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.order.dto.OrderResponseDTO;
import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperPresenterConfig {

    @Bean
    public ModelMapper modelMapperPresenter() {
        ModelMapper modelMapperPresenter = new ModelMapper();
        modelMapperPresenter.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true)
                .setDeepCopyEnabled(true)
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true);
        configModelMapperPresenter(modelMapperPresenter);
        configMenuToMenuBatchResponseDTOMapperPresenter(modelMapperPresenter);
        configMenuItemToMenuItemResponseDTOMapperPresenter(modelMapperPresenter);
        configOrderToOrderResponseDTOMapperPresenter(modelMapperPresenter);
        configUserToUserResponseDTOModelMapperPresenter(modelMapperPresenter);
        return modelMapperPresenter;
    }

    private void configModelMapperPresenter(ModelMapper modelMapperPresenter) {
        configJwtToJwtResponseDTOMapperPresenter(modelMapperPresenter);
    }

    private void configJwtToJwtResponseDTOMapperPresenter(ModelMapper modelMapperPresenter) {
        modelMapperPresenter.typeMap(Jwt.class, JwtResponseDTO.class)
                .addMappings(mapperPresenter -> mapperPresenter.map(jwt -> jwt.getUser().getLogin(), JwtResponseDTO::setUserLogin));
    }

    private void configMenuToMenuBatchResponseDTOMapperPresenter(ModelMapper modelMapperPresenter) {
        modelMapperPresenter.typeMap(Menu.class, MenuBatchResponseDTO.class)
                .addMappings(mapperPresenter -> mapperPresenter.map(menu -> menu.getRestaurant().getHashId(), (menuBatchResponseDTO, hashIdRestaurant) -> menuBatchResponseDTO.setHashIdRestaurant((String) hashIdRestaurant)));
    }

    private void configMenuItemToMenuItemResponseDTOMapperPresenter(ModelMapper modelMapperPresenter) {
        modelMapperPresenter.typeMap(MenuItem.class, MenuItemResponseDTO.class)
                .addMappings(mapperPresenter -> mapperPresenter.map(menuItem -> menuItem.getMenu().getRestaurant().getHashId(), (menuItemResponseDTO, hashIdRestaurant) -> menuItemResponseDTO.getMenu().setHashIdRestaurant((String) hashIdRestaurant)));
    }

    private void configOrderToOrderResponseDTOMapperPresenter(ModelMapper modelMapperPresenter) {
        PropertyMap<Order, OrderResponseDTO> orderPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                destination.getMenuItemOrders().forEach(menuItemOrderResponseDTO -> skip(menuItemOrderResponseDTO.getMenuItem().getMenu()));
            }
        };
        modelMapperPresenter.typeMap(Order.class, OrderResponseDTO.class)
                .addMappings(orderPropertyMap);
    }

    private void configUserToUserResponseDTOModelMapperPresenter(ModelMapper modelMapperPresenter) {
        modelMapperPresenter.typeMap(User.class, UserResponseDTO.class)
                .addMappings(mapperPresenter -> mapperPresenter.map(src -> src.getType().getName(), UserResponseDTO::setType));
    }
}