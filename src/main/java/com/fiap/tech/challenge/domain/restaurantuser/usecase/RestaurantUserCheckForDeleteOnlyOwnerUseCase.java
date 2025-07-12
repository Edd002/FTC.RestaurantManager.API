package com.fiap.tech.challenge.domain.restaurantuser.usecase;

import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserTypeEnum;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import lombok.NonNull;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class RestaurantUserCheckForDeleteOnlyOwnerUseCase {

    private final Boolean isAllowedToDelete;

    public RestaurantUserCheckForDeleteOnlyOwnerUseCase(@NonNull User loggedUser, @NonNull List<RestaurantUser> restaurantUsers) {
        if (loggedUser.getType().equals(UserTypeEnum.OWNER) && restaurantUsers.stream().filter(restaurantUser -> restaurantUser.getUser().getType().equals(UserTypeEnum.OWNER)).count() == NumberUtils.LONG_ONE) {
            throw new EntityCannotBeDeletedException("Não foi possível realizar a exclusão pois deve existir pelo menos uma associação de usuário DONO (OWNER) com o restaurante.");
        }
        this.isAllowedToDelete = true;
    }

    public Boolean isAllowedToDelete() {
        return this.isAllowedToDelete;
    }
}