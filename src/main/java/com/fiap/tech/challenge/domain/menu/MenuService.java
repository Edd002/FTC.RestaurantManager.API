package com.fiap.tech.challenge.domain.menu;

import com.fiap.tech.challenge.domain.menu.dto.MenuBatchPutRequestDTO;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.MenuItemService;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemBatchResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemCreateUseCase;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurant.RestaurantService;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserService;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.global.base.BaseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService extends BaseService<IMenuRepository, Menu> {

    private final RestaurantService restaurantService;
    private final RestaurantUserService restaurantUserService;
    private final ModelMapper modelMapper;
    private final MenuItemService menuItemService;

    @Autowired
    public MenuService(RestaurantService restaurantService, RestaurantUserService restaurantUserService, ModelMapper modelMapper, MenuItemService menuItemService) {
        this.restaurantService = restaurantService;
        this.restaurantUserService = restaurantUserService;
        this.modelMapper = modelMapper;
        this.menuItemService = menuItemService;
    }

    @Transactional
    public MenuBatchResponseDTO createOrUpdate(MenuBatchPutRequestDTO menuBatchPostRequestDTO) {
        Restaurant restaurant = restaurantUserService.findByRestaurantAndUser(restaurantService.findByHashId(menuBatchPostRequestDTO.getHashIdRestaurant()), AuthUserContextHolder.getAuthUser()).getRestaurant();
        List<MenuItem> newOrUpdatedMenuItems = menuBatchPostRequestDTO.getMenuItems().stream().map(menuItemBatchPutRequestDTO ->
                menuItemService.save(
                        Optional.ofNullable(menuItemBatchPutRequestDTO.getHashId())
                                .map(manuItemHashId -> new MenuItemUpdateUseCase(menuItemService.findByHashId(manuItemHashId), restaurant, modelMapper.map(menuItemBatchPutRequestDTO, MenuItemPutRequestDTO.class)).getRebuiltedMenuItem())
                                .orElseGet(() -> new MenuItemCreateUseCase(restaurant, modelMapper.map(menuItemBatchPutRequestDTO, MenuItemPostRequestDTO.class)).getBuiltedMenuItem())
                )).toList();
        return new MenuBatchResponseDTO(restaurant.getHashId(), newOrUpdatedMenuItems.stream().map(newOrUpdatedMenuItem -> modelMapper.map(newOrUpdatedMenuItem, MenuItemBatchResponseDTO.class)).toList());
    }

    @Override
    public Menu findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O menu com o hash id %s não foi encontrado.", hashId));
    }
}