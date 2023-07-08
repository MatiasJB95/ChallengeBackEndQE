package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.models.Rol;
import com.matiasbadano.challeng.models.Usuario;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministradorController {
    private UsuarioRepository usuarioRepository;
    private UserDetailsServiceImpl customUserDetailsService;
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);



    @GetMapping("/admin")
    public String admin(Model model, HttpSession session) {
            return "admin";
        }

    @PostMapping ("/admin/crearUser")
    public String crearUsuario(@RequestParam("nombre") String nombre, @RequestParam("email") String email, @RequestParam("contrasena") String contrasena, @RequestParam("rol") String rolValue) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContrasena(contrasena);
        Rol rol = Rol.valueOf(rolValue);
        nuevoUsuario.setRol(rol);
        return "redirect:/admin";
    }
}
