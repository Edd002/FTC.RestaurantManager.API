package com.fiap.tech.challenge.domain.menuitem;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemGetFilter;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.menuitem.specification.MenuItemSpecificationBuilder;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemCreateUseCase;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurant.RestaurantService;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MenuItemService extends BaseService<IMenuItemRepository, MenuItem> {

    private final RestaurantService restaurantService;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuItemService(RestaurantService restaurantService, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.restaurantService = restaurantService;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public MenuItemResponseDTO create(MenuItemPostRequestDTO menuItemPostRequestDTO) {
        Restaurant restaurant = restaurantService.findByHashId(menuItemPostRequestDTO.getMenu().getHashIdRestaurant());
        MenuItem newMenuItem = new MenuItemCreateUseCase(AuthUserContextHolder.getAuthUser(), restaurant, menuItemPostRequestDTO).getBuiltedMenuItem();
        return modelMapper.map(save(newMenuItem), MenuItemResponseDTO.class);
    }

    @Transactional
    public MenuItemResponseDTO update(String hashId, MenuItemPutRequestDTO menuItemPutRequestDTO) {
        MenuItem existingMenuItem = findByHashId(hashId);
        Restaurant restaurant = restaurantService.findByHashId(menuItemPutRequestDTO.getMenu().getHashIdRestaurant());
        MenuItem updatedMenuItem = new MenuItemUpdateUseCase(AuthUserContextHolder.getAuthUser(), existingMenuItem, restaurant, menuItemPutRequestDTO).getBuiltedMenuItem();
        return modelMapper.map(save(updatedMenuItem), MenuItemResponseDTO.class);
    }

    @Transactional
    public Page<MenuItemResponseDTO> find(MenuItemGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<MenuItem>> specification = new MenuItemSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(menuItem -> modelMapper.map(menuItem, MenuItemResponseDTO.class));
    }

    @Transactional
    public MenuItemResponseDTO find(String hashId) {
        return modelMapper.map(findByHashId(hashId), MenuItemResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        deleteByHashId(hashId);
        SecurityContextHolder.clearContext();
    }

    @Override
    public MenuItem findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O item do menu com o hash id %s não foi encontrado.", hashId));
    }
}