package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        logger.debug("Login");
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        logger.info("Logeando");
        logger.info("Usuario: {}", username);
        logger.info("Contraseña: {}", password);

        if (username == null || username.isEmpty()) {
            model.addAttribute("error", "Email Inválido");
            logger.debug("Email inválido");
            return "login";
        }

        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            PasswordEncoder passwordEncoder = passwordEncoder(); // Obtener el PasswordEncoder

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                model.addAttribute("error", "Contraseña incorrecta");
                logger.debug("Contraseña inválida");
                model.addAttribute("username", username);
                return "login";
            }

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"))) {
                logger.info("Usuario autenticado como ADMINISTRADOR:");
                logger.info("Nombre: {}", userDetails.getUsername());
                logger.info("Rol: ADMINISTRADOR");
                return "admin";
            } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PROFESOR"))) {
                logger.info("Usuario autenticado como PROFESOR:");
                logger.info("Nombre: {}", userDetails.getUsername());
                logger.info("Rol: PROFESOR");
                return "profesor";
            } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USUARIO"))) {
                logger.info("Usuario autenticado como USUARIO:");
                logger.info("Nombre: {}", userDetails.getUsername());
                logger.info("Rol: USUARIO");
                return "usuario";
            }

            logger.info("Usuario autenticado con rol desconocido");
            model.addAttribute("error", "Rol desconocido.");
            return "login";

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "Email no encontrado");
            logger.debug("Email no encontrado");
            model.addAttribute("username", username);
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        model.addAttribute("message", "Logout successful.");
        return "redirect:/login";
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}