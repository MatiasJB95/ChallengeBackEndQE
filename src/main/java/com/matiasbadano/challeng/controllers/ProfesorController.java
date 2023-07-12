package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.models.Categoria;
import com.matiasbadano.challeng.models.Usuario;
import com.matiasbadano.challeng.repository.AlumnoRepository;
import com.matiasbadano.challeng.repository.InscripcionRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfesorController {
        private final UsuarioRepository usuarioRepository;
        private final InscripcionRepository inscripcionRepository;
        private final AlumnoRepository alumnoRepository;
        private final ProfesorService profesorService;
        @Autowired
        public ProfesorController(UsuarioRepository usuarioRepository, AlumnoRepository alumnoRepository,
                                InscripcionRepository inscripcionRepository, ProfesorService profesorService) {

            this.usuarioRepository = usuarioRepository;
            this.alumnoRepository = alumnoRepository;
            this.inscripcionRepository =inscripcionRepository;
            this.profesorService = profesorService;


        }
    @GetMapping("/profesor")
    public String profesor(Model model, Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);
        String nombre = usuario.getNombre();
        Categoria categoria = profesorService.obtenerCategoriaPorProfesor((long) usuario.getId());
        String nombreCategoria = categoria != null ? categoria.getNombre() : "";

        model.addAttribute("nombreProfesor", nombre);
        model.addAttribute("emailProfesor", email);
        model.addAttribute("nombreCategoria", nombreCategoria);

        return "profesor";
    }
}
