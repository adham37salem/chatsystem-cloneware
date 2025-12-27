package com.aenumz.whatsapcclone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.support.WebContentGenerator;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Security filter chain bean that configures the security filter chain.
     * The filter chain is configured to allow the following endpoints:
     * <ul>
     * <li>/v3/api-docs</li>
     * <li>/v2/api-docs/**</li>
     * <li>/swagger-ui/**</li>
     * <li>/swagger-ui.html</li>
     * <li>/swagger-resources/**</li>
     * <li>/webjars/**</li>
     * <li>/actuator/**</li>
     * <li>/configuration/ui</li>
     * <li>/configuration/security</li>
     * <li>/ws/**</li>
     * </ul>
     * All other endpoints are secured with OAuth2.0.
     * The JWT authentication converter is set to use the {@link KeycloakJwtAuthenticationConverter}.
     *
     * @return the security filter chain.
     * @throws Exception if an error occurs while building the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                        "/v3/api-docs",
                                        "/v2/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/webjars/**",
                                        "/actuator/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/ws/**"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(
                        auth ->
                                auth.jwt(
                                        token ->
                                                token.jwtAuthenticationConverter
                                                        (new KeycloakJwtAuthenticationConverter()))
                );
        return httpSecurity.build();
    }

    /**
     * A CORS filter that allows cross-origin requests from {@code http://localhost:4200}.
     * The filter allows GET, POST, PUT, PATCH, and OPTIONS requests and allows the following headers:
     * <ul>
     *     <li>{@code HttpHeaders.ORIGIN}</li>
     *     <li>{@code HttpHeaders.CONTENT_TYPE}</li>
     *     <li>{@code HttpHeaders.ACCEPT}</li>
     *     <li>{@code HttpHeaders.AUTHORIZATION}</li>
     * </ul>
     *
     * @param webContentGenerator ignored
     * @return the CORS filter
     */
    @Bean
    public CorsFilter corsFilter(WebContentGenerator webContentGenerator) {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION
        ));
        config.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "OPTIONS"
        ));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
