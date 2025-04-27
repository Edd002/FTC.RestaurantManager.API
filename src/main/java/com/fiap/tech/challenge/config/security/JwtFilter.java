package com.fiap.tech.challenge.config.security;

import com.fiap.tech.challenge.domain.jwt.JwtBuilder;
import com.fiap.tech.challenge.domain.jwt.JwtClaims;
import com.fiap.tech.challenge.domain.jwt.JwtService;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import com.fiap.tech.challenge.global.exception.AuthenticationHttpException;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import com.fiap.tech.challenge.global.exception.TokenValidationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Log
public class JwtFilter extends OncePerRequestFilter {

    private final JwtBuilder jwtBuilder;
    private final JwtService jwtService;
    private final BundleAuthUserDetailsService bundleAuthUserDetailsService;

    private static final String[] IGNORE_FILTER_PATHS = {
            "/user-manager/swagger-ui/index.html",
            "/user-manager/swagger-ui/swagger-ui.css",
            "/user-manager/swagger-ui/swagger-ui-standalone-preset.js",
            "/user-manager/swagger-ui/swagger-ui-bundle.js",
            "/user-manager/v3/api-docs/swagger-config",
            "/user-manager/swagger-ui/favicon-32x32.png",
            "/user-manager/swagger-ui/favicon-16x16.png",
            "/user-manager/v3/api-docs",
            "/user-manager/h2-console",
            "/actuator/health",
            "/user-manager/api/v1/jwts/generate"
    };

    private static final String[] IGNORE_RESPONSE_FILTER_PATHS = {
            "/api/v1/jwts/generate",
            "/api/v1/jwts/validate",
            "/api/v1/jwts/invalidate"
    };

    public JwtFilter(JwtBuilder jwtBuilder, JwtService jwtService, BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        this.jwtBuilder = jwtBuilder;
        this.jwtService = jwtService;
        this.bundleAuthUserDetailsService = bundleAuthUserDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest httpServletRequest) {
        return Arrays.stream(IGNORE_FILTER_PATHS).anyMatch(path -> httpServletRequest.getRequestURI().contains(path));
    }

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws IOException, ServletException {
        try {
            JwtClaims jwt = jwtBuilder.resolveBearerToken(httpServletRequest);
            if (!jwtService.isJwtActiveByBearerToken(jwt.getBearerToken())) {
                httpServletRequest.setAttribute("jwtError", "Sessão encerrada ou expirada. O tempo de expiração da sessão é de 1 hora.");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            Authentication authentication = bundleAuthUserDetailsService.getAuthentication(jwt.getEmail());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            if (httpServletResponse.getStatus() != HttpStatus.UNAUTHORIZED.value() && !ArrayUtils.contains(IGNORE_RESPONSE_FILTER_PATHS, httpServletRequest.getServletPath())) {
                jwtService.refreshByBearerToken(jwt.getBearerToken());
            }
            return;
        } catch (TokenValidationException tokenValidationException) {
            httpServletRequest.setAttribute("jwtError", tokenValidationException.getMessage());
            log.severe(tokenValidationException.getMessage());
            SecurityContextHolder.clearContext();
        } catch (AuthenticationHttpException | EntityNotFoundException exception) {
            log.severe(exception.getMessage());
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}