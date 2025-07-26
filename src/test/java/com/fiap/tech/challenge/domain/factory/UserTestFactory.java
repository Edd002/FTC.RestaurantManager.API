package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;

public class UserTestFactory {

    public static User loadEntityOwner() {
        return new User(
                "Manu",
                "manu@email.com",
                "manu",
                "123456789012345678901234",
                "123456",
                new UserType("Owner"),
                RestaurantTestFactory.loadDefaultAddress()
        );
    }

    public static User loadEntityClient() {
        return new User(
                "Manu",
                "manu@email.com",
                "manu",
                "123456789012345678901234",
                "123456",
                new UserType("Client"),
                RestaurantTestFactory.loadDefaultAddress()
        );
    }

}
