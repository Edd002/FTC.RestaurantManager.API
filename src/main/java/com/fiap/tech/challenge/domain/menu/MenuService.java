package com.fiap.tech.challenge.domain.menu;

import com.fiap.tech.challenge.domain.menu.dto.MenuBatchPutRequestDTO;
import com.fiap.tech.challenge.domain.menu.dto.MenuBatchResponseDTO;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menu.usecase.MenuCreateUseCase;
import com.fiap.tech.challenge.domain.restaurant.RestaurantService;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.global.base.BaseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService extends BaseService<IMenuRepository, Menu> {

    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuService(RestaurantService restaurantService, ModelMapper modelMapper) {
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public MenuBatchResponseDTO createOrUpdate(MenuBatchPutRequestDTO menuBatchPostRequestDTO) {
        Restaurant restaurant = restaurantService.findByHashId(menuBatchPostRequestDTO.getHashIdRestaurant());
        Menu newMenu = new MenuCreateUseCase(AuthUserContextHolder.getAuthUser(), restaurant, menuBatchPostRequestDTO).getBuiltedMenu();
        return modelMapper.map(save(newMenu), MenuBatchResponseDTO.class);
    }

    @Override
    public Menu findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O menu com o hash id %s não foi encontrado.", hashId));
    }
}