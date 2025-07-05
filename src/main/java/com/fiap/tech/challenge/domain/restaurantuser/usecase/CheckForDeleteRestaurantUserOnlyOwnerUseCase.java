package com.fiap.tech.challenge.domain.restaurantuser.usecase;

import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import lombok.NonNull;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class CheckForDeleteRestaurantUserOnlyOwnerUseCase {

    public CheckForDeleteRestaurantUserOnlyOwnerUseCase(@NonNull User loggedUser, @NonNull List<RestaurantUser> restaurantUsers) {
        if (loggedUser.getRole().equals(UserRoleEnum.OWNER) && restaurantUsers.stream().filter(restaurantUser -> restaurantUser.getUser().getRole().equals(UserRoleEnum.OWNER)).count() == NumberUtils.LONG_ONE) {
            throw new EntityCannotBeDeletedException("Não foi possível realizar a exclusão pois deve existir pelo menos uma associação de usuário DONO (OWNER) com o restaurante.");
        }
    }
}