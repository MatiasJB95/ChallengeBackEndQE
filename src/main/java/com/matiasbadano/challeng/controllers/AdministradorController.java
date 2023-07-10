package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.dto.AlumnoDTO;
import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.dto.ProfesorDTO;
import com.matiasbadano.challeng.models.Rol;
import com.matiasbadano.challeng.models.Usuario;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.AlumnoService;
import com.matiasbadano.challeng.services.CursoService;
import com.matiasbadano.challeng.services.ProfesorService;
import com.matiasbadano.challeng.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdministradorController {
    private UsuarioRepository usuarioRepository;
    private final ProfesorService profesorService;
    private final CursoService cursoService;

    private final AlumnoService alumnoService;

    private UserDetailsServiceImpl customUserDetailsService;
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdministradorController(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository,
                                   ProfesorRepository profesorRepositor, CursoService cursoService,
                                   ProfesorService profesorService, AlumnoService alumnoService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.profesorService = profesorService;
        this.cursoService = cursoService;
        this.alumnoService = alumnoService;

    }


    @GetMapping("/admin")
    public String admin(Model model, Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);
        String nombre = usuario.getNombre();
        logger.debug("Nombre de usuario obtenido de la autenticación: " + email + " Nombre " + nombre);
        model.addAttribute("nombreUsuario", nombre);
        model.addAttribute("email", email);

        return "admin";
    }


    @PostMapping("/admin/profesores")
    public String crearProfesor(@RequestParam("nombre") String nombre,
                                @RequestParam("email") String email,
                                @RequestParam("contrasena") String contrasena,
                                @RequestParam("categoriaId") int categoriaId, Model model) {
        profesorService.crearProfesor(nombre, email, contrasena, categoriaId);
        model.addAttribute("mensaje", "Profesor creado");
        return "profesores";
    }


    @GetMapping("/admin/profesores")
    public String obtenerProfesores(Model model) {
        List<ProfesorDTO> profesores = profesorService.obtenerTodosLosProfesoresDTO();
        model.addAttribute("profesores", profesores);
        return "profesores";
    }

    @GetMapping("/admin/cursos")
    public String mostrarCursos(Model model) {
        List<CursoDTO> cursosDTO = cursoService.getAllCursos();
        model.addAttribute("cursos", cursosDTO);
        logger.debug("cursos", cursosDTO);
        return "cursos";
    }

    @GetMapping("/admin/alumnos")
    public String getAllAlumnos(Model model) {
        List<AlumnoDTO> alumnosDTO = alumnoService.getAllAlumnos();
        model.addAttribute("alumnos", alumnosDTO);
        return "alumnos";
    }


}


