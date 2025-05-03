package com.fiap.tech.challenge.config.security;

import com.fiap.tech.challenge.domain.jwt.JwtBuilder;
import com.fiap.tech.challenge.domain.jwt.JwtService;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;
import com.fiap.tech.challenge.global.base.serializer.ErrorResponseJsonSerializer;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Value("${crypto.key}")
    private String cryptoKey;

    private final JwtBuilder jwtBuilder;
    private final JwtService jwtService;
    private final BundleAuthUserDetailsService bundleAuthUserDetailsService;

    private static final String[] PUBLIC_MATCHERS = {
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-config/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/h2-console/**",
            "/configuration/**",
            "/webjars/**",
            "/actuator/health/**",
            "/api/v1/jwts/generate",

            "/api/v1/users/**", // TODO Remove after config done
            "/api/v1/cities/**" // TODO Remove after config done
    };

    private static final String[] OWNER_MATCHERS = {
            "/api/v1/users/**",
            "/api/v1/cities/**"
    };

    @Autowired
    public SecurityConfig(@Lazy JwtBuilder jwtBuilder, @Lazy JwtService jwtService, @Lazy BundleAuthUserDetailsService bundleAuthUserDetailsService) {
        this.jwtBuilder = jwtBuilder;
        this.jwtService = jwtService;
        this.bundleAuthUserDetailsService = bundleAuthUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        return CryptoUtil.newInstance(cryptoKey);
    }

    @Bean
    public HttpFirewall getHttpFirewall() {
        StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
        strictHttpFirewall.setAllowSemicolon(false);
        return strictHttpFirewall;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configure(httpSecurity))
                .with(new SecurityConfigurer(jwtBuilder, jwtService, bundleAuthUserDetailsService), sc -> sc.configure(httpSecurity))
                .exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, e) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    BaseErrorResponse baseErrorResponse = new BaseErrorResponse401(Collections.singletonList(ValidationUtil.isNotNull(request.getAttribute("jwtError")) ? request.getAttribute("jwtError").toString() : "Cliente não autenticado."));;
                    Gson gson = new GsonBuilder().registerTypeAdapter(BaseErrorResponse.class, new ErrorResponseJsonSerializer()).setDateFormat(DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.getValue()).create();
                    response.getWriter().write(gson.toJson(baseErrorResponse));
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
                        // .requestMatchers(OWNER_MATCHERS).hasAuthority(UserRoleEnum.OWNER.name()) TODO Add after config
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(true).ignoring().requestMatchers(PUBLIC_MATCHERS);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8084"
        ));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}