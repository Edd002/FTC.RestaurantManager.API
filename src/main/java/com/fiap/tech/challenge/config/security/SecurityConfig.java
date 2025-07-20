package com.fiap.tech.challenge.config.security;

import com.fiap.tech.challenge.domain.jwt.JwtBuilder;
import com.fiap.tech.challenge.domain.jwt.JwtService;
import com.fiap.tech.challenge.domain.user.authuser.BundleAuthUserDetailsService;
import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
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
import org.springframework.http.HttpMethod;
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

    private static final String[] IGNORE_SECURITY_CONFIG_PATHS = {
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
            "/error"
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
    public PasswordEncoder passwordEncoder() {
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
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .with(new SecurityConfigurer(jwtBuilder, jwtService, bundleAuthUserDetailsService), securityConfigurer -> {})
                .exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, e) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    BaseErrorResponse baseErrorResponse = new BaseErrorResponse401(Collections.singletonList(ValidationUtil.isNotNull(request.getAttribute("jwtError")) ? request.getAttribute("jwtError").toString() : "O usuário não possui permissão para a operação solicitada."));;
                    Gson gson = new GsonBuilder().registerTypeAdapter(BaseErrorResponse.class, new ErrorResponseJsonSerializer()).setDateFormat(DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.getValue()).create();
                    response.getWriter().write(gson.toJson(baseErrorResponse));
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(IGNORE_SECURITY_CONFIG_PATHS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/cities/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/menu-items").hasAuthority(DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/menu-items/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/menu-items").hasAuthority(DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/menu-items").hasAuthority(DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/jwts/generate").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/menus").hasAuthority(DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/restaurants").hasAuthority(DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/restaurants/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/restaurants").hasAuthority(DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/restaurants").hasAuthority(DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/filter").hasAnyAuthority(DefaultUserTypeEnum.ADMIN.name(), DefaultUserTypeEnum.OWNER.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user-types").hasAuthority(DefaultUserTypeEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/user-types/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user-types").hasAuthority(DefaultUserTypeEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user-types").hasAuthority(DefaultUserTypeEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/restaurant-users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/restaurant-users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/restaurant-users").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(true).ignoring().requestMatchers(IGNORE_SECURITY_CONFIG_PATHS);
    }

    private CorsConfigurationSource corsConfigurationSource() {
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