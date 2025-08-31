package com.fiap.tech.challenge.domain.menu;

import com.fiap.tech.challenge.domain.menu.dto.MenuBatchPutRequestDTO;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.MenuItemServiceGateway;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemBatchResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemCreateUseCase;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurant.RestaurantServiceGateway;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserServiceGateway;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceGateway extends BaseServiceGateway<IMenuRepository, Menu> {

    private final RestaurantServiceGateway restaurantServiceGateway;
    private final RestaurantUserServiceGateway restaurantUserServiceGateway;
    private final MenuItemServiceGateway menuItemServiceGateway;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public MenuServiceGateway(RestaurantServiceGateway restaurantServiceGateway, RestaurantUserServiceGateway restaurantUserServiceGateway, MenuItemServiceGateway menuItemServiceGateway, ModelMapper modelMapperPresenter) {
        this.restaurantServiceGateway = restaurantServiceGateway;
        this.restaurantUserServiceGateway = restaurantUserServiceGateway;
        this.menuItemServiceGateway = menuItemServiceGateway;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public MenuBatchResponseDTO createOrUpdate(MenuBatchPutRequestDTO menuBatchPostRequestDTO) {
        Restaurant restaurant = restaurantUserServiceGateway.findByRestaurantAndUser(restaurantServiceGateway.findByHashId(menuBatchPostRequestDTO.getHashIdRestaurant()), AuthUserContextHolder.getAuthUser()).getRestaurant();
        List<MenuItem> newOrUpdatedMenuItems = menuBatchPostRequestDTO.getMenuItems().stream().map(menuItemBatchPutRequestDTO ->
                menuItemServiceGateway.save(
                        Optional.ofNullable(menuItemBatchPutRequestDTO.getHashId())
                                .map(menuItemHashId -> new MenuItemUpdateUseCase(menuItemServiceGateway.findByHashId(menuItemHashId), restaurant, modelMapperPresenter.map(menuItemBatchPutRequestDTO, MenuItemPutRequestDTO.class)).getRebuiltedMenuItem())
                                .orElseGet(() -> new MenuItemCreateUseCase(restaurant, modelMapperPresenter.map(menuItemBatchPutRequestDTO, MenuItemPostRequestDTO.class)).getBuiltedMenuItem())
                )).toList();
        return new MenuBatchResponseDTO(restaurant.getHashId(), newOrUpdatedMenuItems.stream().map(newOrUpdatedMenuItem -> modelMapperPresenter.map(newOrUpdatedMenuItem, MenuItemBatchResponseDTO.class)).toList());
    }

    @Override
    public Menu findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O menu com o hash id %s não foi encontrado.", hashId));
    }
}