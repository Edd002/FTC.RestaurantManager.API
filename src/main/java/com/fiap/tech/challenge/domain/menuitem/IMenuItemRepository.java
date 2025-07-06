package com.fiap.tech.challenge.domain.menuitem;

import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMenuItemRepository extends IBaseRepository<MenuItem> {

    Optional<MenuItem> findByHashIdAndMenu(String hashId, Menu menu);
}