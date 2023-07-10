package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.dto.AlumnoDTO;
import com.matiasbadano.challeng.dto.CategoriaDTO;
import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.dto.ProfesorDTO;
import com.matiasbadano.challeng.models.Categoria;
import com.matiasbadano.challeng.models.Rol;
import com.matiasbadano.challeng.models.Usuario;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.*;
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
import org.thymeleaf.model.IModel;

import java.util.List;

@Controller
public class AdministradorController {
    private UsuarioRepository usuarioRepository;
    private CategoriaService categoriaService;
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
                                   ProfesorService profesorService, AlumnoService alumnoService,
                                   CategoriaService categoriaService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.profesorService = profesorService;
        this.cursoService = cursoService;
        this.alumnoService = alumnoService;
        this.categoriaService = categoriaService;

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

    @GetMapping("/admin/categorias")
    public String obtenerCategorias(Model model) {
        List<CategoriaDTO> categoriasDTO = categoriaService.obtenerNombresCategorias();
        model.addAttribute("categorias", categoriasDTO);
        return "categorias";
    }
    @PostMapping("/admin/categorias")
    public String registrarCategoria(@RequestParam("nombre") String nombre, Model model) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        Categoria savedCategoria = categoriaService.guardarCategoria(categoria);

        model.addAttribute("mensaje", "Categoria Registrada: " + savedCategoria.getNombre());
        return "categorias";
    }

}


