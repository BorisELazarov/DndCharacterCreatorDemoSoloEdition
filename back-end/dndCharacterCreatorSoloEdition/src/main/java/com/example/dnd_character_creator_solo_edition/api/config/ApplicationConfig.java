package com.example.dnd_character_creator_solo_edition.api.config;

import com.example.dnd_character_creator_solo_edition.bll.dtos.users.LoginCredentials;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import com.example.dnd_character_creator_solo_edition.dal.repos.UserRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    private final UserRepo userRepo;

    public ApplicationConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public UserDetailsService userDetailsServiceEmail(){
        return username -> {
            User user=userRepo.findByEmail(username)
                    .orElseThrow(() -> new NotFoundException("There is no such user with such email."));

            LoginCredentials login=new LoginCredentials();
            login.setEmail(username);
            login.setPassword(user.getPassword());
            login.setRole(user.getRole().getTitle());

            return login;
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceEmail());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
