package com.fiap.tech.challenge.domain.restaurantuser.usecase;

import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import lombok.NonNull;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class RestaurantUserCheckForDeleteUseCase {

    private final Boolean isAllowedToDelete;

    public RestaurantUserCheckForDeleteUseCase(@NonNull User loggedUser, @NonNull List<RestaurantUser> restaurantUsers) {
        if (DefaultUserTypeEnum.isUserOwner(loggedUser) && restaurantUsers.stream().filter(restaurantUser -> DefaultUserTypeEnum.isUserOwner(restaurantUser.getUser())).count() == NumberUtils.LONG_ONE) {
            throw new EntityCannotBeDeletedException("Não foi possível realizar a exclusão pois deve existir pelo menos uma associação de usuário dono de restaurante com o restaurante.");
        }
        this.isAllowedToDelete = true;
    }

    public Boolean isAllowedToDelete() {
        return this.isAllowedToDelete;
    }
}