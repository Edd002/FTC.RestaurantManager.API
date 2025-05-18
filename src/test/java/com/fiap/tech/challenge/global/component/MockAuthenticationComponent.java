package com.fiap.tech.challenge.global.component;

import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.util.JsonUtil;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MockAuthenticationComponent {

    private static final String PATH_RESOURCE_USER = "/mock/user/user.json";

    private final BundleAuthUserDetailsService bundleAuthUserDetailsService;

    @Autowired
    public MockAuthenticationComponent(BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        this.bundleAuthUserDetailsService = bundleAuthUserDetailsService;
    }

    public void mockAuthenticationOwner() {
        User user = JsonUtil.objectFromJson("existing_owner_user", PATH_RESOURCE_USER, User.class);
        mockAuthentication(user);
    }

    public void mockAuthenticationClient() {
        User user = JsonUtil.objectFromJson("existing_client_user", PATH_RESOURCE_USER, User.class);
        mockAuthentication(user);
    }

    private void mockAuthentication(User user) {
        Authentication authentication = bundleAuthUserDetailsService.getAuthentication(user.getLogin());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}