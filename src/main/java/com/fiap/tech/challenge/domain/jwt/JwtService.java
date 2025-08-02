package com.fiap.tech.challenge.domain.jwt;

import com.fiap.tech.challenge.domain.jwt.dto.JwtGeneratePostRequestDTO;
import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.jwt.usecase.JwtIsActiveUseCase;
import com.fiap.tech.challenge.domain.jwt.usecase.JwtRefreshByBearerTokenUserCase;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetails;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.BaseService;
import com.fiap.tech.challenge.global.exception.AuthenticationHttpException;
import com.fiap.tech.challenge.global.exception.JwtNotFoundHttpException;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtService extends BaseService<IJwtRepository, Jwt> {

    @Value("${security.jwt.expiration-in-milliseconds}")
    private int millisecondsToExpireJwt;

    private final IJwtRepository jwtRepository;
    private final JwtBuilder jwtBuilder;
    private final BundleAuthUserDetailsService bundleAuthUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public JwtService(IJwtRepository jwtRepository, JwtBuilder jwtBuilder, BundleAuthUserDetailsService bundleAuthUserDetailsService, AuthenticationManager authenticationManager, ModelMapper modelMapperPresenter) {
        this.jwtRepository = jwtRepository;
        this.jwtBuilder = jwtBuilder;
        this.bundleAuthUserDetailsService = bundleAuthUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public JwtResponseDTO generate(JwtGeneratePostRequestDTO jwtGeneratePostRequestDTO) {
        try {
            Authentication authentication = bundleAuthUserDetailsService.createAuthentication(jwtGeneratePostRequestDTO.getLogin(), jwtGeneratePostRequestDTO.getPassword());
            BundleAuthUserDetails bundleAuthUserDetails = (BundleAuthUserDetails) authenticationManager.authenticate(authentication).getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(bundleAuthUserDetailsService.getAuthentication(bundleAuthUserDetails));
            User user = bundleAuthUserDetails.getUser();
            return modelMapperPresenter.map(save(new Jwt(jwtBuilder.createBearerToken(user), user)), JwtResponseDTO.class);
        } catch (AuthenticationException exception) {
            throw new JwtNotFoundHttpException("Login ou senha do usuário inválido(s).");
        }
    }

    @Transactional
    public void validate(String bearerToken) {
        if (!isJwtActiveByBearerToken(extractBearerToken(bearerToken))) {
            throw new AuthenticationHttpException();
        }
    }

    @Transactional
    public void invalidate(String bearerToken) {
        jwtRepository.delete(Optional.of(jwtRepository.findByBearerToken(extractBearerToken(bearerToken))).orElseThrow(AuthenticationHttpException::new));
        SecurityContextHolder.clearContext();
    }

    private String extractBearerToken(String bearerToken) {
        return ValidationUtil.isNotNull(bearerToken) ? bearerToken.substring("Bearer ".length()) : Strings.EMPTY;
    }

    public Boolean isJwtActiveByBearerToken(String bearerToken) {
        return new JwtIsActiveUseCase(jwtRepository.findByBearerToken(bearerToken), millisecondsToExpireJwt).isActive();
    }

    public void refreshByBearerToken(String bearerToken) {
        save(new JwtRefreshByBearerTokenUserCase(jwtRepository.findByBearerToken(bearerToken), millisecondsToExpireJwt).getBuiltedJwt());
    }

    @Override
    public Jwt findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O JWT com hash id %s não foi encontrado.", hashId));
    }
}