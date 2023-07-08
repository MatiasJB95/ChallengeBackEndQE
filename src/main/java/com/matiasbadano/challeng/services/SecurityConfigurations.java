package com.matiasbadano.challeng.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity

public class SecurityConfigurations {
    private final UserDetailsService userDetailsService ;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfigurations(UserDetailsService  userDetailsService ,
                                  AuthenticationEntryPoint authenticationEntryPoint ) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;

    }

    //requestMatchers
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http.csrf().disable()
               .authorizeHttpRequests()
               .requestMatchers("/").permitAll()
               .and()
               .authorizeHttpRequests()
               .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
               .requestMatchers("/dashboard/**").hasAnyRole("ADMINISTRADOR","PROFESOR","USUARIO")
               .anyRequest()
               .authenticated().and().formLogin().and().build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}