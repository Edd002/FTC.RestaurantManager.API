package com.fiap.tech.challenge.config;

import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.menuitemorder.dto.MenuItemOrderResponseDTO;
import com.fiap.tech.challenge.domain.menuitemorder.entity.MenuItemOrder;
import com.fiap.tech.challenge.domain.order.dto.OrderResponseDTO;
import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

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
        modelMapperPresenter.typeMap(Order.class, OrderResponseDTO.class)
        .addMappings(mapper -> mapper.using(generateConverterListMenuItemOrderResponseDTOForOrderResponseDTO())
                .map(Order::getMenuItemOrders, (orderResponseDTO, menuItemOrdersResponseDTO) -> orderResponseDTO.setMenuItemOrders((List<MenuItemOrderResponseDTO>) menuItemOrdersResponseDTO)));
    }

    private void configUserToUserResponseDTOModelMapperPresenter(ModelMapper modelMapperPresenter) {
        modelMapperPresenter.typeMap(User.class, UserResponseDTO.class)
                .addMappings(mapperPresenter -> mapperPresenter.map(src -> src.getType().getName(), UserResponseDTO::setType));
    }

    private Converter<List<MenuItemOrder>, List<MenuItemOrderResponseDTO>> generateConverterListMenuItemOrderResponseDTOForOrderResponseDTO() {
        return context -> ValidationUtil.isNotNull(context.getSource()) ?
                context.getSource().stream()
                        .map(menuItemOrder -> {
                            MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO();
                            BeanUtils.copyProperties(menuItemOrder.getMenuItem(), menuItemResponseDTO);
                            return new MenuItemOrderResponseDTO(menuItemResponseDTO);
                        }).collect(Collectors.toList()) : null;
    }

}