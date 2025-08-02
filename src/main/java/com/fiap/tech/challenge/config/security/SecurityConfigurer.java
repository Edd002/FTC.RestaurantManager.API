package com.fiap.tech.challenge.config.security;

import com.fiap.tech.challenge.config.security.filter.JwtFilter;
import com.fiap.tech.challenge.domain.jwt.JwtBuilder;
import com.fiap.tech.challenge.domain.jwt.JwtServiceGateway;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtBuilder jwtBuilder;
    private final JwtServiceGateway jwtServiceGateway;
    private final BundleAuthUserDetailsService bundleAuthUserDetailsService;

    public SecurityConfigurer(JwtBuilder jwtBuilder, JwtServiceGateway jwtServiceGateway, BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        this.jwtBuilder = jwtBuilder;
        this.jwtServiceGateway = jwtServiceGateway;
        this.bundleAuthUserDetailsService = bundleAuthUserDetailsService;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtFilter jwtFilter = new JwtFilter(jwtBuilder, jwtServiceGateway, bundleAuthUserDetailsService);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
