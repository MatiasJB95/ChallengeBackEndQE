package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.config.AlumnoNotFoundException;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.*;
import com.matiasbadano.challeng.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AlumnoController {
    private final UsuarioRepository usuarioRepository;
    private final InscripcionRepository inscripcionRepository;
private final AlumnoRepository alumnoRepository;
    @Autowired
    public AlumnoController(UsuarioRepository usuarioRepository, AlumnoRepository alumnoRepository,
                            InscripcionRepository inscripcionRepository) {

        this.usuarioRepository = usuarioRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository =inscripcionRepository;


    }

    @GetMapping("/alumno")
    public String alumno(Model model, Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);
        String nombre = usuario.getNombre();
        Long usuarioId = Long.valueOf(usuario.getId());

        Alumno alumno = alumnoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));

        List<String> nombresCursos = inscripcionRepository.findCursosByAlumnoId((long) alumno.getId());

        model.addAttribute("nombreUsuario", nombre);
        model.addAttribute("email", email);
        model.addAttribute("nombresCursos", nombresCursos);
        model.addAttribute("alumno", alumno);

        return "alumno";
    }
    }


