package com.fiap.tech.challenge.global.util;

import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import com.fiap.tech.challenge.domain.user.entity.User;
import lombok.experimental.UtilityClass;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class MockAuthenticationUtil {

    private static final String PATH_RESOURCE_USER = "/mock/user/user.json";

    public void mockAuthenticationOwner(BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        User user = JsonUtil.objectFromJson("existing_owner_user", PATH_RESOURCE_USER, User.class);
        mockAuthentication(bundleAuthUserDetailsService, user);
    }

    public void mockAuthenticationClient(BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        User user = JsonUtil.objectFromJson("existing_client_user", PATH_RESOURCE_USER, User.class);
        mockAuthentication(bundleAuthUserDetailsService, user);
    }

    public void mockAuthentication(BundleAuthUserDetailsService bundleAuthUserDetailsService, User user) {
        Authentication authentication = bundleAuthUserDetailsService.getAuthentication(user.getLogin());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}