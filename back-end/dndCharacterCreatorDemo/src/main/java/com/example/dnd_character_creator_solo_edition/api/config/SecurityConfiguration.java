package com.example.dnd_character_creator_solo_edition.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String dataManagerAuthority="data manager";
        String userAuthority="user";

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry
                                -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers("api/auth/**").permitAll()
                                .requestMatchers("api/classes").hasAnyAuthority(userAuthority,dataManagerAuthority)
                                .requestMatchers("api/proficiencies").hasAnyAuthority(userAuthority,dataManagerAuthority)
                                .requestMatchers("api/spells").hasAnyAuthority(userAuthority,dataManagerAuthority)
                                .requestMatchers("api/characters/**").hasAuthority(userAuthority)
                                .requestMatchers("api/spells/**").hasAuthority(dataManagerAuthority)
                                .requestMatchers("api/classes/**").hasAuthority(dataManagerAuthority)
                                .requestMatchers("api/proficiencies/**").hasAuthority(dataManagerAuthority)
                                .requestMatchers("api/users/changeRole/**").hasAuthority("admin")
                                .requestMatchers("api/users/getAll").hasAuthority("admin")
                                .requestMatchers("api/users/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer
                                ->httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
