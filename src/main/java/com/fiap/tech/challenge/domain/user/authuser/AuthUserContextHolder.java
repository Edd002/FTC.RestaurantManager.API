package com.fiap.tech.challenge.domain.user.authuser;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.exception.AuthenticationHttpException;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthUserContextHolder {

    public static boolean hasAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ValidationUtil.isNotNull(authentication) && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static boolean hasNoAuthUser() {
        return !hasAuthUser();
    }

    public static User getAuthUser() {
        if (hasNoAuthUser()) {
            throw new AuthenticationHttpException();
        }
        return getUserFromAuthUser();
    }

    public static Optional<User> getAuthUserIfExists() {
        if (!hasAuthUser()) {
            return Optional.empty();
        }
        return Optional.of(getUserFromAuthUser());
    }

    private static User getUserFromAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BundleAuthUserDetails userDetails = (BundleAuthUserDetails) principal;
        return userDetails.getUser();
    }
}