package com.fiap.tech.challenge.domain.menuitem;

import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService extends BaseService<IMenuItemRepository, MenuItem> {

    private final IMenuItemRepository menuItemRepository;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuItemService(IMenuItemRepository menuItemRepository, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.menuItemRepository = menuItemRepository;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Override
    public MenuItem findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O item do menu com o hash id %s não foi encontrado.", hashId));
    }
}