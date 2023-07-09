package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.UserDetailsServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


import java.util.Collection;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private UsuarioRepository usuarioRepository;
    private UserDetailsServiceImpl customUserDetailsService;
    private PasswordEncoder passwordEncoder;

    public HomeController(UsuarioRepository usuarioRepository, UserDetailsServiceImpl customUserDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.customUserDetailsService = customUserDetailsService;

    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"))) {
            return "redirect:/admin";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PROFESOR"))) {
            return "redirect:/profesor";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ALUMNO"))) {
            return "redirect:/alumno";
        }
        
        return "error";
    }

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}