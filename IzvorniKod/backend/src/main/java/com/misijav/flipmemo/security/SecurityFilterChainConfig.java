package com.misijav.flipmemo.security;

import com.misijav.flipmemo.jwt.JWTAuthenticationFilter;
import com.misijav.flipmemo.model.Roles;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                     JWTAuthenticationFilter jwtAuthenticationFilter,
                                     @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
                //.cors(Customizer.withDefaults())
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/login")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/register")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/api/v1/secured/any")).hasAnyRole(Roles.USER.name())
            .requestMatchers(new AntPathRequestMatcher("/api/v1/secured/admin")).hasRole(Roles.ADMIN.name())
            .requestMatchers(new AntPathRequestMatcher("/api/v1/account/**")).authenticated()
            .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).authenticated()
            .anyRequest().permitAll()
        );
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        );
        http.exceptionHandling(configurer -> {
            // Add media type application/json required.
            configurer.authenticationEntryPoint((request, response, authException) ->
                    handlerExceptionResolver.resolveException(request, response, null, authException));
            configurer.accessDeniedHandler((request, response, deniedException) ->
                    handlerExceptionResolver.resolveException(request, response, null, deniedException));
        });

        return http.build();
    }

}