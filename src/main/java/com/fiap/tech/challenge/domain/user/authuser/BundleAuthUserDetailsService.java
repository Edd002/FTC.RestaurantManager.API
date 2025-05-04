package com.fiap.tech.challenge.domain.user.authuser;

import com.fiap.tech.challenge.domain.user.UserService;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import com.fiap.tech.challenge.global.exception.JwtNotFoundHttpException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BundleAuthUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public BundleAuthUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            return new BundleAuthUserDetails(userService.findByLogin(login));
        } catch (JwtNotFoundHttpException | EntityNotFoundException exception) {
            throw new UsernameNotFoundException(String.format("O usuário com login %s não foi encontrado.", login));
        }
    }

    public Authentication createAuthentication(String login, String password) {
        return new UsernamePasswordAuthenticationToken(login, password);
    }

    public Authentication getAuthentication(String login) {
        UserDetails userDetails = loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, Strings.EMPTY, userDetails.getAuthorities());
    }

    public Authentication getAuthentication(BundleAuthUserDetails bundleAuthUserDetails) {
        return new UsernamePasswordAuthenticationToken(bundleAuthUserDetails, Strings.EMPTY, bundleAuthUserDetails.getAuthorities());
    }
}