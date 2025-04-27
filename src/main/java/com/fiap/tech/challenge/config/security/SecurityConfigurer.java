package com.fiap.tech.challenge.config.security;

import com.fiap.tech.challenge.domain.jwt.JwtBuilder;
import com.fiap.tech.challenge.domain.jwt.JwtService;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtBuilder jwtBuilder;
    private final JwtService jwtService;
    private final BundleAuthUserDetailsService bundleAuthUserDetailsService;

    public SecurityConfigurer(JwtBuilder jwtBuilder, JwtService jwtService, BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        this.jwtBuilder = jwtBuilder;
        this.jwtService = jwtService;
        this.bundleAuthUserDetailsService = bundleAuthUserDetailsService;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtFilter jwtFilter = new JwtFilter(jwtBuilder, jwtService, bundleAuthUserDetailsService);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
