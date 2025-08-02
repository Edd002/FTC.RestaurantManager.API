package com.fiap.tech.challenge.config.security.filter;

import com.fiap.tech.challenge.domain.jwt.JwtBuilder;
import com.fiap.tech.challenge.domain.jwt.JwtClaims;
import com.fiap.tech.challenge.domain.jwt.JwtServiceGateway;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import com.fiap.tech.challenge.global.exception.AuthenticationHttpException;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import com.fiap.tech.challenge.global.exception.TokenValidationException;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log
public class JwtFilter extends OncePerRequestFilter {

    private final JwtBuilder jwtBuilder;
    private final JwtServiceGateway jwtServiceGateway;
    private final BundleAuthUserDetailsService bundleAuthUserDetailsService;

    public JwtFilter(JwtBuilder jwtBuilder, JwtServiceGateway jwtServiceGateway, BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        this.jwtBuilder = jwtBuilder;
        this.jwtServiceGateway = jwtServiceGateway;
        this.bundleAuthUserDetailsService = bundleAuthUserDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest httpServletRequest) {
        return PathFilterEnum.getIgnoreFilterConfigPaths().stream().anyMatch(path -> httpServletRequest.getRequestURI().contains(path)) || authorizationHeaderIsMissingAndPathIsAllowed(httpServletRequest) || isGeneratingJwt(httpServletRequest);
    }

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws IOException, ServletException {
        try {
            JwtClaims jwt = jwtBuilder.resolveBearerToken(httpServletRequest);
            if (!jwtServiceGateway.isJwtActiveByBearerToken(jwt.getBearerToken())) {
                httpServletRequest.setAttribute("jwtError", "Sessão encerrada ou expirada. O tempo de expiração da sessão é de 1 hora.");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(bundleAuthUserDetailsService.getAuthentication(jwt.getLogin()));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            if (httpServletResponse.getStatus() != HttpStatus.UNAUTHORIZED.value() && (!ArrayUtils.contains(PathFilterEnum.getIgnoreResponseFilterPaths().stream().map(PathFilterEnum::getPath).toArray(), httpServletRequest.getServletPath()) && !isDeletingUser(httpServletRequest))) {
                jwtServiceGateway.refreshByBearerToken(jwt.getBearerToken());
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

    private boolean authorizationHeaderIsMissingAndPathIsAllowed(HttpServletRequest httpServletRequest) {
        return ValidationUtil.isNull(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)) && PathFilterEnum.getAllowedPathsWithoutAuthorization().stream().anyMatch(securityPathEnum -> httpServletRequest.getRequestURI().contains(securityPathEnum.getCompletePath()) && httpServletRequest.getMethod().equals(securityPathEnum.getHttpMethod().name()));
    }

    private boolean isGeneratingJwt(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getServletPath().equals(PathFilterEnum.API_V1_JWTS_GENERATE_POST.getPath()) && httpServletRequest.getMethod().equals(PathFilterEnum.API_V1_JWTS_GENERATE_POST.getHttpMethod().name());
    }

    private boolean isDeletingUser(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getServletPath().equals(PathFilterEnum.API_V1_USERS_DELETE.getPath()) && httpServletRequest.getMethod().equals(PathFilterEnum.API_V1_USERS_DELETE.getHttpMethod().name());
    }
}