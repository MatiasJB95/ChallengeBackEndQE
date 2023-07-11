package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.config.AlumnoNotFoundException;
import com.matiasbadano.challeng.dto.AlumnoDTO;
import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.dto.InscripcionDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.*;
import com.matiasbadano.challeng.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AlumnoController {
    private final UsuarioRepository usuarioRepository;
    private final InscripcionRepository inscripcionRepository;
    private final AlumnoService alumnoService;
    private final CursoService cursoService;
    private final AlumnoRepository alumnoRepository;
    private final InscripcionService inscripcionService;

    @Autowired
    public AlumnoController(UsuarioRepository usuarioRepository, AlumnoRepository alumnoRepository,
                            InscripcionRepository inscripcionRepository,
                            AlumnoService alumnoService, CursoService cursoService,
                            InscripcionService inscripcionService
                            ) {

        this.usuarioRepository = usuarioRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.alumnoService = alumnoService;
        this.cursoService = cursoService;
        this.inscripcionService = inscripcionService;

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

    @PostMapping("/alumno")
    public String informacionadicional(Authentication authentication, @ModelAttribute("alumnoActualizado") AlumnoDTO alumnoActualizado) {
        String email = authentication.getName();
        AlumnoDTO alumnoDTO = alumnoService.obtenerAlumnoPorEmail(email);
        alumnoDTO.setNacionalidad(alumnoActualizado.getNacionalidad());
        alumnoDTO.setPaisResidencia(alumnoActualizado.getPaisResidencia());
        alumnoDTO.setEdad(alumnoActualizado.getEdad());
        alumnoDTO.setTelefono(alumnoActualizado.getTelefono());
        alumnoService.actualizarAlumno(alumnoDTO);
        return "redirect:/alumno";
    }

    @GetMapping("/alumno/cursos")
    public String mostrarCursos(Model model) {
        List<CursoDTO> cursosDTO = cursoService.getAllCursos();
        model.addAttribute("cursos", cursosDTO);
        return "inscripcion";
    }

    @PostMapping("/alumno/cursos/{id}")
    public String Inscripcion(@PathVariable("id") Long id, Authentication authentication) {
        String email = authentication.getName();
        AlumnoDTO alumnoDTO = alumnoService.obtenerAlumnoPorEmail(email);


        InscripcionDTO inscripcionDTO = new InscripcionDTO();
        inscripcionDTO.setAlumnoId((long) alumnoDTO.getId());
        inscripcionDTO.setCursoId(id);

        inscripcionService.guardarInscripcion(inscripcionDTO);

    return "redirect:/alumno";
    }

}


