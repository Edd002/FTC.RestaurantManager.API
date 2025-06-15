package com.fiap.tech.challenge.domain.menu;

import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService extends BaseService<IMenuRepository, Menu> {

    private final IMenuRepository menuRepository;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuService(IMenuRepository menuRepository, PageableBuilder pageableBuilder, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.pageableBuilder = pageableBuilder;
        this.modelMapper = modelMapper;
    }

    @Override
    public Menu findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O menu com o hash id %s não foi encontrado.", hashId));
    }
}