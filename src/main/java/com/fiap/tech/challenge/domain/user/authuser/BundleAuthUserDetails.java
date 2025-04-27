package com.fiap.tech.challenge.domain.user.authuser;

import com.fiap.tech.challenge.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class BundleAuthUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final User user;
    private final List<GrantedAuthority> authorities;

    public BundleAuthUserDetails(User user) {
        this.user = user;
        this.authorities = Collections.singletonList(user.getRole().getSimpleGrantedAuthority());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.user.getLogin();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }
}